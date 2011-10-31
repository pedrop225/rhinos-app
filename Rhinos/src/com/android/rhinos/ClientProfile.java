package com.android.rhinos;

import java.util.ArrayList;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;

public class ClientProfile extends Activity {

	private TextView cp_name;
	private TextView cp_id;
	private TextView cp_tlf_1;
	private TextView cp_tlf_2;
	private TextView cp_mail;
	private TextView cp_address;
	private TableLayout cp_services;
	
	private Client client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_profile);
		overridePendingTransition(anim.fade_in, anim.fade_out);
		
		cp_name = (TextView) findViewById(R.id.client_profile_name);
		cp_id = (TextView) findViewById(R.id.client_profile_id);
		cp_tlf_1 = (TextView) findViewById(R.id.client_profile_tlf_1);
		cp_tlf_2 = (TextView) findViewById(R.id.client_profile_tlf_2);
		cp_mail = (TextView) findViewById(R.id.client_profile_mail);
		cp_address = (TextView) findViewById(R.id.client_profile_address);
		cp_services = (TableLayout) findViewById(R.id.client_profile_services);
		
		client = (Client) getIntent().getSerializableExtra("client");
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/rhinos_font.ttf");	
		cp_name.setTypeface(tf);
		
		cp_name.setText(client.getName());
		cp_id.setText(client.getId().toString());
		cp_tlf_1.setText(client.getTlf_1());
		cp_tlf_2.setText(client.getTlf_2());
		cp_mail.setText(client.getMail());
		cp_address.setText(client.getAddress());
		
		ArrayList<Service> services = App.src.getServices(client.getId().toString());
		for (Service s : services) {
			ServiceRowItem item = new ServiceRowItem(ClientProfile.this, s);
			
			LayoutParams lp = new LayoutParams(	LayoutParams.FILL_PARENT,
												LayoutParams.WRAP_CONTENT, 1f);
			cp_services.addView(item, lp);
		}
	}
}

class ServiceRowItem extends TableRow {
		
	private TableLayout table;
	private TableRow tr_campaign;
	private TableRow tr_service;
	private TableRow tr_commission;
	private TableRow tr_address;
	private TableRow tr_notes;
	private TableRow tr_date;
	
	public ServiceRowItem(Context context, Service service) {
		super(context);
		
		table = new TableLayout(context);
		table.setBackgroundResource(android.R.drawable.toast_frame);
		
		tr_campaign = new TableRow(context);
		tr_service = new TableRow(context);
		tr_commission = new TableRow(context);
		tr_address = new TableRow(context);
		tr_notes = new TableRow(context);
		tr_date = new TableRow(context);
		
		LayoutParams lp_1 = new LayoutParams(	LayoutParams.FILL_PARENT, 
												LayoutParams.WRAP_CONTENT, .3f);
		lp_1.gravity = Gravity.LEFT;
		
		LayoutParams lp_2 = new LayoutParams(	LayoutParams.FILL_PARENT,
												LayoutParams.WRAP_CONTENT, .7f);
		lp_2.gravity = Gravity.RIGHT;
		
		table.addView(tr_campaign);
		table.addView(tr_service);
		table.addView(tr_commission);
		table.addView(tr_address);
		table.addView(tr_notes);
		table.addView(tr_date);
		
		TextView tv_campaign = new TextView(context);
		tv_campaign.setText("Campaña");
		tv_campaign.setTypeface(null, Typeface.BOLD);
		TextView tv_campaign_data = new TextView(context);
		tv_campaign_data.setText(service.getCampaign());
		
		tr_campaign.addView(tv_campaign, lp_1);
		tr_campaign.addView(tv_campaign_data, lp_2);
		
		TextView tv_service = new TextView(context);
		tv_service.setText("Servicio");
		tv_service.setTypeface(null, Typeface.BOLD);
		TextView tv_service_data = new TextView(context);
		tv_service_data.setText(service.getService());
		
		tr_service.addView(tv_service, lp_1);
		tr_service.addView(tv_service_data, lp_2);
		
		TextView tv_commission = new TextView(context);
		tv_commission.setText("Comisión");
		tv_commission.setTypeface(null, Typeface.BOLD);
		TextView tv_commission_data = new TextView(context);
		tv_commission_data.setText(""+service.getCommission());
		
		tr_commission.addView(tv_commission, lp_1);
		tr_commission.addView(tv_commission_data, lp_2);
		
		TextView tv_address = new TextView(context);
		tv_address.setText("Dirección");
		tv_address.setTypeface(null, Typeface.BOLD);
		TextView tv_address_data = new TextView(context);
		tv_address_data.setText(service.getAddress());
		
		tr_address.addView(tv_address, lp_1);
		tr_address.addView(tv_address_data, lp_2);
		
		TextView tv_notes = new TextView(context);
		tv_notes.setText("Notas");
		tv_notes.setTypeface(null, Typeface.BOLD);
		TextView tv_notes_data = new TextView(context);
		tv_notes_data.setText(service.getNotes());
		
		tr_notes.addView(tv_notes, lp_1);
		tr_notes.addView(tv_notes_data, lp_2);
		
		TextView tv_date = new TextView(context);
		tv_date.setText("Fecha");
		tv_date.setTypeface(null, Typeface.BOLD);
		TextView tv_date_data = new TextView(context);
		tv_date_data.setText(service.getDate().toLocaleString());
		
		tr_date.addView(tv_date, lp_1);
		tr_date.addView(tv_date_data, lp_2);
		
		addView(table);
	}
}
