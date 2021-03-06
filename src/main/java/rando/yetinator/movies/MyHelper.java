package rando.yetinator.movies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rando.yetinator.movies.model.InviteEntry;
import rando.yetinator.movies.model.MovieService;
import rando.yetinator.movies.model.User;
import rando.yetinator.movies.model.UserFriendsList;
import rando.yetinator.movies.model.dao.InviteEntryDao;
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
	/*
	@Autowired
	protected InviteEntryDao inviteEntryDao;
	
	@Autowired
	protected InvitedGuestDao invitedGuestDao;
	*/
	public static boolean AreMutualFriends(User userOne, User userTwo, UserFriendsListDao UserFriendsListDao){
		//UserOne is a number userTwo is a number
	//if UserOne exists as userTwo in a list where userTwo is primary then true else false
		if(relationshipExists(userOne.getUid() ,userTwo.getUid(), UserFriendsListDao)){
			if(relationshipExists(userTwo.getUid(), userOne.getUid(),UserFriendsListDao)){
				return true;
			}
		}
		
		return false;
	}
	/*
	//overload AreMutualFriends
	public static boolean AreMutualFriends(int userOne, int userTwo){
		MyHelper aHelper = new MyHelper();

		User one = aHelper.UserDao.findByuid(userOne);
		User two = aHelper.UserDao.findByuid(userTwo);

		return AreMutualFriends(one,two);
	}
	*/
	private static boolean relationshipExists(int userOneUid, int userTwoUid, UserFriendsListDao UserFriendsListDao){
		//checks to see if userTwo is on userOne's friend list (but not visa versa)
		MyHelper ahelper = new MyHelper();


		List<UserFriendsList> friendEntries = UserFriendsListDao.findByUserOne(userOneUid);//TODO - problem here per waldo
	
		for(UserFriendsList entry : friendEntries){
			if(entry.getUserTwo() == userTwoUid){
				return true;
			}
		}
		return false;
		
	}
	
	public static void inviteToMovie(User host, List<User> guests, int TMDBid, String message, InviteEntryDao inviteEntryDao){
		//This takes input and organizes an invite using Movie-system classes and DAO
		//MyHelper ahelper = new MyHelper();
		
		InviteEntry testItem;
		try{
			//create an inviteEntry item
			int hostid = host.getUid();
			testItem = new InviteEntry(host,TMDBid, message);
			inviteEntryDao.save(testItem);
		}catch(Error e){
			System.out.println("Problem creating and saving InviteEntry Object.");
			testItem = null;
		}
		//Todo - make the list of guests better.  Get rid of duplicates?  Add inviter as a guest?  
		if(testItem != null){
		//populate invites
			testItem.setInvited(guests);
			inviteEntryDao.save(testItem);
		for(User guest : guests){
			//InvitedGuest guestEntry = new InvitedGuest(guest, testItem);
			//testItem.addInvite(guestEntry);
			//testItem.addInvite(guestEntry);
			//testItem.setInvited(guest);
			//invitedGuestDao.save(guestEntry);
		}
		}
	}
	

}
