public class Clothing extends Product{
    private String size;
    private String colour;
    private static String category = "Clothing";

    public Clothing(String productID, String productName, int numberofavailableitems, double price, String size, String colour){
        super(productID,productName,numberofavailableitems,price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize(){
        return this.size;
    }
    public void setSize(String size){
        this.size = size;
    }

    public String getColour(){
        return this.colour;
    }
    public void setColour(){
        this.colour = colour;
    }

    @Override
    public String toString(){
        return "Category : Clothing, ID : " + super.getProductID() + " , Name : " + super.getProductName() + " , No of available items : "+super.getNumberofavailableitems()+ " , Size : " + this.size + ", Price : " + super.getPrice() + ", Colour : " +this.colour;
    }

    @Override
    public String getCategory(){
        return category;
    } // returns category of the product

    @Override
    public String getInfo(){
        return (this.size + " , " + this.colour);
    }
}
