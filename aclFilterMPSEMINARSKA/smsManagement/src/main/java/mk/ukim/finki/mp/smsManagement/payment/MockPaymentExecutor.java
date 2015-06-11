package mk.ukim.finki.mp.smsManagement.payment;

import org.springframework.stereotype.Component;

@Component
public class MockPaymentExecutor implements PaymentExecutor {

	@Override
	public double sendSms(int sender, int recevier, String body) {
		//send sms if the numbers are valid
		return 10;
	}

	@Override
	public double getSaldo(int broj) {
		//get saldo by broj
		return 3000;
	}

	

}
