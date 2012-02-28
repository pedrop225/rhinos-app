package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class RhPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel north;
	private JPanel west;
	private ClientsTable clients;
	private JPanel south;
	
	private JButton lookUp;
	private JButton delete;

	public RhPanel() {
		init();
	}
	
	private void init() {
		north = new JPanel(new BorderLayout());
		west = new JPanel();
		clients = new ClientsTable();
		south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		lookUp = new JButton("Ver");
		delete = new JButton("Eliminar");
		
		lookUp.setFont(App.DEFAULT_FONT);
		delete.setFont(App.DEFAULT_FONT);
		
		lookUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clients.lookUpSelected();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clients.removeSelected();
			}
		});
		
		setLayout(new BorderLayout());
		
		north.add(getUserBanner());
		south.add(lookUp);
		south.add(delete);
		
		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(clients);
		add(south, BorderLayout.SOUTH);
	}
	
	private JPanel getUserBanner() {
		JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel((App.user.getName() != null) ? App.user.getName()+"  " : "", JLabel.RIGHT);
		
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 16);
		l.setFont(f);
		b.setBackground(Color.LIGHT_GRAY);
		b.add(l);
		
		return b;
	}
	
	public void showUserBanner() {
		north.add(getUserBanner());
	}
	
	public void updateClientsTableData() {
		clients.updateTableData();
	}
	
	public void addContract() {
		AddContract ac = new AddContract(null);
		ac.setVisible(true);
	}
}
