package mk.ukim.finki.mp.smsManagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@FilterDef(name="stockRecordFilter", 
//parameters=@ParamDef( name="stockRecordFilterParam", type="date" ) )
public class SMS {

	@Id
	@GeneratedValue
	private int sms_id;
	
	private int oid;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Korisnik sender;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="sms_receivers",
	joinColumns=@JoinColumn(name="sms_id"),
	inverseJoinColumns=@JoinColumn(name="broj"))
	private List<Korisnik> receivers;
	
	public SMS(){
		receivers= new ArrayList<Korisnik>();
	}
	
	@NotNull
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
		return oid;
	}

	public void setId(int oid) {
		this.oid = oid;
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
