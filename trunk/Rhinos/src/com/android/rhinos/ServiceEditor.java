package com.android.rhinos;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class ServiceEditor extends Dialog {
	
	private Spinner campaignSpinner;
	private Spinner serviceSpinner;
	private EditText serviceTlf_1;
	private ToggleButton service_tlf_1_new;
	private EditText serviceTlf_2;
	private ToggleButton service_tlf_2_new;
	private DatePicker service_date_picker;
	private Button addServicio;
	
	
	public ServiceEditor(Context context, Service s) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_editor);
		
		campaignSpinner = (Spinner) findViewById(R.id.campaignSpinner);
		serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
		addServicio = (Button) findViewById(R.id.addServicio);
		
		ArrayList<Campaign> c = App.src.getCampaigns();
		campaignSpinner.setAdapter(new ArrayAdapter<Campaign>(getContext(), android.R.layout.simple_spinner_item, c));
		
		/*
		ArrayList<Campaign> c1 = App.src.getCampaigns();
		serviceSpinner.setAdapter(new ArrayAdapter<Campaign>(getContext(), android.R.layout.simple_spinner_item, c1));*/
		
		addServicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
	}
}
