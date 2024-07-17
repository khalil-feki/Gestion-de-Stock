import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Order {
    private int id;
    private Date orderDate;
    private String status;
    private List<OrderItem> items;
    private double totalValue;

    public Order(int id, Date orderDate) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = "Pending";
        this.items = new ArrayList<>();
        this.totalValue = 0.0;
    }

    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem(product, quantity);
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
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalValue() { return totalValue; }

    public static class OrderItem {
        private Product product;
        private int quantity;

        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
    }
}