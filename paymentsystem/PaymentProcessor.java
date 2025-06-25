package paymentsystem;

public class PaymentProcessor {

    public void process(PaymentMethod paymentMethod, double amount) {
        System.out.println("\n--- Initiating Payment Processing ---");
        paymentMethod.processPayment(amount);
        System.out.println("--- Payment Processing Complete ---\n");
    }
}

