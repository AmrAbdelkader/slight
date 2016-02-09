package com.masdar.entities;

//import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Idea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idea_id;
	private String idea_header;
	private int idea_type; 
	private String idea_blob_key;
	private Long user_id;
	private Long voting_number;
	
	public Long getVoting_number() {
		return voting_number;
	}

	public void setVoting_number(Long voting_number) {
		this.voting_number = voting_number;
	}

	public String getIdea_header() {
		return idea_header;
	}
	
	public void setIdea_header(String idea_header) {
		this.idea_header = idea_header;
	}
	
	public int getIdea_type() {
		return idea_type;
	}
	
	public void setIdea_type(int idea_type) {
		this.idea_type = idea_type;
	}
	
	public String getIdea_blob_key() {
		return idea_blob_key;
	}
	
	public void setIdea_blob_key(String idea_blob_key) {
		this.idea_blob_key = idea_blob_key;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Long getIdea_id() {
		return idea_id;
	}
	
}
