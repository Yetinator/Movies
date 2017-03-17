package rando.yetinator.movies.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.dao.InviteEntryDao;
import rando.yetinator.movies.model.dao.MovieDictionaryDao;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

public abstract class AbstractController {
	//Borrowed from blogz Assignment

	@Autowired
    protected UserDao UserDao;
	
	@Autowired
	protected MovieLikeDao MovieLikeDao;
	
	@Autowired 
	protected MovieDictionaryDao MovieDictionaryDao;
	
	@Autowired 
	protected UserFriendsListDao UserFriendsListDao;
	
	@Autowired
	protected InviteEntryDao inviteEntryDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : UserDao.findByuid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
    
    /*
      protected void setupPage(Model model, HttpSession session) {
     
    	model.addAttribute("loggedOnName", getUserFromSession(session).getUserName());
    }
*/
	
}
