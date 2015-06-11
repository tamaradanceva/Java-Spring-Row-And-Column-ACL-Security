package mk.ukim.finki.mp.smsServices.payment;

import mk.ukim.finki.mp.smsServices.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockPaymentExecutor implements PaymentExecutor {

	@Override
	public boolean validatePaymentDetails(String name, String lastName,
			String creditCardNumber) {
		return true;
	}

	@Override
	public void executePayment(String creditCardNumber, double total) {
		// it is mock and does nothing
	}

	@Override
	public int getSaldo() {
		return 1000;
	}

	@Override
	public int odzemiSaldo(int saldo, int odzemi) {
		return saldo-odzemi;
	}

}
