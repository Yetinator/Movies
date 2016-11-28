package rando.yetinator.movies.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.model.MovieDictionary;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.dao.MovieDictionaryDao;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;

@Controller
public class PostMovieController extends AbstractController{

	//@Autowired
	//private UserDao UserDao;
	
	//@Autowired
	//private MovieLikeDao MovieLikeDao;
	
	//@Autowired
	//private MovieDictionaryDao MovieDictionaryDao;

	@RequestMapping("/trending")
	public String trending(Model model){
		//make this only accessible to registered users(make everything but signup only accessible to registered users.  
		//model.addAttribute("random", "trending");
		//TODO - narrow this to a list of the most popular or order by popularity
		org.springframework.data.domain.Sort a = new org.springframework.data.domain.Sort("title");
		List<MovieLike> movies = MovieLikeDao.findAll(a);
		
		model.addAttribute("movies", movies);
		
		return "trending";
	}
	
	@RequestMapping(value = "/trending", method = RequestMethod.POST)
	public String trendingPost(Model model, HttpServletRequest arequest){
		
		String title = arequest.getParameter("movieTitle");
		if(MovieDictionary.validMovie(title)){
			//save a movieLike to someone other than Mike ie: uid == 3
			Integer useridloggedin = getUserFromSession(arequest.getSession()).getUid();
			System.out.println(title);
			User user = UserDao.findByuid(useridloggedin);
			//Removed from below useridloggedin, 
			MovieLike like = new MovieLike(title, user);
			MovieLikeDao.save(like);
			System.out.println(like.getUser().getUserName());
			model.addAttribute("random", "Post trending");
		}else{
			model.addAttribute("random", "Bad Post");
		}
		
		
		
		//TODO - change to a list of trending movies?  
		return "home";
	}
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
		
		return "user";
	}
	
	
}
