package mk.ukim.finki.mp.smsServices.dao;

import java.util.List;

import mk.ukim.finki.mp.smsServices.model.Korisnik;

public interface KorisnikDao {

	public Korisnik getKorisnik(int broj);
	
	public List<Korisnik> getKorisnici();
	
}
