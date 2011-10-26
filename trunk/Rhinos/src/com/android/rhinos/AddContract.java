package com.android.rhinos;

import com.android.rhinos.gest.Id;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddContract extends Activity {
		
	private EditText name;
	private EditText clientId;
	private TextView clientTlf_1;
	private TextView clientTlf_2;
	private TextView clientMail;
	private TextView clientAddress;
	private TextView clientServices;
	
	private Spinner idSpinner;
	private Button save;
			
	private void initialize() {
		name = (EditText) findViewById(R.id.clientName);
		clientId = (EditText) findViewById(R.id.clientId);
		
		idSpinner = (Spinner) findViewById(R.id.idSpinner);
		save = (Button) findViewById(R.id.bSaveClient);
				
		idSpinner.setAdapter(new ArrayAdapter<String>(AddContract.this, android.R.layout.simple_spinner_item, Id.TYPES));
		
		save.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_editor);
		initialize();
	}
}
