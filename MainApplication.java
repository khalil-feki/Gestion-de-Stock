import javax.swing.*;

public class MainApplication implements LoginGUI.LoginSuccessListener {

    public MainApplication() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setLoginSuccessListener(this);
        loginGUI.setVisible(true);
    }

    @Override
    public void onLoginSuccess(boolean isAuthenticated) {
        JOptionPane.showMessageDialog(null, "Login successful!");
    }

    @Override
    public void onProductButtonClick() {
        ProductGUI productGUI = new ProductGUI();
        productGUI.setVisible(true);
    }

    @Override
    public void onCategoryButtonClick() {
        CategoryGUI categoryGUI = new CategoryGUI();
        categoryGUI.setVisible(true);
    }

    @Override
    public void onOrderButtonClick() {
        OrderGUI orderGUI = new OrderGUI();
        orderGUI.setVisible(true);
    }
    @Override
    public void oncommandeButtonClick() {
        commandeGUI commandeGUI = new commandeGUI();
        commandeGUI.setVisible(true);
    }

    public static void main(String[] args) {
        new MainApplication();
    }
}