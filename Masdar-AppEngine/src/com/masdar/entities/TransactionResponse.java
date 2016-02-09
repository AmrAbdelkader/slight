package com.masdar.entities;

public class TransactionResponse {
	
	boolean isValid;
	String errMessage;
	
	public TransactionResponse(){
		
	}
	
	public TransactionResponse(boolean isValid){
		this.isValid = isValid;
	}
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
