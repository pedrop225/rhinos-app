package com.android.rhinos;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;

public class AddContract extends Activity {
		
	private EditText clientName;
	private EditText clientId;
	private ImageView clientIdState;
	private EditText clientTlf_1;
	private EditText clientTlf_2;
	private EditText clientMail;
	private EditText clientAddress;
	private EditText clientServices;
	
	private Spinner idSpinner;
	private Button save;
	
	private Client client;
			
	private void initialize() {
		clientName = (EditText) findViewById(R.id.clientName);
		clientId = (EditText) findViewById(R.id.clientId);
		clientIdState = (ImageView) findViewById(R.id.clientIdState);
		clientTlf_1 = (EditText) findViewById(R.id.clientTlf1);
		clientTlf_2 = (EditText) findViewById(R.id.clientTlf2);
		clientMail = (EditText) findViewById(R.id.clientMail);
		clientAddress = (EditText) findViewById(R.id.clientAddress);
		clientServices = (EditText) findViewById(R.id.clientServices);
		
		idSpinner = (Spinner) findViewById(R.id.idSpinner);
		
		save = (Button) findViewById(R.id.bSaveClient);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (checkData()) {
					App.src.addClient(client);
					finish();
				}
			}
		});
		
		idSpinner.setAdapter(new ArrayAdapter<String>(AddContract.this, android.R.layout.simple_spinner_item, Id.TYPES));
		idSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				updateClientIdState();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		client = new Client();
		clientId.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				updateClientIdState();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_editor);
		initialize();
	}

	private void updateClientIdState() {
		switch (idSpinner.getSelectedItemPosition()) {
			case Id.DNI: client.setId(new Dni(clientId.getText().toString())); break;
			case Id.NIE: client.setId(new Nie(clientId.getText().toString())); break;
			case Id.CIF: client.setId(new Dni(clientId.getText().toString())); break;
		}
		clientIdState.setImageResource(client.getId().isValid() ? R.drawable.action_check : R.drawable.action_delete);
	}
	
	private boolean checkData() {
		client.setName(clientName.getText().toString());
		updateClientIdState();
		client.setTlf_1(clientTlf_1.getText().toString());
		client.setTlf_2(clientTlf_2.getText().toString());
		client.setMail(clientMail.getText().toString());
		client.setAddress(clientAddress.getText().toString());
		
		//checking
		if 	((client.getName().trim().length() == 0) ||
			(!client.getId().isValid()) ||
			(client.getTlf_1().trim().length() == 0) ||
			(client.getTlf_2().trim().length() == 0) ||
			(client.getAddress().trim().length() == 0))
				return false;
		return true;
	}
}
