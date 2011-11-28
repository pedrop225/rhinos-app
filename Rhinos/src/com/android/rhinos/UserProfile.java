package com.android.rhinos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.rhinos.gest.User;

public class UserProfile extends Activity {
	
	private TextView up_id;
	private TextView up_user;
	private TextView up_name;
	private TextView up_mail;
	private TableLayout up_campaigns;
	
	private UserProfile _this = this;
	public Bundle _bundle;
	
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);		
		_bundle = savedInstanceState;

		up_id = (TextView) findViewById(R.id.user_profile_id);
		up_user = (TextView) findViewById(R.id.user_profile_user);
		up_name = (TextView) findViewById(R.id.user_profile_name);
		up_mail = (TextView) findViewById(R.id.user_profile_mail);
		up_campaigns = (TableLayout) findViewById(R.id.user_profile_campaigns);
		
		user = (User) getIntent().getSerializableExtra("user");
		
		up_id.setText(user.getExtId());
		up_user.setText(user.getUser());
		up_name.setText(user.getName());
		up_mail.setText(user.getMail());
		
		/*
		ArrayList<Service> services = App.src.getServices(client.getId().toString());
		for (Service s : services) {
			ServiceRowItem item = new ServiceRowItem(ClientProfile.this, s, _this);
			
			LayoutParams lp = new LayoutParams(	LayoutParams.FILL_PARENT,
												LayoutParams.WRAP_CONTENT, 1f);
			cp_services.addView(item, lp);
		}*/
	}
}
