/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hotel.reservation.system; // New package for this new project

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.sql.*; // For JDBC database interaction
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; // For JSpinner and date comparisons
import java.util.regex.Matcher;
import java.util.regex.Pattern; // For email validation

public class HotelReservationSystemGUI extends JFrame {

    // --- GUI Components ---
    private JTextField guestNameField;
    private JTextField emailField;
    private JTextField numberOfGuestsField; // Using JTextField for easier validation
    private JComboBox<String> roomTypeComboBox;
    private JSpinner checkInDateSpinner;
    private JSpinner checkOutDateSpinner;
    private JButton makeReservationButton;
    private JButton displayReservationsButton;
    private JTable reservationsTable;
    private DefaultTableModel tableModel; // Model for the JTable

    // --- Database Configuration ---
    // SQLite will create this file in your project's root directory if it doesn't exist
    private static final String DB_URL = "jdbc:sqlite:hotel_reservations.db";

    public HotelReservationSystemGUI() {
        // --- 1. Set up the main JFrame (Window) ---
        setTitle("Hotel Reservation System");
        setSize(1200, 750); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(15, 15)); // Main frame layout with larger gaps

        // --- 2. Initialize GUI Components ---
        guestNameField = new JTextField(25);
        emailField = new JTextField(25);
        numberOfGuestsField = new JTextField(25); // For number input

        String[] roomTypes = {"Single", "Double", "Suite", "Deluxe"};
        roomTypeComboBox = new JComboBox<>(roomTypes);

        // JSpinner for Dates
        SpinnerDateModel checkInModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        checkInDateSpinner = new JSpinner(checkInModel);
        JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(checkInDateSpinner, "yyyy-MM-dd");
        checkInDateSpinner.setEditor(checkInEditor);

        SpinnerDateModel checkOutModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        checkOutDateSpinner = new JSpinner(checkOutModel);
        JSpinner.DateEditor checkOutEditor = new JSpinner.DateEditor(checkOutDateSpinner, "yyyy-MM-dd");
        checkOutDateSpinner.setEditor(checkOutEditor);

        // Set check-out date to be at least one day after check-in initially
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        checkOutDateSpinner.setValue(cal.getTime());

        makeReservationButton = new JButton("Make Reservation");
        displayReservationsButton = new JButton("Display All Reservations");

