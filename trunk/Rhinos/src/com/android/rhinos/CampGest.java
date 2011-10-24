package com.android.rhinos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.rhinos.gest.Campaign;

public class CampGest extends Activity {
	
	private EditText repo;
	private Spinner spin;
	private TextView text;
	
	private Button updt;
	
	private void initialize() {
		repo = (EditText) findViewById(R.id.tRepository);
		spin = (Spinner) findViewById(R.id.campSpinner);
		text = (TextView) findViewById(R.id.detCampaign);
		updt = (Button) findViewById(R.id.bActualizar);

		repo.setText(App.repository);
		
		ArrayList<Campaign> c = App.src.getCampaigns();
		spin.setAdapter(new ArrayAdapter<Campaign>(CampGest.this, android.R.layout.simple_spinner_item, c));

		updt.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				App.repository = repo.getText().toString();		
				try {
					ArrayList<Campaign> campaigns = Campaign.containerLoader(new URL(App.repository));
					
					spin.setAdapter(new ArrayAdapter<Campaign>(CampGest.this, android.R.layout.simple_spinner_item, campaigns));
					spin.setSelection(0, true);
					text.setText(((Campaign)spin.getSelectedItem()).getDetails());
					
					//adding to source connector
					App.src.clearCampaigns();
					for (Campaign c : campaigns) {
						App.src.insertCampaign(c);
					}
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				text.setText(((Campaign)spin.getSelectedItem()).getDetails());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.campaigns);
		initialize();
	}
}
