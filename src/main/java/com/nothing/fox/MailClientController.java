package com.nothing.fox;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.nothing.fox.utils.EmailServer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MailClientController {
	
	@Autowired
	EmailServer emailServer;

	@RequestMapping("/")
	public String goToLoginPage(HttpServletRequest req) {

		
		
		MailResponse mailResp = (MailResponse) req.getAttribute("mailResp") != null
				? (MailResponse) req.getAttribute("createAdResponse")
				: new MailResponse("init", "init");

		System.err.println("@@@ mailResp :: " + mailResp.toString());
		if (mailResp.getStatus().equalsIgnoreCase("success")) {

			req.setAttribute("mailResp", new MailResponse(mailResp.getStatus(), mailResp.getMessage()));

			System.err.println("adReponse @@@ :: " + mailResp.getStatus());
		} else {

			req.setAttribute("mailResp", new MailResponse("init", "init"));
		}

		return "mail";
	}

	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public RedirectView sendMail(@RequestParam(name = "files", required = true) List<String> files,
			RedirectAttributes reqAtt, HttpServletRequest request) {

		log.info("@@@ inside sendMail  ");

		files.forEach(e -> log.info(e));

		MailResponse mailResponse = null;

		log.info("calling mail server");
		String to = (String) request.getParameter("to");
		String subject =  (String) request.getParameter("subject");
		String body =  (String) request.getParameter("body");

		try {
			emailServer.sendEmail(to, subject, body);
			mailResponse = new MailResponse("success", "mail sent");

		} catch (Exception e2) {

			mailResponse = new MailResponse("error", "mail not sent");
			log.error("@@@ exception {} :: Email not sent", e2);
		}

		reqAtt.addFlashAttribute("mailResp", mailResponse);

		return new RedirectView("/");
	}

//	@RequestMapping("/showDashboard")
//	public String goToDashboard(HttpServletRequest req) {
//
//		
//		
//		AdResponse adResponse = (AdResponse) req.getAttribute("createAdResponse") != null ? (AdResponse) req.getAttribute("createAdResponse") :  new AdResponse("init", "init");
//		
//		System.err.println("@@@ adResponse :: " + adResponse.toString());
//		if(adResponse.getStatus().equalsIgnoreCase("success")) {
//			
//			req.setAttribute("createAdResponse", new AdResponse(adResponse.getStatus(), adResponse.getMessage()));
//
//			System.err.println("adReponse @@@ :: " + adResponse.getStatus());
//		}
//		
//		
//		req.setAttribute("createAdResponse", new AdResponse("init", "init"));
//
//		
//		return "dashboard";
//	}
//
//	@RequestMapping("/signup")
//	public String goToSignupPage() {
//
//		return "signup";
//	}
//
//	@RequestMapping("/forget-password")
//	public String goToForgetPwd() {
//		return "signup";
//	}
//
//	@RequestMapping("/showWelcomePage")
//	public String goToLandingPage() {
//
//		return "home";
//	}
//	

//	@GetMapping("/*")
//	public String objectUrl(HttpServletRequest req) {
//		
//		System.out.println(req.getRequestURI());
//		
//		return "";
//		
//	}
//	

	public static void main(String[] args) {
		File file = new File("text.txt");

		// file.w

	}
}
