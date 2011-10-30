package com.android.rhinos;

import android.R.anim;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.android.rhinos.gest.Client;

public class ClientProfile extends Activity {

	private TextView cp_name;
	private TextView cp_id;
	private TextView cp_tlf_1;
	private TextView cp_tlf_2;
	private TextView cp_mail;
	private TextView cp_address;
	
	private Client client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_profile);
		overridePendingTransition(anim.fade_in, anim.fade_out);
		
		cp_name = (TextView) findViewById(R.id.client_profile_name);
		cp_id = (TextView) findViewById(R.id.client_profile_id);
		cp_tlf_1 = (TextView) findViewById(R.id.client_profile_tlf_1);
		cp_tlf_2 = (TextView) findViewById(R.id.client_profile_tlf_2);
		cp_mail = (TextView) findViewById(R.id.client_profile_mail);
		cp_address = (TextView) findViewById(R.id.client_profile_address);
		
		client = (Client) getIntent().getSerializableExtra("client");
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rhinos_font.ttf");	
		cp_name.setTypeface(tf);
		
		cp_name.setText(client.getName());
		cp_id.setText(client.getId().toString());
		cp_tlf_1.setText(client.getTlf_1());
		cp_tlf_2.setText(client.getTlf_2());
		cp_mail.setText(client.getMail());
		cp_address.setText(client.getAddress());
	}
}
