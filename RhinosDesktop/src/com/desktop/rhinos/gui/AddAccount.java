package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.dataCollector.UserDataCollector;

public class AddAccount extends JDialog {

	private static final long serialVersionUID = 1L;

	private UserDataCollector dc;
	private JButton add;
	private User u;
	
	public AddAccount(JFrame loc) {
		init();
		setLocationRelativeTo(loc);
	}
	
	private void init() {
		setTitle("Añadir Usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddAccount.class.getResource("/icons/rhinos.png")));
		setResizable(false);
		setModal(true);
		
		u = null;
		dc = new UserDataCollector();
		dc.getResetPasswordButton().setVisible(false);
		
		add = dc.getOkButton();
		add.setText("Añadir Usuario");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				u = dc.getUser();
				MySqlConnector.getInstance().createAccount(u, "_0x"+u.getUser().toLowerCase()+"_", dc.isSendMailSelected());
				dispose();
			}
		});
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(dc);
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(new JPanel(), BorderLayout.EAST);
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		getContentPane().add(new JPanel(), BorderLayout.SOUTH);
				
		pack();
	}
}
