package com.android.rhinos;

import android.R.anim;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	private String list[] = {"A�adir Contrato", "Gesti�n de Contratos", "Gesti�n del Equipo", "Informes", "Backups", "Gesti�n de Campa�as", "Ajustes"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(anim.fade_in, anim.fade_out);

		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, list));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String execute="";
		switch (position) {
		
			//My profile
			case 0: 
				execute = "AddContract"; break;
			case 1:
				execute = "ContractGest"; break;
			case 2:
				execute = "TeamGest"; break;
			case 3:
				execute = "Informes"; break;
			case 4:
				execute = "Backups"; break;
			case 5:
				execute = "CampGest"; break;
			case 6:
				execute = "Settings"; break;
		}
		
		try {
			Intent intent = new Intent(Menu.this, Class.forName("com.android.rhinos."+execute));
			startActivity(intent);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
