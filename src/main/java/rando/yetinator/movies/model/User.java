package rando.yetinator.movies.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity  //import form java persistence
@Table(name = "user")
public class User extends AbstractEntity {
	
	private String userName;
	private String passwordHash;
	private int zipcode;
	//private MovieLike like;
	private List<MovieLike> likes;
	//private List<UserFriendsList> friends;
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public User() {}
	
	public User(String userName, String password, int zipcode) {
		
		super();
		
		this.userName = userName;
		this.passwordHash = hashPassword(password);
		this.zipcode = zipcode;
		
		
	}
	
	
	//Getters and setters
	/**
	 * @return the userName
	 */
	@NotNull
	@Column(name = "name", unique = true)
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the passwordHash
	 */
	@NotNull
	@Column(name = "pwHash")
	public String getPasswordHash() {
		return passwordHash;
	}
	
	@NotNull
	@Column(name = "zipcode")
	public int getzipcode() {
		return zipcode;
	}
	
	/*
	public MovieLike getLike(){
		return like;
	}*/
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	/*
	public void setLike(MovieLike like){
		this.like = like;
	}
	*/
	//I totally copied this hashing from blogz, look this up later
	private static String hashPassword(String password) {		
		return encoder.encode(password);
	}
	public boolean checkHash(String password){
		//also copied from blogz.  Why encoder.matches?  why can't I just hash it again? 
		return encoder.matches(password, passwordHash);
	}	
	
	/*
	
	@OneToMany
	@JoinColumn(name="user_one")
	public List<UserFriendsList> getFriends() {
		return friends;
	}

	public void setFriends(List<UserFriendsList> friends) {
		this.friends = friends;
	}
*/
	@OneToMany
	@JoinColumn(name="user_uid")//this creates a column user_id in the other table
	public List<MovieLike> getLikes(){
		return likes;
	}
	
	
	public void addLike(MovieLike movieLike){
		//TODO - understand this implementation
		likes.add(movieLike);
	}
	/*
	public void addFriendList(UserFriendsList UserFriendsList){
		friends.add(UserFriendsList);
	}
*/
	public void setLikes(List<MovieLike> likes) {
		this.likes = likes;
	}
	
	
}
