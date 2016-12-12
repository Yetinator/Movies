package rando.yetinator.movies.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import rando.yetinator.movies.model.dao.UserDao;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

@Entity  //import form java persistence
@Table(name = "user_friends")
public class UserFriendsList extends AbstractEntity{
	
	//UserDao UserDao;
	//UserFriendsListDao UserFriendsListDao;
	
	private int userOne;
	private int userTwo;
	
	//private User uOne;
	//private User uTwo;
	
	public UserFriendsList(int userOne, int userTwo) {
		super();
		this.userOne = userOne;
		this.userTwo = userTwo;
		//this.uOne = UserDao.findByuid(userOne);
		//this.uTwo = UserDao.findByuid(userTwo);
	}
	
	public UserFriendsList(){}

	//@NotNull
	@Column(name="user_one")
	public int getUserOne() {
		return userOne;
	}

	//@NotNull
	@Column(name="user_two")
	public int getUserTwo() {
		return userTwo;
	}

	public void setUserOne(int userOne) {
		this.userOne = userOne;
	}

	public void setUserTwo(int userTwo) {
		this.userTwo = userTwo;
	}
	
	//users
	/*
	public User getuOne() {
		return uOne;
	}
	public void setuOne(User uOne) {
		this.uOne = uOne;
	}
	public User getuTwo() {
		return uTwo;
	}
	public void setuTwo(User uTwo) {
		this.uTwo = uTwo;
	}
	*/
	
	
	
	
	
	

}
