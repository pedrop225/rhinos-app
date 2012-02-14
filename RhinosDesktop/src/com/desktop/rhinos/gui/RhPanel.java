package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class RhPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final String [][] default_data = {{"", "", "", "", ""}};
	
	private JPanel north;
	private JPanel west;
	private ClientsTablePanel cliPanel;
	private JPanel south;
	
	
	public RhPanel(Object [][] data) {
		init(data);
	}
	
	private void init(Object [][] data) {
		north = new JPanel();
		west = new JPanel();
		cliPanel = new ClientsTablePanel(data);
		south = new JPanel();
		
		setLayout(new BorderLayout());
		
		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(cliPanel);
		add(south, BorderLayout.SOUTH);
	}
}
