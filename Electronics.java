public class Electronics extends Product{

   private String brand;
   private static String category = "Electronics";
    private int warranty;

    public Electronics(String productID, String productName, int numberofavailableitems, double price, String brand, int warranty){
        super(productID,productName,numberofavailableitems,price);
        this.brand = brand;
        this.warranty = warranty;
    }


    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }
    public int getWarranty() {
        return this.warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public String toString(){
        return "Category : Electronics , ID : " + super.getProductID() + " , Name : " + super.getProductName() + " , No of available items : "+super.getNumberofavailableitems()+ " , Brand : " + this.brand + ", Price : " + super.getPrice() + ", warranty (years) : " +this.warranty;
    }

    @Override
    public String getCategory(){
        return category;
    } // returns category of the product

    @Override
    public String getInfo(){
        return(this.brand + " , " + this.warranty + " year warranty");
    }



}
