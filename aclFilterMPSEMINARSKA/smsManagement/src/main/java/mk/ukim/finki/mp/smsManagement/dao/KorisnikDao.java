package mk.ukim.finki.mp.smsManagement.dao;


import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;

public interface KorisnikDao {

	public Korisnik getKorisnik(int broj);
	
	public void addSMS(int broj,SMS s);
	
}
