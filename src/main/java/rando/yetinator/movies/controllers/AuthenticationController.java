package rando.yetinator.movies.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.ConfigData;
import rando.yetinator.movies.model.InviteEntry;
import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;

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
		model.addAttribute("random2", "On this site you: make friends, like movies, invite friends with similar movie tastes to a movie.");
		return "generic";
	}
	
	@RequestMapping("/home")
	public String userHome(Model model, HttpServletRequest arequest){
		//this is the user's home page and should display the user's personal movie invites among other things
		Integer useridloggedin = getUserFromSession(arequest.getSession()).getUid();
		User loggedIn = UserDao.findByuid(useridloggedin);
		//Create a list of invites "loggedin" is invited to
		List<InviteEntry> homeUserInvites = inviteEntryDao.findByInvited(loggedIn);
		
		//need a list of movies or MovieService to correspond with TMDBid from above list
		List<MovieService> correspondingMovies = new ArrayList<>();
		for(InviteEntry anInvite : homeUserInvites){
			MovieService aMovie = new MovieService(anInvite.getTmdbid());
			correspondingMovies.add(aMovie);
		}
		//MovieService theMovie = new MovieService(oneInvite.getTmdbid());
		ConfigData data = new ConfigData();
		
		String imageBaseUrl = data.getImageBaseURL(0);
		
		//List<User> invitedUsers = oneInvite.getInvited();
		model.addAttribute("correspondingMovies", correspondingMovies);
		model.addAttribute("homeUserInvites", homeUserInvites);
		model.addAttribute("imageBaseUrl", imageBaseUrl);
		//model.addAttribute("host", loggedIn);
		//model.addAttribute("guestList", oneEntry.getinviteList());
		//model.addAttribute("guestList", invitedUsers);
		model.addAttribute("random", "Welcome to my fancy Movies page. ");
		model.addAttribute("random2", "On this site you: make friends, like movies, invite friends with similar movie tastes to a movie.");
		
		
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
			model.addAttribute("random", "Welcome " + name);
			model.addAttribute("random2", "What would you like to do?");
			
		}else{
			//error logging on!
		}
		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/testing")
	public String testing(HttpServletRequest request, Model model){
		System.out.println("Testing Something");
		// Start test code
//		InviteEntry anEntry = inviteEntryDao.findByUid(3);
//		System.out.println(anEntry.getTmdbid());
//		System.out.println(anEntry.getMessage());
//		List<InvitedGuest> invitedpeeps = anEntry.getinviteList();
		/*
		for(InvitedGuest entry : invitedpeeps){
			User aUser = entry.getGuest();
			System.out.println(aUser.getUserName() + " " + aUser.getzipcode());
		}
		*/
		//System.out.println(x);
		
		return "testing";
	}
	
	@RequestMapping(value = "/populateDatabase", method= RequestMethod.GET)
	public String signedup(Model model){
		//posts and saves a user
		
		String[] names = {"Brian", "Sam", "Bob", "Jack", "Susie"};
		String password = "thisthis";
		String zipString = "63021";
		Integer zip = Integer.valueOf(zipString);
		int[][] movieIds = {{197,333484,941,944,942,943,115},{10144,321612,10020,812,11006},{4232,609,333484,11470},{333484,11452,11006},{333484,11006,10147}};
		
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
		
		return "generic";
	}
	
	
	
}
