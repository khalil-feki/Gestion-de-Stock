import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class OrderDatabase {
    private static final String DB_URL = "jdbc:sqlite:./orders.db";

    public OrderDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS orders " +
                         "(id INTEGER PRIMARY KEY, order_date TEXT, status TEXT, total_value REAL)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS order_items " +
                  "(order_id INTEGER, product_id INTEGER, quantity INTEGER, " +
                  "FOREIGN KEY(order_id) REFERENCES orders(id), " +
                  "FOREIGN KEY(product_id) REFERENCES products(id))";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public void addOrder(Order order) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO orders (id, order_date, status, total_value) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, order.getId());
            pstmt.setString(2, order.getOrderDate().toString());
            pstmt.setString(3, order.getStatus());
            pstmt.setDouble(4, order.getTotalValue());
            pstmt.executeUpdate();

            for (Order.OrderItem item : order.getItems()) {
                sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, order.getId());
                pstmt.setInt(2, item.getProduct().getId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error adding order: " + e.getMessage());
        }
    }

    public Order getOrderById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Order order = new Order(rs.getInt("id"), new Date(rs.getString("order_date")));
                order.setStatus(rs.getString("status"));

                sql = "SELECT * FROM order_items WHERE order_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                ResultSet itemRs = pstmt.executeQuery();

                ProductDatabase productDb = new ProductDatabase();
                while (itemRs.next()) {
                    Product product = productDb.getProductById(itemRs.getInt("product_id"));
                    int quantity = itemRs.getInt("quantity");
                    order.addItem(product, quantity);
                }

                return order;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving order: " + e.getMessage());
        }
        return null;
    }

    public void updateOrder(Order order) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE orders SET status = ?, total_value = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, order.getStatus());
            pstmt.setDouble(2, order.getTotalValue());
            pstmt.setInt(3, order.getId());
            pstmt.executeUpdate();

            // Delete existing items and re-add them
            sql = "DELETE FROM order_items WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, order.getId());
            pstmt.executeUpdate();

            for (Order.OrderItem item : order.getItems()) {
                sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, order.getId());
                pstmt.setInt(2, item.getProduct().getId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }
}
