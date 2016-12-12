package rando.yetinator.movies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;
import rando.yetinator.movies.model.dao.MovieLikeDao;
import rando.yetinator.movies.model.dao.UserDao;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

public class MyHelper {
	
	@Autowired
	protected UserFriendsListDao UserFriendsListDao;
	
	@Autowired
	protected UserDao UserDao;
	
	@Autowired
	protected MovieLikeDao MovieLikeDao;
	
	public static boolean AreMutualFriends(User userOne, User userTwo, UserFriendsListDao UserFriendsListDao){
		//UserOne is a number userTwo is a number
	//if UserOne exists as userTwo in a list where userTwo is primary then true else false
		System.out.println("waldo2");
		if(relationshipExists(userOne.getUid() ,userTwo.getUid(), UserFriendsListDao)){
			System.out.println("waldo3");
			if(relationshipExists(userTwo.getUid(), userOne.getUid(),UserFriendsListDao)){
				System.out.println("waldomutualtrue");
				return true;
			}
		}
		System.out.println("waldomutualfalse");
		
		return false;
	}
	/*
	//overload AreMutualFriends
	public static boolean AreMutualFriends(int userOne, int userTwo){
		MyHelper aHelper = new MyHelper();
		System.out.println("Waldo100");
		User one = aHelper.UserDao.findByuid(userOne);
		User two = aHelper.UserDao.findByuid(userTwo);
		System.out.println("Waldo101");
		return AreMutualFriends(one,two);
	}
	*/
	private static boolean relationshipExists(int userOneUid, int userTwoUid, UserFriendsListDao UserFriendsListDao){
		//checks to see if userTwo is on userOne's friend list (but not visa versa)
		MyHelper ahelper = new MyHelper();
		System.out.println("waldo6");
		System.out.println(userOneUid);
		System.out.println(userTwoUid);
		List<UserFriendsList> friendEntries = UserFriendsListDao.findByUserOne(userOneUid);//TODO - problem here per waldo
		System.out.println("waldo7");
		for(UserFriendsList entry : friendEntries){
			System.out.println("waldo8");
			if(entry.getUserTwo() == userTwoUid){
				System.out.println("waldo9");
				return true;
			}
		}
		System.out.println("waldofalse");
		return false;
		
	}
	

}
