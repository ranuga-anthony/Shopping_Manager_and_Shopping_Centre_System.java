import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.ArrayList;

public class ShoppingCart extends JFrame {
    private ArrayList<String> listOfProducts = new ArrayList<>();
    private ArrayList<User> UserList = new ArrayList<>();
    public JTable cartTable;
    public JTextField Total;
    public JTextField firstPurchaseDiscountField;
    public JTextField categoryDiscountField;
    public JTextField FinalTotalField;

    private String[][] data = new String[0][3];
    private static User user;

    private String[] columnNames = {"Product", "Quantity", "Price (£)"};

    public ShoppingCart(){

    }
    public ShoppingCart(String title) {
        super(title);
        //setVisible(true);
        setSize(900, 800);

        //JPanel panel1 = new JPanel(new GridLayout(5, 0, 30, 30));
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel cartTablePanel = new JPanel();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        cartTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        cartTablePanel.add(scrollPane);
        cartTable.setGridColor(Color.BLACK);

        //cartTablePanel.add(cartTable);


        JPanel cartPriceDetails = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel TtlPrice = new JLabel(" Total");
        //double total = calculateTotal();

        Total = new JTextField();
        Total.setEditable(false);
        JLabel FirstDiscount = new JLabel("First Purchase Discount (10%)");
        firstPurchaseDiscountField = new JTextField();
        firstPurchaseDiscountField.setEditable(false);

        JLabel CategoryDiscount = new JLabel("Three items in same category discount (20%)");
        categoryDiscountField = new JTextField();
        categoryDiscountField.setEditable(false);

        JLabel FinalTotal = new JLabel("Final Total");
        FinalTotalField = new JTextField();
        FinalTotalField.setEditable(false);

        JButton ConfirmBtn = new JButton("Confirm Cart");

        JPanel TotalPanel = new JPanel(new FlowLayout());
        TotalPanel.add(TtlPrice);
        TotalPanel.add(Total);


        JPanel FirstDiscountPanel = new JPanel(new FlowLayout());
        FirstDiscountPanel.add(FirstDiscount);
        FirstDiscountPanel.add(firstPurchaseDiscountField);

        JPanel CategoryDiscountPanel = new JPanel(new FlowLayout());
        CategoryDiscountPanel.add(CategoryDiscount);
        CategoryDiscountPanel.add(categoryDiscountField);

        JPanel FinalTotalPanel = new JPanel(new FlowLayout());
        FinalTotalPanel.add(FinalTotal);
        FinalTotalPanel.add(FinalTotalField);

        JPanel ConfirmOrderPanel = new JPanel(new FlowLayout());
        ConfirmOrderPanel.add(ConfirmBtn);


        cartPriceDetails.add(TotalPanel);
        cartPriceDetails.add(FirstDiscountPanel);
        cartPriceDetails.add(CategoryDiscountPanel);
        cartPriceDetails.add(FinalTotalPanel);
        cartPriceDetails.add(ConfirmOrderPanel);

        panel1.add(cartTablePanel, BorderLayout.CENTER);
        panel1.add(cartPriceDetails, BorderLayout.SOUTH);

        //EventHandler handler = new EventHandler();
        //ConfirmBtn.addActionListener(handler);

        ConfirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNo_ofPurchases();
            }
        });

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    updateTotal();
                    updateFirstDiscount();
                    updateCategoryDiscount();
                    calculateFinalTotal();
                }

            }
        });

        add(panel1);
        setVisible(true);

    }

    public void addProductToCart(String productName, int quantity, double price) {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.addRow(new Object[]{productName, quantity, price});
        updateTotal();
        updateFirstDiscount();
        updateCategoryDiscount();
        calculateFinalTotal();

    }

    public double calculateTotal() {
        double cartPrice = 0;
        for (int j = 0; j < cartTable.getRowCount(); j++) {
            cartPrice += (double) cartTable.getValueAt(j, 2);
        }
        return cartPrice;
    }
    public void updateTotal(){
        double total = calculateTotal();
        Total.setText(String.format("%.2f", total));
    }

    public double calculateFirstDiscount(double price){
        double firstdiscount = 0;
        boolean FirstPurchase = checkNoOfPurchases(this.user);
        if (FirstPurchase){
            firstdiscount = price * 0.1;
        }
        return firstdiscount;
    }
    public void updateFirstDiscount(){
        double total = calculateTotal();
        double firstdiscount = calculateFirstDiscount(total);
        firstPurchaseDiscountField.setText(String.format("%.2f", firstdiscount));

    }
    public double calculatecategoryDiscount(double price){
        double categoryDiscount = 0;
        boolean eligible  = VerifyEligbilityCategoryDiscount();
        if(eligible){
            categoryDiscount = price * 0.2;
        }
        return categoryDiscount;
    }
    public void updateCategoryDiscount(){
        double total = calculateTotal();
        double categoryDiscount = calculatecategoryDiscount(total);
        categoryDiscountField.setText(String.format("%.2f",categoryDiscount));

    }
    public void calculateFinalTotal(){
        double total = calculateTotal();
        double FirstPurchaseDiscount = calculateFirstDiscount(total);
        double categoryDiscount = calculatecategoryDiscount(total);
        double finaltotal = total-(FirstPurchaseDiscount + categoryDiscount);
        FinalTotalField.setText(String.format("%.2f",finaltotal));
    }
    public boolean checkNoOfPurchases(User user){
        boolean firstpurchase = true;
        if (user != null && user.getNo_of_purchases() > 0) {
            firstpurchase = false;
        }

        return firstpurchase;
    }
    public static void SetUser(User customer){
        if (user == null) {
            user = customer;
            User.list_of_users.add(customer);
        }
    }
    public void updateNo_ofPurchases() {
        if (user!= null) {
            user.setNo_of_purchases();
        }
    }
    public boolean VerifyEligbilityCategoryDiscount(){
        boolean discounteligible = false;
        int clothingno = 0;
        int electronicno = 0;
        for(int i = 0; i<cartTable.getRowCount();i++) {
            String Product = (String)cartTable.getValueAt(i,0);
            String [] details = Product.split(" ");
            String ProductID = details[0].trim();
            if(ProductID.contains("E")){
                electronicno++;
            } else if (ProductID.contains("C")) {
                clothingno++;
            }
        }
        if((clothingno >=3 ) || (electronicno>=3)){
            discounteligible = true;
        }
        return discounteligible;
    }


}


