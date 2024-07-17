import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class commandeDatabase {
    private static final String DB_URL = "jdbc:sqlite:./commandes.db";

    public commandeDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS commandes " +
                         "(id INTEGER PRIMARY KEY, commande_date TEXT, status TEXT, total_value REAL)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS commande_items " +
                  "(commande_id INTEGER, product_id INTEGER, quantity INTEGER, " +
                  "FOREIGN KEY(commande_id) REFERENCES commandes(id), " +
                  "FOREIGN KEY(product_id) REFERENCES products(id))";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    

    public void addcommande(commande commande) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO commandes (id, commande_date, status, total_value) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commande.getId());
            pstmt.setString(2, commande.getcommandeDate().toString());
            pstmt.setString(3, commande.getStatus());
            pstmt.setDouble(4, commande.getTotalValue());
            pstmt.executeUpdate();

            for (commande.commandeItem item : commande.getItems()) {
                sql = "INSERT INTO commande_items (commande_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, commande.getId());
                pstmt.setInt(2, item.getProduct().getId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error adding commande: " + e.getMessage());
        }
    }

    public commande getcommandeById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM commandes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                commande commande = new commande(rs.getInt("id"), new Date(rs.getString("commande_date")));
                commande.setStatus(rs.getString("status"));

                sql = "SELECT * FROM commande_items WHERE commande_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                ResultSet itemRs = pstmt.executeQuery();

                ProductDatabase productDb = new ProductDatabase();
                while (itemRs.next()) {
                    Product product = productDb.getProductById(itemRs.getInt("product_id"));
                    int quantity = itemRs.getInt("quantity");
                    commande.addItem(product, quantity);
                }

                return commande;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving commande: " + e.getMessage());
        }
        return null;
    }

    public void updatecommande(commande commande) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE commandes SET status = ?, total_value = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, commande.getStatus());
            pstmt.setDouble(2, commande.getTotalValue());
            pstmt.setInt(3, commande.getId());
            pstmt.executeUpdate();

            // Delete existing items and re-add them
            sql = "DELETE FROM commande_items WHERE commande_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commande.getId());
            pstmt.executeUpdate();

            for (commande.commandeItem item : commande.getItems()) {
                sql = "INSERT INTO commande_items (commande_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, commande.getId());
                pstmt.setInt(2, item.getProduct().getId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error updating commande: " + e.getMessage());
        }
    }
}
