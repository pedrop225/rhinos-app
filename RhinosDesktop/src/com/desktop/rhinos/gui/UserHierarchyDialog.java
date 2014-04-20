package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import com.android.rhinos.gest.User;

@SuppressWarnings("serial")
public class UserHierarchyDialog extends JDialog {

	public static final int WIDTH = 460;
	public static final int HEIGHT = 300;
	public static String TITLE = "User Hierarchy";

	private UserHierarchy uh;
	
	public UserHierarchyDialog() {
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(new ImageIcon(UserHierarchy.class.getResource("/icons/User/User_16x16.png")).getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		uh = new UserHierarchy();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(uh);
		setLocationRelativeTo(null);
	}
	
	public void setUser(User user) {
		uh.setUser(user);
	}
}
