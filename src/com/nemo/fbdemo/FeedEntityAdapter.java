package com.nemo.fbdemo;

import java.util.ArrayList;

import com.nemo.fbdemo.model.FeedEntity;
import com.nemo.fbdemo.model.StatusEntry;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view;
		
		if(arg1 == null){
			view = new TextView(arg2.getContext());
		}
		else{
			view = arg1;
		}
		
		if(data.get(arg0) instanceof StatusEntry){
			((TextView)view).setText(((StatusEntry)data.get(arg0)).getMessage());
		}
		else{
			((TextView)view).setText("unknown type");
		}
		
		return view;
	}

}
