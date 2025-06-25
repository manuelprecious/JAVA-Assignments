package multiPaymentProcessingSystem;

class PaymentProcessor {
    public void process(Payment payment, double amount) {
        payment.processPayment(amount);
    }
}