package com.android.rhinos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rhinos.gest.User;

public class CreateAccount extends Activity {

	private EditText ca_user;
	private EditText ca_name;
	private EditText ca_mail;
	private EditText ca_password1;
	private EditText ca_password2;
	private	TextView ca_status_bar;
	private Button ca_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		ca_user = (EditText) findViewById(R.id.create_account_user);
		ca_name = (EditText) findViewById(R.id.create_account_name);
		ca_mail = (EditText) findViewById(R.id.create_account_mail);
		ca_password1 = (EditText) findViewById(R.id.create_account_password1);
		ca_password2 = (EditText) findViewById(R.id.create_account_password2);
		ca_status_bar = (TextView) findViewById(R.id.create_account_status_bar);
		ca_button = (Button) findViewById(R.id.create_account_button);
		
		ca_button.setEnabled(App.user.isOnline());
		
		ca_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				User u = new User();
				u.setUser(ca_user.getText().toString());
				u.setName(ca_name.getText().toString());
				u.setMail(ca_mail.getText().toString());
				
				if (checkForm()) {
					if (App.src.createAccount(u, ca_password1.getText().toString().trim()))
						ca_status_bar.setText("Cuenta creada con éxito ...");
					else
						ca_status_bar.setText("Error:  No se pudo crear la cuenta!!");
				}
			}
		});
	}
	
	private boolean checkForm() {
		if (!ca_password1.getText().toString().equals(ca_password2.getText().toString())) {
			ca_status_bar.setText("Error:  Las contraseñas no coinciden!!");
			return false;
		}
		
		if ((ca_user.getText().toString().trim().length() == 0) ||
			(ca_name.getText().toString().trim().length() == 0) ||
			(ca_mail.getText().toString().trim().length() == 0) ||
			(ca_password1.getText().toString().trim().length() == 0) ||
			(ca_password2.getText().toString().trim().length() == 0)) {
			
			ca_status_bar.setText("Error:  Datos incompletos!!");

			return false;
		}
		
		return true;
	}
}
