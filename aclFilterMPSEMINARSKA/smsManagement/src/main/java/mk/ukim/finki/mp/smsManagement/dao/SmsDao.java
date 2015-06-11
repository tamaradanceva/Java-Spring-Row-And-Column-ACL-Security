package mk.ukim.finki.mp.smsManagement.dao;

import java.util.Date;
import java.util.List;

import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;

public interface SmsDao {

	public SMS getSms(int sms_id);
	
	public List<SMS> getSMSbySender(int broj);
	
	public List<SMS> getSMSbyReceiver(int broj);

	public List<SMS> getSentByDate(int broj, Date date);
	
	public List<SMS> getReceivedByDate(int broj, Date date);
	
	public SMS saveSMS(Korisnik sender, String body, List<Korisnik> receivers);
	
	public void addKorisnik(int sms_id, Korisnik k);
	
	public void updateSeen(SMS s);
	
	public List<SMS> getBySenderAndReceiverFilter(int s,int r);
	
}
