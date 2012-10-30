package com.nemo.fbdemo.network;

import java.util.ArrayList;

import com.nemo.fbdemo.model.FeedEntity;

public interface FeedDownloaderCallback {
	public void Downloaded(ArrayList<FeedEntity> entities);
}
