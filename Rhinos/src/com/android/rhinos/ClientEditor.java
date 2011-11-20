package com.android.rhinos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;

public class ClientEditor extends Activity {
		
	private EditText clientName;
	private EditText clientId;
	private ImageView clientIdState;
	private EditText clientTlf_1;
	private EditText clientTlf_2;
	private EditText clientMail;
	private EditText clientAddress;
	
	private Spinner idSpinner;
	private ImageButton addCampaignButton;
	
	private Client client;
	private int client_status;
			
	private void initialize() {
		clientName = (EditText) findViewById(R.id.clientName);
		clientId = (EditText) findViewById(R.id.clientId);
		clientIdState = (ImageView) findViewById(R.id.clientIdState);
		clientTlf_1 = (EditText) findViewById(R.id.clientTlf1);
		clientTlf_2 = (EditText) findViewById(R.id.clientTlf2);
		clientMail = (EditText) findViewById(R.id.clientMail);
		clientAddress = (EditText) findViewById(R.id.clientAddress);
		addCampaignButton = (ImageButton) findViewById(R.id.addCampaignButton);
		idSpinner = (Spinner) findViewById(R.id.idSpinner);
		
		client_status = App.NOT_STORED;
		
		addCampaignButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (checkData()) {
					Intent intent = new Intent().setClass(ClientEditor.this, ServiceEditor.class);
					intent.putExtra("client", client);
					intent.putExtra("client_status", client_status);
					startActivity(intent);
				}
				else {
					AlertDialog builder = new AlertDialog.Builder(ClientEditor.this).create();
					builder.setMessage(	"Los datos introducidos son insuficientes o incorrectos. " +
										"Por favor, verifíquelos.");
					
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setTitle("Error. ");
					builder.show();
				}
			}
		});
		
		idSpinner.setAdapter(new ArrayAdapter<String>(ClientEditor.this, android.R.layout.simple_spinner_item, Id.TYPES));
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

	private void cleanFields() {
		clientName.setText("");
		clientTlf_1.setText("");
		clientTlf_2.setText("");
		clientMail.setText("");
		clientAddress.setText("");
	}
	
	private void updateClientIdState() {
		switch (idSpinner.getSelectedItemPosition()) {
			case Id.DNI: client.setId(new Dni(clientId.getText().toString())); break;
			case Id.NIE: client.setId(new Nie(clientId.getText().toString())); break;
			case Id.CIF: client.setId(new Dni(clientId.getText().toString())); break;
		}
		
		if (client.getId().isValid()) {	
			Client temp;
			//client is valid and already exist.
			if ((temp = App.src.clientExists(clientId.getText().toString())) != null){
				clientIdState.setImageResource(android.R.drawable.ic_menu_crop);
				client_status = App.STORED;
				
				clientName.setText(temp.getName());
				clientTlf_1.setText(temp.getTlf_1());
				clientTlf_2.setText(temp.getTlf_2());
				clientMail.setText(temp.getMail());
				clientAddress.setText(temp.getAddress());
				
				clientName.requestFocus();
			}
			else {
				client_status = App.NOT_STORED;
				clientIdState.setImageResource(R.drawable.action_check);
			}
		}
		else {
			cleanFields();
			clientIdState.setImageResource(android.R.drawable.ic_delete);
		}
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
			(client.getAddress().trim().length() == 0))
				return false;
		return true;
	}
}
