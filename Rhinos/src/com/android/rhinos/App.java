package com.android.rhinos;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

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
		
		try {
			String p = "Pedro Alfonso Pérez";
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			
			Cipher c_enc = Cipher.getInstance("DES");
			Cipher c_dec = Cipher.getInstance("DES");
			
			c_enc.init(Cipher.ENCRYPT_MODE, key);
			c_dec.init(Cipher.DECRYPT_MODE, key);
			
			byte [] utf8 = p.getBytes("UTF8");
			
			byte [] enc = c_enc.doFinal(utf8);
			
			Log.v("log_tag", Base64.encodeToString(enc, Base64.DEFAULT));
			
			String rec = new String(c_dec.doFinal(enc), "UTF8"); 
			
			Log.v("log_tag", rec);
		} 
		catch (Exception e) {e.printStackTrace();};
	}
}