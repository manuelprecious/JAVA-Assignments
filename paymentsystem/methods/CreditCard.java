package paymentsystem.methods;

import paymentsystem.PaymentMethod;

public class CreditCard extends PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing $%.2f payment through Credit Card.%n", amount);
    }

}