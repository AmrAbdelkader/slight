package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VotingUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vote_id;
	private Long vote_user_id;
	private Long vote_idea_id;
	private byte vote_type;
	
	public byte getVote_type() {
		return vote_type;
	}
	public void setVote_type(byte vote_type) {
		this.vote_type = vote_type;
	}
	public Long getVote_id() {
		return vote_id;
	}
	public void setVote_id(Long vote_id) {
		this.vote_id = vote_id;
	}
	public Long getVote_user_id() {
		return vote_user_id;
	}
	public void setVote_user_id(Long vote_user_id) {
		this.vote_user_id = vote_user_id;
	}
	public Long getVote_idea_id() {
		return vote_idea_id;
	}
	public void setVote_idea_id(Long vote_idea_id) {
		this.vote_idea_id = vote_idea_id;
	}
}
