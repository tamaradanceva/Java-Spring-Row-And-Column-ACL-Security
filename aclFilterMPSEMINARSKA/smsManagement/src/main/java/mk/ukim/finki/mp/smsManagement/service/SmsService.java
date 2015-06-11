package mk.ukim.finki.mp.smsManagement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.MutableAclService;

import mk.ukim.finki.mp.smsManagement.dao.KorisnikDao;
import mk.ukim.finki.mp.smsManagement.dao.SmsDao;
import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;
import mk.ukim.finki.mp.smsManagement.model.mockSMS;
import mk.ukim.finki.mp.smsManagement.payment.PaymentExecutor;

public interface SmsService {
	
	public Collection<mockSMS> getmockSMSFromSender(int broj);
	
	public Collection<mockSMS> getmockSMSFromreceiver(int broj);
	
	public Map<Integer, mockSMS> getSmsList();
	
	public void fillMap();


	public void setSmsList(Map<Integer, mockSMS> smsList);
	
	public List<SMS> getSMSFromSender(int broj);
	
	public List<SMS> getSMSFromreceiver(int broj);
	
	
	public SMS getSMS(int sms_id);
	
	public Korisnik getKorisnik(int broj);
	
	public boolean sendSMS(Korisnik sender, String body, List<Korisnik> receivers);
	
	public void addSms(int broj, SMS s);
	
	public void addKorisnik(int sms_id, Korisnik k);
	
	public void updateSeen(SMS s);
	
	public List<SMS> getFilter(int s, int r);
	
	public List<SMS> getSentByDate(int broj, Date date);
	
	public List<SMS> getReceivedByDate(int broj, Date date);
}
