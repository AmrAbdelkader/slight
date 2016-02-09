package com.masdar.drawing;

public enum IdeaType {
	GENERAL_IDEA(0) ,
	DESIGN(1),
	SOCIAL_ACTIVITY(2),
	ANIMATION(3);
	
	private final int value;
	private IdeaType(int value){
		this.value = value;
	}
	public int getValue(){
		return value;
	}
	
	
	
}
