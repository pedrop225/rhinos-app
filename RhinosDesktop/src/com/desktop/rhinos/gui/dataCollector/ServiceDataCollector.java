package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;

public class ServiceDataCollector extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JComboBox campaign;
	private JComboBox service;
	private JTextField yyyy;
	private JTextField MM;
	private JTextField dd;
	
	private JLabel labCampaign;
	private JLabel labService;
	private JLabel labDate;
	
	private JPanel labPanel;
	private JPanel dataPanel;
	
	private JButton accept;
	
	private JPanel c;
	
	private String clientId;

	public ServiceDataCollector(String _c) {
		clientId = _c;
		init();
		setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("deprecation")
	private void init() {
		setModal(true);
		setTitle("Nuevo Servicio");
		setResizable(false);
		
		c = new JPanel(new BorderLayout());
		c.setBorder(BorderFactory.createTitledBorder(" Servicio "));
		
		campaign = new JComboBox(importUserCampaigns().toArray());
		service = new JComboBox();
		updateServices();
		
		campaign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateServices();
			}
		});
		
		yyyy = new JTextField(""+(new Date().getYear() + 1900), 5);
		MM = new JTextField(""+(new Date().getMonth() + 1), 3);
		dd = new JTextField(""+new Date().getDate(), 3);
		accept = new JButton("Aceptar");
		
		labCampaign = new JLabel("Campaña: ");
		labService = new JLabel("Servicio: ");
		labDate = new JLabel("Fecha: ");
		
		labPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		dataPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		
		campaign.setFont(App.DEFAULT_FONT);
		service.setFont(App.DEFAULT_FONT);
		yyyy.setFont(App.DEFAULT_FONT);
		MM.setFont(App.DEFAULT_FONT);
		dd.setFont(App.DEFAULT_FONT);
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Client client = new Client();
				client.setId(new Dni(clientId));
								
				if (checkData()) {
					
					int y = Integer.parseInt(yyyy.getText().trim());
					int m = Integer.parseInt(MM.getText().trim());
					int d = Integer.parseInt(dd.getText().trim());
					
					Service ms = (Service)service.getSelectedItem();
					ms.setCampaign(((Campaign)campaign.getSelectedItem()).toString());
					ms.setDate(new Date(y - 1900, m - 1, d));
					ms.setTlf_1("");
					ms.setTlf_2("");
					
					MySqlConnector.getInstance().addService(ms, client);
					dispose();
				}
			}
		});
		
		labPanel.add(labCampaign);
		labPanel.add(labService);
		labPanel.add(labDate);
		
		dataPanel.add(campaign);
		dataPanel.add(service);
		dataPanel.add(Util.packInJP(new FlowLayout(), dd, new JLabel("/"), MM, new JLabel("/"), yyyy));
		
		c.setLayout(new BorderLayout(10, 5));
		c.add(labPanel, BorderLayout.WEST);
		c.add(dataPanel);
		c.add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), accept), BorderLayout.SOUTH);
		
		setLayout(new BorderLayout());
		add(c);
		
		//adding free space
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.SOUTH);

		pack();
	}
	
	private boolean checkData() {
		try {
			Integer.parseInt(yyyy.getText().trim());
			Integer.parseInt(MM.getText().trim());
			Integer.parseInt(dd.getText().trim());
		}
		catch (NumberFormatException e) {return false;}
		
		return true;
	}
	private ArrayList<Campaign> importUserCampaigns() {
		return MySqlConnector.getInstance().getCampaigns(App.user);
	}
	
	private void updateServices() {
		service.removeAllItems();
		
		for (Service s : ((Campaign)campaign.getSelectedItem()).getServices().values()) {
			service.addItem(s);
		}
	}
}