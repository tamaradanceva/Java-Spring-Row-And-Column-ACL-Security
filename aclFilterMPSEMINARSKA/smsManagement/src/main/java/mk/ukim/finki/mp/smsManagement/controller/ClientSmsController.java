package mk.ukim.finki.mp.smsManagement.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mk.ukim.finki.mp.smsManagement.model.Korisnik;
import mk.ukim.finki.mp.smsManagement.model.SMS;
import mk.ukim.finki.mp.smsManagement.payment.PaymentExecutor;
import mk.ukim.finki.mp.smsManagement.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/client")
@SessionAttributes(value = { "ime", "saldo", "broj" })
//@Secured("ROLE_PRIVATE")
public class ClientSmsController {

	@Autowired
	@Qualifier("customerServiceProxy")
	SmsService service;

	@Autowired
	PaymentExecutor paymentService;

	@RequestMapping(value = "/*")
	public String invalid() {
		return "redirect:loginClient";
	}

	@RequestMapping(value = { "/loginClient" }, method = RequestMethod.GET)
	public ModelAndView getLoginClient(HttpServletRequest request,
			HttpSession session) {
		ModelAndView result = new ModelAndView("loginClient");
		// if ima cookie otvori direktno myMessages ako ne nosi na login
		int br = checkCookieBroj(request);
		if (br != -1) {
			Korisnik k = service.getKorisnik(br);
			result.setViewName("myMessages");
			result.addObject("ime", k.getIme() + " " + k.getPrezime());
			result.addObject("saldo", paymentService.getSaldo(br));
			result.addObject("broj", br);
			List<SMS> lista = service.getSMSFromSender(br);
			result.addObject("listaSent", lista);

			List<SMS> lista1 = service.getSMSFromreceiver(br);
			result.addObject("listaReceived", lista1);

			return result;
		} else {
			return result;
		}
	}

	@RequestMapping(value = { "/loginClient" }, method = RequestMethod.POST)
	public ModelAndView postLoginClient(@RequestParam("number") String broj,
			@RequestParam(value = "cookie", required = false) String check,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("myMessages");
		int br = Integer.parseInt(broj);
		Korisnik k = service.getKorisnik(br);
		if (k != null) {
			result.setViewName("myMessages");
			List<SMS> lista = service.getSMSFromSender(br);
			result.addObject("listaSent", lista);
			List<SMS> lista1 = service.getSMSFromreceiver(br);
			result.addObject("listaReceived", lista1);
			result.addObject("ime", k.getIme() + " " + k.getPrezime());
			result.addObject("saldo", paymentService.getSaldo(br));
			result.addObject("broj", br);
			if (check != null) {
				saveUser(br, response);
			}
			return result;
		} else {
			String message = "Ne ste registrirani!";
			result.addObject("message", message);
			result.setViewName("loginClient");
			return result;
		}
	}

	@RequestMapping(value = { "/myMessages" }, method = RequestMethod.GET)
	public ModelAndView getsmsPregled(HttpServletRequest request,
			HttpSession session) {
		ModelAndView result = new ModelAndView("myMessages");
		Integer br = (Integer) session.getAttribute("broj");
		List<SMS> lista = service.getSMSFromSender(br.intValue());
		result.addObject("listaSent", lista);
		List<SMS> lista1 = service.getSMSFromreceiver(br.intValue());
		result.addObject("listaReceived", lista1);
		return result;
	}
	
	
	
	@RequestMapping(value={"/aclTest"},method=RequestMethod.GET)
	public ModelAndView getTest(HttpServletRequest request,HttpSession session){
		ModelAndView model= new ModelAndView("test");
		StringBuilder sb=new StringBuilder();
		double sum=0;
		double startTime=0;
		double endTime=0;
		List<SMS> list=null;
		//fillMap();
		
		Integer br = (Integer) session.getAttribute("broj");
		
		//int brSMS=service.getSmsList().size();
		int brSMS=service.getmockSMSFromSender(br).size();
		startTime=System.nanoTime();
		//list= new ArrayList<mockSMS>(service.getmockSMSFromSender(br));
		list=service.getSMSFromSender(br);
		endTime=System.nanoTime();
		double time=endTime-startTime;
		sum+=time;
		sb.append("TIME:"+time);
		sb.append("\n");
		sb.append("Number of rows:");
		sb.append(list.size());
		sb.append("\n");
		
		for(int i=0;i<list.size();i++){
			SMS s= list.get(i);
			sb.append("id:"+s.getSms_id()+" body:"+s.getBody()+" sender:"+s.getSender().toString()+" date:"+s.getDate());
			sb.append("\n");
		}
		model.addObject("msg", sb.toString());
		model.addObject("brSMS", brSMS);
		return model;
	}
	
	public void fillMap(){
		service.fillMap();
	}

