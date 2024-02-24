import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;
//import java.util.Queue;
import java.util.Map;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager{
    public static HashMap <String,Product> product_list = new HashMap<>(); // stores the product details entered by the manager

    public static void main(String [] args){
        while(true){
            DisplayMenu();
            Scanner input = new Scanner(System.in);
            System.out.print("Enter option :");
            if(input.hasNextInt()){
                int option = input.nextInt();
                if(option == 0){
                    System.out.println("Exiting the Westminster Shopping Manager. Have a good day!");
                    break;
                }
                switch(option){
                    case 1 :
                        AddProduct();
                        break;
                    case 2 :
                        DeleteProduct();
                        break;
                    case 3 :
                        PrintListOfProducts();
                        break;
                    case 4 :
                        SaveInfo();
                        break;
                    case 5 :
                        ReadInfo(product_list);
                        break;
                    case 6 :
                        loadGUI();
                        break;

                    default :
                        System.out.println("Invalid Input. Please enter a number between 1 and 4");
                        break;
                }

            }else{
                System.out.println("Invalid Input. Enter a valid integer.");
            }
        }




    }
    public static void DisplayMenu(){
        System.out.println("----------------------------------------");
        System.out.println("Welcome to Westminster Shopping Manager");
        System.out.println();
        System.out.println("Console Menu ");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save info in a file");
        System.out.println("5. Read info in a file");
        System.out.println("6. Open Westminster Shopping Centre");
        System.out.println("0. Exit Westminster Shopping Manager");
        System.out.println("----------------------------------------");
    }

    public static void AddProduct(){
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("---------------------------------");
            System.out.println("Product Categories.");
            System.out.println("1. Electronics ");
            System.out.println("2. Clothing ");
            System.out.println("---------------------------------");
            System.out.print("Enter the product category : ");
            if (input.hasNextInt()) {
                int prod_category = input.nextInt();

                if (prod_category == 1) {
                    String id = Validator.ValidateProductID();
                    System.out.print("Enter the product name : ");
                    String name = input.next();
                    System.out.print("Enter the number of available items in the product  : ");
                    int product_items_number = Validator.StringtoInteger();
                    System.out.print("Enter the product price : ");
                    double price = Validator.StringtoDouble();
                    System.out.print("Enter the product brand : ");
                    String brand = input.next();
                    System.out.print("Enter the product warranty period : ");
                    int warranty = Validator.StringtoInteger();
                    Electronics electronic_product = new Electronics(id, name, product_items_number, price, brand, warranty); //  adding an electronic product to the system
                    product_list.put(id,electronic_product);
                    break;

                } else if (prod_category == 2) {
                    String id = Validator.ValidateProductID();
                    System.out.print("Enter the product name : ");
                    String name = input.next();
                    System.out.print("Enter the number of available items in the product  : ");
                    int product_items_number = Validator.StringtoInteger();
                    System.out.print("Enter the product price : ");
                    double price = Validator.StringtoDouble();
                    System.out.print("Enter the product size between Small (S) , Medium (M), Large(L): ");
                    String size = input.next();
                    System.out.print("Enter the product colour : ");
                    String colour = input.next();
                    Clothing clothing_product = new Clothing(id, name, product_items_number, price, size, colour); // adding a clothing product to the system
                    product_list.put(id,clothing_product);
                    break;

                } else {
                    System.out.println("Invalid option. Please select either option 1 or option 2.");
                }


            } else {
                System.out.println("Invalid input. Enter a valid integer as product category.");
                input.nextLine();
            }
        }




    }
    public static void DeleteProduct(){
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the product id of the product that you want to remove :");
        String id = input.next().toUpperCase();


        boolean ExistingID = product_list.containsKey(id);


        if(ExistingID){
            product_list.remove(id);
            System.out.println("The product with product ID : " + id + " is deleted from the list.");

        }else{
            System.out.println("The product with product ID : " + id + " is not present in the list." );
        }
    }

    public static void PrintListOfProducts(){
        for(Product i : product_list.values()){ // iterate through the product list to display the product information
            System.out.println(i.toString());
        }
    }

    public static void SaveInfo() {
        try {
            File myFile = new File("list of products.txt");
            boolean file_created = myFile.createNewFile();
            if (file_created) {
                System.out.println("The file " + myFile + " was successfully created.");
            } else if (myFile.exists()) {
                System.out.println("The file " + myFile + " already exists.");
            } else {
                System.out.println("Error while creating the file.");
            }
            BufferedWriter bf; // https://www.javacodeexamples.com/write-hashmap-to-text-file-in-java-example/2353
            bf = new BufferedWriter(new FileWriter(myFile)); //https://www.javacodeexamples.com/write-hashmap-to-text-file-in-java-example/2353

            for(Product i : product_list.values()){
                bf.write(i.toString());
                bf.write("\n");
            }
            bf.flush(); // https://www.javacodeexamples.com/write-hashmap-to-text-file-in-java-example/2353
            bf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ReadInfo(HashMap <String,Product> product_list){
        BufferedReader br = null;
        try {
            File file = new File("list of products.txt");
            br = new BufferedReader(new FileReader(file));
            String Line;

            while((Line = br.readLine()) != null){
                String [] details = Line.split(", | :"); // to split the line into an array of strings using ", | :"
                if(details[1].trim().equals("Electronics")){ // to identify the product as an electronic product
                    Electronics electronic_product = new Electronics(details[3].trim(),details[5].trim(),Integer.parseInt(details[7].trim()),Double.parseDouble(details[11].trim()),details[9].trim(),Integer.parseInt(details[13].trim())); //to save the information stored in the file in an object using trim method
                    product_list.put(details[3],electronic_product);
                } else if (details[1].trim().equals("Clothing") ) {// to identify the product as an electronic product
                    Clothing clothing_product = new Clothing(details[3].trim(),details[5].trim(),Integer.parseInt(details[7].trim()),Double.parseDouble(details[11].trim()),details[9].trim(),details[13].trim());
                    product_list.put(details[3],clothing_product);

                }

            }
            System.out.println("All items are saved in the system successfully.");
        }catch (Exception e){
            e. printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void loadGUI(){
        User customer = verifyUser();
        User.addUsertoList(customer);
        WestminsterShoppingCentre sc1 = new WestminsterShoppingCentre("Westminster Shopping Centre",customer);
    }

    public static User verifyUser(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Customer's name : ");
        String name = input.next();
        System.out.print("Enter unique user name : ");
        String username = input.next();
        return new User(name,username);
    }


}
