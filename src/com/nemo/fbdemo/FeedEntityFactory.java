package com.nemo.fbdemo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nemo.fbdemo.model.FeedEntity;
import com.nemo.fbdemo.model.StatusEntry;

public class FeedEntityFactory {

	public static FeedEntity Create(JSONObject data){
		String type = data.optString("type");
		
		if(type.equals("status")) 
			return new StatusEntry(data);
		
		return null;
	}
	
	public static ArrayList<FeedEntity> CreateBunch(JSONObject data){
		JSONArray dataArray = data.optJSONArray("data");
		ArrayList<FeedEntity> result = new ArrayList<FeedEntity>();
		for(int i = 0; i < dataArray.length(); i++){
			result.add(Create(dataArray.optJSONObject(i)));
		}
		return result;
	}
}
