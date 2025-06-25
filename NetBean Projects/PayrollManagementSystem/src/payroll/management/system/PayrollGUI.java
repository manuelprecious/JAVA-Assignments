/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package payroll.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*; // For JDBC database interaction
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayrollGUI extends JFrame {

    // --- GUI Components ---
    private JTextField employeeNameField;
    private JTextField employeeIdField;
    private JTextField basicSalaryField;
    private JTextField allowancesField;
    private JTextField deductionsField;
    private JButton calculateAndSaveButton;
    private JButton displayPayrollButton;
    private JTable payrollTable;
    private DefaultTableModel tableModel;

    // --- Database Configuration (NOW FOR SQLITE) ---
    // This will create a file named 'payroll.db' in your project's root directory
    private static final String DB_URL = "jdbc:sqlite:payroll.db";
    // For SQLite, no username or password is required for file-based databases

    public PayrollGUI() {
        // --- 1. Set up the main JFrame ---
        setTitle("Payroll Management System");
        setSize(1300, 800);
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

        employeeNameField = new JTextField(25);
        employeeNameField.setFont(componentFont);
        employeeIdField = new JTextField(25);
        employeeIdField.setFont(componentFont);
        basicSalaryField = new JTextField(25);
        basicSalaryField.setFont(componentFont);
        allowancesField = new JTextField(25);
        allowancesField.setFont(componentFont);
        deductionsField = new JTextField(25);
        deductionsField.setFont(componentFont);

        calculateAndSaveButton = new JButton("Calculate and Save Payroll");
        calculateAndSaveButton.setFont(labelFont);
        displayPayrollButton = new JButton("Display All Payroll Records");
        displayPayrollButton.setFont(labelFont);

        // Table and its Model
        String[] columnNames = {"ID", "Employee ID", "Name", "Basic Salary", "Allowances", "Deductions", "Net Pay"};
        tableModel = new DefaultTableModel(columnNames, 0);
        payrollTable = new JTable(tableModel);
        payrollTable.setFillsViewportHeight(true);
        payrollTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Modern Table Appearance
        payrollTable.setRowHeight(30);
        payrollTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        payrollTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        payrollTable.setGridColor(new Color(220, 220, 220));
        payrollTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(payrollTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1), "Payroll Records")
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);


        // --- 3. Arrange Components for the Left (Payroll Form) Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1), "Employee Payroll Details")
        ));
        formPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(formPanel, new JLabel("Employee Name:", SwingConstants.RIGHT), employeeNameField, gbc, 0);
        addFormRow(formPanel, new JLabel("Employee ID:", SwingConstants.RIGHT), employeeIdField, gbc, 1);
        addFormRow(formPanel, new JLabel("Basic Salary:", SwingConstants.RIGHT), basicSalaryField, gbc, 2);
        addFormRow(formPanel, new JLabel("Allowances:", SwingConstants.RIGHT), allowancesField, gbc, 3);
        addFormRow(formPanel, new JLabel("Deductions:", SwingConstants.RIGHT), deductionsField, gbc, 4);
        
        for(Component comp : formPanel.getComponents()) {
            if(comp instanceof JLabel) {
                ((JLabel)comp).setFont(labelFont);
            }
        }

        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        formButtonPanel.setBackground(formPanel.getBackground());
        formButtonPanel.add(calculateAndSaveButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(formButtonPanel, BorderLayout.SOUTH);

        // --- 4. Arrange Components for the Right (Display Table) Panel ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel displayButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        displayButtonPanel.add(displayPayrollButton);
        rightPanel.add(displayButtonPanel, BorderLayout.SOUTH);

        // --- 5. Use JSplitPane to put Left and Right Panels Side-by-Side ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.40);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        // Add the split pane to a central content panel with padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.add(splitPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);

        // --- 7. Make the JFrame Visible ---
        setVisible(true);

        // --- 8. Database Initialization ---
        createPayrollTable();

        // --- 9. Add Action Listeners ---
        calculateAndSaveButton.addActionListener(e -> calculateAndSavePayroll());
        displayPayrollButton.addActionListener(e -> displayPayrollRecords());
        
        // Load existing records on startup
        displayPayrollRecords();
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
    private void createPayrollTable() {
        // SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Payroll (" + // SQLite syntax for IF NOT EXISTS
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // SQLite auto-increment
                                "employee_id VARCHAR(50) NOT NULL UNIQUE," +
                                "employee_name VARCHAR(255) NOT NULL," +
                                "basic_salary REAL NOT NULL," + // SQLite uses REAL for floating-point numbers
                                "allowances REAL NOT NULL," +
                                "deductions REAL NOT NULL," +
                                "net_pay REAL NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Payroll table checked/created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Table Creation Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Input Validation Method ---
    private boolean validateForm() {
        String employeeName = employeeNameField.getText().trim();
        String employeeId = employeeIdField.getText().trim();
        String basicSalaryText = basicSalaryField.getText().trim();
        String allowancesText = allowancesField.getText().trim();
        String deductionsText = deductionsField.getText().trim();

        if (employeeName.isEmpty() || employeeId.isEmpty() || basicSalaryText.isEmpty() || allowancesText.isEmpty() || deductionsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            // Use Double.parseDouble for parsing, as REAL in SQLite maps to double in Java
            Double.parseDouble(basicSalaryText);
            Double.parseDouble(allowancesText);
            Double.parseDouble(deductionsText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Basic Salary, Allowances, and Deductions must be valid numbers.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (Double.parseDouble(basicSalaryText) < 0 || Double.parseDouble(allowancesText) < 0 || Double.parseDouble(deductionsText) < 0) {
            JOptionPane.showMessageDialog(this, "Salary, Allowances, and Deductions cannot be negative.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // --- Calculate and Save Payroll Method ---
    private void calculateAndSavePayroll() {
        if (!validateForm()) {
            return;
        }

        String employeeName = employeeNameField.getText().trim();
        String employeeId = employeeIdField.getText().trim();
        double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
        double allowances = Double.parseDouble(allowancesField.getText().trim());
        double deductions = Double.parseDouble(deductionsField.getText().trim());

        double netPay = basicSalary + allowances - deductions;

        String insertSQL = "INSERT INTO Payroll (employee_id, employee_name, basic_salary, allowances, deductions, net_pay) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, employeeId);
            pstmt.setString(2, employeeName);
            pstmt.setDouble(3, basicSalary);
            pstmt.setDouble(4, allowances);
            pstmt.setDouble(5, deductions);
            pstmt.setDouble(6, netPay);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Payroll record saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                displayPayrollRecords(); // Update table after successful save
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save payroll record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Error: An employee with this ID already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during save operation: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Display Payroll Records Method ---
    private void displayPayrollRecords() {
        tableModel.setRowCount(0); // Clear existing data

        String selectSQL = "SELECT id, employee_id, employee_name, basic_salary, allowances, deductions, net_pay FROM Payroll ORDER BY employee_name ASC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String employeeId = rs.getString("employee_id");
                String employeeName = rs.getString("employee_name");
                double basicSalary = rs.getDouble("basic_salary");
                double allowances = rs.getDouble("allowances");
                double deductions = rs.getDouble("deductions");
                double netPay = rs.getDouble("net_pay");

                tableModel.addRow(new Object[]{id, employeeId, employeeName, basicSalary, allowances, deductions, netPay});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error while displaying records: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper Method to Clear Form Fields ---
    public void clearFormFields() {
        employeeNameField.setText("");
        employeeIdField.setText("");
        basicSalaryField.setText("");
        allowancesField.setText("");
        deductionsField.setText("");
    }

    // --- Main Method: Entry point for the application ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PayrollGUI();
        });
    }
}