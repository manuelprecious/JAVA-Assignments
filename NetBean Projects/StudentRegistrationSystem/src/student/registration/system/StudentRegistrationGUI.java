/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.registration.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*; // Import JDBC classes for database interaction
import java.util.regex.Matcher;
import java.util.regex.Pattern; // For email validation

public class StudentRegistrationGUI extends JFrame {

    // --- GUI Components ---
    private JTextField nameField;
    private JTextField ageField;
    private JTextField emailField;
    private JComboBox<String> courseComboBox;
    private JButton registerButton;
    private JButton displayButton;
    private JTable studentTable;
    private DefaultTableModel tableModel; // Model for the JTable

    // --- Database Configuration ---
    // SQLite will create this file in your project's root directory if it doesn't exist
    // The .db file will be created relative to where the application is run.
    // In NetBeans, this is typically your project's root folder (e.g., StudentRegistrationSystem/).
    private static final String DB_URL = "jdbc:sqlite:student_registration.db";

    public StudentRegistrationGUI() {
        // --- 1. Set up the main JFrame (Window) ---
        setTitle("Student Registration System");
        setSize(1000, 700); // Increased width and height for side-by-side layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the application when window is closed
        setLocationRelativeTo(null); // Centers the window on the screen
        setLayout(new BorderLayout(10, 10)); // Main frame layout with 10px gaps

        // --- 2. Initialize GUI Components ---
        nameField = new JTextField(25); // 25 columns wide hint for layout
        ageField = new JTextField(25);
        emailField = new JTextField(25);

        // Predefined courses for the JComboBox
        String[] courses = {"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology", "Engineering"};
        courseComboBox = new JComboBox<>(courses);

        registerButton = new JButton("Register Student");
        displayButton = new JButton("Display All Students");

        // Define column names for the JTable
        String[] columnNames = {"ID", "Name", "Age", "Course", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0); // 0 initial rows
        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true); // Make the table fill its scroll pane vertically
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row to be selected

        // Wrap the JTable in a JScrollPane to enable scrolling if content overflows
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students")); // Titled border for the table

        // --- 3. Arrange Components for the Left (Registration Form) Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout for flexible form layout
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details Input")); // Titled border for the form

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Padding around each component (top, left, bottom, right)
        gbc.fill = GridBagConstraints.HORIZONTAL; // Components will fill their display area horizontally
        gbc.anchor = GridBagConstraints.WEST; // Labels align to the left

        // Row 0: Name Field
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; // Column 0, Row 0, no horizontal weight for label
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; // Column 1, allow this column to take extra horizontal space
        formPanel.add(nameField, gbc);

        // Row 1: Age Field
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(ageField, gbc);

        // Row 2: Course ComboBox
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(courseComboBox, gbc);

        // Row 3: Email Field
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(emailField, gbc);

        // Panel for form buttons (specifically the Register button)
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Centered flow layout
        formButtonPanel.add(registerButton);

