package com.nemo.fbdemo.model;

import org.json.JSONObject;

import android.graphics.Bitmap;

public class FeedEntry {
	
	User from;
	
	String id;
	String message;
	String name;
	String caption;
	String description;
	String link;
	String pictureUrl;
	
	FeedEntryType type;
	
	Bitmap picture;
	
	public FeedEntry(UsersList users, JSONObject data){
		
		fillUser(users, data);
		
		id          = data.optString("id");
		message     = data.optString("message");
		name        = data.optString("name");
		caption     = data.optString("caption");
		description = data.optString("description");
		link        = data.optString("link");
		pictureUrl  = data.optString("picture");
	
		type = FeedEntryType.Error;
		String typeString = data.optString("type");
		if(typeString.equals("status")) type = FeedEntryType.Status;
		if(typeString.equals("link"))   type = FeedEntryType.Link;
		if(typeString.equals("video"))  type = FeedEntryType.Video;
		if(typeString.equals("photo"))  type = FeedEntryType.Photo;
	}
	
	private void fillUser(UsersList users, JSONObject data){
		JSONObject userData = data.optJSONObject("from");
		
		String userId = userData.optString("id");
		String userName = userData.optString("name");
	
		from = users.getUser(userId, userName);
	}
	
	public User getUser(){
		return from;
	}
	
	public String getId(){
		return id;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getLink(){
		return link;
	}
	
	public String getPictureUrl(){
		return pictureUrl;
	}
	
	public FeedEntryType getType(){
		return type;
	}
	
	public Bitmap getPicture(){
		return picture;
	}
	
	public void setPicture(Bitmap picture){
		this.picture = picture;
	}

}
