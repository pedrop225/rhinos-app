package com.android.rhinos;

import com.android.rhinos.gest.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import connectors.DBConnector;
import connectors.MySqlConnector;

public class Splash extends Activity {

	private MediaPlayer mPlayer;	
	private TextView rhinosText;
	private TextView splash_status_bar;
	private EditText splash_user;
	private EditText splash_pass;
	private Button splash_online;
	private Button splash_offline;
	
	public void initialize() {
		//initializing
		rhinosText = (TextView) findViewById(R.id.rhinosText);
		splash_user = (EditText) findViewById(R.id.splash_user);
		splash_pass = (EditText) findViewById(R.id.splash_password);
		splash_online = (Button) findViewById(R.id.splash_online);
		splash_offline = (Button) findViewById(R.id.splash_offline);
		splash_status_bar = (TextView) findViewById(R.id.splash_status_bar);
		
		//setting fonts
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rhinos_font.ttf");	
		rhinosText.setTypeface(tf);
				
		//initializing sound
		mPlayer = MediaPlayer.create(Splash.this, R.raw.rhinos_splash_sound);
		mPlayer.start();
		
		splash_online.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				App.src = new MySqlConnector(getApplicationContext());
				
				String user = splash_user.getText().toString();
				String pass = splash_pass.getText().toString();
				
				if (App.src.login(user, pass)) {
					App.user.setConnectionMode(User.ONLINE);
					startActivity();
				}
				else
					splash_status_bar.setText("\tDatos erróneos!!");
			}
		});
		
		splash_offline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				App.src = new DBConnector(getApplicationContext());
				App.user.setConnectionMode(User.OFFLINE);
				App.user.doRoot();
				startActivity();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		initialize();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mPlayer.release();
	}
	
	public void startActivity () {
		Intent startMenuIntent = new Intent("com.android.rhinos.Menu");
		startActivity(startMenuIntent);
	}	
}