package com.nemo.fbdemo.model;

import org.json.JSONObject;

public abstract class FeedEntity {
	
	String id;
	User user;
	
	public FeedEntity(UsersList users, JSONObject data){
		id = data.optString("id");
		
		JSONObject userData = data.optJSONObject("from");
	
		String userId = userData.optString("id");
		String userName = userData.optString("name");
	
		user = users.getUser(userId, userName);
	}
	
	public User getUser() {
		return user;
	}

}
