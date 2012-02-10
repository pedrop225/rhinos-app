package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ClientsTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private DefaultTableModel data;
	
	public ClientsTablePanel() {
		init();
	}
	
	public void init() {
		data = new DefaultTableModel();
		data.addColumn("Nif");
		data.addColumn("Nombre");
		data.addColumn("Teléfono");
		data.addColumn("Teléfono Aux");
		data.addColumn("Mail");
		data.addColumn("Comisión");
		
		data.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println("hi sun");
				table.setValueAt("ola", 1, 1);
			}
		});
		
		table = new JTable(data);

		setLayout(new BorderLayout());
		
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table));
	}
	
	public void addTableData(Object[] d) {
		data.addRow(d);
	}
}