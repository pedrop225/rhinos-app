package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.android.rhinos.gest.Consultancy;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;

public class ConsultancyTable extends RhTable {
	
	private static final long serialVersionUID = 1L;

	private ConsultancyDataCollector display;
	private ConsultancyDataCollector extDisplay;
	
	private JDialog parent;
	
	private ArrayList<Consultancy> c;
	
	private boolean editMode;
	private JButton editButton;
	
	public ConsultancyTable(ConsultancyDataCollector extDisplay) {
		this.extDisplay = extDisplay;
		init();
		updateTableData();
	}
	
	public ConsultancyTable(ConsultancyDataCollector extDisplay, JDialog p, boolean editMode) {
		parent = p;
		this.editMode = editMode;
		this.extDisplay = extDisplay;
		init();
		updateTableData();
	}
	
	private void init() {
		c = new ArrayList<Consultancy>();
		
		tm.addColumn("Asesoría");
		tm.addColumn("Asesor");
		
		delete.setVisible(editMode);
		lookUp.setVisible(false);
		
		display = new ConsultancyDataCollector();
		display.setFieldsEditable(false);
		display.getSearchButton().setVisible(false);
		
		editButton = new JButton("Guardar");
		editButton.setVisible(false);
		editButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("saving data ...");
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRowCount() > 0)
					display.setData(c.get(table.convertRowIndexToModel(table.getSelectedRow())));
				
				display.setFieldsEditable(false);
				editButton.setVisible(false);
			}
		});
		
		add(Util.packInJP(display), BorderLayout.EAST);
		display.getSearchButton().getParent().add(editButton);
	}
	
	@Override
	public void updateTableData() {
		tm.setRowCount(0);
		delete.setVisible(tm.getRowCount() > 0);
		c = MySqlConnector.getInstance().getConsultancy();
		
		for (Consultancy _c : c) {
			Object [] i = {_c.getName(), _c.getConsultant()};
			tm.addRow(i);
		}
	}
	
	@Override
	protected void lookUpSelected() {
		if (editMode) {
			display.setFieldsEditable(true);
			editButton.setVisible(true);
		}
		else {
			if (extDisplay != null) {
				extDisplay.setData(display.getConsultancy());
				if (parent != null)
					parent.dispose();
			}
		}
	}
	
	@Override
	protected void removeSelected() {
		if ((editMode) && (table.getSelectedRowCount() > 0)) {
			Consultancy cons = display.getConsultancy();
			if (JOptionPane.showConfirmDialog(null, "Eliminar asesoría "+cons.getName()+" - "+cons.getConsultant()+"?", 
												"Eliminar asesoría", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
												== JOptionPane.YES_OPTION) {
				
				int r = table.convertRowIndexToModel(table.getSelectedRow());
				
				c.remove(r);
				tm.removeRow(r);
				MySqlConnector.getInstance().deleteConsultancy(cons.getExtId());
			}
			
			updateTableData();
		}
	}
}