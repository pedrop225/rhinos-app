package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector.App;

@SuppressWarnings("serial")
public class UserHierarchyDialog extends JDialog {

	public static final int WIDTH = 460;
	public static final int HEIGHT = 300;
	public static String TITLE = "User Hierarchy";

	private UserHierarchy uh;
	private JButton accept;
	
	private int exit_mode;
	
	public UserHierarchyDialog() {
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(new ImageIcon(UserHierarchy.class.getResource("/icons/User/User_16x16.png")).getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		uh = new UserHierarchy();
		accept = new JButton("Aceptar");
		accept.setFont(App.DEFAULT_FONT);
		
		exit_mode = JOptionPane.CLOSED_OPTION;
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				exit_mode = JOptionPane.OK_OPTION;
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				exit_mode = JOptionPane.CLOSED_OPTION;
			}
		});
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(uh);
		c.add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), accept), BorderLayout.SOUTH);
		setLocationRelativeTo(null);
	}
	
	public void setUser(User user) {
		uh.setUser(user);
	}
	
	public User getSelectedUser() {
		return (exit_mode == JOptionPane.OK_OPTION) ? uh.getSelectedNode() : null;
	}
	
	public int getExitMode() {
		return exit_mode;
	}
}
