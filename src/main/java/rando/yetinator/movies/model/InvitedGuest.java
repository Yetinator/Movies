package rando.yetinator.movies.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "InvitedGuestList")
public class InvitedGuest extends AbstractEntity{
	//This represents a line in the inviteEntry object linking users who were invited
	
	
	InviteEntry inviteDetails ;//link to InviteEntry table
	User userIdentifier;//link to user
	/**
	 * @param inviteDetails
	 * @param userIdentifier
	 */
	
	public InvitedGuest(){}
	//removed complex constructor
	public InvitedGuest(User userIdentifier, InviteEntry inviteDetails) {
		super();
		this.inviteDetails = inviteDetails;
		this.userIdentifier = userIdentifier;
		
	}
	
	/**
	 * @return the inviteDetails
	 * THIS IS THE CONNECTOR FOR THE OTHER TABLE
	 */
	@ManyToOne
	public InviteEntry getInviteDetails() {
		return inviteDetails;
	}
	/**
	 * @return the userIdentifier
	 */
	@ManyToOne
	public User getUserIdentifier() {
		return userIdentifier;
	}
	/**
	 * @param inviteDetails the inviteDetails to set
	 */
	public void setInviteDetails(InviteEntry inviteDetails) {
		this.inviteDetails = inviteDetails;
	}
	/**
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(User userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	
	
	
}
