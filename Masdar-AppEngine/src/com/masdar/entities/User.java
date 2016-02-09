package com.masdar.entities;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.google.appengine.api.datastore.Key;
import javax.persistence.Entity;

@Entity
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;	
	
	private String email;
	private String device_id;
	private String user_name;
	private Long fb_id;
	private Long gplus_id;
	private String mob_no;
	private String join_date;
	private String profile_pic;
	
	/*
	public User(){
		
	}
	
	public User(String email, String device_id, String user_name, Long fb_id, Long gplus_id, String mob_no, String join_date, String profile_pic){
		this.email = email;
		this.device_id = device_id;
		this.user_name = user_name;
		this.fb_id = fb_id;
		this.gplus_id = gplus_id;
		this.mob_no = mob_no;
		this.join_date = join_date;
		this.profile_pic = profile_pic;
	}
	*/
	
	public Long getUser_id() {
		return user_id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public Long getFb_id() {
		return fb_id;
	}
	
	public void setFb_id(Long fb_id) {
		this.fb_id = fb_id;
	}
	
	public Long getGplus_id() {
		return gplus_id;
	}
	
	public void setGplus_id(Long gplus_id) {
		this.gplus_id = gplus_id;
	}
	
	public String getMob_no() {
		return mob_no;
	}
	
	public void setMob_no(String mob_no) {
		this.mob_no = mob_no;
	}
	
	public String getJoin_date() {
		return join_date;
	}
	
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	
	public String getProfile_pic() {
		return profile_pic;
	}
	
	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}
	
}
