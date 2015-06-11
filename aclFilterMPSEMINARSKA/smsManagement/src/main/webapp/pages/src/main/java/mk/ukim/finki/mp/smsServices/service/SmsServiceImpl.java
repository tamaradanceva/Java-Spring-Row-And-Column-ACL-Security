package mk.ukim.finki.mp.smsServices.service;

import java.text.MessageFormat;
import java.util.List;

import mk.ukim.finki.mp.smsServices.dao.KorisnikDao;
import mk.ukim.finki.mp.smsServices.dao.SmsDao;
import mk.ukim.finki.mp.smsServices.model.Korisnik;
import mk.ukim.finki.mp.smsServices.model.SMS;
import mk.ukim.finki.mp.smsServices.payment.PaymentExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SmsServiceImpl implements SmsService{

	@Autowired
	SmsDao smsDao;

	@Autowired
	KorisnikDao korDao;

	@Autowired
	PaymentExecutor paymentExecutor;
	
	@Override
	public List<SMS> getSMSFromSender(int broj) {
		
		return smsDao.getSMSbySender(broj);
	}

	@Override
	public SMS getSMS(int sms_id) {
		return smsDao.getSms(sms_id);
	}

	@Override
	public Korisnik getKorisnik(int broj) {
		return korDao.getKorisnik(broj);
	}

	@Override
	public boolean sendSMS(Korisnik sender, Korisnik receiver, String body) {
		
		smsDao.saveSMS(sender, receiver, body);
		return true;
	}

	@Override
	public List<SMS> getReceivedSMS(int broj) {
		return smsDao.getReceivedSMS(broj);
	}

	@Override
	public List<Korisnik> getKorisnici() {
		return korDao.getKorisnici();
	}

}
