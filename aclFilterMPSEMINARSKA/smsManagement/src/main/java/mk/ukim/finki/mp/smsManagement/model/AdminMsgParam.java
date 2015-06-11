package mk.ukim.finki.mp.smsManagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AdminMsgParam {

	@Id
	@GeneratedValue
	private int smsAdmin_id;
	
	private String sender;
	
	private String receivers;
	
	private String body;

	public int getSmsAdmin_id() {
		return smsAdmin_id;
	}

	public void setSmsAdmin_id(int smsAdmin_id) {
		this.smsAdmin_id = smsAdmin_id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
