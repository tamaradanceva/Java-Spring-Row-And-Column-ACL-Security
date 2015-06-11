package mk.ukim.finki.mp.smsServices.dao;

import java.util.List;

import mk.ukim.finki.mp.smsServices.model.Korisnik;

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

	@Override
	public Korisnik getKorisnik(int broj) {
		//return (Korisnik) getCurrentSession().get(Korisnik.class, broj);
		Korisnik res=(Korisnik) getCurrentSession().createQuery("from Korisnik where broj=:broj").setParameter("broj",broj).uniqueResult();
		return res;
	}

	@Override
	public List<Korisnik> getKorisnici() {
		return getCurrentSession().createQuery("from Korisnik").list();//vrati ja cela lista na korisnici sho ja imat vo bazata
	}
	
}
