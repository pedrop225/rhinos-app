package com.android.rhinos;

import android.R.anim;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rhinos.gest.Client;

public class ClientProfile extends Activity {

	private TextView nameData;
	private EditText idData;
	private EditText tlf_1Data;
	private EditText tlf_2Data;
	
	private Client client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_profile);
		overridePendingTransition(anim.fade_in, anim.fade_out);
		
		nameData = (TextView) findViewById(R.id.nameData);
		idData = (EditText) findViewById(R.id.idData);
		tlf_1Data = (EditText) findViewById(R.id.tlf_1Data);
		tlf_2Data = (EditText) findViewById(R.id.tlf_2Data);
		
		client = (Client) getIntent().getSerializableExtra("client");
		
		nameData.setText(client.getName());
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rhinos_font.ttf");	
		nameData.setTypeface(tf);
		
		idData.setText(client.getId().toString());
		tlf_1Data.setText(client.getTlf_1());
		tlf_2Data.setText(client.getTlf_2());
	}
}
