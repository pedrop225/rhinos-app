package com.android.rhinos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddContract extends Activity {
		
	private EditText name;
	private Button save;
		
	private Spinner spin;
	
	private void initialize() {
		name = (EditText) findViewById(R.id.userName);
		spin = (Spinner) findViewById(R.id.idSpinner);
		save = (Button) findViewById(R.id.bSaveUser);
				
		//spin.setAdapter();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_editor);
		initialize();
	}
}
