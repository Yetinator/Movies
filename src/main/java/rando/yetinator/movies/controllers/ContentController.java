package rando.yetinator.movies.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rando.yetinator.movies.MyHelper;
import rando.yetinator.movies.model.MovieLike;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;

@Controller
public class ContentController extends AbstractController{
	
	@RequestMapping("/invite")
	public String inviteForm(Model model){
		//This creates the form for information needed for the invite Object
		//This may end up being done on the movies page instead
		
		
		//model.addAttribute("movies", addme);
		
		
		
		return "generic";
		
	}
	
	@RequestMapping(value = "/invite",  method = RequestMethod.POST)
	public String inviteCreate(Model model, HttpServletRequest arequest){
		//This creates and invite object
		//First get proposed invites
		String[] invitedGuestids = arequest.getParameterValues("invited");
		//Second get logged user
		User useridloggedin = getUserFromSession(arequest.getSession());
		//Get the movie int
		int TMDBid = Integer.parseInt(arequest.getParameter("movieid"));
		//MovieService TMDB = new MovieService(TMDBid);
		String message = arequest.getParameter("message");
		List<User> invitedGuests = new ArrayList();
		for(String guestid : invitedGuestids){
			User aUser = UserDao.findByuid(Integer.parseInt(guestid));
			//check to see if the list already contains
			if(!invitedGuests.contains(aUser)){
				invitedGuests.add(aUser);
			}
			//turn these back into users?  
		}
		if(!invitedGuests.contains(useridloggedin)){
			invitedGuests.add(useridloggedin);
		}
		//duplicates checked upon list creation >> and decide whether the inviter ends up on this list or not.
		MyHelper.inviteToMovie(useridloggedin, invitedGuests, TMDBid, message, inviteEntryDao);
		
		model.addAttribute("random", "Your invite has been created");
		return "generic";
		
	}

}
