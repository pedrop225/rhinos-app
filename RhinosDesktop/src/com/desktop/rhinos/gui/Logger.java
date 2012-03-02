package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.desktop.rhinos.connector.MySqlConnector.App;


public class Logger extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel user;
	private JLabel pass;
	private JTextField userField;
	private JPasswordField passField;
	
	private JButton accept;
	
	private JLabel status;
	
	//--------------------------------
	private JPanel button;
	private JPanel labels;
	private JPanel text;
	
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
		setTitle("Login");
		
		user = new JLabel("Usuario: ");
		pass = new JLabel("Contraseña: ");
		userField = new JTextField(10);
		passField = new JPasswordField();
		
		user.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		pass.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		userField.setFont(App.DEFAULT_FONT);
		passField.setFont(App.DEFAULT_FONT);
		
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				if (e.getKeyChar() == '\n')
					accept.doClick();
			}
		});
		
		accept = new JButton("Aceptar");
		accept.setFont(App.DEFAULT_FONT);
		accept.setFocusable(false);
		
		status = new JLabel("", JLabel.CENTER);
		
		setLayout(new BorderLayout());
		
		labels = new JPanel(new GridLayout(2, 1, 4, 4));
		labels.add(user);
		labels.add(pass);
		
		text = new JPanel(new GridLayout(2, 1, 4, 4));
		text.add(userField);
		text.add(passField);
		
		JPanel aux = new JPanel();
		aux.add(labels);
		aux.add(text);
		
		JPanel aux_accept = new JPanel();
		aux_accept.add(accept);
		
		button = new JPanel(new BorderLayout());
		button.add(status, BorderLayout.NORTH);
		button.add(aux_accept, BorderLayout.SOUTH);
		
		add(aux);
		add(button, BorderLayout.SOUTH);
		
		pack();
	}
	
	public void clear() {
		userField.setText("");
		passField.setText("");
	}
	
	public JButton getAcceptButton() {
		return accept;
	}
	
	public String getUserString() {
		return userField.getText().trim();
	}
	
	public String getPasswordString() {
		return new String(passField.getPassword());
	}
}
