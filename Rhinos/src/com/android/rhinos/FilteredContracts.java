package com.android.rhinos;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;

public class FilteredContracts extends Activity {
	
	private TableLayout base;
	private ScrollView scroll;
	
	private FilteredContracts profile;
	
	public Bundle _bundle;
	
	public static Date dateIn;
	public static Date dateOut;
	
	private SubMenu filter;
	private SubMenu info;
	private SubMenu updt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		profile = this;
		_bundle = savedInstanceState;
		
		base = new TableLayout(FilteredContracts.this);
		base.setPadding(0, 2, 0, 2);
				
		scroll = new ScrollView(FilteredContracts.this);
		scroll.addView(base);
		setContentView(scroll);
		
		Campaign campaign = (Campaign) getIntent().getSerializableExtra("campaign");
		
		ArrayList<Client> clients = (campaign != null) ? App.src.getCampaignClients(campaign, App.user) : App.src.getClients(App.user);
		clients = ApplyDateFilter(clients);
		
		for (Client u : clients) {
			ContractItemView item = new ContractItemView(FilteredContracts.this, u, profile);
			base.addView(item);
						
			View v = new View(getBaseContext());
			v.setBackgroundColor(Color.DKGRAY);
			v.setMinimumHeight(2);
			base.addView(v);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.filteredcontracts_option_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.filter_menu_item: Intent intent = new Intent().setClass(this, FilterEditor.class);
										startActivity(intent);
										break;
			
			case R.id.apply_menu_item: 	onCreate(_bundle);
										break;
			
			case R.id.form_menu_item: 	break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private ArrayList<Client> ApplyDateFilter(ArrayList<Client> clients) {
		ArrayList<Client> r = new ArrayList<Client>(clients);
		
		if ((dateIn != null) && (dateOut != null)) {
			for (int i = 0; i < clients.size(); i++) {
				
				for (int j = 0; j < clients.get(i).getServices().size(); j++) {
					Service s = clients.get(i).getServices().get(j);
					
					if ((s.getDate().after(FilteredContracts.dateIn)) || (s.getDate().before(FilteredContracts.dateOut))) {
						r.get(i).getServices().remove(j);
					}
				}
				
				if (clients.get(i).getServices().isEmpty()) {
					r.remove(clients.get(i));
				}
			}
		}
		return r;
	}
}

class ContractItemView extends LinearLayout implements View.OnClickListener {
	
	private static final int COMMISION_FONT_SIZE = 16;
	
	private static final int ID_FONT_SIZE= 12;
	private static final int NAME_FONT_SIZE = 14;
	
	private LinearLayout base;
	private TextView comm;
	private TextView id;
	
	private LinearLayout info;
	private TextView name;
	
	private Client client;
		
	public ContractItemView(Context context, Client c, final FilteredContracts profile) {
		super(context);
		this.client = c;
		setClickable(true);
		
		name = new TextView(context);
		id = new TextView(context);
		comm = new TextView(context);
				
		name.setText(c.getName());
		name.setTypeface(Typeface.DEFAULT_BOLD);
		name.setTextSize(NAME_FONT_SIZE);
		
		id.setText(c.getId().toString());
		id.setTextSize(ID_FONT_SIZE);
		
		comm.setText(""+App.src.getSumCommissions(c)+"€");
		comm.setTextColor(Color.GREEN);
		comm.setTextSize(COMMISION_FONT_SIZE);
		
		base = new LinearLayout(context);
		base.setOrientation(LinearLayout.VERTICAL);
		base.addView(name);
		base.addView(id);
		
		LayoutParams lp = new LayoutParams( LayoutParams.FILL_PARENT,
											LayoutParams.FILL_PARENT, .75f);
		info = new LinearLayout(context);
		info.setOrientation(LinearLayout.VERTICAL);
		info.addView(new TextView(context));
		info.addView(comm);
			
		addView(base, lp);
		addView(info);
		
		setOnClickListener(this);
		
		setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("Eliminando Cliente");
				builder.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
				builder.setMessage("¿Desea eliminar el cliente '"+client.getName()+"'?");
				builder.setCancelable(false);
				
				builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						App.src.deleteClient(client.getId().toString());
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
	}
	
	@Override
	public void onClick(View v) {
		setBackgroundColor(Color.BLACK);
		
		Intent intent = new Intent().setClass(getContext(), ClientProfile.class);
		intent.putExtra("client", client);
		getContext().startActivity(intent);
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			setBackgroundColor(Color.DKGRAY);
		
		else if ((event.getAction() == MotionEvent.ACTION_UP) || 
				(event.getAction() == MotionEvent.ACTION_CANCEL))
			
			setBackgroundColor(Color.BLACK);
		return true;
	}
}