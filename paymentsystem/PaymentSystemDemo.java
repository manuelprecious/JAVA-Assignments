package paymentsystem;

import paymentsystem.methods.CreditCard;
import paymentsystem.methods.PayPal;
import paymentsystem.methods.BankTransfer;

public class PaymentSystemDemo {
    public static void main(String[] args) {
        CreditCard creditCardMethod = new CreditCard();
        PayPal payPalMethod = new PayPal();
        BankTransfer bankTransferMethod = new BankTransfer();

        PaymentProcessor processor = new PaymentProcessor();

        System.out.println("Scenario 1: Processing Credit Card Payment");
        processor.process(creditCardMethod, 150);

        System.out.println("Scenario 2: Processing PayPal Payment");
        processor.process(payPalMethod, 75.00);

        System.out.println("Scenario 3: Processing Bank Transfer Payment");
        processor.process(bankTransferMethod, 500.20);

        // Example of adding a hypothetical new payment
        // New Payment method: CryptoPayment

        PaymentMethod cryptoMethod = new PaymentMethod() {
            @Override
            public void processPayment(double amount) {
                System.out.printf("Processing $%.2f payment through Crypto Currency.%n", amount);
            }
        };

        System.out.println("Scenario 4: Processing Crypto Payment (New Method)");
        processor.process(cryptoMethod, 1000.00);
    }
}
