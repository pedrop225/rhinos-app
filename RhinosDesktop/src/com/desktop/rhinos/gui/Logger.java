package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class Logger extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel user;
	private JLabel pass;
	private JTextField userField;
	private JPasswordField passField;
	
	private JButton acept;
	
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
	
	public void clean() {
		userField.setText("");
		passField.setText("");
	}
	
	private void init() {
		setModal(true);
		
		user = new JLabel("Usuario: ");
		pass = new JLabel("Contraseña: ");
		userField = new JTextField(10);
		passField = new JPasswordField();
		
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				if (e.getKeyChar() == '\n')
					acept.doClick();
			}
		});
		
		acept = new JButton("Aceptar");
		acept.setFocusable(false);
		acept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
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
		
		JPanel aux_acept = new JPanel();
		aux_acept.add(acept);
		
		button = new JPanel(new BorderLayout());
		button.add(status, BorderLayout.NORTH);
		button.add(aux_acept, BorderLayout.SOUTH);
		
		add(aux);
		add(button, BorderLayout.SOUTH);
		
		pack();
	}
}
