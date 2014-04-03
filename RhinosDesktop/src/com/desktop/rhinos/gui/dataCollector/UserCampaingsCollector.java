package com.desktop.rhinos.gui.dataCollector;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;

@SuppressWarnings("serial")
public class UserCampaingsCollector extends JPanel {

	/**
	 * Create the panel.
	 */
	public UserCampaingsCollector() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GridLayout(0, 1));
	}
	
	public void setData(User u) {
		removeAll();
		
		ArrayList<Campaign> allCamps = MySqlConnector.getInstance().getCampaigns(App.user);
		ArrayList<String> authCamps = MySqlConnector.getInstance().getAuthorizedCampaigns(u);
		
		for (Campaign i : allCamps) {
			add(new JCheckBox(i.getName(), authCamps.contains(i.getName())));
		}
		
		validate();
	}
}
