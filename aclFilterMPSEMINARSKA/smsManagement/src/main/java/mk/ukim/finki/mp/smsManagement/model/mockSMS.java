package mk.ukim.finki.mp.smsManagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

public class mockSMS {


		private int sms_id;
		
		private int id;
		
		private Korisnik sender;
		
		private List<Korisnik> receivers;
		
		public mockSMS(int sms_id, Korisnik sender, List<Korisnik> receivers, String body, Date date){
			this.sms_id=sms_id;
			this.receivers= receivers;
			this.sender=sender;
			this.body=body;
			this.seen=false;
			this.date=date;
		}
		
		private String body;
		
		private boolean seen;
		
		private Date date;

		public int getSms_id() {
			return sms_id;
		}

		public void setSms_id(int sms_id) {
			this.sms_id = sms_id;
		}

		public int getId() {
			return id;
		}

		public void setObj_id(int id) {
			this.id = id;
		}

		public Korisnik getSender() {
			return sender;
		}

		public void setSender(Korisnik sender) {
			this.sender = sender;
		}

		public List<Korisnik> getReceivers() {
			return receivers;
		}

		public void setReceivers(List<Korisnik> receivers) {
			this.receivers = receivers;
		}
		
		public void addReceiver(Korisnik r){
			this.receivers.add(r);
		}
		
		public void removeReceiver(Korisnik r){
			this.receivers.remove(r);
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public boolean isSeen() {
			return seen;
		}

		public void setSeen(boolean seen) {
			this.seen = seen;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date=date;
		}
		
		@Override
		public int hashCode() {
		final int prime = 31;
		int result = 1;
		Integer id=null;
		if(sms_id>=0)
			id=new Integer(sms_id);
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((id ==null ) ? 0 : id.hashCode());
		return result;
		}
	
}
