package com.desktop.rhinos.gui.table;

import java.awt.Desktop;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.android.rhinos.gest.RhFile;
import com.desktop.rhinos.connector.MySqlConnector;

@SuppressWarnings("serial")
public class DocumentTable extends RhTable {

	private int idService;
	private ArrayList<RhFile> files;
	
	public DocumentTable(int idService) {
		
		this.idService = idService;
		
		tm.addColumn("Nombre");
		tm.addColumn("Fecha");
	}
	
	@Override
	protected void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			String name = (String)table.getValueAt(r, c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el documento \""+name+"\"? ", "Elimindo documento ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				tm.removeRow(r);
				MySqlConnector.getInstance().deleteDocument(files.get(r).getId());
			}
		}
	}
	
	@Override
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			
			int idDocument = ((RhFile)files.get(r)).getId();
			try {
				Desktop.getDesktop().open(MySqlConnector.getInstance().getDocument(idDocument));
			} 
			catch (IOException e) { }
		}
	}
	
	@Override
	public void updateTableData() {
		tm.setRowCount(0);
		
		files = MySqlConnector.getInstance().getDocumentsInfo(idService);
		filterBackUp = new Object[files.size()][];
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		for (int i = 0; i < files.size(); i++) {
			RhFile f = files.get(i);
			Object [] o = {	f.getName(), formatter.format(f.getDate())};
			
			tm.addRow(filterBackUp[i] = o);
		}
	}
	
	@Override
	protected float[] getWidthsPrintableView() {
		float[] i={50f, 25f };
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Documents";
	}
}
