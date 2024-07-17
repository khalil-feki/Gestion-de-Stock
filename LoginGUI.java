import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton productButton;
    private JButton categoryButton;
    private JButton orderButton;
    private JButton commandeButton;
    private LoginSuccessListener loginSuccessListener;

    public LoginGUI() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                boolean isAdmin = authenticate(username, new String(password));
                if (isAdmin) {
                    if (username.equals("admin")) {
                        enableAllButtons();
                    } else if (username.equals("productUser")) {
                        enableProductButton();
                    } else if (username.equals("categoryUser")) {
                        enableCategoryButton();
                    } else if (username.equals("orderUser")) {
                        enableOrderButton();
                    } else if (username.equals("commandeUser")) {
                        enablecommandeButton();
                    }
                    if (loginSuccessListener != null) {
                        loginSuccessListener.onLoginSuccess(isAdmin);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        productButton = new JButton("Open Product");
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginSuccessListener != null) {
                    loginSuccessListener.onProductButtonClick();
                }
            }
        });
        productButton.setEnabled(false);

        categoryButton = new JButton("Open Category");
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginSuccessListener != null) {
                    loginSuccessListener.onCategoryButtonClick();
                }
            }
        });
        categoryButton.setEnabled(false);

        orderButton = new JButton("Open Order");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginSuccessListener != null) {
                    loginSuccessListener.onOrderButtonClick();
                }
            }
        });
        orderButton.setEnabled(false);
        
        commandeButton = new JButton("Open commande");
        commandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginSuccessListener != null) {
                    loginSuccessListener.oncommandeButtonClick();
                }
            }
        });
        
        commandeButton.setEnabled(false);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(productButton);
        panel.add(categoryButton);
        panel.add(orderButton);
        panel.add(commandeButton);

        getContentPane().add(panel);
    }

    private boolean authenticate(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        } else if (username.equals("productUser") && password.equals("productPass")) {
            return true;
        } else if (username.equals("categoryUser") && password.equals("categoryPass")) {
            return true;
        } else if (username.equals("orderUser") && password.equals("orderPass")) {
            return true;
         } else if (username.equals("commandeUser") && password.equals("commandePass")) {
            return true;
        }
        return false;
    }

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    private void enableAllButtons() {
        productButton.setEnabled(true);
        categoryButton.setEnabled(true);
        orderButton.setEnabled(true);
        commandeButton.setEnabled(true);
    }

    private void enableProductButton() {
        productButton.setEnabled(true);
    }

    private void enableCategoryButton() {
        categoryButton.setEnabled(true);
    }

    private void enableOrderButton() {
        orderButton.setEnabled(true);
    }
    private void enablecommandeButton() {
       commandeButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
    }

    public interface LoginSuccessListener {
        void onLoginSuccess(boolean isAdmin);
        void onProductButtonClick();
        void onCategoryButtonClick();
        void onOrderButtonClick();
        void oncommandeButtonClick();
    }
}