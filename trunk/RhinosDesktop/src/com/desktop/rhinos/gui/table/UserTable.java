package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.UserCampaingsCollector;
import com.desktop.rhinos.gui.dataCollector.UserDataCollector;
import com.desktop.rhinos.gui.dataCollector.UserHierarchyCollector;
import com.desktop.rhinos.gui.dataCollector.interfaces.UserDisplay;

public class UserTable extends RhTable {
	
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs;
	private UserDataCollector display;
	private UserCampaingsCollector campaigns;
	private UserHierarchyCollector hierarchy;
	
	private ArrayList<UserDisplay> to_update;
	
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

		tabs = new JTabbedPane(JTabbedPane.TOP);
		
		display = new UserDataCollector();
		display.getMailCheck().setVisible(false);
		display.setFieldsEditable(false);
		
		campaigns = new UserCampaingsCollector();
		campaigns.setFieldsEditable(false);
		
		hierarchy = new UserHierarchyCollector();
		hierarchy.setFieldsEditable(false);
		
		editButton = display.getOkButton();
		editButton.setText("Guardar");
		editButton.setVisible(false);
		
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//MySqlConnector.getInstance().editAccount(display.getUser());
				display.setFieldsEditable(false);
				updateTableData();
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				updateDisplay();
			}
		});
		
		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateDisplay();
			}
		});
		
		to_update = new ArrayList<UserDisplay>();
		to_update.add(display);
		to_update.add(campaigns);
		to_update.add(hierarchy); 
		
		tabs.addTab("Contacto", Util.packInJP(display));
		tabs.addTab("Campañas", Util.packInJP(campaigns));
		tabs.addTab("Jerarquía", hierarchy);
		
		add(tabs, BorderLayout.EAST);
	}
	
	/**
	 * Actualiza el display correspondiente a la pestaña visible.
	 * */
	private void updateDisplay() {
		if (table.getSelectedRowCount() > 0) {
			int tab_ind = tabs.getSelectedIndex();
			User u = c.get(table.convertRowIndexToModel(table.getSelectedRow()));
			
			to_update.get(tab_ind).setData(u);
			to_update.get(tab_ind).setFieldsEditable(false);
		}
		editButton.setVisible(false);
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
			to_update.get(tabs.getSelectedIndex()).setFieldsEditable(true);
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
				MySqlConnector.getInstance().deleteAccount(c.get(r).getExtId());
			}
			
			updateTableData();
		}
	}
	
	protected String getPrintableTitle(){
		return "Usuarios";
	}
}