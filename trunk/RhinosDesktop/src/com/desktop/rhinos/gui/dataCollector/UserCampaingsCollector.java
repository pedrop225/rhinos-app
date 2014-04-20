package com.desktop.rhinos.gui.dataCollector;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.dataCollector.interfaces.UserDisplay;

@SuppressWarnings("serial")
public class UserCampaingsCollector extends JPanel implements UserDisplay {
	
	private ArrayList<JCheckBox> checkCamps;
	private User user;
	
	/**
	 * Create the panel.
	 */
	public UserCampaingsCollector() {
		setLayout(new GridLayout(0, 1, 0, 0));
		
		checkCamps = new ArrayList<JCheckBox>();
				
		ArrayList<Campaign> allCamps = MySqlConnector.getInstance().getCampaigns(App.user);
		for (Campaign i : allCamps) {
			JCheckBox cb = new JCheckBox(i.getName());
			
			cb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Campaign c = new Campaign(((JCheckBox)e.getSource()).getText());
					
					if (((JCheckBox)e.getSource()).isSelected())
						MySqlConnector.getInstance().grantCampaignPermission(user, c);
					
					else
						MySqlConnector.getInstance().removeCampaingPermission(user, c);
				}
			});
			
			add(cb);
			checkCamps.add(cb);
		}
	}
	
	@Override
	public void setData(User u) {
		user = u;
		ArrayList<String> authCamps = MySqlConnector.getInstance().getAuthorizedCampaigns(user);
		for (JCheckBox i : checkCamps)
			i.setSelected(authCamps.contains(i.getText()));
	}
	
	@Override
	public void setFieldsEditable(boolean e) {
		for (JCheckBox i : checkCamps)
			i.setEnabled(e);
	}
}
