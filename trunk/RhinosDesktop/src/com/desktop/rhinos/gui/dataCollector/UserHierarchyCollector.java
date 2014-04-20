package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.gui.UserHierarchy;
import com.desktop.rhinos.gui.dataCollector.interfaces.UserDisplay;

@SuppressWarnings("serial")
public class UserHierarchyCollector extends JPanel implements UserDisplay {

	private UserHierarchy hierarchy;
	private User user;
	
	public UserHierarchyCollector() {
		super(new BorderLayout());
		init();
	}
	
	private void init() {
		hierarchy = new UserHierarchy();
		add(hierarchy);
	}

	@Override
	public void setData(User u) {
		/*
		hierarchy.setUser(u);
		add(hierarchy);
		*/
		user = u;
	}

	@Override
	public void setFieldsEditable(boolean e) {
		if (e) {
			hierarchy.setUser(user);
			add(hierarchy);
		}
		hierarchy.setVisible(e);
	}
}
