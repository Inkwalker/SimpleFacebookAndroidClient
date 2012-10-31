package com.nemo.fbdemo.model;

import java.util.Vector;

import com.nemo.fbdemo.network.UserDownloader;

public class UsersList {
		
	public interface Callback{
		public void userDataLoaded();
	}
	
	private Vector<User> users;
	private Callback callback;
	
	public UsersList() {
		users = new Vector<User>();
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
			UserDownloader.Download(result, new UserDownloader.Callback() {
				@Override
				public void Downloaded(User user) {
					if(callback != null) callback.userDataLoaded();
				}
			});
		}
		
		return result;
	}
	
}
