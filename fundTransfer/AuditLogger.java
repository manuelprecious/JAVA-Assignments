package fundTransfer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String AUDIT_LOG_FILE = "audit.log";

    /**
     * Logs exception details to a text file for audit purposes.
     * The log includes timestamp, context, exception type, message, and stack
     * trace.
     */

    public static void logException(Exception e, String context) {
        try (
                FileWriter fileWriter = new FileWriter(AUDIT_LOG_FILE, true);
                PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            printWriter.println("-----------------------------------------");
            printWriter.println("Timestamp: " + timeStamp);
            printWriter.println("Context: " + context);
            printWriter.println("Exception Type: " + e.getClass().getName());
            printWriter.println("Message: " + e.getMessage());
            printWriter.println("Stack Trace:");
            e.printStackTrace(printWriter);
            printWriter.println("------------------------------------------");

        } catch (IOException ioException) {
            System.err.println("Error logging exception for file: " + ioException.getMessage());
        }
    }
}
