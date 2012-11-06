package com.nemo.fbdemo.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.nemo.fbdemo.model.FeedEntry;
import com.nemo.fbdemo.model.FeedEntryType;
import com.nemo.fbdemo.model.UsersList;

public class FeedDownloader {
	
	public interface Callback{
		public void Downloaded(ArrayList<FeedEntry> entities);
		public void Error();
	}
	
	public static void Download(final UsersList users, final Callback callback){
	    final Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        Request request = Request.newGraphPathRequest(session, "me/home/", new Request.Callback() {		
				@Override
				public void onCompleted(Response response) {
					if(response.getError() == null){
						ArrayList<FeedEntry> feedEntries = createFeedEntries(users, response.getGraphObject().getInnerJSONObject());
						callback.Downloaded(feedEntries);
					} else callback.Error();
				}
			});
	        
	        Request.executeBatchAsync(request);
	    }
	}
	
	private static ArrayList<FeedEntry> createFeedEntries(UsersList users, JSONObject data){
		ArrayList<FeedEntry> result = new ArrayList<FeedEntry>();
		
		JSONArray feedArray = data.optJSONArray("data");
		
		if(feedArray != null){
			for(int i=0; i < feedArray.length(); i++){
				JSONObject feedEntryData = feedArray.optJSONObject(i);
				
				if(feedEntryData != null){
					FeedEntry feedEntry = new FeedEntry(users, feedEntryData);
					if(feedEntry.getType() != FeedEntryType.Error) result.add(feedEntry);
				}
			}
		}
		
		return result;
	}
}
