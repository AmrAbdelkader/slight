package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long comment_id;
	private Long idea_id;
	private Long user_id;
	private String comment_text;
	private String comment_date;
	
	public Long getComment_id() {
		return comment_id;
	}
	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}
	public Long getIdea_id() {
		return idea_id;
	}
	public void setIdea_id(Long idea_id) {
		this.idea_id = idea_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}	
}
