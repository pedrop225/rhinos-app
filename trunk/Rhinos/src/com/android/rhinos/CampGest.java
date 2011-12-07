package com.android.rhinos;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Service;

public class CampGest extends Activity {
	
	private EditText repo;
	private Spinner spin;
	private TableLayout table;
	private Button updt;
	
	private ArrayList<Campaign> campaigns;
	
	private void initialize() {
		repo = (EditText) findViewById(R.id.tRepository);
		spin = (Spinner) findViewById(R.id.campSpinner);
		table = (TableLayout) findViewById(R.id.campaign_table);
		updt = (Button) findViewById(R.id.bActualizar);

		campaigns = App.src.getCampaigns(App.user);
		spin.setAdapter(new ArrayAdapter<Campaign>(CampGest.this, android.R.layout.simple_spinner_item, campaigns));

		repo.setText(App.repository);
		repo.setEnabled(App.user.isRoot());
		
		updt.setEnabled(!App.user.isOnline() || App.user.isRoot());
		updt.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				App.repository = repo.getText().toString();		

				//inserting data into db
				AsyncAddCampaign ic = new AsyncAddCampaign();
				ic.execute();

				spin.setAdapter(new ArrayAdapter<Campaign>(CampGest.this, android.R.layout.simple_spinner_item, campaigns));
			}
		});
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				table.removeAllViews();
				ArrayList<Service> serv = new ArrayList<Service>(((Campaign)spin.getSelectedItem()).getServices().values());
				Collections.sort(serv);
				
				for (int i = 0; i < serv.size(); i++) {
					TextView service = new TextView(CampGest.this);
					service.setText(serv.get(i).getService());
					
					TableRow tr = new TableRow(CampGest.this);
					tr.addView(service);
					tr.setPadding(0, 2, 0, 2);
					
					table.addView(tr);
					
					if (i < serv.size() -1) {
						TableRow line = new TableRow(CampGest.this);
						line.setMinimumHeight(2);
						line.setBackgroundColor(Color.BLACK);
						table.addView(line);
					}
				}
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
	
	private class AsyncAddCampaign extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new ProgressDialog(CampGest.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Actualizando ...");
			
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				campaigns = Campaign.containerLoader(new URL(App.repository));					
				
				//adding to source connector
				App.src.clearCampaigns();
				for (Campaign c : campaigns) {
					App.src.addCampaign(c);
				}
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
