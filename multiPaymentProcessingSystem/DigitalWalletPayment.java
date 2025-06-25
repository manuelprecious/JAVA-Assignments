package multiPaymentProcessingSystem;

public class DigitalWalletPayment implements Payment {
    private String walletId;
    private String password;

    public DigitalWalletPayment(String walletId, String password) {
        this.walletId = walletId;
        this.password = password;
    }

    // Encapsulation of sensitive details only accessible through getters
    public String getWalletId() {
        return walletId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Digital Wallet payment of $" + amount);
        System.out.println("Wallet ID: " + walletId);
        System.out.println("Password: " + "*".repeat(password.length()));
        System.out.println("Digital Wallet payment processed successfully.");
    }
}
