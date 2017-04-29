package rando.yetinator.movies.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

@Controller		
public class AdministrativeController extends AbstractController{
	
	@RequestMapping("/admin")
	public String home(Model model){
		
		model.addAttribute("random", "This is the administrative page.  You can mess stuff up here.  If you don't belong, then go away.  ");
		model.addAttribute("random2", "");
		return "admin";
	}
	
	@RequestMapping("/testing")
	public String testing(HttpServletRequest request, Model model){
		//This is text code for administrative purposes
		
		/*This code will be to test added movieService pieces.  
		 * 
		 * 
		 */
		//query the api for a series of objects
		int[] movieIds = {197,333484,941,944,942,943,115,415375};
		List<MovieService> movies = new ArrayList<MovieService>();
		//loop to create movie Service Objects and do something usefull.  
		for(int Id : movieIds){
			MovieService movie = new MovieService(Id);
			movies.add(movie);
		}
		//loop to test for adult content
		for(MovieService movie : movies){
			System.out.println(movie.getTitle() + " is an adult movie?: " + movie.getAdult());
			System.out.println("and the following genres : " + movie.getGenreIds().toString());
			for(int r : movie.getGenreIds()){
				System.out.println("id is " + r);
			}
			for(String w : movie.getGenreNames()){
				System.out.println("name is " + w);
			}
		}
		
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
