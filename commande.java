import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class commande {
    private int id;
    private Date commandeDate;
    private String status;
    private List<commandeItem> items;
    private double totalValue;

    public commande(int id, Date commandeDate) {
        this.id = id;
        this.commandeDate = commandeDate;
        this.status = "Pending";
        this.items = new ArrayList<>();
        this.totalValue = 0.0;
    }

    public void addItem(Product product, int quantity) {
        commandeItem item = new commandeItem(product, quantity);
        items.add(item);
        calculateTotalValue();
    }

    private void calculateTotalValue() {
        totalValue = items.stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }

    // Getters and setters
    public int getId() { return id; }
    public Date getcommandeDate() { return commandeDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<commandeItem> getItems() { return items; }
    public double getTotalValue() { return totalValue; }

    public static class commandeItem {
        private Product product;
        private int quantity;

        public commandeItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
    }
}