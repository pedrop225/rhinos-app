package com.android.rhinos;

import android.app.Application;
import connectors.Connector;
import connectors.DBConnector;

public class App extends Application {
	
	public static String repository;
	public static Connector src;
	
	@Override
	public void onCreate() {
		super.onCreate();	
		src = new DBConnector(getApplicationContext());
		repository = "http://www.fileden.com/files/2011/10/5/3205115//campaigns_repository.cfg";
	}
}