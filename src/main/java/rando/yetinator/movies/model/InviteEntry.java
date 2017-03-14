package rando.yetinator.movies.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity  //import form java persistence
@Table(name = "inviteEntry")
@SecondaryTable(name="inviteItem")
public class InviteEntry extends AbstractEntity{
	//this will link to an invite (event table with invite message and calendar date ect
	public int invitorId;
	public int inviteeId;
	public int tmdbid;
	
	//Secondary Table
	public String message;//Double check for length restrictions
	//Calendar day and time
	
	//there will be other users of the same invite uid
	
	public InviteEntry() {}
	
	public InviteEntry(int invitorId, int inviteeId, int tmdbid, String message){
		super();
		this.invitorId = invitorId;
		this.inviteeId = inviteeId;
		this.tmdbid = tmdbid;
		this.message = message;
		
	}

	/**
	 * @return the invitorId
	 */
	@NotNull
	@Column(name="InvitorId")
	public int getInvitorId() {
		return invitorId;
	}

	/**
	 * @return the inviteeId
	 */
	@NotNull
	@Column(name="InviteeId")
	public int getInviteeId() {
		return inviteeId;
	}

	/**
	 * @return the tmdbid
	 */
	@NotNull
	@Column(name="tmdbid")
	public int getTmdbid() {
		return tmdbid;
	}
	
	//@return message
	@NotNull
	@Column(name="message", table="inviteItem")
	public String getMessage(){
		return message;
	}

	/**
	 * @param invitorId the invitorId to set
	 */
	public void setInvitorId(int invitorId) {
		this.invitorId = invitorId;
	}

	/**
	 * @param inviteeId the inviteeId to set
	 */
	public void setInviteeId(int inviteeId) {
		this.inviteeId = inviteeId;
	}

	/**
	 * @param tmdbid the tmdbid to set
	 */
	public void setTmdbid(int tmdbid) {
		this.tmdbid = tmdbid;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
