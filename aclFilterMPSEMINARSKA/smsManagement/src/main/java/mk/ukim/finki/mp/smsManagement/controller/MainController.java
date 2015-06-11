package mk.ukim.finki.mp.smsManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value="/")
	public ModelAndView mainPage() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/index")
	public ModelAndView indexPage() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/custom_login")
	public String showLogin() {
		return "login";
	}
}
