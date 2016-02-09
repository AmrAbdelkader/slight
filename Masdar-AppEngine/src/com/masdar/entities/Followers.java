package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Followers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long follower_id;
	Long follower_user_id;
	Long following_user_id;
	
	
	public Long getFollower_user() {
		return follower_user_id;
	}


	public void setFollower_user(Long follower_user) {
		this.follower_user_id = follower_user;
	}


	public Long getFollowing_user() {
		return following_user_id;
	}


	public void setFollowing_user(Long following_user) {
		this.following_user_id = following_user;
	}


	public Long getFollower_id() {
		return follower_id;
	}
}
