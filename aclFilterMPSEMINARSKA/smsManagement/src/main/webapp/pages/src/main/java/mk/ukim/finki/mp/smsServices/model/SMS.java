package mk.ukim.finki.mp.smsServices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SMS {

	@Id
	@GeneratedValue
	private int sms_id;
	
	@OneToOne
	private Korisnik sender;
	
	@OneToOne
	private Korisnik receiver;
	
	private String body;

	public int getSms_id() {
		return sms_id;
	}

	public void setSms_id(int sms_id) {
		this.sms_id = sms_id;
	}

	public Korisnik getSender() {
		return sender;
	}

	public void setSender(Korisnik sender) {
		this.sender = sender;
	}

	public Korisnik getReceiver() {
		return receiver;
	}

	public void setReceiver_broj(Korisnik receiver) {
		this.receiver = receiver;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
