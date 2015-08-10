package com.desktop.rhinos.gui.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.android.rhinos.gest.Client;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;

public class ClientTable extends RhTable {

	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public ClientTable() {
		tm.addColumn("Nif");
		tm.addColumn("Nombre");
		tm.addColumn("F. Nacimiento");
		tm.addColumn("Teléfono");
	}
	
	protected void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			int _c = table.convertColumnIndexToModel(1);
			String id = (String)table.getValueAt(r, c);
			String name = (String)table.getValueAt(r, _c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el cliente \""+name+"\"? ", "Elimindo cliente ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				MySqlConnector.getInstance().deleteClient(id);
				updateTableData();
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
		
		ArrayList<Client> ac = MySqlConnector.getInstance().getClients(App.user);
		filterBackUp = new Object[ac.size()][];
		
		for (int i = 0; i < ac.size(); i++) {
			Client c = ac.get(i);
			Object [] o = {	c.getId().toString(),
							c.getName(),
							formatter.format(c.getBDate()),
							c.getTlf_1()};
			
			tm.addRow(filterBackUp[i] = o);
		}
	}
	
	protected float[] getWidthsPrintableView() {
		float[] i={10f, 25f, 7f, 15f};
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Clientes";
	}
}