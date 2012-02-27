package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class RhPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel north;
	private JPanel west;
	private ClientsTable clients;
	private JPanel south;
	
	public RhPanel() {
		init();
	}
	
	private void init() {
		north = new JPanel(new BorderLayout());
		west = new JPanel();
		clients = new ClientsTable();
		south = new JPanel();
		
		setLayout(new BorderLayout());
		
		north.add(getUserBanner());
		
		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(clients);
		add(south, BorderLayout.SOUTH);
	}
	
	private JPanel getUserBanner() {
		JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel((App.user.getUser() != null) ? App.user.getUser()+"  " : "", JLabel.RIGHT);
		
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 16);
		l.setFont(f);
		b.setBackground(Color.LIGHT_GRAY);
		b.add(l);
		
		return b;
	}
	
	public void showUserBanner() {
		north.add(getUserBanner());
	}
	
	public void setClientsData(Object [][] d) {
		for (Object [] md : d) {
			clients.addTableData(md);
		}
	}
	
	public void cleanClientsData() {
		clients.cleanTableData();
	}
}
