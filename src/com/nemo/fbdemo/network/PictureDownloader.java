package com.nemo.fbdemo.network;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureDownloader {
	public interface Callback{
		public void downloaded(Bitmap picture);
	}
	
	public static void download(final String url, final Callback callback){
	 
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
			    	Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
			    	callback.downloaded(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();

	}
}
