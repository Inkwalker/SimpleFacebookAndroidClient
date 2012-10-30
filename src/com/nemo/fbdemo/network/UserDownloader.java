package com.nemo.fbdemo.network;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nemo.fbdemo.model.User;

public class UserDownloader {
	
	//TODO load bath
	public void Download(final User user, final UserDownloaderCallback callback){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				user.setPicture(getUserPic(user.getId()));
				callback.Downloaded(user);
			}
		}).start();

	}
	
	public Bitmap getUserPic(String userID) {
	    String imageURL;
	    Bitmap bitmap = null;
	    imageURL = "http://graph.facebook.com/"+userID+"/picture?type=small";
	    try {
	        bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return bitmap;
	}
	
}
