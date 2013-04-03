package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePassword extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JPasswordField textField;
	private JPasswordField textField_1;
	private JPasswordField textField_2;
	
	private ChangePassword _this = this;
	
	public ChangePassword(JFrame f) {
		setModal(true);
		setTitle("Modificar Contrase\u00F1a");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangePassword.class.getResource("/icons/Key/Key_16x16.png")));
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel_3.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(0, 1, 0, 4));
		
		JLabel lblNewLabel = new JLabel("Contrase\u00F1a actual:   ");
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Repetir contrase\u00F1a:  ");
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 4));
		
		textField = new JPasswordField(15);
		textField.setFont(App.DEFAULT_FONT);
		panel_2.add(textField);
		
		textField_1 = new JPasswordField(15);
		textField.setFont(App.DEFAULT_FONT);
		panel_2.add(textField_1);
		
		textField_2 = new JPasswordField(15);
		textField.setFont(App.DEFAULT_FONT);
		panel_2.add(textField_2);
		
		JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(panel_4, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panel_5, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Modificar");
		btnNewButton.setFont(App.DEFAULT_FONT);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (MySqlConnector.getInstance().login(App.user.getUser(), new String(textField.getPassword())) &&
					new String(textField_1.getPassword()).trim().equals(new String(textField_2.getPassword()).trim())) {
					
					MySqlConnector.getInstance().changePassword(App.user.getUser(), new String(textField_1.getPassword()).trim());
					
					_this.setVisible(false);
				}
			}
		});
		btnNewButton.setFocusable(false);
		panel_5.add(btnNewButton);
		
		JPanel panel_6 = new JPanel();
		getContentPane().add(panel_6, BorderLayout.WEST);
		
		JPanel panel_7 = new JPanel();
		getContentPane().add(panel_7, BorderLayout.EAST);
		
		pack();
		setLocationRelativeTo(f);
	}

}
