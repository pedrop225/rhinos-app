package com.android.rhinos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;

public class ServiceEditor extends Activity {
	
	private Spinner campaignSpinner;
	private Spinner serviceSpinner;
	private EditText service_tlf_1;
	private ToggleButton service_tlf_1_new;
	private EditText service_tlf_2;
	private ToggleButton service_tlf_2_new;
	private DatePicker service_date_picker;
	private Button addServicio;

	private Service service;
	private ArrayList<Campaign> campaigns;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_editor);
		
		campaignSpinner = (Spinner) findViewById(R.id.campaignSpinner);
		serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
		service_tlf_1 = (EditText) findViewById(R.id.service_tlf_1);
		service_tlf_2 = (EditText) findViewById(R.id.service_tlf_2);
		service_tlf_1_new = (ToggleButton) findViewById(R.id.service_tlf1_new);
		service_tlf_2_new = (ToggleButton) findViewById(R.id.service_tlf_2_new);
		service_date_picker = (DatePicker) findViewById(R.id.service_date_picker);
		addServicio = (Button) findViewById(R.id.addServicio);
		
		service = new Service();
		campaigns = App.src.getCampaigns();
		
		campaignSpinner.setAdapter(new ArrayAdapter<Campaign>(ServiceEditor.this, android.R.layout.simple_spinner_item, campaigns));
		  
		ArrayList<Service> c1 = new ArrayList<Service>(((Campaign)campaignSpinner.getSelectedItem()).getServices().values());
		serviceSpinner.setAdapter(new ArrayAdapter<Service>(ServiceEditor.this, android.R.layout.simple_spinner_item, c1));
		
		campaignSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				ArrayList<Service> c1 = new ArrayList<Service>(((Campaign)campaignSpinner.getSelectedItem()).getServices().values());
				Collections.sort(c1);
				serviceSpinner.setAdapter(new ArrayAdapter<Service>(ServiceEditor.this, android.R.layout.simple_spinner_item, c1));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
		});
		
		service_tlf_1_new.setChecked(true);
		service_tlf_1_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				service_tlf_1.setEnabled(service_tlf_1_new.isChecked());
				
				if (!service_tlf_1_new.isChecked())  
					service_tlf_1.setText("Nuevo");
				else
					service_tlf_1.setText("");
			}
		});
		
		service_tlf_2_new.setChecked(true);
		service_tlf_2_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				service_tlf_2.setEnabled(service_tlf_2_new.isChecked());
				
				if (!service_tlf_2_new.isChecked())  
					service_tlf_2.setText("Nuevo");
				else
					service_tlf_2.setText("");
			}
		});
		
		addServicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				service.setService(serviceSpinner.getSelectedItem().toString());
				service.setCampaign(campaignSpinner.getSelectedItem().toString());

				Date date = new Date();
				date.setYear(service_date_picker.getYear() - 1900);
				date.setMonth(service_date_picker.getMonth());
				date.setDate(service_date_picker.getDayOfMonth());
				service.setDate(date);
				
				service.setCommission(((Service)serviceSpinner.getSelectedItem()).getCommission());
				service.setTlf_1(service_tlf_1.getText().toString());
				service.setTlf_2(service_tlf_2.getText().toString());
								
				Client client = (Client) getIntent().getSerializableExtra("client");
				int client_status = getIntent().getIntExtra("client_status", -1);
				
				if (client_status == App.NOT_STORED)
					App.src.addClient(client);
				
				App.src.addService(service, client);				
				finish();
			}
		});
	}
}
