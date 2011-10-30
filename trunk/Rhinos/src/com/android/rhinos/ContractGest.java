package com.android.rhinos;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.android.rhinos.gest.Campaign;

public class ContractGest extends TabActivity {

	private TabHost host;
	private Intent intent;
	private Resources res;
	private TabSpec spec;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contracts_tab);
		
		host = getTabHost();
		res = getResources();
		
		intent = new Intent().setClass(this, FilteredContracts.class);
		spec = host.newTabSpec("All").setIndicator("	Vista General	", res.getDrawable(R.drawable.all_campaign)).setContent(intent);
		host.addTab(spec);
		
		for (Campaign a : App.src.getCampaigns()) {
			intent = new Intent().setClass(this, FilteredContracts.class);
			intent.putExtra("camp", a.getName());
			spec = host.newTabSpec(a.getName()).setIndicator("  "+a.getName()+"  ").setContent(intent);
			host.addTab(spec);
		}
	}
}