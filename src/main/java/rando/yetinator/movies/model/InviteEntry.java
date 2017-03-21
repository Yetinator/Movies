package rando.yetinator.movies.model;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;


import rando.yetinator.movies.model.dao.UserDao;
import rando.yetinator.movies.model.dao.UserFriendsListDao;

@Entity  //import form java persistence
@Table(name = "inviteEntry")
//@SecondaryTable(name="inviteList")
public class InviteEntry extends AbstractEntity{
	//this will link to an invite (event table with invite message and calendar date ect
	

	//private User invitorId;
	private int invitorId;
	//private List<User> inviteList = new ArrayList<User>();
	//private List<InvitedGuest> inviteList = new ArrayList<InvitedGuest>();
	//private List<User> inviteList = new ArrayList<User>();
	private List<User> invited;
	private int tmdbid;
	private String message;//Double check for length restrictions
	
	//Calendar day and time
	
	//second table use only
	private long tableId;//possibly not used
	//there will be other users of the same invite uid
	
	public InviteEntry() {}
	//TODO invitor id need to change to invitor if this works
	public InviteEntry(int invitorId, int tmdbid, String message){
		super();
		
		this.invitorId = invitorId;
		//this.inviteList = inviteList;
		this.tmdbid = tmdbid;
		this.message = message;
		/*
		//move this to inviteList.add(); to be called in the controller
		for(User theUser : inviteListofUsers){
			InvitedGuest guestLine = new InvitedGuest(theUser);
			inviteList.add(guestLine);
		}
		invitedGuestDao.save(inviteList);
		*/
	}


	/**
	 * @return the invitorId
	 * These annotations are having trouble being treated normally.  
	 * Somehow JoinColumn and a one to one annotation help this work.  I'm not sure why this is important
	 */
	//@OneToOne
	//@JoinColumn(name="invitor_uid", table="invited_guest_list")
	@NotNull
	@Column(name="InvitorUid")
	public int getInvitorId() {//ChangedInvitor
		return invitorId;
	}
	
	@ManyToMany
	@JoinColumn(name = "user_uid")
	public List<User> getInvited(){
		return this.invited;
	}


	//@Id
	//@OneToOne
		//@JoinColumn(name="stupidId", unique = true, table="inviteList")
	//@ElementCollection
	//@CollectionTable(name = "inviteList")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@NotNull
	//@Column(name = "stupidId", unique = true)
	public long getTableId() {
		return this.tableId;
	}
	
	//@NotNull
	//@OrderColumn(name="messedUp")
	//@ElementCollection
	//@CollectionTable(name = "inviteList", joinColumns={@JoinColumn(name = "inviteEntryUidIThink")})//is this a table uid?  should it autoGenerate in some way? 
	//@Column(name = "why")
	//@GenericGenerator(name="hilo-gen", strategy="hilo")//https://www.youtube.com/watch?v=kk207HAym_I#t=713.759166
	//@CollectionId(columns = { @Column(name="inviteId") }, generator = "hilo-gen", type = @Type(type = "long"))
//	@JoinColumn(name="wheehoo")
//	@OneToMany
//	public List<InvitedGuest> getinviteList() {
//		return inviteList;
//	}
//	
//	//move inviteListAdd to end of file
//	public void addInvite(InvitedGuest invitedGuest){
//		inviteList.add(invitedGuest);
//	}
//	//overload above for fun
//	public void addInvite(List<InvitedGuest> wholeList){
//		for(InvitedGuest invitedGuest : wholeList){
//			inviteList.add(invitedGuest);
//		}
//	}
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
	@Column(name="message")//, table="inviteItem")
	public String getMessage(){
		return message;
	}

	/**
	 * @param invitorId the invitorId to set
	 */
	public void setInvitorId(int invitorId) {//ChangedInvitor
		this.invitorId = invitorId;
	}

	/**
	
	 */
//	public void setinviteList(List<InvitedGuest> inviteList) {
//		this.inviteList = inviteList;
//	}
	public void setInvited(List<User> invited){
		this.invited = invited;
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
	
	protected void setTableId(long tableId){
		this.tableId = tableId;
	}
	

}
