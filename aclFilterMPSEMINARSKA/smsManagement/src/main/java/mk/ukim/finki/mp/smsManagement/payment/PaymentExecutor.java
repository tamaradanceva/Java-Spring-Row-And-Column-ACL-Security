package mk.ukim.finki.mp.smsManagement.payment;

public interface PaymentExecutor {

	public double sendSms(int sender, int recevier, String body );

	public double getSaldo(int broj);

}
