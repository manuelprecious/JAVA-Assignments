package multiPaymentProcessingSystem;

public class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    @Override
    public void processPayment(double amount) {
        // In a real system, this would involve connecting to a payment gateway.
        System.out.println("Processing Credit Card Payment of $" + amount);
        System.out.println("Card Number: " + maskCardNumber(cardNumber));
        System.out.println("CVV:");
        System.out.println("Credit Card payment processed successfully.");
    }

    // Helper method to mask card number for display
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }

        return "************" + cardNumber.substring(cardNumber.length() - 4);
    }
}
