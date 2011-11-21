package com.android.rhinos;

import javax.crypto.SecretKey;

import android.app.Application;
import android.util.Log;

import com.android.rhinos.cipher.RCipher;
import com.android.rhinos.gest.User;

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
		
		String p = "Pedro Alfonso Pérez";
		String q = "";
		
		try {
			SecretKey key = RCipher.importKeyFromUrl(App.external_path+"/security/security.keys");
			RCipher rc = new RCipher(key);
			
			Log.v("log_tag", p);
			Log.v("log_tag", q = rc.encode(p));
			Log.v("log_tag", rc.decode(q));
		}
		catch (Exception e) {e.printStackTrace();}
	}
}