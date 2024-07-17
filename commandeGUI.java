import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class commandeGUI extends JFrame {
    private JTextField commandeIdField, productIdField, quantityField;
    private JTextArea commandeDetailsArea;
    private commandeDatabase commandeDatabase;
    private ProductDatabase productDatabase;
    private JButton backButton;
    

    public commandeGUI() {
        super("commande Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        commandeDatabase = new commandeDatabase();
        productDatabase = new ProductDatabase();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("commande ID:"));
        commandeIdField = new JTextField();
        inputPanel.add(commandeIdField);
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
        
        

        JButton createcommandeButton = new JButton("Create commande");
        createcommandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createcommande();
            }
        });

        JButton addItemButton = new JButton("Add Item to commande");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemTocommande();
            }
        });

        JButton viewcommandeButton = new JButton("View commande");
        viewcommandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewcommande();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createcommandeButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(viewcommandeButton);
        buttonPanel.add(backButton);

        commandeDetailsArea = new JTextArea(10, 40);
        commandeDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(commandeDetailsArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void createcommande() {
        try {
            int commandeId = Integer.parseInt(commandeIdField.getText());
            commande commande = new commande(commandeId, new Date());
            commandeDatabase.addcommande(commande);
            commandeDetailsArea.setText("commande created with ID: " + commandeId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid commande ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addItemTocommande() {
        try {
            int commandeId = Integer.parseInt(commandeIdField.getText());
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = productDatabase.getProductById(productId);
            if (product != null) {
                commande commande = commandeDatabase.getcommandeById(commandeId);
                if (commande != null) {
                    commande.addItem(product, quantity);
                    commandeDatabase.updatecommande(commande);
                    commandeDetailsArea.setText("Item added to commande " + commandeId);
                } else {
                    JOptionPane.showMessageDialog(this, "commande not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewcommande() {
        try {
            int commandeId = Integer.parseInt(commandeIdField.getText());
            commande commande = commandeDatabase.getcommandeById(commandeId);
            if (commande != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("commande ID: ").append(commande.getId()).append("\n");
                sb.append("Date:").append(commande.getcommandeDate()).append("\n");
                sb.append("Status: ").append(commande.getStatus()).append("\n");
                sb.append("Items:\n");
                for (commande.commandeItem item : commande.getItems()) {
                    sb.append("  - ").append(item.getProduct().getName())
                      .append("(Qty: ").append(item.getQuantity())
                      .append(",Price: $").append(item.getProduct().getPrice()).append(")\n");
                }
                sb.append("Total Value: $").append(String.format("%.2f", commande.getTotalValue()));
                commandeDetailsArea.setText(sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "commande not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid commande ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new commandeGUI().setVisible(true);
            }
        });
    }
}