package com.nemo.fbdemo.network;

import android.graphics.Bitmap;
import com.nemo.fbdemo.model.User;

public class UserDownloader {
	
	public interface Callback{
		public void Downloaded(User user);
	}
	
	//TODO load bath
	public static void Download(final User user, final Callback callback){
		
		String imageURL = "http://graph.facebook.com/"+user.getId()+"/picture?type=small";
		
		PictureDownloader.getInstance().download(imageURL, new PictureDownloader.Callback() {			
			@Override
			public void downloaded(Bitmap picture) {
				user.setPicture(picture);
				callback.Downloaded(user);
			}
		});

	}	
}
