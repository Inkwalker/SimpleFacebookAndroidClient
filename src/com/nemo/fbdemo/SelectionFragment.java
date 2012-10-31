package com.nemo.fbdemo;

import java.util.ArrayList;

import com.facebook.GraphUser;
import com.facebook.ProfilePictureView;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.Response;
import com.nemo.fbdemo.model.FeedEntry;
import com.nemo.fbdemo.model.FeedEntryType;
import com.nemo.fbdemo.model.FeedList;
import com.nemo.fbdemo.model.UsersList;
import com.nemo.fbdemo.network.FeedDownloader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SelectionFragment extends Fragment {
	
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private ListView feedListView;
	private FeedEntryAdapter feedAdapter;
	private UsersList users;
	private FeedList feedList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState){
	    super.onCreateView(inflater, container, savedInstanceState);
	    
	    View view = inflater.inflate(R.layout.selection, container, false);

	    profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
	    profilePictureView.setCropped(true);

	    userNameView = (TextView) view.findViewById(R.id.selection_user_name);
	    
	    feedListView = (ListView) view.findViewById(R.id.feed);
	    feedListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
				FeedEntry entry = feedList.getFeed().get((int)id);
				if(entry.getType() == FeedEntryType.Link ||
				   entry.getType() == FeedEntryType.Photo ||
				   entry.getType() == FeedEntryType.Video){
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(entry.getLink()));
					startActivity(browserIntent);
				}
			}
	    	
		});
	    
		users = new UsersList();
		users.setCallback(new UsersList.Callback() {
			
			@Override
			public void userDataLoaded() {
				if(feedAdapter != null){
					
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							feedAdapter.notifyDataSetChanged();
						}
					});
				}
			}
			
		});
		
		feedList = new FeedList();
		feedList.setCallback(new FeedList.Callback() {
			
			@Override
			public void dataChanged() {
				if(feedAdapter != null){
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							feedAdapter.notifyDataSetChanged();
						}
					});
				}
			}
		});

	    final Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
	        	
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	                if (session == Session.getActiveSession()) {
	                    if (user != null) {
	                        profilePictureView.setUserId(user.getId());
	                        userNameView.setText(user.getName());
	                    }   
	                }   
	            }
	            
	        });
	        
	        FeedDownloader.Download(users, new FeedDownloader.Callback() {
				
				@Override
				public void Downloaded(ArrayList<FeedEntry> entries) {
					feedList.add(entries);
					feedAdapter = new FeedEntryAdapter(feedList);
					feedListView.setAdapter(feedAdapter);					
				}
			});
	        
	        Request.executeBatchAsync(request);
	    }  
	    
	    return view;
	}
}
