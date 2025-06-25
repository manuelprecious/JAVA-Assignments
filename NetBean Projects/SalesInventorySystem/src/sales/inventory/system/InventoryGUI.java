/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sales.inventory.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*; // For JDBC database interaction
import java.text.SimpleDateFormat; // For formatting dates for SQLite TEXT storage
import java.util.Date;

public class InventoryGUI extends JFrame {

    // --- GUI Components ---
    // Product Management Form
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField pricePerUnitField;
    private JTextField quantityInStockField;
    private JButton addProductButton;
    private JButton updateStockButton;

    // Sales Management Form
    private JTextField saleProductIdField;
    private JTextField quantitySoldField;
    private JButton recordSaleButton;

    // Inventory View
    private JButton viewInventoryButton;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    // --- Database Configuration (NOW FOR SQLITE) ---
    // This will create a file named 'inventory.db' in your project's root directory
    private static final String DB_URL = "jdbc:sqlite:inventory.db";
    // For SQLite, no username or password is required for file-based databases

    public InventoryGUI() {
        // --- 1. Set up the main JFrame ---
        setTitle("Sales Inventory Management System");
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        // --- Apply Nimbus Look and Feel (for modern UI) ---
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus Look and Feel not available. Using default.");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Fallback to default if even cross-platform fails
            }
        }

        // --- 2. Initialize GUI Components ---
        Font componentFont = new Font("SansSerif", Font.PLAIN, 14);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        // Product Management Fields
        productIdField = new JTextField(20);
        productIdField.setFont(componentFont);
        productNameField = new JTextField(20);
        productNameField.setFont(componentFont);
        pricePerUnitField = new JTextField(20);
        pricePerUnitField.setFont(componentFont);
        quantityInStockField = new JTextField(20);
        quantityInStockField.setFont(componentFont);

        addProductButton = new JButton("Add New Product");
        addProductButton.setFont(labelFont);
        updateStockButton = new JButton("Update Stock");
        updateStockButton.setFont(labelFont);

        // Sales Management Fields
        saleProductIdField = new JTextField(20);
        saleProductIdField.setFont(componentFont);
        quantitySoldField = new JTextField(20);
        quantitySoldField.setFont(componentFont);

        recordSaleButton = new JButton("Record Sale");
        recordSaleButton.setFont(labelFont);

        // Inventory View Button & Table
        viewInventoryButton = new JButton("View All Inventory");
        viewInventoryButton.setFont(labelFont);

        String[] columnNames = {"Product ID", "Product Name", "Price per Unit", "Quantity in Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Modern Table Appearance
        inventoryTable.setRowHeight(30);
        inventoryTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        inventoryTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inventoryTable.setGridColor(new Color(220, 220, 220));
        inventoryTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1), "Current Inventory")
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);


        // --- 3. Arrange Components into Panels ---

        // Product Management Form Panel
        JPanel productFormPanel = new JPanel(new GridBagLayout());
        productFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1), "Product Management")
        ));
        productFormPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(productFormPanel, new JLabel("Product ID:", SwingConstants.RIGHT), productIdField, gbc, 0);
        addFormRow(productFormPanel, new JLabel("Product Name:", SwingConstants.RIGHT), productNameField, gbc, 1);
        addFormRow(productFormPanel, new JLabel("Price per Unit:", SwingConstants.RIGHT), pricePerUnitField, gbc, 2);
        addFormRow(productFormPanel, new JLabel("Quantity in Stock:", SwingConstants.RIGHT), quantityInStockField, gbc, 3);
        
        // Product buttons panel
        JPanel productButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        productButtonsPanel.setBackground(productFormPanel.getBackground());
        productButtonsPanel.add(addProductButton);
        productButtonsPanel.add(updateStockButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        productFormPanel.add(productButtonsPanel, gbc);


        // Sales Management Form Panel
        JPanel salesFormPanel = new JPanel(new GridBagLayout());
        salesFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1), "Sales Management")
        ));
        salesFormPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints s_gbc = new GridBagConstraints(); // Separate GBC for sales form
        s_gbc.insets = new Insets(8, 8, 8, 8);
        s_gbc.fill = GridBagConstraints.HORIZONTAL;
        s_gbc.anchor = GridBagConstraints.WEST;

        addFormRow(salesFormPanel, new JLabel("Product ID (for Sale):", SwingConstants.RIGHT), saleProductIdField, s_gbc, 0);
        addFormRow(salesFormPanel, new JLabel("Quantity Sold:", SwingConstants.RIGHT), quantitySoldField, s_gbc, 1);
        
        // Sales button panel
        JPanel salesButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        salesButtonPanel.setBackground(salesFormPanel.getBackground());
        salesButtonPanel.add(recordSaleButton);

        s_gbc.gridx = 0; s_gbc.gridy = 2; s_gbc.gridwidth = 2; s_gbc.weightx = 1.0;
        salesFormPanel.add(salesButtonPanel, s_gbc);
        
        // Set label fonts for both forms
        for(Component comp : productFormPanel.getComponents()) {
            if(comp instanceof JLabel) {
                ((JLabel)comp).setFont(labelFont);
            }
        }
        for(Component comp : salesFormPanel.getComponents()) {
            if(comp instanceof JLabel) {
                ((JLabel)comp).setFont(labelFont);
            }
        }


        // Top Panel containing both forms side-by-side
        JPanel topFormsPanel = new JPanel(new GridLayout(1, 2, 15, 0)); // 1 row, 2 columns, with horizontal gap
        topFormsPanel.add(productFormPanel);
        topFormsPanel.add(salesFormPanel);
        topFormsPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Padding between forms and table


        // Bottom Panel for View Inventory Button
        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        viewButtonPanel.add(viewInventoryButton);


        // Add panels to the main JFrame
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15)); // Vertical gap between top forms and table
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Overall padding
        contentPanel.add(topFormsPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(viewButtonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);


        // --- 7. Make the JFrame Visible ---
        setVisible(true);

        // --- 8. Database Initialization ---
        createTables();

        // --- 9. Add Action Listeners ---
        addProductButton.addActionListener(e -> addProduct());
        updateStockButton.addActionListener(e -> updateStock());
        recordSaleButton.addActionListener(e -> recordSale());
        viewInventoryButton.addActionListener(e -> viewInventory());
        
        // Initial load of inventory
        viewInventory();
    }

    // --- Helper method for consistent form row addition ---
    private void addFormRow(JPanel panel, JLabel label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    // --- Database Connection Method (Adjusted for SQLite) ---
    private Connection getConnection() throws SQLException {
        try {
            // Load the SQLite JDBC driver (often not strictly necessary but good practice)
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "SQLite JDBC Driver not found. " +
                                            "Please add sqlite-jdbc-X.X.X.jar to your project libraries.",
                                            "Driver Error", JOptionPane.ERROR_MESSAGE);
            throw new SQLException("SQLite JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL); // No user/password for SQLite
    }

    // --- Database Table Creation Method (Adjusted for SQLite) ---
    private void createTables() {
        // SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT
        String createProductsTableSQL = "CREATE TABLE IF NOT EXISTS Products (" + // SQLite syntax for IF NOT EXISTS
                                        "product_id VARCHAR(50) PRIMARY KEY," +
                                        "product_name VARCHAR(255) NOT NULL," +
                                        "price_per_unit REAL NOT NULL," + // SQLite uses REAL for floating-point numbers
                                        "quantity_in_stock INTEGER NOT NULL DEFAULT 0)"; // SQLite uses INTEGER

        String createSalesTableSQL = "CREATE TABLE IF NOT EXISTS Sales (" +
                                     "sale_id INTEGER PRIMARY KEY AUTOINCREMENT," + // SQLite auto-increment
                                     "product_id VARCHAR(50) NOT NULL," +
                                     "quantity_sold INTEGER NOT NULL," +
                                     "sale_date TEXT NOT NULL DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%S', 'now', 'localtime'))," + // SQLite current timestamp
                                     "FOREIGN KEY (product_id) REFERENCES Products(product_id))"; // Foreign Key Constraint

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createProductsTableSQL);
            stmt.execute(createSalesTableSQL);
            System.out.println("Products and Sales tables checked/created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Table Creation Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper for Product Form Input Validation ---
    private boolean validateProductInput() {
        String productId = productIdField.getText().trim();
        String productName = productNameField.getText().trim();
        String priceText = pricePerUnitField.getText().trim();
        String quantityText = quantityInStockField.getText().trim();

        if (productId.isEmpty() || productName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All product fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            if (price < 0 || quantity < 0) {
                JOptionPane.showMessageDialog(this, "Price and Quantity must be non-negative numbers.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Quantity must be valid numbers.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // --- Add Product Feature ---
    private void addProduct() {
        if (!validateProductInput()) {
            return;
        }

        String productId = productIdField.getText().trim();
        String productName = productNameField.getText().trim();
        double pricePerUnit = Double.parseDouble(pricePerUnitField.getText().trim());
        int quantityInStock = Integer.parseInt(quantityInStockField.getText().trim());

        String insertSQL = "INSERT INTO Products (product_id, product_name, price_per_unit, quantity_in_stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, productId);
            pstmt.setString(2, productName);
            pstmt.setDouble(3, pricePerUnit);
            pstmt.setInt(4, quantityInStock);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearProductFields();
                viewInventory(); // Refresh inventory display
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Error: Product ID '" + productId + "' already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error adding product: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Update Stock Feature ---
    private void updateStock() {
        String productId = productIdField.getText().trim();
        String quantityText = quantityInStockField.getText().trim();

        if (productId.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product ID and Quantity in Stock fields must be filled for stock update.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int newQuantity = Integer.parseInt(quantityText);
            if (newQuantity < 0) {
                 JOptionPane.showMessageDialog(this, "Quantity in Stock must be a non-negative number.", "Input Error", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            // Check if product exists first
            try (Connection conn = getConnection();
                 PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM Products WHERE product_id = ?")) {
                checkStmt.setString(1, productId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(this, "Product ID '" + productId + "' does not exist. Cannot update stock.", "Update Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String updateSQL = "UPDATE Products SET quantity_in_stock = ? WHERE product_id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

                pstmt.setInt(1, newQuantity);
                pstmt.setString(2, productId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Stock updated successfully for Product ID: " + productId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearProductFields();
                    viewInventory(); // Refresh inventory display
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update stock. Product ID might not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity in Stock must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error updating stock: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper for Sales Form Input Validation ---
    private boolean validateSaleInput() {
        String productId = saleProductIdField.getText().trim();
        String quantityText = quantitySoldField.getText().trim();

        if (productId.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both Product ID and Quantity Sold must be filled for recording a sale.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity Sold must be a positive whole number.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity Sold must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // --- Record Sale Feature ---
    private void recordSale() {
        if (!validateSaleInput()) {
            return;
        }

        String productId = saleProductIdField.getText().trim();
        int quantitySold = Integer.parseInt(quantitySoldField.getText().trim());

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Check current stock availability
            String checkStockSQL = "SELECT quantity_in_stock FROM Products WHERE product_id = ?";
            int currentStock = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(checkStockSQL)) {
                pstmt.setString(1, productId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    currentStock = rs.getInt("quantity_in_stock");
                } else {
                    JOptionPane.showMessageDialog(this, "Product ID '" + productId + "' not found.", "Sale Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback(); // Rollback in case product not found
                    return;
                }
            }

            if (currentStock < quantitySold) {
                JOptionPane.showMessageDialog(this, "Insufficient stock for Product ID '" + productId + "'. Available: " + currentStock, "Sale Error", JOptionPane.ERROR_MESSAGE);
                conn.rollback(); // Rollback if stock is insufficient
                return;
            }

            // 2. Record the sale in Sales table
            String insertSaleSQL = "INSERT INTO Sales (product_id, quantity_sold, sale_date) VALUES (?, ?, STRFTIME('%Y-%m-%d %H:%M:%S', 'now', 'localtime'))";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSaleSQL)) {
                pstmt.setString(1, productId);
                pstmt.setInt(2, quantitySold);
                pstmt.executeUpdate();
            }

            // 3. Update quantity in Products table
            String updateStockSQL = "UPDATE Products SET quantity_in_stock = ? WHERE product_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateStockSQL)) {
                pstmt.setInt(1, currentStock - quantitySold);
                pstmt.setString(2, productId);
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            JOptionPane.showMessageDialog(this, "Sale recorded and stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearSalesFields();
            viewInventory(); // Refresh inventory display
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback on any SQL error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Database error during sale transaction: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore auto-commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // --- View Inventory Feature ---
    private void viewInventory() {
        tableModel.setRowCount(0); // Clear existing data

        String selectSQL = "SELECT product_id, product_name, price_per_unit, quantity_in_stock FROM Products ORDER BY product_name ASC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                String productId = rs.getString("product_id");
                String productName = rs.getString("product_name");
                double pricePerUnit = rs.getDouble("price_per_unit");
                int quantityInStock = rs.getInt("quantity_in_stock");

                tableModel.addRow(new Object[]{productId, productName, pricePerUnit, quantityInStock});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error while displaying inventory: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper Method to Clear Product Form Fields ---
    public void clearProductFields() {
        productIdField.setText("");
        productNameField.setText("");
        pricePerUnitField.setText("");
        quantityInStockField.setText("");
    }

    // --- Helper Method to Clear Sales Form Fields ---
    public void clearSalesFields() {
        saleProductIdField.setText("");
        quantitySoldField.setText("");
    }

    // --- Main Method: Entry point for the application ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryGUI();
        });
    }
}
