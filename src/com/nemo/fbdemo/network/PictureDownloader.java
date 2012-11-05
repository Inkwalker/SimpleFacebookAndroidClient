package com.nemo.fbdemo.network;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class PictureDownloader {
	public interface Callback{
		public void downloaded(Bitmap picture);
	}
	
	private final int DOWNLOAD_QUEUE_SIZE = 5;
	
	private static Hashtable<String, Bitmap> bitmaps = new Hashtable<String, Bitmap>();
	private ArrayList<DownloadEntity> downloadQueue = new ArrayList<DownloadEntity>();
	private ArrayList<DownloadEntity> downloading = new ArrayList<DownloadEntity>();
	
	private static PictureDownloader instance;
	public static PictureDownloader getInstance(){
		if(instance == null) instance = new PictureDownloader();
		return instance;
	}
	
	private PictureDownloader(){
		//worker thread
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(!downloadQueue.isEmpty() && downloading.size() < DOWNLOAD_QUEUE_SIZE){
						DownloadEntity entity = downloadQueue.get(0);
						downloadQueue.remove(0);
						downloading.add(entity);
						new BitmapDownloadTask(entity.callbacks).execute(entity.url);
					} else {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}).start(); 
	}
	
	public void download(String url, Callback callback){
		if(bitmaps.containsKey(url)){
			callback.downloaded(bitmaps.get(url));
		} else {
			for (int i = 0; i < downloadQueue.size(); i++) {
				if(downloadQueue.get(i).url.equals(url)){
					downloadQueue.get(i).callbacks.add(callback);
					return;
				}
			}
			
			for (int i = 0; i < downloading.size(); i++) {
				if(downloading.get(i).url.equals(url)){
					downloading.get(i).callbacks.add(callback);
					return;
				}				
			}
			DownloadEntity entity = new DownloadEntity();
			entity.url = url;
			entity.callbacks = new ArrayList<PictureDownloader.Callback>();
			entity.callbacks.add(callback);
			
			downloadQueue.add(entity);
		}			
	}
	
	private class BitmapDownloadTask extends AsyncTask<String, Boolean, Bitmap>{
		String url;
		ArrayList<PictureDownloader.Callback> callbacks;
		
		public BitmapDownloadTask(ArrayList<PictureDownloader.Callback> callbacks) {
			super();
			this.callbacks = callbacks;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				url = params[0];
		    	Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		    	return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			downloading.remove(url);
			bitmaps.put(url, result);
			for (PictureDownloader.Callback callback : callbacks) {
				callback.downloaded(result);
			}
			super.onPostExecute(result);
		}
		
	}
	
	private class DownloadEntity{
		public String url;
		public ArrayList<PictureDownloader.Callback> callbacks;
	}
}
