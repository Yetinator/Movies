package rando.yetinator.movies.controllers;

import java.util.ArrayList;
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

import rando.yetinator.movies.model.InviteEntry;
import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;
import rando.yetinator.movies.model.dao.InviteEntryDao;
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
	
	@RequestMapping("/testing")
	public String testing(HttpServletRequest request, Model model){
		System.out.println("Testing Somthing");
		// Start test code
		
		List <User> guests = new ArrayList<User>();
		guests.add(UserDao.findByuid(2));
		guests.add(UserDao.findByuid(1));
		User host = UserDao.findByuid(2);
		System.out.println("The host will be " + host.getUserName());
		for(User guest : guests){
			System.out.println("the guest will be " + guest.getUserName());
		}
		
		
		InviteEntry testItem = new InviteEntry(host,guests,333485, "Hey Hey Mama it's the way you dance. It's the way you grooooove, yeah!");
		inviteEntryDao.save(testItem);
		
		
		
		return "testing";
	}
	
	@RequestMapping(value = "/populateDatabase", method= RequestMethod.GET)
	public String signedup(Model model){
		//posts and saves a user
		
		String[] names = {"Brian", "Sam", "Bob", "Jack", "Susie"};
		String password = "thisthis";
		String zipString = "63021";
		Integer zip = Integer.valueOf(zipString);
		int[][] movieIds = {{197,333484,941,944,942,943,115},{10144,321612,10020,812},{4232,609,333484,11470},{333484},{333484}};
		
		for(String name : names){
			User newUser = new User(name, password, zip);
			UserDao.save(newUser);
		}
		//Create and like movie
		
		for(Integer i = 0; i < names.length; i++){
			for(int j = 0; j < movieIds[i].length; j++){
				System.out.println("length = " + movieIds[i].length);
				System.out.println("in movieLike Loop " + i + " " + j);
				int TMDBid = movieIds[i][j];
				System.out.println("TMDBid is " + TMDBid);
				MovieService movie = new MovieService(TMDBid);
				String title = movie.getTitle();
				System.out.println("the title is " + title);
				if(MovieDictionary.validMovie(title, TMDBid)){
					//save a movieLike after post request
					User user = UserDao.findByuid(i+1);
					
					System.out.println("the user name is " + user.getUserName() + user.getUid());
					MovieLike like = new MovieLike(title, user, TMDBid);
					System.out.println("the getUser is " + like.getUser().getUserName());
					//user.setLike(like);//TODO - GET RID OF THIS
					//user.addLike(like);
					System.out.println("waldo");
					MovieLikeDao.save(like);
					System.out.println("like created");
				}else{
					System.out.println("like screwed up");
				}
			}//end inner for loop "j"
		}//end for loop
		
		//create Friends List
		Integer[][] friendsArray = {{3,4},{3},{3,4},{1,4},{1,2}};
		for(Integer guy = 0; guy < names.length; guy++){
			for(int friend = 0; friend < friendsArray[guy].length; friend++){
				//populate each friend
				UserFriendsList friendEntry = new UserFriendsList(guy + 1,  friendsArray[guy][friend]);
				UserFriendsListDao.save(friendEntry);
				
			}
		}
		System.out.println("Redirecting Now");
		model.addAttribute("random", "You have populated your database.");
		return "redirect:/home";
	}
	
	
	//TODO - make a JSON controlled link to Movies API Database
	/*
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

		} */
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
