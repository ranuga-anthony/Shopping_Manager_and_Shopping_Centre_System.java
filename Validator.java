import java.util.Scanner;

public class Validator {
   public static Scanner input = new Scanner(System.in);

   public static String ValidateProductID(){
       System.out.println("Validating Product ID...");
       String ID;
       System.out.print("Enter the product ID : ");
       while(true) {

           ID = input.next();
           if (ID.startsWith("E")) {
               System.out.println("The product ID entered is valid.");
               break;
           } else if (ID.startsWith("C")) {
               System.out.println(" The product ID entered is valid.");
               break;
           } else {
               System.out.println(" The product ID entered is invalid.");
               System.out.print("Please enter a valid product ID : ");
           }
       }
       return ID;
   }
    public static int StringtoInteger(){
        int num = 0;
        while(true) {
            if (input.hasNextInt()) {
                num = input.nextInt();
                break;
            } else {
                System.out.println("Invalid Input. Please enter an integer.");
                System.out.print("Please enter the correct input : ");
                input.next();
            }

        }
        return num;
    }
    public static double StringtoDouble(){
        double num = 0;
        while(true) {
            if (input.hasNextDouble()) {
                num = input.nextDouble();
                break;
            } else {
                System.out.println("Invalid Input. Please enter a valid double or floating number as double.");
                System.out.print("Please enter the correct input : ");
                input.nextLine();
            }

        }
        return num;
    }
}
