/**
 * @author Dino Cajic
 */
public class Item implements Comparable<Item> {

    private String location, itemNumber, description, last_sale_date, last_receipt;
    private int on_hand_qty, commited_qty, available_qty, yr_to_date_sold, last_year_sold, location_id;
    private double wavg_cost, level_0_price;

    Item(String location, String itemNumber, String description, String last_sale_date, String last_receipt,
         int on_hand_qty, int commited_qty, int available_qty, int yr_to_date_sold, int last_year_sold,
         double wavg_cost, double level_0_price)
    {
        this.setLocation(location);
        this.setItemNumber(itemNumber);
        this.setDescription(description);
        this.setLast_sale_date(last_sale_date);
        this.setLast_receipt(last_receipt);
        this.setOn_hand_qty(on_hand_qty);
        this.setCommited_qty(commited_qty);
        this.setAvailable_qty(available_qty);
        this.setYr_to_date_sold(yr_to_date_sold);
        this.setLast_year_sold(last_year_sold);
        this.setWavg_cost(wavg_cost);
        this.setLevel_0_price(level_0_price);
    }

    public String getLocation() { return location; }

    public String getItemNumber() { return itemNumber; }

    public String getDescription() { return description; }

    public String getLast_sale_date() { return last_sale_date; }

    public String getLast_receipt() { return last_receipt; }

    public int getOn_hand_qty() { return on_hand_qty; }

    public int getCommited_qty() { return commited_qty; }

    public int getAvailable_qty() { return available_qty; }

    public int getYr_to_date_sold() { return yr_to_date_sold; }

    public int getLast_year_sold() { return last_year_sold; }

    public String getWavg_cost() {
        return "$" + String.format("%.2f", wavg_cost);
    }

    public String getLevel_0_price() {
        return "$" + String.format("%.2f", level_0_price);
    }

    public int getLocation_id() { return location_id; }

    public void setAvailable_qty(int available_qty) {
        this.available_qty = available_qty;
    }

    public void setCommited_qty(int commited_qty) {
        this.commited_qty = commited_qty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setLast_receipt(String last_receipt) {
        this.last_receipt = last_receipt;
    }

    public void setLast_sale_date(String last_sale_date) {
        this.last_sale_date = last_sale_date;
    }

    public void setLast_year_sold(int last_year_sold) {
        this.last_year_sold = last_year_sold;
    }

    public void setYr_to_date_sold(int yr_to_date_sold) { this.yr_to_date_sold = yr_to_date_sold; }

    public void setLocation(String location) {
        this.location = location;

        switch( location ) {
            case "ATL":  this.location_id = 1; break;
            case "CHAR": this.location_id = 4; break;
            case "NO":   this.location_id = 3; break;
        }
    }

    public void setOn_hand_qty(int on_hand_qty) {
        this.on_hand_qty = on_hand_qty;
    }

    public void setLevel_0_price(double level_0_price) {
        this.level_0_price = level_0_price;
    }

    public void setWavg_cost(double wavg_cost) {
        this.wavg_cost = wavg_cost;
    }

    @Override
    public String toString() {
        return  this.getLocation() + "~" +
                this.getItemNumber()  + "~" +
                this.getDescription() + "~" +
                this.getOn_hand_qty() + "~" +
                this.getCommited_qty() + "~" +
                this.getAvailable_qty() + "~" +
                this.getYr_to_date_sold() + "~" +
                this.getLast_year_sold() + "~" +
                this.getLast_sale_date() + "~" +
                this.getLast_receipt() + "~" +
                this.getWavg_cost() + "~" +
                this.getLevel_0_price();
    }

    private String getCompareToString() {
        return this.getItemNumber() + "-" + this.getLocation_id();
    }

    @Override
    public int compareTo(Item newItem) {
        return this.getCompareToString().compareTo( newItem.getCompareToString() );
    }
}
