package com.desktop.rhinos.gui.table;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.android.rhinos.gest.Client;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;

public class ClientTable extends RhTable {

	private static final long serialVersionUID = 1L;
	
	public ClientTable() {
		tm.addColumn("Nif");
		tm.addColumn("Nombre");
		tm.addColumn("Teléfono");
		tm.addColumn("Mail");
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
	
	protected void removeSelected() {
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
	
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			
			new AddContract(null, (String)tm.getValueAt(r, c)).setVisible(true);
		}
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		Object [][] c = importMySqlClients();
		
		for (Object [] d : c){
			tm.addRow(d);
		}
	}
}