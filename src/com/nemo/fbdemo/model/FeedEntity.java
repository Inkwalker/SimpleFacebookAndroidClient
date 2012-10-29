package com.nemo.fbdemo.model;

import org.json.JSONObject;

public abstract class FeedEntity {
	
	String id;
	User user;
	
	public FeedEntity(JSONObject data){
		id = data.optString("id");
		
		JSONObject userData = data.optJSONObject("from");
		user = UsersList.getInstance().getUser(userData.optString("id"));
	}

}
