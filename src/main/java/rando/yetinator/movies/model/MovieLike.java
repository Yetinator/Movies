package rando.yetinator.movies.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	//private Integer userId;
	//private List<Integer> friendUserIds;
	
	public MovieLike(User user, String title) {
		super();
		this.user = user;
		this.title = title;
		this.created = new Date();
		//this.userId = userId;
		//this.friendUserIds = friendUserIds;
	}
	public MovieLike(){}
	
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
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	/*public void setFriendUserIds(List<Integer> friendUserIds) {
		this.friendUserIds = friendUserIds;
	}*/
	/*@NotNull
    @Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}*/
	/*public void setUserId(Integer userId) {
		this.userId = userId;
	}*/
	
	
	
	
}
