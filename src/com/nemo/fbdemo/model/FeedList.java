package com.nemo.fbdemo.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.nemo.fbdemo.network.PictureDownloader;

public class FeedList {
	
	public interface Callback{
		public void dataChanged();
	}
	
	String nextPageUrl;
	String prevPageUrl;
	
	Callback callback;
	
	ArrayList<FeedEntry> feed;
	
	public FeedList(){
		feed = new ArrayList<FeedEntry>();
	}
	
	public void setCallback(Callback callback){
		this.callback = callback;
	}
	
	public void add(final FeedEntry entry, boolean downloadPictures){
		String pictureUrl = entry.getPictureUrl();
		
		if(entry.getType() == FeedEntryType.Status && entry.getMessage().equals("")) return; //bugfix (empty status messages)
		
		if(downloadPictures && !pictureUrl.equals("")){
			PictureDownloader.getInstance().download(pictureUrl, new PictureDownloader.Callback() {
				
				@Override
				public void downloaded(Bitmap picture) {
					entry.setPicture(picture);
					if(callback != null) callback.dataChanged();
				}
			});
		}
		
		feed.add(entry);
	}
	
	public void add(ArrayList<FeedEntry> entries, boolean downloadPictures){
		for (int i = 0; i < entries.size(); i++) {
			add(entries.get(i), downloadPictures);
		}
	}
	
	public void add(FeedList list, boolean toEnd){
		if(toEnd){
			add(list.getFeed(), true);
			nextPageUrl = list.getNextPageUrl();
		} else {
			//TODO
		}
		
		
	}
	
	public ArrayList<FeedEntry> getFeed(){
		return feed;
	}
	
	public void clear(){
		feed.clear();
	}
	
	
	public String getNextPageUrl(){
		return nextPageUrl;
	}
	
	public void setNextPageUrl(String url){
		nextPageUrl = url;
	}
	
	public String getPrevPageUrl(){
		return prevPageUrl;
	}
	
	public void setPrevPageUrl(String url){
		prevPageUrl = url;
	}

}
