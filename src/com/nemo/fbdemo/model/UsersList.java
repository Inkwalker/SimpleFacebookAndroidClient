package com.nemo.fbdemo.model;

import java.util.ArrayList;

public class UsersList {
	
	private static UsersList instance;
	
	private ArrayList<User> users;
	
	private UsersList() {
		users = new ArrayList<User>();
	}
	
	public User getUser(String id) {
		User result = null;
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getId() == id){
				result = users.get(i);
				break;
			}
		}
		
		if(result == null){
			result = new User();
		}
		
		return result;
	}
	
	public static UsersList getInstance() {
		if(instance == null){
			instance = new UsersList();
		}
		
		return instance;
	}
	
}
