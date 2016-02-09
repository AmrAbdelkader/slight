package com.masdar.clientdao;

import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.masdar.CloudEndpointUtils;
import com.masdar.entities.ideaendpoint.Ideaendpoint;
import com.masdar.entities.ideaendpoint.model.Idea;

public class IdeaManager {

	public static Ideaendpoint getIdeaEndpoint(){
		Ideaendpoint.Builder builder = new Ideaendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), 
				new HttpRequestInitializer() {
					
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						
					}
				});
			return CloudEndpointUtils.updateBuilder(builder).build();
	}
	
	public static boolean createNewIdea(Idea idea){
		boolean isCreated = false;
		try{
			Idea createdIdea = getIdeaEndpoint().insertIdea(idea).execute();
			if(createdIdea != null){
				isCreated = true;
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return isCreated;
	}
	
	public static boolean createNewSocialIdea(Idea idea , String usersIds){
		boolean isCreated = false;
		try{
			Idea createdIdea = getIdeaEndpoint().insertSocialIdea(usersIds, idea).execute();
			if(createdIdea != null){
				isCreated = true;
				Log.d("createNewSocialIdea - Created idea id: " , createdIdea.getIdeaId().toString());
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return isCreated;
	}
	
	public static Idea getIdeaById(Long ideaId){
		Idea currentIdea = null;
		try {
			currentIdea = getIdeaEndpoint().getIdea(ideaId).execute();
		} catch (IOException e) {
			Log.d("IdeaManager.getIdeaById-Exception: ", e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			Log.d("IdeaManager.getIdeaById-Exception: ", e.getMessage());
			e.printStackTrace();
		}
		return currentIdea;
	}
	
	public static boolean updateIdea(Idea idea){
		boolean isUpdated = false;
		try{
			Idea updatedIdea = getIdeaEndpoint().updateIdea(idea).execute();
			if(updatedIdea != null){
				isUpdated = true;
			}
		}catch (Exception e) {
			Log.d("IdeaManager.updateIdea-Exception: ", e.getMessage());
			e.printStackTrace();
		}
		return isUpdated;
	}
}
