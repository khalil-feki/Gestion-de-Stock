import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class OrderGUI extends JFrame {
    private JTextField orderIdField, productIdField, quantityField;
    private JTextArea orderDetailsArea;
    private OrderDatabase orderDatabase;
    private ProductDatabase productDatabase;
    private JButton backButton;
    

    public OrderGUI() {
        super("Order Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        orderDatabase = new OrderDatabase();
        productDatabase = new ProductDatabase();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        inputPanel.add(orderIdField);
        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        backButton = new JButton("Back to Login");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the OrderGUI window
                new InterfaceSelectionPanel().setVisible(true); // Open the InterfaceSelectionPanel window
            }
        });
        

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });

        JButton addItemButton = new JButton("Add Item to Order");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToOrder();
            }
        });

        JButton viewOrderButton = new JButton("View Order");
        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrder();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createOrderButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(viewOrderButton);
        buttonPanel.add(backButton);

        orderDetailsArea = new JTextArea(10, 40);
        orderDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderDetailsArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void createOrder() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            Order order = new Order(orderId, new Date());
            orderDatabase.addOrder(order);
            orderDetailsArea.setText("Order created with ID: " + orderId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addItemToOrder() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = productDatabase.getProductById(productId);
            if (product != null) {
                Order order = orderDatabase.getOrderById(orderId);
                if (order != null) {
                    order.addItem(product, quantity);
                    orderDatabase.updateOrder(order);
                    orderDetailsArea.setText("Item added to order " + orderId);
                } else {
                    JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewOrder() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            Order order = orderDatabase.getOrderById(orderId);
            if (order != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Order ID: ").append(order.getId()).append("\n");
                sb.append("Date: ").append(order.getOrderDate()).append("\n");
                sb.append("Status: ").append(order.getStatus()).append("\n");
                sb.append("Items:\n");
                for (Order.OrderItem item : order.getItems()) {
                    sb.append("  - ").append(item.getProduct().getName())
                      .append(" (Qty: ").append(item.getQuantity())
                      .append(", Price: $").append(item.getProduct().getPrice()).append(")\n");
                }
                sb.append("Total Value: $").append(String.format("%.2f", order.getTotalValue()));
                orderDetailsArea.setText(sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrderGUI().setVisible(true);
            }
        });
    }
}