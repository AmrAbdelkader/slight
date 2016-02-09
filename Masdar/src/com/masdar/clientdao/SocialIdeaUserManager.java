package com.masdar.clientdao;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.masdar.CloudEndpointUtils;
import com.masdar.entities.ideaendpoint.Ideaendpoint;
import com.masdar.entities.socialideauserendpoint.Socialideauserendpoint;
import com.masdar.entities.socialideauserendpoint.model.SocialIdeaUser;

public class SocialIdeaUserManager {
	
	public static Socialideauserendpoint getSocialideauserendpoint(){
		Socialideauserendpoint.Builder builder = new Socialideauserendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), 
				new HttpRequestInitializer() {
					
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						
					}
				});
			return CloudEndpointUtils.updateBuilder(builder).build();
	}
	
	
}
