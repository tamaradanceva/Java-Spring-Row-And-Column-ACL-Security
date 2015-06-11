package mk.ukim.finki.mp.smsManagement.dao;

import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KorisnikDaoImpl implements KorisnikDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public Korisnik getKorisnik(int broj) {
		if((Korisnik) getCurrentSession().get(Korisnik.class, broj)!=null)
		return (Korisnik) getCurrentSession().get(Korisnik.class, broj);
		else {
			System.out.println("null");
			return null;
		}
	}

	@Override
	public void addSMS(int broj,SMS s) {
		Korisnik k= getKorisnik(broj);
		k.addSms(s);
		getCurrentSession().save(k);
	}

}
