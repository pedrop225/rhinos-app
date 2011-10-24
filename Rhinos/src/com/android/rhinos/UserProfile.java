package com.android.rhinos;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;

public class UserProfile extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_profile);
		
		overridePendingTransition(anim.fade_in, anim.fade_out);
	}
}
