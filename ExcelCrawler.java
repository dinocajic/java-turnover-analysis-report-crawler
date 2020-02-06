import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExcelCrawler {

    public void start(String[] locations, String[] locations_names, String group, String output_folder, String[] brands_to_include) {
        List<Item> items = new ArrayList<>();

        int mainRowStart = 0;
        int mainRowStop  = 0;

        // For each brand to run the report on
        //     Loop through each location
        //         Open File for that location
        //         Select the First Sheet in the Workbook
        //         Loop: Start from the first row and go to the end (to get the first and last row for the brand
        //             If a row matches Grp: 1, we save the Starting and ending rows for that brand
        //         Add items for that range to the items list
        for ( String brand : brands_to_include ) {

            for ( int k = 0; k < locations.length; k++ ) {
                try( Workbook wb = WorkbookFactory.create(
                        new File( locations[k] ))
                ) {
                    Sheet sheet = wb.getSheetAt(0);

                    int rowStart = 0;
                    int rowEnd   = sheet.getLastRowNum();

                    for( int i = rowStart; i < rowEnd; i++ ) {
                        Row row = sheet.getRow(i);

                        try {
                            if ( row.getCell(0).getStringCellValue().startsWith( group ) ) {

                                if ( row.getCell(0).getStringCellValue().contains( brand ) && mainRowStart == 0 ) {
                                    mainRowStart = i;
                                } else if ( !row.getCell(0).getStringCellValue().contains( brand ) && mainRowStart != 0 ) {
                                    mainRowStop = i;
                                    break;
                                }
                            }
                        } catch (NullPointerException ignored) { }
                    }

                    createItems(items, locations_names[k], mainRowStart, mainRowStop, sheet);

                    mainRowStart = 0;
                    mainRowStop = 0;

                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }

            Collections.sort( items );
            generateExcelFile( items, brand, output_folder );

            items = new ArrayList<>();
        }
    }

    private static void createItems(List<Item> items, String location, int rowStart, int rowEnd, Sheet sheet ) {

        for( int i = rowStart - 1; i < rowEnd; i++ ) {
            Row row = sheet.getRow(i);

            // If blank values: throws NullPointerException
            // Only get rows that have EA in column 22
            try {
                if ( ! row.getCell(22).getStringCellValue().contains("EA") )
                    continue;
            } catch (NullPointerException e) { continue; }

            // Get item number. Certain item numbers are completely numeric
            // so we need to test for that.
            String itemNumber  = getStringValue( row, 0 );

            String description = getStringValue( row, 3 ) +
                                 getStringValue( row, 4 ) +
                                 getStringValue( row, 5 ) +
                                 getStringValue( row, 6 );

            description = description.replace("\n", "")
                                     .replace("\r", "");

            int on_hand_qty     = (int) row.getCell(7).getNumericCellValue();
            int committed_qty   = (int) row.getCell(9).getNumericCellValue();
            int available_qty   = (int) row.getCell(10).getNumericCellValue();
            int yr_to_date_sold = (int) row.getCell(13).getNumericCellValue();
            int last_year_sold  = (int) row.getCell(14).getNumericCellValue();

            SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy");
            String last_sale_date = "";

            try { last_sale_date = sdf.format( row.getCell(15).getDateCellValue() ); }
            catch ( NullPointerException ignored){}

            String last_receipt = "";

            try { last_receipt = sdf.format( row.getCell(16).getDateCellValue() ); }
            catch ( NullPointerException ignored) {}

            double wavg_cost     = row.getCell(18).getNumericCellValue();
            double level_0_price = row.getCell(20).getNumericCellValue();

            // Add items to sorted location
            items.add(
                    new Item(location, itemNumber, description, last_sale_date, last_receipt, on_hand_qty, committed_qty,
                            available_qty, yr_to_date_sold, last_year_sold, wavg_cost, level_0_price)
            );
        }
    }

    /**
     * Returns a String representation of the cell since a cell could be numeric or a string
     * @param row
     * @param column
     * @return
     */
    private static String getStringValue( Row row, int column ) {
        String value;

        if (row.getCell( column ).getCellType().equals( CellType.STRING )) {
            value = row.getCell( column ).getStringCellValue();
        } else {
            Object item = row.getCell( column ).getNumericCellValue();
            value = new BigDecimal( item.toString() ).toPlainString();
        }

        return value;
    }

    private static void generateExcelFile( List<Item> items, String brand, String output_folder ) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet       = workbook.createSheet("Report");
        sheet.getFooter().setCenter( "Page: &P of &N" );

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        sheet.getHeader().setCenter( "Turnover Analysis Report: " + brand + " (" + dateFormat.format( date ) + ")" );

        brand = brand.replace("\n", "")
                .replace("\r", "")
                .replace("/", "_")
                .replace(" ", "_");

        Object[][] excelData = new Object[ items.size() + 1 ][12];

        excelData[0] = new Object[]{ "Location", "Item Number", "Description", "On Hand Qty", "Commit", "Available",
                "YrToDate\nSold", "LastYr\nSold", "Last Sale Date", "Last Receipt", "WAVG Cost", "Level-0 Price"
        };

        for ( int i = 1; i < items.size(); i++ ) {
            excelData[i] = new Object[]{
                    items.get( i - 1 ).getLocation(),
                    items.get( i - 1 ).getItemNumber(),
                    items.get( i - 1 ).getDescription(),
                    items.get( i - 1 ).getOn_hand_qty(),
                    items.get( i - 1 ).getCommited_qty(),
                    items.get( i - 1 ).getAvailable_qty(),
                    items.get( i - 1 ).getYr_to_date_sold(),
                    items.get( i - 1 ).getLast_year_sold(),
                    items.get( i - 1 ).getLast_sale_date(),
                    items.get( i - 1 ).getLast_receipt(),
                    items.get( i - 1 ).getWavg_cost(),
                    items.get( i - 1 ).getLevel_0_price()
            };
        }

        // Set Excel styles
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom( BorderStyle.THIN );
        style.setBorderLeft( BorderStyle.THIN );
        style.setBorderRight( BorderStyle.THIN );
        style.setBorderTop( BorderStyle.THIN );
        style.setAlignment( HorizontalAlignment.CENTER );

        int rowCount = 0;

        for (Object[] excel_row : excelData) {
            Row row = sheet.createRow( rowCount++ );

            int columnCount = 0;

            for (Object field : excel_row) {
                Cell cell = row.createCell( columnCount++ );

                cell.setCellStyle( style );

                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(
                 output_folder +
                        "/TurnOverAnalysis_" +
                        brand + "_" +
                        System.currentTimeMillis() +
                        ".xlsx")
        ) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
