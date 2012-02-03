package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Logger extends JDialog {
	
	private JLabel user;
	private JLabel pass;
	private JTextField userField;
	private JPasswordField passField;
	
	private JButton acept;
	
	private JTextField status;
	
	//--------------------------------
	private JPanel button;
	
	//--------------------------------
	
	public Logger(final RhFrame parent) {
		init();
		setResizable(false);
		setLocationRelativeTo(parent);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				parent.exit();
			}
		});
	}
	
	private void init() {
		setModal(true);
		
		user = new JLabel("Usuario: ");
		pass = new JLabel("Contraseña: ");
		userField = new JTextField();
		passField = new JPasswordField();
		
		acept = new JButton("Aceptar");
		acept.setFocusable(false);
		acept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		button = new JPanel();
		button.add(acept);
		
		setLayout(new BorderLayout());
		
		add(button, BorderLayout.SOUTH);
		
		pack();
	}
}
