package com.android.rhinos;

import android.R.anim;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UsersGestMenu extends ListActivity {

	private String list[] = {"Nuevo usuario", "Lista de usuarios"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(anim.fade_in, anim.fade_out);

		setListAdapter(new ArrayAdapter<String>(UsersGestMenu.this, android.R.layout.simple_list_item_1, list));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String execute="";
		switch (position) {
		
			//My profile
			case 0: 
				execute = "CreateAccount"; break;
			case 1:
				execute = "UsersList"; break;
		}
		
		try {
			Intent intent = new Intent(UsersGestMenu.this, Class.forName("com.android.rhinos."+execute));
			startActivity(intent);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
