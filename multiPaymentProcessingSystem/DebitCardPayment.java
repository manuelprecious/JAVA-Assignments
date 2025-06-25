package multiPaymentProcessingSystem;

public class DebitCardPayment implements Payment {
    private String cardNumber;
    private String pin;

    public DebitCardPayment(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    // Encapsulate sensitive information accessible only through getters.
    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getPin() {
        return this.pin;
    }

    @Override
    public void processPayment(double amount) {
        // In a real System, this would involve connecting to a payment gateway.
        System.out.println("Processing Debit Card payment of " + amount);
        System.out.println("Card Number: " + maskCardNumber(cardNumber));
        System.out.println("PIN: ****");
        System.out.println("Debit Card payment processed successfully");
    }

    // Helper method to mask card number for display.
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }

        return "************" + cardNumber.substring(cardNumber.length() - 4);
    }
}
