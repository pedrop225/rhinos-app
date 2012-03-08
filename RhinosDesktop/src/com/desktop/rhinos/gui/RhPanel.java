package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.table.ClientTable;
import com.desktop.rhinos.gui.table.ConsultancyTable;
import com.desktop.rhinos.gui.table.ServiceTable;

public class RhPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel north;
	private JPanel center;
	private JPanel south;

	private JTabbedPane centerTab;
	
	private ClientTable clients;
	private JPanel consultancy;
	private ServiceTable services;

	public RhPanel() {
		init();
	}
	
	private void init() {
		north = new JPanel(new BorderLayout());
		center = new JPanel(new BorderLayout());
		south = new JPanel();
		
		clients = new ClientTable();
		centerTab = new JTabbedPane();
		
		consultancy = new JPanel(new BorderLayout());
		consultancy.add(new ConsultancyTable(null, null, false));
		
		services = new ServiceTable() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected ArrayList<Service> importMySqlData() {
				return MySqlConnector.getInstance().getUserServices(App.user);
			}
		};
		
		centerTab.addTab("Clientes", clients);
		centerTab.addTab("Servicios", services);
		centerTab.addTab("Asesorías", consultancy);

		setLayout(new BorderLayout());
		
		north.add(getUserBanner());
		center.add(centerTab);
		
		add(north, BorderLayout.NORTH);
		add(center);
		add(south, BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
	}
	
	private JPanel getUserBanner() {
		JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel((App.user.getName() != null) ? App.user.getName()+"  " : "", JLabel.RIGHT);
		
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 16);
		l.setFont(f);
		b.setBackground(Color.LIGHT_GRAY);
		b.add(l);
		
		return b;
	}
	
	public void showUserBanner() {
		north.add(getUserBanner());
	}
	
	public void updateClientsTableData() {
		clients.updateTableData();
	}
	
	public void updateServicesTableData() {
		services.updateTableData();
	}
	
	public void addContract() {
		AddContract ac = new AddContract(null);
		ac.setVisible(true);
	}
	
	public void clear() {
		removeAll();
		init();
		validate();
	}
}
