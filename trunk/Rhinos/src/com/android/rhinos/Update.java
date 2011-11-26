package com.android.rhinos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Update extends Activity {

	private Button update_button;
	private TextView update_status_bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
	
		update_button = (Button) findViewById(R.id.update_button);
		update_status_bar = (TextView) findViewById(R.id.update_status_bar);
		
		update_button.setEnabled(App.user.isOnline());
		update_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String version = getString(R.string.app_version);
				String c_version = App.src.getCurrentVersion();
				
				if (version.equals(c_version))
					update_status_bar.setText("Versión actualizada ...");
				else {
					Intent download = new Intent(Intent.ACTION_VIEW, Uri.parse(App.external_path+"/download/Rhinos_"+c_version+".apk"));
					startActivity(download);
					update_status_bar.setText("Actualizando a "+c_version+" ...");
				}					
			}
		});
	}
}
