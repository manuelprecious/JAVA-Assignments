package paymentsystem.methods;

import paymentsystem.PaymentMethod;

public class BankTransfer extends PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Process payment $%.2f payment through Bank Transfer.%n", amount);
    }
}
