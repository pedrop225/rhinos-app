package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.UserDataCollector;

public class UserTable extends RhTable {
	
	private static final long serialVersionUID = 1L;

	private UserDataCollector display;
	
	private ArrayList<User> c;
	
	private boolean editMode;
	private JButton editButton;
	
	public UserTable() {
		init();
		updateTableData();
	}
	
	public UserTable(boolean editMode) {
		this.editMode = editMode;
		init();
		updateTableData();
	}
	
	private void init() {
		c = new ArrayList<User>();
		
		tm.addColumn("Nombre");
		tm.addColumn("Usuario");
		
		delete.setVisible(editMode);
		lookUp.setVisible(false);
		
		display = new UserDataCollector();
		display.setFieldsEditable(false);
		
		editButton = new JButton("Guardar");
		editButton.setFont(App.DEFAULT_FONT);
		editButton.setVisible(false);
		
		editButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//MySqlConnector.getInstance().editAccount(display.getUser());
				updateTableData();
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
		
		c = MySqlConnector.getInstance().getUsers();
		filterBackUp = new Object[c.size()][];
		
		for (int j = 0; j < c.size(); j++) {
			User _c = c.get(j);
			Object [] o = {_c.getName(), _c.getUser()};
			
			tm.addRow(filterBackUp[j] = o);
		}
	}
	
	@Override
	protected void lookUpSelected() {
		if (editMode) {
			display.setFieldsEditable(true);
			editButton.setVisible(true);
		}
	}
	
	@Override
	protected void removeSelected() {
		if ((editMode) && (table.getSelectedRowCount() > 0)) {
			User cons = display.getUser();
			if (JOptionPane.showConfirmDialog(null, "Eliminar usuario "+cons.getName()+" - "+cons.getUser()+"?", 
												"Eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
												== JOptionPane.YES_OPTION) {
				
				int r = table.convertRowIndexToModel(table.getSelectedRow());
				
				/*
				 * no necesario, la actualizacion de la tabla ya lo hace.
				 * 
				c.remove(r);
				tm.removeRow(r);*/
				
				MySqlConnector.getInstance().deleteAccount(c.get(r).getExtId());
			}
			
			updateTableData();
		}
	}
	
	protected String getPrintableTitle(){
		return "Usuarios";
	}
}