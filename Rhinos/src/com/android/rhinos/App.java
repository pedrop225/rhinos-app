package com.android.rhinos;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;

import android.app.Application;
import connectors.Connector;
import connectors.DBConnector;

public class App extends Application {
	
	public static String repository;
	public static Connector src;
	
	public static ArrayList<Campaign> Cremoved;
	
	public static final int STORED = 0;
	public static final int NOT_STORED = 1; 
	
	@Override
	public void onCreate() {
		super.onCreate();	
		src = new DBConnector(getApplicationContext());
		repository = "http://www.fileden.com/files/2011/10/5/3205115/campaigns_repository.cfg";
		Cremoved = new ArrayList<Campaign>();
	}
}