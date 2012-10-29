package com.nemo.fbdemo;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Request.Callback;

public class FeedDownloader {
	
	public FeedDownloader(){
		
	}
	
	public void Download(final FeedDownloaderCallback callback){
	    final Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        Request request = Request.newGraphPathRequest(session, "me/home/", new Callback() {
				
				@Override
				public void onCompleted(Response response) {
					callback.Downloaded(
							FeedEntityFactory.CreateBunch(response.getGraphObject().getInnerJSONObject())
					);					
				}
			});
	        
	        Request.executeBatchAsync(request);
	    }
	}
}
