package com.nemo.fbdemo.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.nemo.fbdemo.model.FeedEntry;
import com.nemo.fbdemo.model.FeedEntryType;
import com.nemo.fbdemo.model.FeedList;
import com.nemo.fbdemo.model.UsersList;

public class FeedDownloader {
	
	public interface Callback{
		public void Downloaded(FeedList feed);
		public void Error();
	}
	
	public static void Download(final UsersList users, final boolean downloadPictures, Bundle params, final Callback callback){
	    final Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        Request request = Request.newGraphPathRequest(session, "me/home", new Request.Callback() {		
				@Override
				public void onCompleted(Response response) {
					if(response.getError() == null){
						JSONObject feedJson = response.getGraphObject().getInnerJSONObject();
						JSONObject pagingJson = feedJson.optJSONObject("paging");
						
						ArrayList<FeedEntry> feedEntries = createFeedEntries(users, feedJson);
						if(feedEntries.size() > 0){
							FeedList feedList = new FeedList();
							feedList.add(feedEntries, downloadPictures);
							
							String nextUrl = pagingJson.optString("next");
							String prevUrl = pagingJson.optString("previous");
							
							String nextTime = Uri.parse(nextUrl).getQueryParameter("until");
							String prevTime = Uri.parse(prevUrl).getQueryParameter("since");
							
							feedList.setNextPageUrl(nextTime);
							feedList.setPrevPageUrl(prevTime);
							
							callback.Downloaded(feedList);
						} else callback.Downloaded(null);
						
					} else callback.Error();
				}
			});
	        request.setParameters(params);
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
