package com.android.rhinos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
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
	
	private ClientProfile _this = this;
	public Bundle _bundle;
	
	private Client client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_bundle = savedInstanceState;
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
			ServiceRowItem item = new ServiceRowItem(ClientProfile.this, s, _this);
			
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
	private TableRow tr_tlf_1;
	private TableRow tr_tlf_2;
	private TableRow tr_date;
	
	public ServiceRowItem(Context context, final Service service, final ClientProfile profile) {
		super(context);

		table = new TableLayout(context);
		table.setBackgroundResource(android.R.drawable.toast_frame);
		
		tr_campaign = new TableRow(context);
		tr_service = new TableRow(context);
		tr_commission = new TableRow(context);
		tr_tlf_1 = new TableRow(context);
		tr_tlf_2 = new TableRow(context);
		tr_date = new TableRow(context);
		
		TextView tv_campaign = new TextView(context);
		tv_campaign.setText("Campaña\t\t\t");
		tv_campaign.setTypeface(null, Typeface.BOLD);
		TextView tv_campaign_data = new TextView(context);
		tv_campaign_data.setText(service.getCampaign());
		
		tr_campaign.addView(tv_campaign);
		tr_campaign.addView(tv_campaign_data);
		
		TextView tv_service = new TextView(context);
		tv_service.setText("Servicio");
		tv_service.setTypeface(null, Typeface.BOLD);
		TextView tv_service_data = new TextView(context);
		tv_service_data.setText(service.getService());
		
		tr_service.addView(tv_service);
		tr_service.addView(tv_service_data);
		
		TextView tv_commission = new TextView(context);
		tv_commission.setText("Comisión");
		tv_commission.setTypeface(null, Typeface.BOLD);
		TextView tv_commission_data = new TextView(context);
		tv_commission_data.setText(""+service.getCommission());
		
		tr_commission.addView(tv_commission);
		tr_commission.addView(tv_commission_data);
		
		TextView tv_tlf_1 = new TextView(context);
		tv_tlf_1.setText("Teléfono #1");
		tv_tlf_1.setTypeface(null, Typeface.BOLD);
		TextView tv_tlf_1_data = new TextView(context);
		tv_tlf_1_data.setText(""+service.getTlf_1());
		
		tr_tlf_1.addView(tv_tlf_1);
		tr_tlf_1.addView(tv_tlf_1_data);
		
		TextView tv_tlf_2 = new TextView(context);
		tv_tlf_2.setText("Teléfono #2");
		tv_tlf_2.setTypeface(null, Typeface.BOLD);
		TextView tv_tlf_2_data = new TextView(context);
		tv_tlf_2_data.setText(""+service.getTlf_2());
		
		tr_tlf_2.addView(tv_tlf_2);
		tr_tlf_2.addView(tv_tlf_2_data);
		
		TextView tv_date = new TextView(context);
		tv_date.setText("Fecha");
		tv_date.setTypeface(null, Typeface.BOLD);
		TextView tv_date_data = new TextView(context);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
		tv_date_data.setText(df.format(service.getDate()));
		
		tr_date.addView(tv_date);
		tr_date.addView(tv_date_data);
		
		table.addView(tr_campaign);		
		table.addView(tr_service);
		table.addView(tr_commission);
		table.addView(tr_tlf_1);
		table.addView(tr_tlf_2);
		table.addView(tr_date);
		
		addView(table);
		
		setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {				
				table.setBackgroundResource(android.R.drawable.editbox_dropdown_dark_frame);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("Eliminando Servicio");
				builder.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
				builder.setMessage("¿Desea eliminar el servicio '"+service.getService()+"'?");
				builder.setCancelable(false);
				
				builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						App.src.deleteService(service);
						profile.onCreate(profile._bundle);
					}
				});
				
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				
				AlertDialog alert = builder.create();
				alert.show();
								
				return true;
			}
			
		});
		
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if ((event.getAction() == MotionEvent.ACTION_UP) || 
					(event.getAction() == MotionEvent.ACTION_CANCEL))
					
					table.setBackgroundResource(android.R.drawable.toast_frame);
				return false;
			}
		});
	}
}
