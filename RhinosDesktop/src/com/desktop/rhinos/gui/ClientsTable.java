package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class ClientsTable extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private DefaultTableModel tm;
	
	public ClientsTable() {
		init();
	}
	
	public void init() {
		tm = new RhTableModel();
		table = new JTable(tm);
		table.setFont(App.DEFAULT_FONT);
		table.getTableHeader().setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		table.getColumn(tm.getColumnName(0)).setCellRenderer(new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return c;
			}
		});
		
		setLayout(new BorderLayout());
		
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table));
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() > 1) {
					lookUpSelected();
				}
			}
		});
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE: removeSelected(); break;
					default:
				}
			}
		});
	}
	
	public void addTableData(Object [] d) {
		tm.addRow(d);
	}
	
	public void cleanTableData() {
		tm.setRowCount(0);
	}
	
	public void removeSelected() {
		if (table.getSelectedRowCount() > 0)
			tm.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
	}
	
	public void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			
			new AddContract(null, (String)tm.getValueAt(r, c)).setVisible(true);
		}
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