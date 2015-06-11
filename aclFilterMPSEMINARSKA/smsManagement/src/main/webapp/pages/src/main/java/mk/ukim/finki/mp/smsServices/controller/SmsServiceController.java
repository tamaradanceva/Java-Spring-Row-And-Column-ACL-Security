package mk.ukim.finki.mp.smsServices.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mk.ukim.finki.mp.smsServices.model.Korisnik;
import mk.ukim.finki.mp.smsServices.model.SMS;
import mk.ukim.finki.mp.smsServices.payment.PaymentExecutor;
import mk.ukim.finki.mp.smsServices.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { "username","saldo" })
// sekogash ko kje gi koristime vo model and oviie aftomatski da se prajt set
public class SmsServiceController {

	@Autowired
	SmsService service;

	@Autowired
	PaymentExecutor payment;

	@RequestMapping(value = "/*")
	public String invalid() {
		return "redirect:broj";
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/custom_login")
	public String showLogin() {
		return "login";// ja vrakjat login stranata //rabotit i bez ova
	}
	
*/
	@RequestMapping(value = { "broj" }, method = RequestMethod.GET)
	@Secured("ROLE_PRIVATE")
	public ModelAndView getbroj(HttpServletRequest request, HttpSession session) {
		ModelAndView result = new ModelAndView("broj");
		// if nema cookie broj else smsPregled
		Cookie[] cookies = request.getCookies();//vo niza se stavet site coockie
		Korisnik k = null;
		if (cookies != null) {//ako nizata ne e prazna
			for (int i = 0; i < cookies.length; i++) {//izminija
				Cookie cookie = cookies[i];//zemi gi posebno kolachinjata
				if (cookie.getValue().equals("broj")) {//i ako vo kolacheto se sodrzi brojot so brojot sho go vnesvime ,broj e int od korisnik
					int br = Integer.parseInt(cookie.getValue());//zemi go brojot ko integer
					k = service.getKorisnik(br);//i najdi go korisnikot so toj broj
					break;
				}
			}
		}
		if (k != null) { //ako najde takov korisnik premini na nova strana smsPregled
			result.setViewName("smsPregled"); // go vrakjame sms pregled izmenat  so listata
			List<SMS> lista = service.getSMSFromSender(k.getBroj());//
			result.addObject("sentSMS", lista);
			List<SMS> lista1 = service.getReceivedSMS(k.getBroj());
			result.addObject("receivedSMS", lista1);
			result.addObject("saldo", payment.getSaldo());// aftomatski im se
															// prajt set deka se atributi na sesija gore
			// saldo i broj
		} else {			
		    result.setViewName("broj");
		}
		return result;
	}

	@RequestMapping(value = { "broj" }, method = RequestMethod.POST)
	@Secured("ROLE_PRIVATE")
	public ModelAndView postbroj(@RequestParam int broj,//ova int broj,broj e isto imenovana kako vo stranata broj atributot od input type=text
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView result = new ModelAndView("smsPregled");//vrakjat stranata smspregled izmeneta so informacii od tojuser so toj broj
		String username = request.getUserPrincipal().getName();
		result.setViewName("smsPregled");
		List<SMS> lista = service.getSMSFromSender(broj);
		result.addObject("sentSMS", lista);
		List<SMS> lista1 = service.getReceivedSMS(broj);
		result.addObject("receivedSMS", lista1);
		result.addObject("saldo", payment.getSaldo());
		saveUser(broj, response);   //se zachuvuva userot odredeno vreme 
		return result;
		
	}
	
	private void saveUser(int broj, HttpServletResponse response) {
		Cookie c = new Cookie("broj", new Integer(broj).toString());
		int oneMonthSeconds = 1 * 31 * 24 * 60 * 60;
		c.setMaxAge(oneMonthSeconds);//max da se zachuva eden mesec 
		response.addCookie(c);
	}
	

	
	@RequestMapping(value = { "smsPregled" }, method = RequestMethod.GET)//istot kako Get baranjeto od broj
	@Secured("ROLE_PRIVATE")
	public ModelAndView getsmsPregled(HttpServletRequest request,
			HttpSession session) {
		ModelAndView result = new ModelAndView("smsPregled");
		Cookie[] cookies = request.getCookies();
		Korisnik k = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getValue().equals("broj")) {
					int br = Integer.parseInt(cookie.getValue());
					k = service.getKorisnik(br);
					break;
				}
			}
		}
		if (k != null) {
			List<SMS> lista = service.getSMSFromSender(k.getBroj());
			result.addObject("sentSMS", lista);
			List<SMS> lista1 = service.getReceivedSMS(k.getBroj());
			result.addObject("receivedSMS", lista1);//receivedSMS i sentSMS se koristat vo jsp
			result.setViewName("smsPregled");
			return result;
		} else {
			result.setViewName("broj");
			return result;
		}
	}
	
	private Korisnik getUser(HttpServletRequest request) {//,da se najde korisnik vo zavisnost od baranjeto na sesijata
		Korisnik k = null;//preku cookies...
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("broj")) {
					int br = Integer.parseInt(cookie.getValue());
					k = service.getKorisnik(br);//preku querry baranje
					break;
				}
			}
		}
		return null;
	}

	@RequestMapping(value = { "list/{id}" }, method = RequestMethod.GET)
	@Secured("ROLE_PRIVATE")
	public ModelAndView getlist(@PathVariable int id,
			HttpServletRequest request, HttpSession session) {
		ModelAndView result = new ModelAndView("list");
		Korisnik k = getUser(request);
		
		List<SMS> lista1 = service.getReceivedSMS(id);
		List<SMS> lista = service.getSMSFromSender(id);
		for (int i = 0; i < lista1.size(); i++) {
			lista.add(lista1.get(i));
		}
		result.addObject("listaSMS", lista);//spoj gi vo edna lista

		return result;
	}

	@RequestMapping(value = { "sendAdmin" }, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ModelAndView getAdminPage() {
		ModelAndView result = new ModelAndView("sendAdmin");
		return result;
	}

	@RequestMapping(value = { "receive" }, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody
	boolean postAdminPage(HttpServletRequest request, HttpSession session,
			@RequestBody SMS s) {
		if (service.getKorisnik(s.getSender().getBroj()) != null
				&& service.getKorisnik(s.getReceiver().getBroj()) != null) {
			service.sendSMS(s.getSender(), s.getReceiver(), s.getBody());
			return true;
		} else
			return false;
	}

	@RequestMapping(value = { "sendSms" }, method = RequestMethod.GET)
	@Secured("ROLE_PRIVATE")
	public ModelAndView getpratiPoraka(HttpServletRequest request,
			HttpSession session) {
		ModelAndView result = new ModelAndView("sendSms");
		Cookie[] cookies = request.getCookies();///najdi go korisnikot so soodvetnoto coockie za koj sho si najaven
		Korisnik k = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("broj")) {
					int br = Integer.parseInt(cookie.getValue());
					k = service.getKorisnik(br);
					break;
				}
			}
		}

		if (k != null) {//ako najdesh takov korisnik
			//probaj da gi najjdish site korisnici osven ti,if korisnik.id!=coockie.id togash dodavaj vo listata kor

			List<Korisnik> kor = service.getKorisnici();//vo nekoja lista kor,stavi gi site korisnici
			result.addObject("Contacts", kor);
			return result;
		} else {
			result.setViewName("broj");
			return result;
		}
	}

	@RequestMapping(value = { "sendSms" }, method = RequestMethod.POST)
	@Secured("ROLE_PRIVATE")
	public ModelAndView postpratiPoraka(HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "select", required = true) String[] select,
			@RequestParam("body") String body) {
		ModelAndView result = new ModelAndView("successPage");

		Cookie[] cookies = request.getCookies();
		Korisnik k = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("broj")) {
					int br = Integer.parseInt(cookie.getValue());
					k = service.getKorisnik(br);
					break;
				}
			}
		}

		int[] receivers = new int[select.length];
		//napravi lista  od integer vrednostina primachi na taa poraka,najgolema dolzina da e kolku select nizata
 
		
			for (int i = 0; i < select.length; i++) {
			Integer br = Integer.parseInt(select[i]);
			receivers[i] = br.intValue();//nova lista so selectiranite ama vo integer
		 }
			
		List<Korisnik> listaReceivers = new ArrayList<Korisnik>();//nova lista Receiveri  od Korisnici
		for (int i = 0; i < receivers.length; i++) {
			listaReceivers.add(service.getKorisnik(receivers[i]));//dodavaj gi Korisnicite so soodvetnoto ID
		}

		for (int i = 0; i < listaReceivers.size(); i++) {//izmini gi site vo listata 
			Korisnik receive = (Korisnik) listaReceivers.get(i);//najdi go korisnikot koj sho trebit da ja primit porakta get(i)
			service.sendSMS(k, receive, body);//  i prati ja porakta od korisnikot,do receive so soodvetniot tekst sho go primame kako requestparam
		}
		// Korisnik rc = service.getKorisnik(r);
		// service.sendSMS(k, rc, body) ;
		int saldo = (int) session.getAttribute("saldo");//manipuliraj so saldoto
		result.addObject("saldo",
		payment.odzemiSaldo(saldo, listaReceivers.size() * 5));
	
				
		return result;
	}
	
	@RequestMapping("forget")
	public String forgetUser(HttpServletRequest request,
			HttpServletResponse response) {
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
		request.setAttribute("status", " Вашата сесија со број "
				+ request.getSession().getId()
				+ " е успешно избришана<br/> Избришано е и вашето колаче");

		request.getSession().invalidate();
		return "forgetUser";
	}

}
