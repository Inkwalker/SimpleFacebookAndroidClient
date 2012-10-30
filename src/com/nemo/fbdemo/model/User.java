package com.nemo.fbdemo.model;

import org.json.JSONObject;

import android.graphics.Bitmap;

public class User {
	
	private String id;
	private String name;
	private String pictureUrl;
	private Bitmap picture;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Bitmap getPicture() {
		return picture;
	}

	public void setPicture(Bitmap picture) {
		this.picture = picture;
	}

	public void fillFromJson(JSONObject data){
		JSONObject userData = data.optJSONObject("data");
		if(userData!=null) pictureUrl = userData.optString("url");
	}

}
