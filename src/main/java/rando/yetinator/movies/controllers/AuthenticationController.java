package rando.yetinator.movies.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.dao.MovieDictionaryDao;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;

@Controller
public class AuthenticationController extends AbstractController{
	
	//@Autowired
	//private UserDao UserDao;
	
	//@Autowired
	//private MovieLikeDao MovieLikeDao;
	
	//@Autowired
	//private MovieDictionaryDao MovieDictionaryDao;

	@RequestMapping("/")
	public String home(Model model){
		
		model.addAttribute("random", "Welcome to my fancy Movies page. ");
		model.addAttribute("random2", "This is a sight where you can friend people with similar movie tastes and if you have a movie like in common a fellow movie goer will be recomended.");
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
		//TODO - error check input for zipcode
		User newUser = new User(name, password, zip);
		UserDao.save(newUser);
		//log user in for signup?
		
		
		model.addAttribute("fromController", "new user created");
		return "redirect:/signin";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET )
	public String signin(Model model){
		
		model.addAttribute("random", "You are signed in.");
		
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
			HttpSession session = arequest.getSession();
			setUserInSession(session, currentUser);
			//this attribute is for the home link
			model.addAttribute("random", "You are signed in as " + name);
			
		}else{
			//error logging on!
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
	
	//TODO - make a JSON controlled link to Movies API Database
	@RequestMapping(value = "/API")//, method = RequestMethod.GET) Changed this up
	public String APITest(HttpServletRequest request, Model model){
		String apiKey = "e34f926e66fa7ffcaaea86c905cf10de";
		String apiLink = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=Jack+Reacher";
		//System.out.println("API Link is " + apiLink);
		//Borrowed form /trending
		org.springframework.data.domain.Sort a = new org.springframework.data.domain.Sort("title");
		List<MovieLike> movies = MovieLikeDao.findAll(a);
		
		model.addAttribute("movies", movies);
		
		//return "redirect:" + apiLink; 
		return "testing";
	}
	@RequestMapping(value = "/API2", method = RequestMethod.POST)
	public void API2(@RequestBody Map<String, Object> payload) 
		    throws Exception {

		  System.out.println(payload);

		} 
	/*
	@RequestMapping(value = "/APIJava")//, method = RequestMethod.GET) Changed this up
	public String APITestAgain(HttpServletRequest request, Model model){
		String apiKey = "e34f926e66fa7ffcaaea86c905cf10de";
		String apiLink = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=Jack+Reacher";
		//System.out.println("API Link is " + apiLink);
		//Borrowed form /trending
		org.springframework.data.domain.Sort a = new org.springframework.data.domain.Sort("title");
		
		var req = new XMLHttpRequest();
		req.open("GET", "http://api.themoviedb.org/2.1/Movie.search/en/json/XXX/immortals?callback=foobar", true);
		req.send();
		req.onreadystatechange=function() {
		   if (req.readyState==4 && req.status==200) {
		      console.log(req.responseText); 
		   }
		}
		
		//return "redirect:" + apiLink; 
		return "testing";
	}
	*/
	/*
	@RequestMapping("/testing2")
	public @ResponseBody List<MovieService> performLooseSearch(@RequestParam("CHARS") String chars)
	{
		 
		return exampleObjects;
	}*/
}
