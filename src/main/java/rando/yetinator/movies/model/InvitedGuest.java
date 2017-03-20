package rando.yetinator.movies.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "InvitedGuestList")
public class InvitedGuest extends AbstractEntity{
	//This represents a line in the inviteEntry object linking users who were invited

	InviteEntry inviteDetails ;//link to InviteEntry table
	User guest;//link to user
	
	
	
	public InvitedGuest(){}
	//removed complex constructor
	public InvitedGuest(User guest, InviteEntry inviteDetails) {
		super();
		this.inviteDetails = inviteDetails;
		this.guest = guest;
		
	}
	
	/**
	 * @return the inviteDetails
	 * THIS IS THE CONNECTOR FOR THE OTHER TABLE
	 */
	@ManyToOne
	//@JoinColumn
	public InviteEntry getInviteDetails() {
		return inviteDetails;
	}
	/**
	 * @return the userIdentifier
	 */
	@ManyToOne
	public User getGuest() {
		return guest;
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
	public void setGuest(User guest) {
		this.guest = guest;
	}
	
	
	
}
