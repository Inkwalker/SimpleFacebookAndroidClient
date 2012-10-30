package com.nemo.fbdemo.network;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Request.Callback;
import com.nemo.fbdemo.FeedEntityFactory;
import com.nemo.fbdemo.model.UsersList;

public class FeedDownloader {
	
	UsersList users;
	
	public FeedDownloader(UsersList users){
		this.users = users;
	}
	
	public void Download(final FeedDownloaderCallback callback){
	    final Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        Request request = Request.newGraphPathRequest(session, "me/home/", new Callback() {
				
				@Override
				public void onCompleted(Response response) {
					callback.Downloaded(
							FeedEntityFactory.CreateBunch(users, response.getGraphObject().getInnerJSONObject())
					);					
				}
			});
	        
	        Request.executeBatchAsync(request);
	    }
	}
}
