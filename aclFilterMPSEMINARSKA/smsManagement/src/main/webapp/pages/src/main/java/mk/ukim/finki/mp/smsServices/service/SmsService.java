package mk.ukim.finki.mp.smsServices.service;

import java.util.List;

import mk.ukim.finki.mp.smsServices.model.Korisnik;
import mk.ukim.finki.mp.smsServices.model.SMS;

public interface SmsService {
	
public List<SMS> getSMSFromSender(int broj);

public List<SMS> getReceivedSMS(int broj);
	
	public SMS getSMS(int sms_id);
	
	public Korisnik getKorisnik(int broj);
	
	public boolean sendSMS(Korisnik sender, Korisnik receiver, String body);
	
	public List<Korisnik> getKorisnici();
	
}
