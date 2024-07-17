import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CategoryGUI extends JFrame {
    private JTextField categoryNameField, categoryIdField;
    private JTextArea categoryListArea;
    private CategoryDatabase database;
    private JButton backButton;

    public CategoryGUI() {
        super("Category Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        database = new CategoryDatabase();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel categoryNameLabel = new JLabel("Category Name:");
        JLabel categoryIdLabel = new JLabel("Category ID:");
        categoryNameField = new JTextField();
        categoryIdField = new JTextField();
        inputPanel.add(categoryNameLabel);
        inputPanel.add(categoryNameField);
        inputPanel.add(categoryIdLabel);
        inputPanel.add(categoryIdField);
        backButton = new JButton("Back to Login");
backButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose(); // Close the OrderGUI window
        new InterfaceSelectionPanel().setVisible(true); // Open the InterfaceSelectionPanel window
    }
});

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = categoryNameField.getText();
                int id = Integer.parseInt(categoryIdField.getText()); // Get the ID from the field
                addCategory(id, name);
                categoryNameField.setText("");
                categoryIdField.setText(""); // Clear ID field after adding
            }
        });

        JButton showAllButton = new JButton("Show All Categories");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCategoryList();
            }
        });

        JButton deleteButton = new JButton("Delete Category");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(categoryIdField.getText());
                deleteCategory(id);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        categoryListArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(categoryListArea);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(buttonPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        updateCategoryList(); // Initially show all categories
    }

    private void updateCategoryList() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Category> categories = database.getAllCategories();
        for (Category category : categories) {
            sb.append(category.getId()).append(". ").append(category.getName()).append("\n");
        }
        categoryListArea.setText(sb.toString());
    }

    private void addCategory(int id, String name) {
        Category category = new Category(id, name);
        database.addCategory(category);
        updateCategoryList();
    }

    private void deleteCategory(int id) {
        database.deleteCategory(id);
        updateCategoryList(); // Update category list after deletion
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CategoryGUI().setVisible(true);
            }
        });
    }
}
