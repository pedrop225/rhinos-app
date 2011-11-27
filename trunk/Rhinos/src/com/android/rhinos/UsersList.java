package com.android.rhinos;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.rhinos.gest.User;

public class UsersList extends Activity {
	
	private TableLayout base;
	private ScrollView scroll;
	
	private UsersList profile;
	public Bundle _bundle;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		profile = this;
		_bundle = savedInstanceState;
		
		base = new TableLayout(UsersList.this);
		base.setPadding(0, 2, 0, 2);
				
		scroll = new ScrollView(UsersList.this);
		scroll.addView(base);
		setContentView(scroll);
		
		ArrayList<User> users = (App.user.isRoot() ? App.src.getUsers() : new ArrayList<User>());
		
		for (User u : users) {
			UserItemView item = new UserItemView(UsersList.this, u, profile);
			base.addView(item);
						
			View v = new View(getBaseContext());
			v.setBackgroundColor(Color.DKGRAY);
			v.setMinimumHeight(2);
			base.addView(v);
		}
	}
}

class UserItemView extends LinearLayout implements View.OnClickListener {
	
	private static final int MAIL_FONT_SIZE= 12;
	private static final int NAME_FONT_SIZE = 14;
	
	private LinearLayout base;
	private TextView name;
	private TextView mail;
	
	private LinearLayout info;
	private TextView nick;
	
	private User user;
		
	public UserItemView(Context context, User u, final UsersList profile) {
		super(context);
		this.user = u;
		setClickable(true);
		
		name = new TextView(context);
		mail = new TextView(context);
		nick = new TextView(context);
				
		name.setText(u.getName());
		name.setTypeface(Typeface.DEFAULT_BOLD);
		name.setTextSize(NAME_FONT_SIZE);
		
		mail.setText(u.getMail());
		mail.setTextSize(MAIL_FONT_SIZE);
		
		nick.setText(u.getUser());
		nick.setTextColor(Color.GREEN);
		nick.setTextSize(MAIL_FONT_SIZE);
		
		base = new LinearLayout(context);
		base.setOrientation(LinearLayout.VERTICAL);
		base.addView(name);
		base.addView(mail);
		
		LayoutParams lp = new LayoutParams( LayoutParams.FILL_PARENT,
											LayoutParams.FILL_PARENT, .75f);
		
		info = new LinearLayout(context);
		info.setOrientation(LinearLayout.VERTICAL);
		info.addView(new TextView(context));
		info.addView(nick);
		
		addView(base, lp);
		addView(info);


		setOnClickListener(this);


		setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("Eliminando Usuario");
				builder.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
				builder.setMessage("¿Desea eliminar el usuario '"+user.getUser()+"'?");
				builder.setCancelable(false);
				
				builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
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
	/*	
		Intent intent = new Intent().setClass(getContext(), ClientProfile.class);
		intent.putExtra("client", client);
		getContext().startActivity(intent);
*/
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