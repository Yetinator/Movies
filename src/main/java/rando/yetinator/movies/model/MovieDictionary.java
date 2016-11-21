package rando.yetinator.movies.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MovieDictionary")
public class MovieDictionary extends AbstractEntity {
	private String title;
	private String description;
	//private ArrayList<String> actors;// actors probably comes from another database.  
	
	
	public MovieDictionary(String title, String description, ArrayList<String> actors) {
		super();
		this.title = title;
		this.description = description;
		//this.actors = actors;
	}
	
	public MovieDictionary(){}
	
	@NotNull
    @Column(name = "title")
	public String getTitle() {
		return title;
	}
	@NotNull
    @Column(name = "description")
	public String getDescription() {
		return description;
	}
	/*public ArrayList<String> getActors() {
		return actors;
	}*/
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}*/
	
	
}
