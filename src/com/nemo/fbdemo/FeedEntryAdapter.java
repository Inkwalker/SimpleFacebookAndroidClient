package com.nemo.fbdemo;

import java.util.ArrayList;

import com.nemo.fbdemo.model.FeedEntry;
import com.nemo.fbdemo.model.FeedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedEntryAdapter extends BaseAdapter {

	ArrayList<FeedEntry> data;
	
	public FeedEntryAdapter(FeedList data){
		super();
		
		this.data = data.getFeed();
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
	public View getView(int position, View oldView, ViewGroup parent) {
		View view;
		if(oldView == null){
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.feed_entry, null);
		}
		else{
			view = oldView;
			cleanView(view);
		}
		
		TextView userNameView    = (TextView)view.findViewById(R.id.feed_userName);
		TextView feedMessageView = (TextView)view.findViewById(R.id.feed_message);
		ImageView userPicView    = (ImageView)view.findViewById(R.id.feed_userPic);
		ImageView feedPicView    = (ImageView)view.findViewById(R.id.feed_pic);
		
		String userName = data.get(position).getUser().getName();
		Bitmap pic      = data.get(position).getUser().getPicture();
		
		userNameView.setText(userName);
		if(pic != null) userPicView.setImageBitmap(pic);
		
		String feedMessage;
		switch (data.get(position).getType()) {
		case Status:
			feedMessage = data.get(position).getMessage();
			feedPicView.setImageBitmap(null);
			break;
		case Photo:
			feedMessage = data.get(position).getDescription();
			if(data.get(position).getPicture() != null) feedPicView.setImageBitmap(data.get(position).getPicture());
			break;

		default:
			feedMessage = "unsupported type";
			break;
		}
		
		feedMessageView.setText(feedMessage);
		
		return view;
	}
	
	private void cleanView(View view){

		TextView feedMessageView = (TextView)view.findViewById(R.id.feed_message);
		ImageView feedPicView    = (ImageView)view.findViewById(R.id.feed_pic);
		
		feedMessageView.setText("");
		feedPicView.setImageBitmap(null);
	}

}
