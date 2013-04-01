package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.dataCollector.UserDataCollector;

public class AddAccount extends JDialog {

	private static final long serialVersionUID = 1L;

	private UserDataCollector dc;
	private JButton add;
	
	public AddAccount(JFrame loc) {
		init();
		setLocationRelativeTo(loc);
	}
	
	private void init() {
		setTitle("Añadir Usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddAccount.class.getResource("/icons/rhinos.png")));
		setResizable(false);
		setModal(true);
		
		dc = new UserDataCollector();
		dc.getSearchButton().setVisible(false);
		
		add = new JButton("Añadir Usuario");
		add.setFont(App.DEFAULT_FONT);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
	//			MySqlConnector.getInstance().createAccount(u, password);
				dispose();
			}
		});
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(dc);
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(new JPanel(), BorderLayout.EAST);
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		getContentPane().add(new JPanel(), BorderLayout.SOUTH);
		
		dc.getSearchButton().getParent().add(add);
		
		pack();
	}
}
