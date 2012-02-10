package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class RhPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel north;
	private JPanel west;
	private ClientsTablePanel cliPanel;
	private JPanel south;
	
	public RhPanel() {
		init();
	}
	
	private void init() {
		north = new JPanel();
		west = new JPanel();
		cliPanel = new ClientsTablePanel();
		south = new JPanel();
		
		setLayout(new BorderLayout());
		
		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(cliPanel);
		add(south, BorderLayout.SOUTH);
	}
	
	public void addTableData(Object [] d) {
		cliPanel.addTableData(d);
	}
}
