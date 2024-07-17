import java.sql.*;
import java.util.ArrayList;

public class CategoryDatabase {
    private static final String DB_URL = "jdbc:sqlite:./categories.db";

    public CategoryDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS categories (id INTEGER PRIMARY KEY, name TEXT)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public void addCategory(Category category) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO categories (id, name) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, category.getId());
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
        }
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT id, name FROM categories";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving categories: " + e.getMessage());
        }
        return categories;
    }

    public void deleteCategory(int categoryId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "DELETE FROM categories WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
        }
    }
}
