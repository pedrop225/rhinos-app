package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ClientsTable extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String [] ids = {"Nif", "Nombre", "Teléfono", "Teléfono Aux", "Mail", "Comisión"};
	public static final String [][] EMPTY_TABLE = {};
	
	private JTable table;
	
	public ClientsTable(Object [][] d) {
		init(d);
	}
	
	public void init(Object [][] d) {
		table = new JTable(d, ids);
		
		setLayout(new BorderLayout());
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table));
	}
}