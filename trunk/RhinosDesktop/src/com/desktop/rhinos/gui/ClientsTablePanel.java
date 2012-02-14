package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientsTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String [] ids = {"Nif", "Nombre", "Teléfono", "Teléfono Aux", "Mail", "Comisión"};
	
	private JTable table;
	private DefaultTableModel data;
	
	public ClientsTablePanel(Object [][] d) {
		init(d);
	}
	
	public void init(Object [][] d) {
		data = new DefaultTableModel(d, ids);
		table = new JTable(data);
		
		setLayout(new BorderLayout());
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table));
	}
}