        // Table and its Model
        String[] columnNames = {"ID", "Guest Name", "Email", "Room Type", "Guests", "Check-in", "Check-out"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reservationsTable = new JTable(tableModel);
        reservationsTable.setFillsViewportHeight(true);
        reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Improve table appearance (modern UI)
        reservationsTable.setRowHeight(25); // Slightly taller rows
        reservationsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14)); // Bold header font
        reservationsTable.setFont(new Font("SansSerif", Font.PLAIN, 13)); // Content font

        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Existing Reservations"));

        // --- 3. Arrange Components for the Left (Reservation Form) Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reservation Details"));
        formPanel.setBackground(new Color(240, 248, 255)); // AliceBlue for a softer look

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Generous padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Guest Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Guest Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(guestNameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(emailField, gbc);

        // Room Type
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Room Type:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(roomTypeComboBox, gbc);

        // Number of Guests
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("Number of Guests:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(numberOfGuestsField, gbc);

        // Check-in Date
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(checkInDateSpinner, gbc);

        // Check-out Date
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        formPanel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(checkOutDateSpinner, gbc);

        // Make Reservation Button
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        formButtonPanel.setBackground(formPanel.getBackground()); // Match background
        formButtonPanel.add(makeReservationButton);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(formButtonPanel, BorderLayout.SOUTH);
        JPanel leftFillerPanel = new JPanel(); // To help stretching
        leftPanel.add(leftFillerPanel, BorderLayout.CENTER);


        // --- 4. Arrange Components for the Right (Display Table) Panel ---
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Display Reservations Button
        JPanel displayButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        displayButtonPanel.add(displayReservationsButton);
        rightPanel.add(displayButtonPanel, BorderLayout.SOUTH);

        // --- 5. Use JSplitPane to put Left and Right Panels Side-by-Side ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.40); // Give a bit more space to the form initially (40%)
        splitPane.setOneTouchExpandable(true);

        // --- 6. Add the SplitPane to the main JFrame's CENTER region ---
        add(splitPane, BorderLayout.CENTER);

        // --- 7. Make the JFrame Visible ---
        setVisible(true);

        // --- 8. Database Initialization (Run once when GUI starts) ---
        createReservationsTable();

        // --- 9. Add Action Listeners for Buttons ---
        makeReservationButton.addActionListener(e -> makeReservation());
        displayReservationsButton.addActionListener(e -> displayReservations());

        // Optional: Load existing reservations when the app starts
        // We will call displayReservations() initially only if the user specifically requests it
        // based on the new requirement. For a "fresh start", we'll assume they want to see
        // any existing data from previous runs.
        displayReservations();
    }

    // --- Database Connection Method ---
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // --- Database Table Creation Method ---
    private void createReservationsTable() {
        // SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT.
        // DATE type in SQLite stores dates as TEXT (ISO8601 strings "YYYY-MM-DD")
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reservations (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "guest_name VARCHAR(255) NOT NULL," +
                                "email VARCHAR(255) NOT NULL UNIQUE," + // Email unique
                                "room_type VARCHAR(50) NOT NULL," +
                                "number_of_guests INTEGER NOT NULL," +
                                "check_in_date DATE NOT NULL," +
                                "check_out_date DATE NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Reservations table checked/created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Table Creation Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Input Validation Method ---
    private boolean validateForm() {
        String guestName = guestNameField.getText().trim();
        String email = emailField.getText().trim();
        String numGuestsText = numberOfGuestsField.getText().trim();
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        Date checkInDate = (Date) checkInDateSpinner.getValue();
        Date checkOutDate = (Date) checkOutDateSpinner.getValue();

        // 1. Check for empty fields
        if (guestName.isEmpty() || email.isEmpty() || numGuestsText.isEmpty() || roomType == null || roomType.isEmpty() || checkInDate == null || checkOutDate == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2. Validate Email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Email is not in a valid format (e.g., user@example.com).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 3. Validate Number of Guests as a positive integer
        int numberOfGuests;
        try {
            numberOfGuests = Integer.parseInt(numGuestsText);
            if (numberOfGuests <= 0) {
                JOptionPane.showMessageDialog(this, "Number of guests must be a positive whole number.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Number of guests must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 4. Validate Check-out Date is after Check-in Date
        if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            JOptionPane.showMessageDialog(this, "Check-out Date must be after Check-in Date.", "Date Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true; // All validations passed
    }

    // --- Make Reservation Method ---
    private void makeReservation() {
        if (!validateForm()) {
            return; // Exit if validation fails
        }

        String guestName = guestNameField.getText().trim();
        String email = emailField.getText().trim();
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        int numberOfGuests = Integer.parseInt(numberOfGuestsField.getText().trim());
        
        // Format java.util.Date to SQL DATE string (YYYY-MM-DD)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String checkInDateStr = dateFormat.format((Date) checkInDateSpinner.getValue());
        String checkOutDateStr = dateFormat.format((Date) checkOutDateSpinner.getValue());

        String insertSQL = "INSERT INTO reservations (guest_name, email, room_type, number_of_guests, check_in_date, check_out_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, guestName);
            pstmt.setString(2, email);
            pstmt.setString(3, roomType);
            pstmt.setInt(4, numberOfGuests);
            pstmt.setString(5, checkInDateStr); // Use formatted string for SQLite DATE
            pstmt.setString(6, checkOutDateStr); // Use formatted string for SQLite DATE

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Reservation made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                displayReservations(); // ONLY update display if a new person is added
            } else {
                JOptionPane.showMessageDialog(this, "Failed to make reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Specific exception for unique constraint violation (e.g., duplicate email)
            JOptionPane.showMessageDialog(this, "Error: A reservation with this email already exists.", "Reservation Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during reservation: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Display Reservations Method ---
    private void displayReservations() {
        tableModel.setRowCount(0); // Clear existing data in the table

        String selectSQL = "SELECT id, guest_name, email, room_type, number_of_guests, check_in_date, check_out_date FROM reservations ORDER BY check_in_date ASC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String guestName = rs.getString("guest_name");
                String email = rs.getString("email");
                String roomType = rs.getString("room_type");
                int numberOfGuests = rs.getInt("number_of_guests");
                String checkInDate = rs.getString("check_in_date"); // Retrieve as string
                String checkOutDate = rs.getString("check_out_date"); // Retrieve as string

                tableModel.addRow(new Object[]{id, guestName, email, roomType, numberOfGuests, checkInDate, checkOutDate});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error while displaying reservations: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Helper Method to Clear Form Fields ---
    public void clearFormFields() {
        guestNameField.setText("");
        emailField.setText("");
        numberOfGuestsField.setText("");
        roomTypeComboBox.setSelectedIndex(0);
        
        // Reset check-in and check-out dates to current date and next day
        checkInDateSpinner.setValue(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        checkOutDateSpinner.setValue(cal.getTime());
    }

    // --- Main Method: Entry point for the application ---
    public static void main(String[] args) {
        // Set Nimbus Look and Feel for a more modern UI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default or CrossPlatform L&F
            System.err.println("Nimbus Look and Feel not available. Using default.");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Ignore, use default if even cross-platform fails
            }
        }

        // Ensure GUI creation and updates are performed on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            new HotelReservationSystemGUI();
        });
    }
}
