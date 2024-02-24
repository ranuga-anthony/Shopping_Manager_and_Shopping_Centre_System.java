import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class WestminsterShoppingCentre extends JFrame {

    private JButton CartBtn;
    private JButton ShoppingCartBtn;
    private JTable prodInfoTable;
    private ShoppingCart shopcart;
    private JPanel infopanel;

    private String displayText;
    private JComboBox jcboProductCategory;
    private String selectedCategory = "All";
    public static HashMap<String, Product> product_data = new HashMap<>();
    private Map<String, Integer> categoryCounts = new HashMap<>();

    public WestminsterShoppingCentre(String title, User user) throws HeadlessException {
        super(title);
        frameInit();
        ShoppingCart.SetUser(user);

        WestminsterShoppingManager.ReadInfo(product_data);
        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        String[][] data = new String[(product_data.size())][5];

        int i = 0;
        for (Map.Entry<String, Product> entry : product_data.entrySet()) {
            Product product = entry.getValue();
            data[i][0] = product.getProductID();
            data[i][1] = product.getProductName();
            data[i][2] = product.getCategory();
            data[i][3] = Double.toString(product.getPrice());
            data[i][4] = product.getInfo();
            i++;

        }

        JPanel SelectPanel = new JPanel();
        SelectPanel.setLayout(new FlowLayout());

        // constructing the label with text "Select Product Category"
        JLabel jlblName = new JLabel("Select Product Category : ");
        SelectPanel.add(jlblName);


        // constructing the combo box with choices All,Electronics,Clothing
        jcboProductCategory = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        SelectPanel.add(jcboProductCategory);

        // constructing shopping cart btn
        CartBtn = new JButton("Shopping Cart");
        SelectPanel.add(CartBtn);

        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(new FlowLayout());

        // constructing the product info table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        prodInfoTable = new JTable(model);
        TablePanel.add(prodInfoTable);

        // container for a table
        JScrollPane scrollPane = new JScrollPane(prodInfoTable);
        TablePanel.add(scrollPane, BorderLayout.CENTER);
        prodInfoTable.setGridColor(Color.BLACK);

        // panel for the product details
        infopanel = new JPanel(new BorderLayout(0, 25));
        JLabel SelectedProdLbl = new JLabel("Selected Product - Details");
        ShoppingCartBtn = new JButton("Add to Shopping Cart ");
        JLabel text = new JLabel("NBGJDLBLDGB");
        infopanel.add(SelectedProdLbl, BorderLayout.NORTH);
        infopanel.add(text, BorderLayout.CENTER);
        infopanel.add(ShoppingCartBtn, BorderLayout.SOUTH);
        //infopanel.remove(text);
        //text = new JLabel(displayText);
        //infopanel.add(text, BorderLayout.CENTER);
        //infopanel.revalidate();
        //infopanel.repaint();


        EventHandler handler = new EventHandler();
        CartBtn.addActionListener(handler);
        ShoppingCartBtn.addActionListener(handler);
        jcboProductCategory.addActionListener(handler);

        prodInfoTable.getSelectionModel().addListSelectionListener(handler);
        prodInfoTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //String category = (String) table.getValueAt(row, 2);
                //int Category = Integer.parseInt(category);

                String productID = (String) table.getValueAt(row, 0);
                Product product = product_data.get(productID);
                Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (product != null) {
                    int noofavailableItems = product.getNumberofavailableitems();

                    if (noofavailableItems < 3) {
                        renderer.setBackground(Color.RED);
                    } else {
                        renderer.setBackground(Color.WHITE);

                    }
                }

                return renderer;
            }
        });
        // visualise the table and the frame
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(TablePanel);
        add(SelectPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(infopanel, BorderLayout.SOUTH);
        setSize(900, 800);
        setVisible(true);
    }

    private class EventHandler implements ActionListener, ListSelectionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == CartBtn) {
                if (shopcart == null) {
                    shopcart = new ShoppingCart("Shopping Cart");
                } else {
                    shopcart.setVisible(true);
                }
            } else if (event.getSource() == ShoppingCartBtn) {
                int SelectedRow = prodInfoTable.getSelectedRow();
                System.out.println(SelectedRow);
                if (SelectedRow != -1) {
                    String productID = (String) prodInfoTable.getValueAt(SelectedRow, 0);  // https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/JTable.html#getValueAt(int,%20int)
                    String productName = (String) prodInfoTable.getValueAt(SelectedRow, 1);
                    String productdetails = (String) prodInfoTable.getValueAt(SelectedRow, 4);
                    String priceString = (String) prodInfoTable.getValueAt(SelectedRow, 3);
                    double price = Double.parseDouble(priceString);
                    int quantity = 0;
                    double totalprice = 0;

                    String category = (String) prodInfoTable.getValueAt(SelectedRow, 2);
                    categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);

                    String productInfo = String.format("%s \n %s \n %s", productID, productName, productdetails);
                    if (shopcart == null) {
                        shopcart = new ShoppingCart("Shopping Cart");
                    }

                    boolean productExisting = false;
                    for (int i = 0; i < shopcart.cartTable.getRowCount(); i++) {
                        if (productInfo.equals(shopcart.cartTable.getValueAt(i, 0))) {
                            quantity = (Integer) shopcart.cartTable.getValueAt(i, 1) + 1;
                            totalprice = price * quantity;
                            shopcart.cartTable.setValueAt(quantity, i, 1); // Update quantity of a product in the cart
                            shopcart.cartTable.setValueAt(totalprice, i, 2); // update the total price of a product in the cart
                            productExisting = true;
                            break;
                        }
                    }
                    if (!productExisting) {
                        quantity = 1;
                        totalprice = price * quantity;
                        shopcart.addProductToCart(productInfo, quantity, totalprice);
                        shopcart.setVisible(true);
                    }
                }
            } else if (event.getSource() == jcboProductCategory) {
                selectedCategory = (String) jcboProductCategory.getSelectedItem();
                sortProductTable();
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = prodInfoTable.getSelectedRow();
                updateInfoPanel(selectedRow);
            }
        }

        public void sortProductTable() {
            DefaultTableModel table = (DefaultTableModel) prodInfoTable.getModel();
            table.setRowCount(0);  // Clear the existing rows in the JTable

            for (Map.Entry<String, Product> entry : product_data.entrySet()) {
                Product product = entry.getValue();
                if ("All".equals(selectedCategory) || product.getCategory().equals(selectedCategory)) {
                    table.addRow(new Object[]{
                            product.getProductID(),
                            product.getProductName(),
                            product.getCategory(),
                            String.format("%.2f", product.getPrice()),
                            product.getInfo()
                    });
                }
            }
        }


        public void updateInfoPanel(int selectedRow) {
            JPanel newPanel = new JPanel(new BorderLayout());
            //System.out.println("Line 221");
            if (selectedRow != -1) {
                String productID = (String) prodInfoTable.getValueAt(selectedRow, 0);

                Product product = product_data.get(productID);

                if (product != null) {
                    String productName = product.getProductName();
                    String productCategory = product.getCategory();
                    double productPrice = product.getPrice();
                    String productInfo = product.getInfo();

                    displayText = String.format(
                            "Product ID: %s\nName: %s\nCategory: %s\nPrice: £%.2f\nInfo: %s",
                            productID, productName, productCategory, productPrice, productInfo
                    );
                    //updateInfoLabel(displayText);

                    JLabel infoLabel = new JLabel(displayText);
                    newPanel.add(infoLabel, BorderLayout.CENTER);

                    infopanel.removeAll();
                    infopanel.add(newPanel, BorderLayout.CENTER);

                    infopanel.revalidate();
                    infopanel.repaint();

                }
            }
        }




    }


}