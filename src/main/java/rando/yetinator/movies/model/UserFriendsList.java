package rando.yetinator.movies.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity  //import form java persistence
@Table(name = "user_friends")
public class UserFriendsList extends AbstractEntity{
	
	private int userOne;
	private int userTwo;
	
	public UserFriendsList(int userOne, int userTwo) {
		super();
		this.userOne = userOne;
		this.userTwo = userTwo;
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
	
	
	
	

}
