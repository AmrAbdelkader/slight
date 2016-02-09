package com.masdar.util;

import java.util.List;


public class JsonCallbackWrapper {
	
	public static String wrapJsonCallback(String collectionName , List collection){
		StringBuilder jsonCallbackBuilder = new StringBuilder();
		jsonCallbackBuilder.append("jsonCallback({");
		jsonCallbackBuilder.append(" \""+collectionName+"\": ");
		jsonCallbackBuilder.append(IdeaJsonSerializer.serializeIdeaList(collection));
		jsonCallbackBuilder.append("});");
		return jsonCallbackBuilder.toString();
	}
}
