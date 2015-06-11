package mk.ukim.finki.mp.smsServices.model;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Korisnik {

	@Id
	private int broj;
	
	
	private String username;

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return new Integer(broj).toString();
	}
	
	
	
}
