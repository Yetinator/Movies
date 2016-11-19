package rando.yetinator.movies.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import rando.yetinator.movies.model.dao.AbstractEntity;

@Entity  //import form java persistence
@Table(name = "User")
public class User extends AbstractEntity {
	
	String userName;
	String passwordHash;
	
	public User() {}
	
	public User(String userName, String passwordHash) {
		
		super();
		
		this.userName = userName;
		this.passwordHash = passwordHash;
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
	

	
	
}
