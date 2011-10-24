package com.android.rhinos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.rhinos.gest.Client;

class ContractItemView extends LinearLayout implements View.OnClickListener {
	
	private static final int COMMISION_FONT_SIZE = 16;
	
	private static final int ID_FONT_SIZE= 12;
	private static final int NAME_FONT_SIZE = 14;
	
	private LinearLayout base;
	private TextView comm;
	private TextView id;
	
	private LinearLayout info;
	private TextView name;
	private Client user;
		
	public ContractItemView(Context context, Client u) {
		super(context);
		this.user = u;
		setClickable(true);
		
		name = new TextView(context);
		id = new TextView(context);
		comm = new TextView(context);
				
		name.setText(u.getName());
		name.setTypeface(Typeface.DEFAULT_BOLD);
		name.setTextSize(NAME_FONT_SIZE);
		
		id.setText("id");
		id.setTextSize(ID_FONT_SIZE);
		
		comm.setText("85€");
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
	}

	public Client getUser() {
		return user;
	}
	
	@Override
	public void onClick(View v) {
		setBackgroundColor(Color.BLACK);
		
		Intent intent = new Intent().setClass(getContext(), UserProfile.class);
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

public class FilteredContracts extends Activity {
	
	private TableLayout base;
	private ScrollView scroll;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		base = new TableLayout(FilteredContracts.this);
		base.setPadding(0, 2, 0, 2);
				
		scroll = new ScrollView(FilteredContracts.this);
		scroll.addView(base);
		setContentView(scroll);
		
		/*
		for (Client u : Shared.clients) {
			ContractItemView item = new ContractItemView(FilteredContracts.this, u);
			base.addView(item);
						
			View v = new View(getBaseContext());
			v.setBackgroundColor(Color.DKGRAY);
			v.setMinimumHeight(2);
			base.addView(v);
		}*/
	}
}