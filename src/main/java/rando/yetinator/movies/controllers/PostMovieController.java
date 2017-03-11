package rando.yetinator.movies.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.ConfigData;
import rando.yetinator.movies.MyHelper;
import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

@Controller
public class PostMovieController extends AbstractController{


	@RequestMapping("/trending")
	public String trending(Model model){
		//make this only accessible to registered users(make everything but signup only accessible to registered users.  
		//model.addAttribute("random", "trending");
		//TODO - narrow this to a list of the most popular or order by popularity
		org.springframework.data.domain.Sort a = new org.springframework.data.domain.Sort("title");
		List<MovieLike> movies = MovieLikeDao.findAll(a);
		
		model.addAttribute("movies", movies);
		
		
		return "testingagain";
		//return "trending";
		
	}
	
	@RequestMapping(value = "/trending", method = RequestMethod.POST)
	public String trendingPost(Model model, HttpServletRequest arequest){
		
		//String title = arequest.getParameter("movieTitle");
		Integer TMDBid = null;
		try{
			String numString = arequest.getParameter("movieId");
			System.out.println(numString);
			TMDBid = Integer.parseInt(numString);
			System.out.println(TMDBid);
			
			//TMDBid = Integer.getInteger(arequest.getParameter("movieId"));
		}catch(Exception e){
			System.out.println("There is a problem with the TMDBid get parameter");
		}
		//Create a movie object
		MovieService movie = new MovieService(TMDBid);
		String title = movie.getTitle();
		
		if(MovieDictionary.validMovie(title, TMDBid)){
			//save a movieLike after post request
			Integer useridloggedin = getUserFromSession(arequest.getSession()).getUid();
			System.out.println(title);
			User user = UserDao.findByuid(useridloggedin);
			//Removed from below useridloggedin, 
			MovieLike like = new MovieLike(title, user, TMDBid);
			//user.setLike(like);//TODO - GET RID OF THIS
			user.addLike(like);
			MovieLikeDao.save(like);
			System.out.println(like.getUser().getUserName());
			model.addAttribute("random", "Post trending");
		}else{
			model.addAttribute("random", "Bad Post");
		}
		
		
		
		//TODO - change to a list of trending movies?  
		return "home";
	}
	
	//TODO - Probably should be moved to a new controller
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public String userList(Model model, HttpServletRequest arequest){
		
		List<User> users = UserDao.findAll();
		//List<String> names;
		/*
		 for(User user : users){
			names.add(user.getUserName());
		}
		*/
		model.addAttribute("userTemplateTitle", "All Users");
		model.addAttribute("usersList", users);
		
		return "usersAll";
	}
	
	@RequestMapping(value = "/user/{UserName}", method = RequestMethod.GET)
	public String userSingle(@PathVariable String UserName, Model model, HttpServletRequest arequest){
		//Allows you to view a user page and friend that user
		
		User currentUser = getUserFromSession(arequest.getSession());
		User userOnPage = UserDao.findByUserName((String) UserName);
		
		//find list of movies
		//List<MovieLike> movieList = MovieLikeDao.findByUserUid(user.getUid());
		List<MovieLike> movieList = userOnPage.getLikes();
		//TODO - SORT
		
		//list of friends
		List<UserFriendsList> friendNums = UserFriendsListDao.findByUserOne(userOnPage.getUid());
		List<User> friends = new ArrayList<User>();
		List<User> mutualFriends = new ArrayList<User>();
		//create a list of friends using "userfriendlist" uid
		for(UserFriendsList i : friendNums){
			int uidsOfUserOnPageFriends = i.getUserTwo();
			User AFriendOfUserOnPage = UserDao.findByuid(uidsOfUserOnPageFriends);
			friends.add(AFriendOfUserOnPage);
			//mutual friends through helper class
			if(MyHelper.AreMutualFriends(AFriendOfUserOnPage, currentUser, UserFriendsListDao)){
				mutualFriends.add(AFriendOfUserOnPage);
			}
			
			//mutual friends?
			/*
			//in a for loop with i being the current iteration
			List<UserFriendsList> a = UserFriendsListDao.findByUserOne(uidsOfUserOnPageFriends);//This is all wrong
			if(UserFriendsListDao.findByUserOne(uidsOfUserOnPageFriends) != null)
				if()
				mutualFriends.add(AFriendOfUserOnPage);
			
			*/
			
			//class based mutual friend function
			/*
			System.out.println("waldo1");
			if(currentUser.isMutual(user))
				mutualFriends.add(it);
			*/
		}
		
		
		
		//test to see if logged in is friends with page owner
		boolean notFriendsAlready = true;
		if(false){
			//TODO - if logged in user friends list contains "user" from above set notFriendsAlready to false;
		}
		

		model.addAttribute("userTemplateTitle", userOnPage.getUserName());
		model.addAttribute("user", userOnPage);
		model.addAttribute("movieList", movieList);
		model.addAttribute("friends", friends);
		model.addAttribute("mutualFriends", mutualFriends);
		model.addAttribute("notFriendsAlready", notFriendsAlready);
		
		return "user";
	}
	
	@RequestMapping(value = "/user/{UserName}", method = RequestMethod.POST)
	public String userFriend(@PathVariable String UserName, Model model, HttpServletRequest arequest){
		//this post method handles the friend match of a user after it has been requested.  
		User user = UserDao.findByUserName((String) UserName);
		
		//Create Friends Entry in database
		int frienduserid = Integer.parseInt(arequest.getParameter("userid"));
		User currentUser = getUserFromSession(arequest.getSession());
	//if list of entries for UserOne contains an entry that includes frienduser make an error message.
		List<UserFriendsList> currentUserFriends = UserFriendsListDao.findByUserOne(currentUser.getUid());
		for(UserFriendsList aFriend : currentUserFriends){
			if(aFriend.getUserTwo() == frienduserid ){
				model.addAttribute("random", "You are already friends with " + UserName + ".");
				return "home";
			}
				
		}
		
		//Create a line for friends list table
		UserFriendsList friendEntry = new UserFriendsList(currentUser.getUid(),  frienduserid);
		UserFriendsListDao.save(friendEntry);
		
		
		//User friendUser = UserDao.findByuid(1);
		//currentUser.addFriend(friendUser);//this is for user class based friend addition instead of 'userfriendlist' class
		
	
		//return "redirect:/user/" + UserName;
		
		model.addAttribute("random", "You have friended " + UserName + ".");
		return "home";
		
	}
	
	@RequestMapping("/movie/{MovieName}")
	public String moviePage(Model model, @PathVariable String MovieName){
		//This maps a page displaying the movie and all its fun attributes and pictures and stuff
		//TODO fill out movie page and add hyperlinks to user page
		
		//create movie object (need to pass in id)
		//TODO IMPROVE MOVIE ID 
		int movieId = 550;
		MovieService movie = new MovieService(movieId);
		ConfigData data = new ConfigData();
		
		String imageBaseUrl = data.getImageBaseURL(0);
		//pass in movie object
		
		
		model.addAttribute("movieTemplateTitle", MovieName);
		model.addAttribute("movie", movie);
		model.addAttribute("imageBaseUrl", imageBaseUrl);
		
		
		return "movieDisplay";
	}
	
	
	
	
	
}
