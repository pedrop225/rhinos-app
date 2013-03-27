package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.dataCollector.ReportDataCollector;
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
	private ServiceTable services;
	private ReportDataCollector reports;
	private JPanel consultancy;


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
		
		services = new ServiceTable("Nif", "Titular") {
			private static final long serialVersionUID = 1L;
			
			public void updateTableData() {
				tm.setRowCount(0);
				ids.clear();
				
				ArrayList<Service> as = MySqlConnector.getInstance().getUserServices(App.user);
				filterBackUp = new Object[as.size()][];

				for (int i = 0; i < as.size(); i++) {
					Service s = as.get(i);
					ids.add(s.getExtId());
					Object [] o = {	s.getId().toString(), 
									s.getTitular(), 
									s.getCampaign(), 
									s.getService(), 
									new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()),								 
									new SimpleDateFormat("dd-MM-yyyy").format(s.getExpiryDate())};
					
					tm.addRow(filterBackUp[i] = o);
				}		
			}
			
			protected float[] getWidthsPrintableView() {
				float[] i={10f, 25f, 10f, 15f, 7f, 7f};
				return i;
			}
		};
		
		reports = new ReportDataCollector();
		
		centerTab.addTab("Clientes", new ImageIcon(RhPanel.class.getResource("/icons/User/User_16x16.png")), clients);
		centerTab.addTab("Servicios", new ImageIcon(RhPanel.class.getResource("/icons/Globe/Globe_16x16.png")), services);
		centerTab.addTab("Informes", new ImageIcon(RhPanel.class.getResource("/icons/Properties/Properties_16x16.png")), reports);
		centerTab.addTab("Asesorías", new ImageIcon(RhPanel.class.getResource("/icons/Archive/Archive_16x16.png")), consultancy);

		//Actualizando al cambiar de pestañas ...
		centerTab.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (centerTab.getSelectedComponent() == clients)
					clients.updateTableData();
				else if (centerTab.getSelectedComponent() == services)
					services.updateTableData();
			}
		});
		
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