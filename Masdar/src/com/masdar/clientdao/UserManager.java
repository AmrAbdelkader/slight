package com.masdar.clientdao;

import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.masdar.CloudEndpointUtils;
import com.masdar.entities.userendpoint.Userendpoint;
import com.masdar.entities.userendpoint.model.CollectionResponseUser;
import com.masdar.entities.userendpoint.model.User;

public class UserManager {
	
	public static Userendpoint getUserEndpoint(){
		Userendpoint userEndpoint = null;
		Userendpoint.Builder builder;
		try {
			builder = new Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), 
					new HttpRequestInitializer() {
						
						@Override
						public void initialize(HttpRequest httpRequest) throws IOException {
							httpRequest.setReadTimeout(5 * 60 * 1000);
						}
					});
			userEndpoint = CloudEndpointUtils.updateBuilder(builder).build();
		} catch (Exception e) {
			Log.d("UserManager.getUserEndpoint-Exception-Message" , e.getMessage());
		}
		return userEndpoint;
	}
	
	public static User createNewUser(User user){
		User createdUser = null;
		Userendpoint.Builder builder = new Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), 
			new HttpRequestInitializer() {
				
				@Override
				public void initialize(HttpRequest arg0) throws IOException {
					
				}
			});
		Userendpoint endPoint = CloudEndpointUtils.updateBuilder(builder).build();
		try{
			createdUser = endPoint.insertUser(user).execute();
			
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return createdUser;
	}
	
	public static User getUserById(Long userId){
		User user = null;
		try{
			user = getUserEndpoint().getUser(userId).execute();
		}catch(Exception exc){
			
		}
		return user;
	}
	
	public static CollectionResponseUser getFollowingUsers(Long userId){
		CollectionResponseUser users = null;
		try{
			 users = getUserEndpoint().getFollowingByUser(userId).execute();
		}catch(Exception exc){
			Log.d("UserManager.getFollowingUsers-Exception-Message", exc.getMessage());
		}
		return users;
	}
	
	public static boolean authenticateUser(String email , String user_id){
		boolean isFound = false;
		User user = UserManager.getUserById(Long.parseLong(user_id));
		if(user != null){
			if(user.getEmail().equals(email)){
				isFound = true;
			}
		}
		return isFound;
	}	
}
