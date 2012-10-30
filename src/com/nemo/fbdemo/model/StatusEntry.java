package com.nemo.fbdemo.model;

import org.json.JSONObject;

public class StatusEntry extends FeedEntity {

	private String message;
	
	public StatusEntry(UsersList users, JSONObject data) {
		super(users, data);
		
		setMessage(data.optString("message"));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
