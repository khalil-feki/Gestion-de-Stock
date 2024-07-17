import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductGUI extends JFrame {
    private JTextField productNameField, productPriceField, productIdField;
    private JTextArea productListArea;
    private ProductDatabase database;
    private JButton backButton;

    public ProductGUI() {
        super("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        database = new ProductDatabase();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel productNameLabel = new JLabel("Product Name:");
        JLabel productPriceLabel = new JLabel("Product Price:");
        JLabel productIdLabel = new JLabel("Product ID:");
        productNameField = new JTextField();
        productPriceField = new JTextField();
        productIdField = new JTextField();
        inputPanel.add(productNameLabel);
        inputPanel.add(productNameField);
        inputPanel.add(productPriceLabel);
        inputPanel.add(productPriceField);
        inputPanel.add(productIdLabel);
        inputPanel.add(productIdField);
        backButton = new JButton("Back to Login");
        backButton.addActionListener(new ActionListener() {
        @Override
           public void actionPerformed(ActionEvent e) {
        dispose(); // Close the OrderGUI window
        new InterfaceSelectionPanel().setVisible(true); // Open the InterfaceSelectionPanel window
       }
    });

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int id = Integer.parseInt(productIdField.getText()); // Get the ID from the field
                addProduct(id, name, price);
                productNameField.setText("");
                productPriceField.setText("");
                productIdField.setText(""); // Clear ID field after adding
            }
        });

        JButton showAllButton = new JButton("Show All Products");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductList();
            }
        });

        JButton showByIdButton = new JButton("Show Product by ID");
        showByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(productIdField.getText());
                showProductById(id);
            }
        });

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(productIdField.getText());
                deleteProduct(id);
            }
        });

        JButton updateButton = new JButton("Update Product");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(productIdField.getText());
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                updateProduct(id, name, price);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(showByIdButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton); // Add the update button
        buttonPanel.add(backButton);

        productListArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(productListArea);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(buttonPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        updateProductList(); // Initially show all products
    }

    private void updateProductList() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Product> products = database.getAllProducts();
        for (Product product : products) {
            sb.append(product.getId()).append(". ").append(product.getName()).append(" - ").append(product.getPrice()).append("\n");
        }
        productListArea.setText(sb.toString());
    }

    private void showProductById(int id) {
        Product product = database.getProductById(id);
        if (product != null) {
            productListArea.setText(product.getId() + ". " + product.getName() + " - " + product.getPrice());
        } else {
            productListArea.setText("Product not found!");
        }
    }

    private void addProduct(int id, String name, double price) {
        Product product = new Product(id, name, price);
        database.addProduct(product);
        updateProductList();
    }

    private void deleteProduct(int id) {
        database.deleteProduct(id);
        updateProductList(); // Update product list after deletion
    }

    private void updateProduct(int id, String name, double price) {
        Product product = new Product(id, name, price);
        database.updateProduct(product);
        updateProductList(); // Update product list after updating
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductGUI().setVisible(true);
            }
        });
    }
}
