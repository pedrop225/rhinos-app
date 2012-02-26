package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientsTable extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private DefaultTableModel tm;
	
	public ClientsTable() {
		init();
	}
	
	public void init() {
		tm = new DefaultTableModel();
		table = new JTable(tm);
		
		tm.addColumn("Nif");
		tm.addColumn("Nombre");
		tm.addColumn("Teléfono");
		tm.addColumn("Teléfono Aux");
		tm.addColumn("Mail");
		tm.addColumn("Comisión");

		setLayout(new BorderLayout());
		
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table));
	}
	
	public void addTableData(Object [] d) {
		tm.addRow(d);
	}
	
	public void cleanTableData() {
		for (int i = 0; i < tm.getRowCount(); i++)
			tm.removeRow(i);
	}
}