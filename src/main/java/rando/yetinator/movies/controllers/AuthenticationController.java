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
import rando.yetinator.movies.MyHelper;
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
		
		//Populate user friend list
		//list of friends
				List<UserFriendsList> friendNums = UserFriendsListDao.findByUserOne(loggedIn.getUid());
				List<User> friends = new ArrayList<User>();
				List<User> mutualFriends = new ArrayList<User>();
				//create a list of friends using "userfriendlist" DAO uid
				//also a list of mutual friends
				for(UserFriendsList i : friendNums){
					int uidsOfUserOnPageFriends = i.getUserTwo();
					User AFriendOfUserOnPage = UserDao.findByuid(uidsOfUserOnPageFriends);
					friends.add(AFriendOfUserOnPage);
					//mutual friends through helper class
					if(MyHelper.AreMutualFriends(AFriendOfUserOnPage, loggedIn, UserFriendsListDao)){
						mutualFriends.add(AFriendOfUserOnPage);
					}//end if
				}//end for loop
		
		//Populate user movie-like list
		List<MovieLike> movieList = loggedIn.getLikes();

		//List<User> invitedUsers = oneInvite.getInvited();
		model.addAttribute("correspondingMovies", correspondingMovies);
		model.addAttribute("homeUserInvites", homeUserInvites);
		model.addAttribute("imageBaseUrl", imageBaseUrl);
		model.addAttribute("friends", friends);
		model.addAttribute("movieList", movieList);
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
	
	
	
	
	
}
