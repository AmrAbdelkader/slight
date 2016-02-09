package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SocialIdeaUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long social_idea_user_id;
	private Long idea_id;
	private Long user_id;
	private int role_id;
	private int isLocked;
	
	
	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public Long getSocial_idea_user_id() {
		return social_idea_user_id;
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
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public Long getIdea_id() {
		return idea_id;
	} 
	
}
