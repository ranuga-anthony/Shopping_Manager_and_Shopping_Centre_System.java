public abstract class Product {
    private String productID;
    private String productName;
    private int numberofavailableitems;
    private double price;

    public Product(String productID, String productName, int numberofavailableitems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numberofavailableitems = numberofavailableitems;
        this.price = price;
    }

    public String getProductID() {
        return this.productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberofavailableitems() {
        return this.numberofavailableitems;
    }

    public void setNumberofavailableitems(int numberofavailableitems) {
        this.numberofavailableitems = numberofavailableitems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getCategory(){
        return "";
    }
    public String getInfo(){
        return "";
    }
}

