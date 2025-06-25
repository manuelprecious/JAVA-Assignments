package multiPaymentProcessingSystem;

public class Main {
    public static void main(String[] args) {

        // Create instances of the payment methods.
        CreditCardPayment creditCard = new CreditCardPayment("1234567890123456", "123");
        DebitCardPayment debitCard = new DebitCardPayment("9374923423428533", "4321");
        DigitalWalletPayment digitalWallet = new DigitalWalletPayment("user@gmail.com", "mySecurePassword");

        // Create a PaymentProcessor
        PaymentProcessor processor = new PaymentProcessor();

        // Process payments using the Payment processor
        System.out.println("--- Processing Payments ---");
        processor.process(creditCard, 100.50);
        System.out.println();
        processor.process(debitCard, 50.00);
        System.out.println();
        processor.process(digitalWallet, 25.75);
    }
}
