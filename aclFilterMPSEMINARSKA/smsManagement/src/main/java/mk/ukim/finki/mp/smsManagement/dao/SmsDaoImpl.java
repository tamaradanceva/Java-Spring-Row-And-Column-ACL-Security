package mk.ukim.finki.mp.smsManagement.dao;

import java.util.Date;
import java.util.List;

import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;

import org.hibernate.Query;
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

	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
	public List<SMS> getBySenderAndReceiverFilter(int s, int r) {
		@SuppressWarnings("unchecked")
		List<SMS> res = (List<SMS>) getCurrentSession()
				.createQuery(
						"select distinct s from SMS s "
								+ "join s.receivers r "
								+ "where r.broj=:receiver and s.sender.broj=:sender")
				.setParameter("sender", s).setParameter("receiver", r).list();
		return res;
	}

	@Override
	public List<SMS> getSMSbyReceiver(int broj) {
		@SuppressWarnings("unchecked")
		// List<SMS> res = (List<SMS>) getCurrentSession()
		// .createSQLQuery("SELECT * FROM smsservices.sms s WHERE s.sms_id IN (SELECT sr.sms_id FROM smsservices.sms_receivers sr WHERE broj=:br)")
		// .setParameter("br", broj).list();
		String hql = "select distinct s from SMS s " + "join s.receivers r "
				+ "where r.broj=:br";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("br", broj);
		List<SMS> listSms = query.list();
		return listSms;
	}

	@Override
	public SMS saveSMS(Korisnik sender, String body, List<Korisnik> receivers) {
		SMS s1 = new SMS();
		s1.setBody(body);
		s1.setSender(sender);
		s1.setId(s1.hashCode());
		for (int i = 0; i < receivers.size(); i++) {
			s1.addReceiver(receivers.get(i));
		}
		Date d = new Date();
		s1.setDate(d);
		s1.setSeen(false);
		this.getCurrentSession().save(s1);
		for(int i=0;i<receivers.size();i++)
		s1.addReceiver(receivers.get(i));
		for(int i=0;i<receivers.size();i++){
			receivers.get(i).addSms(s1);
		}
		return s1;
		
	}

	@Override
	public void addKorisnik(int sms_id, Korisnik k) {
		SMS s = getSms(sms_id);
		s.addReceiver(k);
		this.getCurrentSession().save(s);
	}

	@Override
	public void updateSeen(SMS s) {
		SMS s1 = new SMS();
		s1.setSms_id(s.getSms_id());
		s1.setBody(s.getBody());
		s1.setDate(s.getDate());
		s1.setSeen(s.isSeen());
		s1.setSender(s.getSender());
		s1.setReceivers(s.getReceivers());
		getCurrentSession().update(s1);
	}

	@Override
	public List<SMS> getSentByDate(int broj, Date date) {
		@SuppressWarnings("unchecked")
		List<SMS> res = (List<SMS>) getCurrentSession()
				.createQuery("from SMS where sender.broj=:br and day(date) = day(:d) and month(date) = month(:d) and year(date) = year(:d)")
				.setDate("d", date).setParameter("br", broj).list();
		return res;
	}

	@Override
	public List<SMS> getReceivedByDate(int broj, Date date) {
		String hql = "select distinct s from SMS s " + "join s.receivers r "
				+ "where r.broj=:br and day(date) = day(:d) and month(date) = month(:d) and year(date) = year(:d)";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("br", broj);
		query.setDate("d", date);
		List<SMS> listSms = query.list();
		return listSms;
	}

}
