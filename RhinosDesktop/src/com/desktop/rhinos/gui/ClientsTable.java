package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ClientsTable extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final Font TABLE_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
	
	private JTable table;
	private DefaultTableModel tm;
	
	public ClientsTable() {
		init();
	}
	
	public void init() {
		tm = new RhTableModel();
		table = new JTable(tm);
		table.setFont(TABLE_FONT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

class RhTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public RhTableModel() {
		addColumn("Nif");
		addColumn("Nombre");
		addColumn("Teléfono");
		addColumn("Mail");
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}