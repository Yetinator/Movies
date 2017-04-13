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
		
		
		
		return "trendingJavaHeavy";
		
	}
	
	@RequestMapping(value = "/trending", method = RequestMethod.POST)
	public String movieConfirm(Model model, HttpServletRequest arequest){
		//There will often be more than one movie of the same title.  This confirms which one and returns
		//the correct TMDBid
		String title = arequest.getParameter("movieTitle");
		//I need to create an array of MovieService objects to pass into my movieConfirm page for selection
		
		int[] movieNumbers = MovieService.allMoviesOfName(title);
		MovieService[] movies = new MovieService[movieNumbers.length]; 
		for(int i = 0; i < movies.length; i++){
			movies[i] = new MovieService(movieNumbers[i]);
			
		}
		
		MovieService tempMovie = new MovieService(155);
		ConfigData data = new ConfigData();
		
		String imageBaseUrl = data.getImageBaseURL(0);
		//pass in movie object
		
		model.addAttribute("movies", movies);
		//model.addAttribute("movie", tempMovie);
		model.addAttribute("imageBaseUrl", imageBaseUrl);
		
		//return "redirect:/movieLikeSelected";Nope, I need a page in between
		return "movieConfirm";
		//return "generic";
	}
	
	@RequestMapping(value = "/movieLikeSelected", method = RequestMethod.POST)
	public String trendingPost(Model model, HttpServletRequest arequest){
		//This saves the movie like to the database
		
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
			User user = UserDao.findByuid(useridloggedin);

			MovieLike like = new MovieLike(title, user, TMDBid);
			//user.setLike(like);//TODO - GET RID OF THIS
			user.addLike(like);
			MovieLikeDao.save(like);
			model.addAttribute("random", "You have liked a movie.  Congrats!");
		}else{
			model.addAttribute("random", "Bad Post");
		}
		
		
		
		//TODO - change to a list of trending movies?  
		return "generic";
	}

	
	//TODO - Probably should be moved to a new controller
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public String userList(Model model, HttpServletRequest arequest){
		//Creates a list of users to display on the screen
		
		List<User> users = UserDao.findAll();

		model.addAttribute("userTemplateTitle", "All Users");
		model.addAttribute("usersList", users);
		
		return "usersAll";
	}
	
	@RequestMapping(value = "/user/{UserName}", method = RequestMethod.GET)
	public String userSingle(@PathVariable String UserName, Model model, HttpServletRequest arequest){
		//Allows you to view a user page and friend that user
		
		//Declare current user
		User currentUser = getUserFromSession(arequest.getSession());
		User userOnPage = UserDao.findByUserName((String) UserName);
		
		//find list of movies Users getLikes() function
		List<MovieLike> movieList = userOnPage.getLikes();
		//fill out tmdb list # Don't Really need this

		//TODO - SORT
		
		//list of friends
		List<UserFriendsList> friendNums = UserFriendsListDao.findByUserOne(userOnPage.getUid());
		List<User> friends = new ArrayList<User>();
		List<User> mutualFriends = new ArrayList<User>();
		//create a list of friends using "userfriendlist" DAO uid
		//also a list of mutual friends
		for(UserFriendsList i : friendNums){
			int uidsOfUserOnPageFriends = i.getUserTwo();
			User AFriendOfUserOnPage = UserDao.findByuid(uidsOfUserOnPageFriends);
			friends.add(AFriendOfUserOnPage);
			//mutual friends through helper class
			if(MyHelper.AreMutualFriends(AFriendOfUserOnPage, currentUser, UserFriendsListDao)){
				mutualFriends.add(AFriendOfUserOnPage);
			}//end if
		}//end for loop
		
		
		
		//test to see if logged in is friends with page owner
		boolean notFriendsAlready = true;
		if(false){
			//TODO - if logged in user friends list contains "user" from above set notFriendsAlready to false;
		}
		
		//model.addAttribute("movieObjects", movieObjects);//actual movieservice objects in an array
		model.addAttribute("userTemplateTitle", userOnPage.getUserName());
		model.addAttribute("user", userOnPage);
		model.addAttribute("movieList", movieList);//list of movieLikes
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
				return "generic";
			}
				
		}
		
		//Create a line for friends list table
		UserFriendsList friendEntry = new UserFriendsList(currentUser.getUid(),  frienduserid);
		UserFriendsListDao.save(friendEntry);
		
		model.addAttribute("random", "You have friended " + UserName + ".");
		return "generic";
		
	}
	
	@RequestMapping("/moviePage")
	public String moviePage(Model model, HttpServletRequest arequest){
		//current user
		User currentUser = getUserFromSession(arequest.getSession());
		//This maps a page displaying the movie and all its fun attributes and pictures and stuff
		int movieId = Integer.parseInt(arequest.getParameter("movieId"));
		//create movie object (need to pass in id)
		MovieService movie = new MovieService(movieId);
		ConfigData data = new ConfigData();
		String imageBaseUrl = data.getImageBaseURL(0);
		//create a list of users who like and pass it in
		int TMDBid = movie.getId();
		List<MovieLike> lotsALikes= MovieLikeDao.findByTmdbid(TMDBid);
		List<User> usersWhoLike = new ArrayList<User>();
		for(MovieLike like : lotsALikes){
			usersWhoLike.add(like.getUser());
		}
		//create a list of friends who like the movie (only friends)
		List<User> friendsWhoLike = new ArrayList<User>();
		//List<User> friends = currentUser.get
		for(User user : usersWhoLike){
			if (true){//(user.isMutual(currentUser)){
				if(!user.equals(currentUser)){
					friendsWhoLike.add(user);
				}
			}
		}
		
		
		//pass in movie object
		model.addAttribute("movie", movie);
		model.addAttribute("imageBaseUrl", imageBaseUrl);
		model.addAttribute("usersWhoLike", usersWhoLike);
		model.addAttribute("friendsWhoLike", usersWhoLike);
		
		return "movieDisplay";
	}
	
	
	
	
	
}
