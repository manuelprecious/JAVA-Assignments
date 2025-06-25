package fundTransfer;


/**
 * Represents a user account and provides functionality for fund transfers.
 */
public class Account {

    private double balance;
    private static final double MAX_TRANSFER_LIMIT = 10000.0; // Max limit for a single transfer

    /**
     * Constructs a new Account with an initial balance.
     *
     * @param initialBalance The starting balance for the account.
     * @throws IllegalArgumentException if the initial balance is negative.
     */
    public Account(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial account balance cannot be negative.");
        }
        this.balance = initialBalance;
    }

    /**
     * Gets the current balance of the account.
     *
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Simulates the fund transfer process from this account.
     * Performs validation checks and deducts the amount if all conditions are met.
     *
     * @param transferAmount The amount to be transferred.
     * @throws IllegalArgumentException If the transfer amount is not positive.
     * @throws ArithmeticException If this account has insufficient balance.
     * @throws SecurityException If the transfer amount exceeds the maximum limit.
     */
    public void transferFunds(double transferAmount)
            throws IllegalArgumentException, ArithmeticException, SecurityException {

        // Check 1: Ensure the transfer amount is positive.
        if (transferAmount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        // Check 2: Ensure this account has enough balance for the transfer.
        if (this.balance < transferAmount) {
            throw new ArithmeticException("Insufficient balance for transfer. Current balance: $" +
                                         String.format("%.2f", this.balance) + ", Transfer requested: $" +
                                         String.format("%.2f", transferAmount));
        }

        // Check 3: Ensure the transfer amount does not exceed the maximum limit.
        if (transferAmount > MAX_TRANSFER_LIMIT) {
            throw new SecurityException("Transfer amount $" + String.format("%.2f", transferAmount) +
                                        " exceeds the maximum limit of $" + String.format("%.2f", MAX_TRANSFER_LIMIT) + ".");
        }

        // If all conditions are met, deduct the amount
        this.balance -= transferAmount;
        System.out.println("DEBUG: Funds successfully deducted. New balance: " + String.format("%.2f", this.balance));
    }
}
