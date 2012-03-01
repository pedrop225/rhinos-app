package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.android.rhinos.gest.Client;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;

public class ClientsTable extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private DefaultTableModel tm;
	
	public ClientsTable() {
		init();
	}
	
	private void init() {
		tm = new RhTableModel();
		table = new JTable(tm);
		table.setFont(App.DEFAULT_FONT);
		table.getTableHeader().setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		setLayout(new BorderLayout());
		
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
					case KeyEvent.VK_F5: updateTableData(); break;
					default:
				}
			}
		});
	}
	
	private Object [][] importMySqlClients() {
		MySqlConnector con = MySqlConnector.getInstance();
		ArrayList<Client> c = con.getClients(App.user);
		Object [][] d = new Object[c.size()][4];
		
		for (int i = 0; i < c.size(); i++) {
			d[i][0] = new String(c.get(i).getId().toString());
			d[i][1] = new String(c.get(i).getName());
			d[i][2] = new String(c.get(i).getTlf_1());
			d[i][3] = new String(c.get(i).getMail());
		}
		return d;
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		Object [][] c = importMySqlClients();
		
		for (Object [] d : c){
			tm.addRow(d);
		}
	}
	
	public void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			int _c = table.convertColumnIndexToModel(1);
			String id = (String)table.getValueAt(r, c);
			String name = (String)table.getValueAt(r, _c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el cliente \""+name+"\"? ", "Elimindo cliente ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
				tm.removeRow(r);
				MySqlConnector.getInstance().deleteClient(id);
			}
		}
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