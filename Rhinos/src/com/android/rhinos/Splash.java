package com.android.rhinos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity {

	private MediaPlayer mPlayer;	
	private TextView splashText;
	
	public void initialize() {
		//initializing
		splashText = (TextView)findViewById(R.id.splashText);
		
		//setting fonts
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rhinos_font.ttf");	
		splashText.setTypeface(tf);
				
		//initializing sound
		mPlayer = MediaPlayer.create(Splash.this, R.raw.rhinos_splash_sound);
		mPlayer.start();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		initialize();
		startActivity();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mPlayer.release();
		finish();
	}
	
	public void startActivity () {
		
		new Thread() {
			public void run() {
				try {
					sleep(5000);
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
				finally {
					Intent startMenuIntent = new Intent("com.android.rhinos.Menu");
					startActivity(startMenuIntent);
				}
			}
		}.start();
	}	
}