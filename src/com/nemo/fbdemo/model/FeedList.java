package com.nemo.fbdemo.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.nemo.fbdemo.network.PictureDownloader;

public class FeedList {
	
	public interface Callback{
		public void dataChanged();
	}
	
	Callback callback;
	
	ArrayList<FeedEntry> feed;
	
	public FeedList(){
		feed = new ArrayList<FeedEntry>();
	}
	
	public void setCallback(Callback callback){
		this.callback = callback;
	}
	
	public void add(final FeedEntry entry){
		String pictureUrl = entry.getPictureUrl();
		
		if(!pictureUrl.equals("")){
			PictureDownloader.download(pictureUrl, new PictureDownloader.Callback() {
				
				@Override
				public void downloaded(Bitmap picture) {
					entry.setPicture(picture);
					callback.dataChanged();
				}
			});
		}
		
		feed.add(entry);
	}
	
	public void add(ArrayList<FeedEntry> entries){
		for (int i = 0; i < entries.size(); i++) {
			add(entries.get(i));
		}
	}
	
	public ArrayList<FeedEntry> getFeed(){
		return feed;
	}

}
