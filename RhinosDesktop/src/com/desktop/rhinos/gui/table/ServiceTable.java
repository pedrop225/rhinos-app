package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.ServiceDataCollector;

public class ServiceTable extends RhTable {
	
	private static final long serialVersionUID = 1L;

	private JButton addService;

	private String clientId;
	private ArrayList<Integer> ids;
	
	public ServiceTable() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	private void init() {
        tm.addColumn("Campaña");
        tm.addColumn("Servicio");
        tm.addColumn("Fecha");
        tm.addColumn("Vencimiento");

		ids = new ArrayList<Integer>();
		addService = new JButton("Nuevo");
		
		addService.setVisible(false);
		lookUp.setVisible(false);
		delete.setVisible(false);
		
		table.setPreferredScrollableViewportSize(new Dimension(0, 75));
		
		addService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final ServiceDataCollector se = new ServiceDataCollector(clientId);
				se.setVisible(true);
				
				new Thread() {
					public void run() {
						while (se.isVisible()) {
							try {
								sleep(500);
							}
							catch (InterruptedException e) {interrupt();}
						}
						updateTableData();
					};
				}.start();
			}
		});
		
		add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), addService), BorderLayout.EAST);
	}
	
	protected ArrayList<Service> importMySqlData() {
		return MySqlConnector.getInstance().getServices(clientId);
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		ids.clear();
		
		ArrayList<Service> as = importMySqlData();
		
		for (Service s : as) {
			ids.add(s.getExtId());
			Object [] o = {s.getCampaign(), s.getService(), new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()),
															new SimpleDateFormat("dd-MM-yyyy").format(s.getExpiryDate())};
			tm.addRow(o);
		}		
	}
	
	public void updateTableData(String id) {
		clientId = id;
		updateTableData();
	}
	
	public void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(1);
			String name = (String)table.getValueAt(r, c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el servicio \""+name+"\"? ", "Elimindo servicio ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				Service s = new Service();
				s.setExtId(ids.get(r));
				
				tm.removeRow(r);
				MySqlConnector.getInstance().deleteService(s);
			}
		}
	}
	
	public boolean checkData() {
		return true;
	}
	
	public void addServiceActivated(boolean b) {
		addService.setVisible(b);
	}
	
	public void addColumn(String col) {
		tm.addColumn(col);
	}
}