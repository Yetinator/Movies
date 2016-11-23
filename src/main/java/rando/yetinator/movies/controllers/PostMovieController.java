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
public class PostMovieController {

	@Autowired
	private UserDao UserDao;
	
	@Autowired
	private MovieLikeDao MovieLikeDao;
	
	@Autowired
	private MovieDictionaryDao MovieDictionaryDao;

	@RequestMapping("/trending")
	public String trending(Model model){
		//TODO - make this only accessible to registered users(make everything but signup only accessible to registered users.  
		//model.addAttribute("random", "trending");
		List<MovieLike> movies = MovieLikeDao.findAll();
		
		model.addAttribute("movies", movies);
		
		return "trending";
	}
	
	@RequestMapping(value = "/trending", method = RequestMethod.POST)
	public String trendingPost(Model model, HttpServletRequest arequest){
		
		String title = arequest.getParameter("movieTitle");
		if(MovieDictionary.validMovie(title)){
			//TODO - save a movieLike to someone other than Mike ie: uid == 3
			System.out.println(title);
			User user = UserDao.findByuid(3);
			MovieLike like = new MovieLike(3, title, user);
			MovieLikeDao.save(like);
			System.out.println(like.getUser().getUserName());
			model.addAttribute("random", "Post trending");
		}else{
			model.addAttribute("random", "Bad Post");
		}
		
		
		
		
		return "home";
	}
}
