package com.android.rhinos;

import javax.crypto.SecretKey;

import android.app.Application;

import com.android.rhinos.cipher.RCipher;
import com.android.rhinos.gest.User;

import connectors.Connector;

public class App extends Application {
	
	public static final String external_path = "http://pedrop225.comuf.com/rhinos";	
	public static String repository = external_path+"/campaigns/campaigns_repository.cfg";
	
	public static Connector src;
	public static User user;
	public static RCipher cipher;
		
	public static final int STORED = 0;
	public static final int NOT_STORED = 1; 
	
	@Override
	public void onCreate() {
		super.onCreate();	
		user = new User();
		
		try {
			SecretKey key = RCipher.importKeyFromUrl(App.external_path+"/security/security.keys");
			cipher = new RCipher(key);
		}
		catch (Exception e) {e.printStackTrace();}
	}
}