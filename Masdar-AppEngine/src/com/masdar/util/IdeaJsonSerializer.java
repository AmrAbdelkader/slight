package com.masdar.util;

import java.util.List;

import com.masdar.entities.Idea;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class IdeaJsonSerializer {
	
	public static String serializeIdea(Idea ideaObject){
		JSONObject ideaJson = new JSONObject();
		ideaJson.put("idea_type", ideaObject.getIdea_type());
		ideaJson.put("idea_blob_key", ideaObject.getIdea_blob_key());
		ideaJson.put("idea_header", ideaObject.getIdea_header());
		ideaJson.put("idea_user_id", ideaObject.getUser_id());
		ideaJson.put("voting_numebr", ideaObject.getVoting_number());
		return ideaJson.toString();
	}
	
	public static String serializeIdeaList(List<Idea> ideaList){
		JSONArray jsonArray = new JSONArray();
		for(Idea ideaItem : ideaList){
			jsonArray.add(ideaItem);
		}
		return jsonArray.toString();
	}
}
