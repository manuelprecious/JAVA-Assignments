package paymentsystem.methods;

import paymentsystem.PaymentMethod;

public class PayPal extends PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing $%.2f payment through PayPal.%n", amount);
    }
}