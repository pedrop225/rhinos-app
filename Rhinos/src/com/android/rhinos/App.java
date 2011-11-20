package com.android.rhinos;

import com.android.rhinos.gest.User;

import android.app.Application;
import connectors.Connector;

public class App extends Application {
	
	public static final String external_path = "http://pedrop225.comuf.com/rhinos";	
	public static String repository = external_path+"/campaigns/campaigns_repository.cfg";
	
	public static Connector src;
	public static User user;
		
	public static final int STORED = 0;
	public static final int NOT_STORED = 1; 
	
	@Override
	public void onCreate() {
		super.onCreate();	
		user = new User();
	}
}