package mk.ukim.finki.mp.smsServices.dao;

import java.util.List;

import mk.ukim.finki.mp.smsServices.model.Korisnik;
import mk.ukim.finki.mp.smsServices.model.SMS;

public interface SmsDao {

	public SMS getSms(int sms_id);
	
	public List<SMS> getSMSbySender(int broj);
	
	public void saveSMS(Korisnik sender, Korisnik receiver, String body);
	
	public List<SMS> getReceivedSMS(int broj);
}
