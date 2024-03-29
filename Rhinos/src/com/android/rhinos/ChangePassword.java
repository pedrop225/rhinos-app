package com.android.rhinos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePassword extends Activity {

	private EditText cp_pass;
	private EditText cp_newpass1;
	private EditText cp_newpass2;
	
	private Button cp_button;
	private TextView cp_status_bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		
		cp_pass = (EditText) findViewById(R.id.change_password_pass);
		cp_newpass1 = (EditText) findViewById(R.id.change_password_newpass1);
		cp_newpass2 = (EditText) findViewById(R.id.change_password_newpass2);
		cp_button = (Button) findViewById(R.id.change_password_button);
		cp_status_bar = (TextView) findViewById(R.id.change_password_status_bar);
		
		cp_button.setEnabled(App.user.isOnline());
		
		cp_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (checkForm()) {
					
					AsyncChangePassword asynccp = new AsyncChangePassword();
					asynccp.execute();
					
					cp_pass.setText("");
					cp_newpass1.setText("");
					cp_newpass2.setText("");
					
					cp_status_bar.setText(" Cambio efectuado con �xito ..");
				}
			}
		});
	}
	
	private boolean checkForm() {
		
		if (!cp_newpass1.getText().toString().equals(cp_newpass2.getText().toString())) {
			cp_status_bar.setText("Error:  Las contrase�as no coinciden!!");
			return false;
		}
		
		if ((cp_pass.getText().toString().trim().length() == 0) ||
			(cp_newpass1.getText().toString().trim().length() == 0) ||
			(cp_newpass2.getText().toString().trim().length() == 0)) {
			
			cp_status_bar.setText("Error:  Datos incompletos!!");

			return false;
		}
		
		if (!App.src.login(App.user.getUser(), cp_pass.getText().toString().trim())) {
			cp_status_bar.setText("Error:  Contrase�a actual incorrecta!!");
			return false;
		}
		
		return true;
	}
	
	private class AsyncChangePassword extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new ProgressDialog(ChangePassword.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Actualizando ...");
			
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				App.src.changePassword(App.user.getUser(), cp_newpass1.getText().toString().trim());
			}
			catch (Exception e) {}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}
}
