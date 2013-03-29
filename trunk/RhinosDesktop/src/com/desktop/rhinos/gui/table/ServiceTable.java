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

	protected String clientId;
	protected ArrayList<Service> services;
	
	public ServiceTable() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	public ServiceTable(String...list) {
		for (String s : list) {
			tm.addColumn(s);
		}
		
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	private void init() {
        tm.addColumn("Campaña");
        tm.addColumn("Servicio");
        tm.addColumn("Fecha");
        tm.addColumn("Vencimiento");
        tm.addColumn("Estado");

		services = new ArrayList<Service>();
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
				
				/*
				 * Espera a la finalizacion de la configuracion del servicio.
				 * Cuando esta es finalizada, actualiza la tabla.
				 * */
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

	public void updateTableData() {
		tm.setRowCount(0);
		services.clear();
		
		services = MySqlConnector.getInstance().getServices(clientId);
		
		for (Service s : services) {
			Object [] o = {s.getCampaign(), s.getService(), new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()),
															new SimpleDateFormat("dd-MM-yyyy").format(s.getExpiryDate()),
															Service.STATES[s.getState()]};
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
				s.setExtId(services.get(r).getExtId());
				
				tm.removeRow(r);
				MySqlConnector.getInstance().deleteService(s);
			}
		}
	}
	
	@Override
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			
			final ServiceDataCollector dc = new ServiceDataCollector(clientId);
			dc.setEditMode(services.get(r));
			dc.setVisible(true);
			
			/*
			 * Espera a la finalizacion de la configuracion del servicio.
			 * Cuando esta es finalizada, actualiza la tabla.
			 * */
			new Thread() {
				public void run() {
					while (dc.isVisible()) {
						try {
							sleep(500);
						}
						catch (InterruptedException e) {interrupt();}
					}
					updateTableData();
				};
			}.start();
		}
	}
	
	public boolean checkData() {
		return true;
	}
	
	/**
	 * @param b Determina si se muestra o no, un boton que permite añadir
	 * mas Servicios a la lista.
	 */
	public void addServiceActivated(boolean b) {
		addService.setVisible(b);
	}
	
	public void addColumn(String col) {
		tm.addColumn(col);
	}
	
	protected float[] getWidthsPrintableView() {
		float[] i= {25f, 25f, 10f, 10f, 10f};
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Servicios";
	}
}