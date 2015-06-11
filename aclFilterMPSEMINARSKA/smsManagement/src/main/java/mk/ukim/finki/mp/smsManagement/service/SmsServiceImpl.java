package mk.ukim.finki.mp.smsManagement.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mk.ukim.finki.mp.smsManagement.aop.MyAclFilter;
import mk.ukim.finki.mp.smsManagement.dao.KorisnikDao;
import mk.ukim.finki.mp.smsManagement.dao.SmsDao;
import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;
import mk.ukim.finki.mp.smsManagement.model.mockSMS;
import mk.ukim.finki.mp.smsManagement.payment.PaymentExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("SmsService")
@Transactional
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsDao smsDao;

	@Autowired
	private KorisnikDao korDao;

	@Autowired
	private PaymentExecutor paymentExecutor;

	@Autowired
	private MutableAclService mutableAclService;

	public Map<Integer, mockSMS> smsList = new HashMap<Integer, mockSMS>();
	
	
	private SmsDao getSmsDao() {
		return smsDao;
	}


	private KorisnikDao getKorDao() {
		return korDao;
	}


	private PaymentExecutor getPaymentExecutor() {
		return paymentExecutor;
	}


	private MutableAclService getMutableAclService() {
		return mutableAclService;
	}


	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}


	public void setKorDao(KorisnikDao korDao) {
		this.korDao = korDao;
	}


	public void setPaymentExecutor(PaymentExecutor paymentExecutor) {
		this.paymentExecutor = paymentExecutor;
	}


	public void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}


	public Map<Integer, mockSMS> getSmsList() {
		return smsList;
	}


	public void setSmsList(Map<Integer, mockSMS> smsList) {
		this.smsList = smsList;
	}


	//postfilter
	@Override
	@PostFilter("(hasPermission(filterObject, 'ADMINISTRATION') or ( hasAnyRole('ROLE_ADMIN','ROLE_PRIVATE') and filterObject.getBody().contains('bla'))) and filterObject.getId()%2==0")
	public Collection<mockSMS> getmockSMSFromSender(int broj) {
	//	return smsDao.getSMSbySender(broj);
		return new ArrayList<mockSMS>(smsList.values());
	}

	//postfilter
	@Override
	@PostFilter("hasPermission(filterObject, 'READ') and hasRole('ROLE_ADMIN')")
	public Collection<mockSMS> getmockSMSFromreceiver(int broj) {
		return new ArrayList<mockSMS>(smsList.values());
	}

	@Override
	public SMS getSMS(int sms_id) {
		return smsDao.getSms(sms_id);
	}

	@Override
	public Korisnik getKorisnik(int broj) {
		return korDao.getKorisnik(broj);
	}

	@Override
	public boolean sendSMS(Korisnik sender, String body,
			List<Korisnik> receivers) {
		SMS newSms=smsDao.saveSMS(sender, body, receivers);
		ObjectIdentity oid = new ObjectIdentityImpl(SMS.class, newSms.getId());
		MutableAcl acl = mutableAclService.createAcl(oid);
		UserDetails userDetails =(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     	acl.insertAce(0, BasePermission.ADMINISTRATION,new PrincipalSid(userDetails.getUsername()), true);
		acl.insertAce(1, BasePermission.READ,new GrantedAuthoritySid("ROLE_PRIVATE"), true);
		mutableAclService.updateAcl(acl);
		//for mockSMS 
//		mockSMS msms=new mockSMS(newSms.getSms_id(), sender, receivers, body, newSms.getDate());
//		//acl logic for new SMS tuple
//		Integer id = new Integer(Math.abs(msms.hashCode()));
//		ObjectIdentity oid = new ObjectIdentityImpl(mockSMS.class, id);
//		MutableAcl acl = mutableAclService.createAcl(oid);
//		UserDetails userDetails =(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		acl.insertAce(0, BasePermission.ADMINISTRATION,new PrincipalSid(userDetails.getUsername()), true);
//		acl.insertAce(1, BasePermission.READ,new GrantedAuthoritySid("ROLE_PRIVATE"), true);
//		mutableAclService.updateAcl(acl);
//		msms.setObj_id(id);
//		smsList.put(id, msms);
//		System.out.println("SIZEEEEEEEEEEEEEEEEEEEEE : "+smsList.size());
		return true;
	}

	@Override
	public void addSms(int broj, SMS s) {
		korDao.addSMS(broj, s);
	}

	@Override
	public void addKorisnik(int sms_id, Korisnik k) {
		smsDao.addKorisnik(sms_id, k);
	}

	@Override
	public void updateSeen(SMS s) {
		smsDao.updateSeen(s);
	}

	@Override
	public List<SMS> getFilter(int s, int r) {
		return smsDao.getBySenderAndReceiverFilter(s, r);
	}

	@Override
	public List<SMS> getSentByDate(int broj, Date date) {
		return smsDao.getSentByDate(broj, date);
	}

	@Override
	public List<SMS> getReceivedByDate(int broj, Date date) {
		return smsDao.getReceivedByDate(broj, date);
	}

	@Override
	@MyAclFilter(permission="READ", typeOfPermission="BasePermission")
	public List<SMS> getSMSFromSender(int broj) {
		return smsDao.getSMSbySender(broj);
	}

	@Override
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<SMS> getSMSFromreceiver(int broj) {
		return smsDao.getSMSbyReceiver(broj);
	}


	@Override
	public void fillMap() {
		List<SMS> list1= getSMSFromSender(1);
		SMS s1= list1.get(1);
		mockSMS ms= new mockSMS(s1.getSms_id(), s1.getSender(),s1.getReceivers(), s1.getBody(), s1.getDate());
		int id=ms.getSms_id();
		for(int i=0;i<1500;i++){
			ms.setSms_id(id+i);
			if(i<200){
				ms.setBody("nope");
			}
			else ms.setBody("bla");
			mockSMS mss= new mockSMS(ms.getSms_id(),ms.getSender(),ms.getReceivers(),ms.getBody(),ms.getDate());
			Integer idd = new Integer(Math.abs(mss.hashCode()));
			ObjectIdentity oid = new ObjectIdentityImpl(mockSMS.class, idd);
			MutableAcl acl = mutableAclService.createAcl(oid);
			UserDetails userDetails =(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			acl.insertAce(0, BasePermission.ADMINISTRATION,new GrantedAuthoritySid("ROLE_ADMIN"), true);
			acl.insertAce(1, BasePermission.READ,new GrantedAuthoritySid("ROLE_PRIVATE"), true);
			mutableAclService.updateAcl(acl);
			mss.setObj_id(idd);
			smsList.put(idd, mss);
			System.out.println("SIZEEEEEEEEEEEEEEEEEEEEEEEEEEE :"+smsList.size());
		}
		
	}


}
