package mk.ukim.finki.mp.smsServices.payment;

public interface PaymentExecutor {

	public boolean validatePaymentDetails(String name, String lastName,
			String creditCardNumber);

	public void executePayment(String creditCardNumber, double total);
	
	public int getSaldo();
	
	public int odzemiSaldo(int saldo, int odzemi);
}
