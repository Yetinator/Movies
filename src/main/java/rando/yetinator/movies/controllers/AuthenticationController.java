package rando.yetinator.movies.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.dao.MovieDictionaryDao;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;

@Controller
public class AuthenticationController {
	
	@Autowired
	private UserDao UserDao;
	
	@Autowired
	private MovieLikeDao MovieLikeDao;
	
	@Autowired
	private MovieDictionaryDao MovieDictionaryDao;

	@RequestMapping("/")
	public String home(Model model){
		
		model.addAttribute("random", "This is working");
		
		return "home";
	}
	
	@RequestMapping(value = "/signup", method= RequestMethod.GET)
	public String createaccount(){
		
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method= RequestMethod.POST)
	public String signedup(Model model, HttpServletRequest arequest){
		//posts and saves a user
		
		String name = arequest.getParameter("name");
		String password = arequest.getParameter("password");
		String zipString = arequest.getParameter("zip");
		Integer zip = Integer.valueOf(zipString);
		//System.out.println(zip);
		//int zip = Integer.getInteger(arequest.getParameter("zip"));
		//TODO - some error checking and password checking
		User newUser = new User(name, password, zip);
		UserDao.save(newUser);
		//log user in for signup?
		
		model.addAttribute("fromController", "posted");
		return "home";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET )
	public String signin(Model model){
		
		model.addAttribute("random", "This is working");
		
		return "signin";
	}
	@RequestMapping(value = "/signin", method = RequestMethod.POST )
	public String signedIn (Model model, HttpServletRequest arequest){
		String name = arequest.getParameter("name");
		String password = arequest.getParameter("password");
		//TODO - bad user??
		User currentUser = UserDao.findByUserName(name);
		if(currentUser.checkHash(password)){
			//log on! How did this work in blogz again?? 
			model.addAttribute("random", "Should be signed in???");
			
		}else{
			//error logging on!
		}
		
		return "home";
	}
}
