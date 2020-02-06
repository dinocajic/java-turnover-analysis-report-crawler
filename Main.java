public class Main {

    public static void main(String[] args) {
        ExcelCrawler excelCrawler = new ExcelCrawler();
        runWheels(excelCrawler);
//        runTires(excelCrawler);
//        runAccessories(excelCrawler);
//        runPartial(excelCrawler);

        System.out.println("Finished");
    }

    private static void runWheels( ExcelCrawler excelCrawler ) {
        String assets_folder = System.getProperty("user.dir") + "/";

        String atlanta     = assets_folder + "atlanta_wheels.xls";
        String charlotte   = assets_folder + "charlotte_wheels.xls";
        String new_orleans = assets_folder + "new_orleans_wheels.xls";

        String[] locations = {atlanta, charlotte, new_orleans};
        String[] locations_names = {"ATL", "CHAR", "NO"};

        String group = "Grp: 1";
        String output_folder = "wheels";

        String[] brands_to_include = {
                "AMERICAN TRUX",
                "AVENUE",
                "BBY OFFROAD",
                "BBY WHEEL",
                "BORGHINI",
                "DCENTI WHEELS",
                "DICK CEPEK WHEELS",
                "FORTE WHEELS",
                "HARD ROCK",
                "HAVOK",
                "HOSTILE",
                "HOYO WHEEL",
                "MKW",
                "MT WHEELS",
                "NS",
                "OE REPLICA",
                "PHINO WHEELS",
                "PRIMAX",
                "RED DIRT ROAD",
                "RED SPORT",
                "REV",
                "SCORPION OFF ROAD",
                "SEVIZIA",
                "SPECIAL ORDERS",
                "U2 WHEELS",
                "VCT WHEELS",
                "VELOCITY WHEEL",
                "VERSANTE",
                "XIX WHEELS",
        };

        excelCrawler.start(locations, locations_names, group, output_folder, brands_to_include);
    }

    private static void runTires( ExcelCrawler excelCrawler ) {
        String assets_folder = System.getProperty("user.dir") + "/";

        String atlanta     = assets_folder + "atlanta_tires.xls";
        String charlotte   = assets_folder + "charlotte_tires.xls";
        String new_orleans = assets_folder + "new_orleans_tires.xls";

        String[] locations = {atlanta, charlotte, new_orleans};
        String[] locations_names = {"ATL", "CHAR", "NO"};

        String group = "Grp: 2";
        String output_folder = "tires";

        String[] brands_to_include = {
                "DICK CEPEK TIRES",
                "MT",
                "MT/BLEMS",
                "MISC TIRES",
                "SPECIAL ORDERS",
                "TOYO TIRES"
        };

        excelCrawler.start(locations, locations_names, group, output_folder, brands_to_include);
    }

    private static void runAccessories( ExcelCrawler excelCrawler ) {
        String assets_folder = System.getProperty("user.dir") + "/";

        String atlanta     = assets_folder + "atlanta_accessories.xls";
        String charlotte   = assets_folder + "charlotte_accessories.xls";
        String new_orleans = assets_folder + "new_orleans_accessories.xls";

        String[] locations = {atlanta, charlotte, new_orleans};
        String[] locations_names = {"ATL", "CHAR", "NO"};

        String group = "Grp: 3";
        String output_folder = "accessories";

        String[] brands_to_include = {
                "REV/KO",
                "FORZA/VERSANTE",
                "VELOCITY ACCESSORIES",
                "MT/DC ACCESSORIES",
                "SUSPENSION KITS/ROUGH COUNTRY",
                "MKW",
                "LOCKS",
                "HUBRINGS",
                "LUGS",
                "CAPS",
                "SENSORS",
                "MISC",
                "MISC PARTS",
                "SPECIAL ORDERS"
        };
    }
}
