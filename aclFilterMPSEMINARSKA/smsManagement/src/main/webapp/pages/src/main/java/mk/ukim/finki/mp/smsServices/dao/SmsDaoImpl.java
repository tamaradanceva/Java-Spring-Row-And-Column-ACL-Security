package mk.ukim.finki.mp.smsServices.dao;

import java.util.List;

import mk.ukim.finki.mp.smsServices.model.Korisnik;
import mk.ukim.finki.mp.smsServices.model.SMS;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SmsDaoImpl implements SmsDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public SMS getSms(int sms_id) {
		return (SMS) getCurrentSession().get(SMS.class, sms_id);
	}

	@Override
	public List<SMS> getSMSbySender(int broj) {
		@SuppressWarnings("unchecked")
		List<SMS> res = (List<SMS>) getCurrentSession()
				.createQuery("from SMS where sender.broj=:br")
				.setParameter("br", broj).list();
		return res;
	}

	@Override
	public void saveSMS(Korisnik sender, Korisnik receiver, String body) {
		SMS s1= new SMS();
		s1.setBody(body);
		s1.setReceiver_broj(receiver);
		s1.setSender(sender);
		this.getCurrentSession().save(s1);
	}

	@Override
	public List<SMS> getReceivedSMS(int broj) {
		@SuppressWarnings("unchecked")
		List<SMS> res = (List<SMS>) getCurrentSession()
				.createQuery("from SMS where receiver.broj=:br")
				.setParameter("br", broj).list();
		return res;
	}

}
