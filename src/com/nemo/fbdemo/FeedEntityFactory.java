package com.nemo.fbdemo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nemo.fbdemo.model.FeedEntity;
import com.nemo.fbdemo.model.StatusEntry;
import com.nemo.fbdemo.model.UsersList;

public class FeedEntityFactory {

	public static FeedEntity Create(UsersList users, JSONObject data){
		String type = data.optString("type");
		
		if(type.equals("status")) 
			return new StatusEntry(users, data);
		
		return null;
	}
	
	public static ArrayList<FeedEntity> CreateBunch(UsersList users, JSONObject data){
		JSONArray dataArray = data.optJSONArray("data");
		ArrayList<FeedEntity> result = new ArrayList<FeedEntity>();
		for(int i = 0; i < dataArray.length(); i++){
			result.add(Create(users, dataArray.optJSONObject(i)));
		}
		return result;
	}
}