	@RequestMapping(value = { "/show/{id}" }, method = RequestMethod.GET)
	public ModelAndView getshowAll(HttpServletRequest request,
			HttpSession session, @PathVariable int id) {
		ModelAndView result = new ModelAndView("filterSMS");
		Integer br = (Integer) session.getAttribute("broj");
		List<SMS> lista = service.getFilter(br.intValue(), id);
		List<SMS> lista1 = service.getFilter(id, br.intValue());
		for (int i = 0; i < lista1.size(); i++) {
			SMS s = (SMS) lista1.get(i);
			lista.add(s);
		}
		result.addObject("listaAll", lista);
		return result;
	}

	@RequestMapping(value = { "markAsSeen/{id}" }, method = RequestMethod.GET)
	public @ResponseBody
	boolean markSMS(HttpServletRequest request, HttpSession session,
			@PathVariable int id) {
		SMS s = service.getSMS(id);
		s.setSeen(true);
		service.updateSeen(s);
		return true;
	}

	@RequestMapping(value = { "/sendClientSms" }, method = RequestMethod.GET)
	public ModelAndView getSendClientSms(HttpServletRequest request,
			HttpSession session) {
		ModelAndView result = new ModelAndView("sendClientSms");
		return result;
	}

	@RequestMapping(value = { "/sendClientSms" }, method = RequestMethod.POST)
	public ModelAndView postSendClientSms(HttpServletRequest request,
			HttpSession session, @RequestParam("body") String body,
			@RequestParam(value = "receiver") String receiver) {
		// request param od povise vrednosti zemeni od multiple select
		ModelAndView result = new ModelAndView("messageSent");
		Integer br = (Integer)session.getAttribute("broj");
			// napravi niza od int od stringot
			String[] t = receiver.split(",");
			Korisnik sd = service.getKorisnik(br.intValue());
			List<Korisnik> receivers = new ArrayList<Korisnik>();
			double total=0;
			boolean send = true;
			for (int i = 0; i < t.length; i++) {
				Integer broj = Integer.parseInt(t[i]);
				Korisnik k = service.getKorisnik(broj);
				if (k == null) {
					send = false;
					break;
				} else {
					receivers.add(k);
				}
			}
			if (sd != null && send == true) {
				boolean b = service.sendSMS(sd, body, receivers);
				for(int i=0;i<receivers.size();i++){
					total+=paymentService.sendSms(sd.getBroj(), receivers.get(i).getBroj(), body);
				}
				if (b == true) {
					result.addObject("saldo", (double)session.getAttribute("saldo")-total);
					return result;
				} else {
					result.setViewName("sendClientSms");
					result.addObject("message",
							"You didn't send your message, try again ");
					return result;
				}
			} else {
				result.setViewName("sendClientSms");
				result.addObject("message",
						"You didn't send your message, try again with valid numbers");
				return result;
			}
	}
	
	
	@RequestMapping(value = { "/filterDate" }, method = RequestMethod.GET)
	public ModelAndView getByDate(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView model= new ModelAndView("filterDate");
		model.addObject("date", new Date());
		return model;
	}
	
	@RequestMapping(value = { "/filterDate" }, method = RequestMethod.POST)
	public ModelAndView getByDate(@RequestParam(value="datepicker") String date, HttpSession session) {
		ModelAndView model= new ModelAndView("filterDate");
		Integer br = (Integer)session.getAttribute("broj");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date d= null;
		try {
			 
			d = formatter.parse(date);
	 
		} catch (ParseException e) {
			return null;
		}
		
		List<SMS> lista= service.getReceivedByDate(br.intValue(), d);
		List<SMS> lista1= service.getSentByDate(br.intValue(), d);
		System.out.println("lista size"+lista.size());
		System.out.println("data"+d.toString());
		model.addObject("lista", lista);
		model.addObject("lista1", lista);
		return model;
	}

	@RequestMapping(value = { "/forgetMyNumber" }, method = RequestMethod.GET)
	public ModelAndView forgetMe(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("redirect:loginClient");
		// invalidate cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int idx = 0; idx < cookies.length; idx++) {
				Cookie c = cookies[idx];
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
		// invalidate session
		request.setAttribute("status", "Your session with id "
				+ request.getSession().getId()
				+ " is deleted<br/> Your cookie is also deleted.");
		request.getSession().invalidate();
		return model;
	}

	// najdi cookie number
	private int checkCookieBroj(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Korisnik k = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("number")) {
					int br = Integer.parseInt(cookie.getValue());
					k = service.getKorisnik(br);
					break;
				}
			}
		}
		if (k != null) {
			return k.getBroj();
		} else {
			return -1;
		}
	}

	// zacuvaj broj na klient vo cookie
	private void saveUser(int broj, HttpServletResponse response) {
		Cookie c = new Cookie("number", new Integer(broj).toString());
		int sixMonthsSeconds = 1 * 31 * 24 * 60 * 60;
		c.setMaxAge(sixMonthsSeconds);
		response.addCookie(c);
	}

}
