import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceSelectionPanel extends JPanel {
    private JButton productButton;
    private JButton categoryButton;
    private JButton orderButton;
    private JButton commandeButton;

    public InterfaceSelectionPanel() {
        setLayout(new GridLayout(3, 1));

        productButton = new JButton("Product Management");
        categoryButton = new JButton("Category Management");
        orderButton = new JButton("Order Management");
        commandeButton = new JButton("commande Management");

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductInterface();
            }
        });

        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCategoryInterface();
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrderInterface();
            }
        });
        commandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opencommandeInterface();
            }
        });

        add(productButton);
        add(categoryButton);
        add(orderButton);
        add(commandeButton);
    }

    private void openProductInterface() {
        ProductGUI productGUI = new ProductGUI();
        productGUI.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }

    private void openCategoryInterface() {
        CategoryGUI categoryGUI = new CategoryGUI();
        categoryGUI.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }

    private void openOrderInterface() {
        OrderGUI orderGUI = new OrderGUI();
        orderGUI.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }
    private void opencommandeInterface() {
        commandeGUI commandeGUI = new commandeGUI();
        commandeGUI.setVisible(true);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }
}













