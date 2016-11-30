package rando.yetinator.movies.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MovieLike")
public class MovieLike extends AbstractEntity{
	//represents post or like for movie.  
	//This is used to connect different users
	//treat like posts
	private User user;
	private String title;
	private Date created;
	//private Integer userId; Removed
	private List<User> users;
	//private List<Integer> friendUserIds;
	//Removed from below int userId, 
	public MovieLike(String title, User user) {
		super();
		this.user = user;
		this.title = title;
		this.created = new Date();
		//this.userId = userId; Removed
		//this.friendUserIds = friendUserIds;
	}
	public MovieLike(){}
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	@NotNull
    @Column(name = "title")
	public String getTitle() {
		return title;
	}
	@NotNull
    @Column(name = "create_date")
	public Date getCreated() {
		return created;
	}
	/*
	public List<Integer> getFriendUserIds() {
		return friendUserIds;
	}*/
	
	public void setUser(User user) {
		this.user = user;
	}//TODO - something with this to create a user from uid
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	/*public void setFriendUserIds(List<Integer> friendUserIds) {
		this.friendUserIds = friendUserIds;
	}*/
	/* Removed
	@NotNull
    @Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	*/
	//TODO - DECIDE ABOUT USABAILITY OF FOLLOWING (probably need another table to link users and movie...likes
	// is this redundant?  A list of users for a movielike.  but there is one user until I get to movie dictionary...
	//This will probably be removed or refactored to movieDictionary
	@OneToMany
	@JoinColumn(name="user_uid")
	public List<User> getUsers(){
		return users;
	}
	
	public void setUsers (List<User> users){
		this.users = users;
	}
	
	
	
	
}
