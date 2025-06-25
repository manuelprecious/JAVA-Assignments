package fundTransfer;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class to simulate the fund transfer functionality,
 * utilizing the Account and AuditLogger classes.
 */
public class FundTransferSimulator {

    public static void main(String[] args) {
        System.out.println("DEBUG: Program started."); // ADD THIS LINE

        Scanner scanner = new Scanner(System.in);
        Account senderAccount = null;
        double transferAmount = 0.0;
        boolean validInput = false;

        System.out.println("--- Fund Transfer Simulation ---");

        // Get sender's initial account balance
        while (!validInput) {
            try {
                System.out.println("Enter sender's initial account balance: $");
                double initialBalance = scanner.nextDouble();
                senderAccount = new Account(initialBalance); // Create an Account object
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for balance.");
                scanner.next(); // Consume the invalid input
                AuditLogger.logException(e, "Initial Balance Input Error");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                AuditLogger.logException(e, "Initial Balance Creation Error");
            }
        }

        validInput = false; // Reset for next input

        // Get transfer amount
        while (!validInput) {
            try {
                System.out.print("Enter the amount to transfer: $");
                transferAmount = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for transfer amount.");
                scanner.next(); // Consume the invalid input
                AuditLogger.logException(e, "Transfer Amount Input Error");
            }
        }

        try {
            // Attempt to transfer funds using the Account object's method
            senderAccount.transferFunds(transferAmount);
            System.out.println("\nTransfer successful!");
            System.out.println("Updated sender's balance: $" + String.format("%.2f", senderAccount.getBalance()));
        } catch (IllegalArgumentException e) {
            System.err.println("\nError: " + e.getMessage());
            AuditLogger.logException(e, "Fund Transfer Validation (Negative/Zero Amount)");
        } catch (ArithmeticException e) {
            System.err.println("\nError: " + e.getMessage());
            AuditLogger.logException(e, "Fund Transfer Validation (Insufficient Balance)");
        } catch (SecurityException e) {
            System.err.println("\nError: " + e.getMessage());
            AuditLogger.logException(e, "Fund Transfer Validation (Maximum Limit Exceeded)");
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("\nAn unexpected error occurred during transfer: " + e.getMessage());
            AuditLogger.logException(e, "Fund Transfer (Unexpected Error)");
        } finally {
            scanner.close();
            System.out.println("\nSimulation complete. Check 'audit.log' for details on any errors.");
        }
    }
}
