package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entity_id;
	
	private User user_object;
	
	public Long getEntity_id() {
		return entity_id;
	}
	public User getUser_object() {
		return user_object;
	}
	public void setUser_object(User user_object) {
		this.user_object = user_object;
	} 
}
