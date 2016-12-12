package rando.yetinator.movies;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import rando.yetinator.movies.controllers.AbstractController;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.dao.UserDao;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao UserDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    	//borrowed from Blogz Assignment
        List<String> authPages = Arrays.asList("/trending", "/user/{UserName}", "/userlist");

        // Require sign-in for auth pages
        if ( authPages.contains(request.getRequestURI()) ) {

        	boolean isLoggedIn = false;
        	User user;
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);

            if (userId != null) {
            	user = UserDao.findByuid(userId);
            	
            	if (user != null) {
            		isLoggedIn = true;
            	}
            }

            // If user not logged in, redirect to login page
            if (!isLoggedIn) {
                response.sendRedirect("/signin");
                return false;
            }
        }

        return true;
    }

}