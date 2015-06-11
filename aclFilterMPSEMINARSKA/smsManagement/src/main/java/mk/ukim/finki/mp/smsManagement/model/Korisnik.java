package mk.ukim.finki.mp.smsManagement.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class Korisnik {

	@Id
	private int broj;
	
	private String ime;
	
	private String prezime;
	
	@ManyToMany(mappedBy="receivers",fetch=FetchType.EAGER)
	private List<SMS> listaSms;
	
	public Korisnik(){
		listaSms= new ArrayList<SMS>();
	}
	

	public List<SMS> getListaSms() {
		return listaSms;
	}



	public void setListaSms(List<SMS> listaSms) {
		this.listaSms = listaSms;
	}
	
	public void addSms(SMS s){
		this.listaSms.add(s);
	}
	
	public void removeSms(SMS s){
		this.listaSms.remove(s);
	}



	public int getBroj() {
		return broj;
	}



	public void setBroj(int broj) {
		this.broj = broj;
	}



	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezime() {
		return prezime;
	}



	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return ime+" "+prezime;
	}
	
	
	
}
