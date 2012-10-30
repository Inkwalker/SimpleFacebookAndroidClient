package com.nemo.fbdemo.model;

import java.util.Vector;

import com.nemo.fbdemo.network.UserDownloader;
import com.nemo.fbdemo.network.UserDownloaderCallback;

public class UsersList {
		
	public interface Callback{
		public void userDataLoaded();
	}
	
	private Vector<User> users;
	private UserDownloader downloader;
	private Callback callback;
	
	public UsersList() {
		users = new Vector<User>();
		downloader = new UserDownloader();
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public User getUser(String id, String name) {
		User result = null;
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getId() == id){
				result = users.get(i);
				break;
			}
		}
		
		if(result == null){
			result = new User();
			
			result.setId(id);
			result.setName(name);
			
			users.add(result);			
			downloader.Download(result, new UserDownloaderCallback() {
				
				@Override
				public void Downloaded(User user) {
					if(callback != null) callback.userDataLoaded();
				}
			});
		}
		
		return result;
	}
	
}
