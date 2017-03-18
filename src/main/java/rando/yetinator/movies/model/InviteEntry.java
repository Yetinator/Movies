package rando.yetinator.movies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity  //import form java persistence
@Table(name = "inviteEntry")
//@SecondaryTable(name="inviteList")
public class InviteEntry extends AbstractEntity{
	//this will link to an invite (event table with invite message and calendar date ect
	private User invitorId;
	private List<User> inviteList = new ArrayList<User>();
	private int tmdbid;
	
	//Secondary Table
	private String message;//Double check for length restrictions
	private int tableId;
	//Calendar day and time
	
	//there will be other users of the same invite uid
	
	public InviteEntry() {}
	
	public InviteEntry(User invitorId, List<User> inviteList, int tmdbid, String message){
		super();
		this.invitorId = invitorId;
		this.inviteList = inviteList;
		this.tmdbid = tmdbid;
		this.message = message;
		
	}

	/**
	 * @return the invitorId
	 */
	@NotNull
	//@Column(name="InvitorId", table = "inviteList")
	@OneToOne
	@JoinColumn(name="invitor_uid")
	public User getInvitorId() {
		return invitorId;
	}
/*
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "tableId", unique = true, table = "inviteList")
	public int getTableId() {
		return this.tableId;
	}
	*/
	//@NotNull
	
	@ElementCollection
	@CollectionTable(name = "inviteList", joinColumns={@JoinColumn(name = "whateverThisIs")})//is this a table uid?  should it autoGenerate in some way? 
	public List<User> getinviteList() {
		return inviteList;
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
	@Column(name="message")//, table="inviteItem")
	public String getMessage(){
		return message;
	}

	/**
	 * @param invitorId the invitorId to set
	 */
	public void setInvitorId(User invitorId) {
		this.invitorId = invitorId;
	}

	/**
	
	 */
	public void setinviteList(List<User> inviteList) {
		this.inviteList = inviteList;
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
	
	public void setTableId(int tableId){
		this.tableId = tableId;
	}
	

}
