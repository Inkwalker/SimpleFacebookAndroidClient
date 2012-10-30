package com.nemo.fbdemo;

import java.util.ArrayList;
import com.nemo.fbdemo.model.FeedEntity;
import com.nemo.fbdemo.model.StatusEntry;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedEntityAdapter extends BaseAdapter {

	ArrayList<FeedEntity> data;
	
	public FeedEntityAdapter(ArrayList<FeedEntity> data){
		super();
		
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view;
		
		if(arg1 == null){
			LayoutInflater inflater = (LayoutInflater)arg2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view = inflater.inflate(R.layout.feed_entry, null);
		}
		else{
			view = arg1;
		}
		
		TextView userNameView = (TextView)view.findViewById(R.id.feed_userName);
		TextView feedMessageView = (TextView)view.findViewById(R.id.feed_message);
		ImageView userPicView = (ImageView)view.findViewById(R.id.feed_userPic);
		
		if(data.get(arg0) != null){
			String userName = data.get(arg0).getUser().getName();
			Bitmap pic = data.get(arg0).getUser().getPicture();
			
			userNameView.setText(userName);
			if(pic != null) userPicView.setImageBitmap(pic);
			
			if(data.get(arg0) instanceof StatusEntry){
				String feedMessage = ((StatusEntry)data.get(arg0)).getMessage();
				feedMessageView.setText(feedMessage);
			}
			else{
				feedMessageView.setText("unknown type");
			}
		}
		else {
			feedMessageView.setText("unknown type");
			userNameView.setText("---");
		}
		
		
		return view;
	}

}
