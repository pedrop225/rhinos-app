package com.android.rhinos;

import com.android.rhinos.gest.User;

import android.app.Application;
import connectors.Connector;

public class App extends Application {
	
	public static String repository;
	public static Connector src;
	public static User user;
		
	public static final int STORED = 0;
	public static final int NOT_STORED = 1; 
	
	@Override
	public void onCreate() {
		super.onCreate();	
		repository = "http://pedrop225.comuf.com/rhinos/campaigns/campaigns_repository.cfg";
		user = new User();
	}
}