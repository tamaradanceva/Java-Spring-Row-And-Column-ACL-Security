package mk.ukim.finki.mp.smsManagement.controller;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mp.smsManagement.model.AdminMsgParam;
import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.payment.PaymentExecutor;
import mk.ukim.finki.mp.smsManagement.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin")
//@Secured("ROLE_ADMIN")
public class AdminSmsController {
	
	@Autowired
	@Qualifier("customerServiceProxy")
	private SmsService service;
	
	@Autowired
	private PaymentExecutor paymentService;
	
	@RequestMapping(value = "/*")
	public ModelAndView getReceive(){
		ModelAndView model= new ModelAndView("sendAdminSms");
		model.addObject("adminSMS",new AdminMsgParam());
		return model;
	}
	
	@RequestMapping(value = { "/receive" }, method = RequestMethod.POST)
	public @ResponseBody boolean  postSendAdminSms(@RequestBody AdminMsgParam param) {
			boolean result= true;
			String [] t= param.getReceivers().split(",");
			String senderStr = param.getSender();
			Korisnik sd= service.getKorisnik(Integer.parseInt(senderStr));
			List<Korisnik> receivers = new ArrayList<Korisnik>();
			boolean send = true;
			for (int i = 0; i < t.length; i++) {
				Integer broj = Integer.parseInt(t[i]);
				Korisnik k = service.getKorisnik(broj);
				if (k == null) {
					return false;
				} else {
					receivers.add(k);
				}
			}
			if (sd != null && send == true) {
				boolean b = service.sendSMS(sd, param.getBody(), receivers);
				for(int i=0; i<receivers.size();i++){
					paymentService.sendSms(sd.getBroj(), receivers.get(i).getBroj(), param.getBody());
				}
				if (b == true) {
					return result;
				} else {
					return false;
				}
			} else {
				return false;
			}
	}
	
	
}