        // Combine form fields and register button into one cohesive left panel
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10)); // Use BorderLayout for left panel's internal structure
        leftPanel.add(formPanel, BorderLayout.NORTH); // Form fields at the top of the left panel
        leftPanel.add(formButtonPanel, BorderLayout.SOUTH); // Register button at the bottom of the left panel

        // Adding an empty panel to the center of leftPanel helps it stretch vertically
        // if the right panel's content is taller, maintaining overall layout balance.
        JPanel leftFillerPanel = new JPanel();
        leftPanel.add(leftFillerPanel, BorderLayout.CENTER);


        // --- 4. Arrange Components for the Right (Display Table) Panel ---
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10)); // Use BorderLayout for right panel
        rightPanel.add(scrollPane, BorderLayout.CENTER); // Table (inside scroll pane) takes the center space

        // Panel for the Display button
        JPanel displayButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        displayButtonPanel.add(displayButton);
        rightPanel.add(displayButtonPanel, BorderLayout.SOUTH); // Display button at the bottom

        // --- 5. Use JSplitPane to put Left and Right Panels Side-by-Side ---
        // JSplitPane allows the user to dynamically resize the two components
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.35); // Initially give 35% width to the left panel, 65% to the right
        splitPane.setOneTouchExpandable(true); // Adds a small button on the divider to expand/collapse panels

        // --- 6. Add the SplitPane to the main JFrame's CENTER region ---
        // The JSplitPane becomes the primary content of our JFrame
        add(splitPane, BorderLayout.CENTER);

        // --- 7. Make the JFrame Visible ---
        setVisible(true);

        // --- 8. Database Initialization (Run once when GUI starts) ---
        // Ensure the students table exists in the database
        createStudentsTable();

        // --- 9. Add Action Listeners for Buttons ---
        // Lambda expressions are used for concise action listeners
        registerButton.addActionListener(e -> registerStudent());
        displayButton.addActionListener(e -> displayStudents());

        // Optional: Load existing students into the table when the application first starts
        displayStudents();
    }

    // --- Database Connection Method ---

    /**
     * Establishes a connection to the SQLite database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs (e.g., driver not found, invalid URL).
     */
    private Connection getConnection() throws SQLException {
        // DriverManager finds the correct JDBC driver (added to Libraries) and connects.
        return DriverManager.getConnection(DB_URL);
    }

    // --- Database Table Creation Method ---

    /**
     * Creates the 'students' table in the database if it does not already exist.
     */
    private void createStudentsTable() {
        // SQL statement for table creation. SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT.
        // 'UNIQUE' constraint on email ensures no two students have the same email.
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "age INTEGER NOT NULL," +
                                "course TEXT NOT NULL," +
                                "email TEXT NOT NULL UNIQUE)";
        try (Connection conn = getConnection(); // Get a connection, automatically closed
             Statement stmt = conn.createStatement()) { // Create a statement, automatically closed
            stmt.execute(createTableSQL); // Execute the SQL command
            System.out.println("Students table checked/created successfully.");
        } catch (SQLException e) {
            // Display error message if table creation fails
            JOptionPane.showMessageDialog(this, "Database Table Creation Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print full error stack trace to console for debugging
        }
    }

    // --- Input Validation Method ---

    /**
     * Validates the input fields (Name, Age, Email, Course) before attempting to register a student.
     * Checks for:
     * - All fields are non-empty.
     * - Age is a valid positive number within a reasonable range.
     * - Email is in a valid format.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateForm() {
        String name = nameField.getText().trim(); // .trim() removes leading/trailing whitespace
        String ageText = ageField.getText().trim();
        String email = emailField.getText().trim();
        String course = (String) courseComboBox.getSelectedItem(); // Get selected item from combo box

        // Check for empty fields
        if (name.isEmpty() || ageText.isEmpty() || email.isEmpty() || course == null || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate Age as a number and within a reasonable range
        int age;
        try {
            age = Integer.parseInt(ageText); // Attempt to convert age string to integer
            if (age <= 0 || age > 150) { // Check for realistic age range
                JOptionPane.showMessageDialog(this, "Age must be a positive number (1-150).", "Input Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            // Catches error if ageText is not a valid number
            JOptionPane.showMessageDialog(this, "Age must be a valid whole number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate Email format using a regular expression
        // This regex is a common pattern for basic email validation.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex); // Compile the regex pattern
        Matcher matcher = pattern.matcher(email);      // Create a matcher for the email string
        if (!matcher.matches()) { // Check if the email matches the pattern
            JOptionPane.showMessageDialog(this, "Email is not in a valid format (e.g., user@example.com).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true; // All validations passed
    }

    // --- Register Student Method ---

    /**
     * Registers a new student into the database after validating input.
     */
    private void registerStudent() {
        // First, perform input validation
        if (!validateForm()) {
            return; // Exit method if validation fails
        }

        // Retrieve validated data from GUI components
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String course = (String) courseComboBox.getSelectedItem();
        String email = emailField.getText().trim();

        // SQL INSERT statement with '?' placeholders for PreparedStatement
        String insertSQL = "INSERT INTO students (name, age, course, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); // Get a database connection
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) { // Prepare the SQL statement

            // Set the values for the placeholders in the prepared statement
            pstmt.setString(1, name);  // First '?' (index 1) for name
            pstmt.setInt(2, age);      // Second '?' (index 2) for age
            pstmt.setString(3, course); // Third '?' (index 3) for course
            pstmt.setString(4, email);  // Fourth '?' (index 4) for email

            // Execute the update (INSERT, UPDATE, DELETE queries use executeUpdate())
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                // Show success message and clear form fields
                JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                displayStudents(); // Refresh the JTable to show the newly registered student
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Catches specific error if a unique constraint (like email) is violated
            JOptionPane.showMessageDialog(this, "Error: A student with this email already exists.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            // Catches any other SQL-related errors
            JOptionPane.showMessageDialog(this, "Database error during registration: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Display Students Method ---

    /**
     * Fetches all registered students from the database and populates the JTable.
     */
    private void displayStudents() {
        // Clear all existing rows from the table model to avoid duplicates when refreshing
        tableModel.setRowCount(0);

        // SQL SELECT statement to retrieve all student data, ordered by name
        String selectSQL = "SELECT id, name, age, course, email FROM students ORDER BY name ASC";
        try (Connection conn = getConnection(); // Get a database connection
             Statement stmt = conn.createStatement(); // Create a simple statement (no parameters needed here)
             ResultSet rs = stmt.executeQuery(selectSQL)) { // Execute query and get results in a ResultSet

            // Iterate through each row in the ResultSet
            while (rs.next()) {
                // Retrieve data from current row using column names
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String course = rs.getString("course");
                String email = rs.getString("email");
                
                // Add the retrieved data as a new row to the JTable's model
                tableModel.addRow(new Object[]{id, name, age, course, email});
            }
        } catch (SQLException e) {
            // Display error if data retrieval fails
            JOptionPane.showMessageDialog(this, "Database error while displaying students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper Methods for GUI State Management ---

    /**
     * Clears all input fields in the registration form.
     */
    public void clearFormFields() {
        nameField.setText("");
        ageField.setText("");
        emailField.setText("");
        courseComboBox.setSelectedIndex(0); // Reset combo box to the first item
    }

    // --- Main Method: Entry point for the application ---
    public static void main(String[] args) {
        // Ensures that GUI creation and updates are performed on the Event Dispatch Thread (EDT).
        // This is crucial for Swing applications to ensure thread safety and responsiveness.
        SwingUtilities.invokeLater(() -> {
            new StudentRegistrationGUI(); // Creates an instance of our GUI and makes it visible
        });
    }
}