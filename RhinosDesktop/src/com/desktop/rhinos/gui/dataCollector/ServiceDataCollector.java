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

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;
import com.toedter.calendar.JDateChooser;

public class ServiceDataCollector extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JComboBox campaign;
	private JComboBox service;
	private JDateChooser dch;
	
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
	
	private void init() {
		setModal(true);
		setTitle("Nuevo Servicio");
		setResizable(false);
		
		c = new JPanel(new BorderLayout());
		c.setBorder(BorderFactory.createTitledBorder(" Servicio "));
		
		campaign = new JComboBox(importUserCampaigns().toArray());
		service = new JComboBox();
		dch = new JDateChooser(new Date());
		dch.setFont(App.DEFAULT_FONT);
		dch.setDateFormatString("dd/MM/yyyy");
		dch.getDateEditor().setEnabled(false);
		
		campaign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateServices();
			}
		});
		
		accept = new JButton("Aceptar");
		
		labCampaign = new JLabel("Campaña: ");
		labService = new JLabel("Servicio: ");
		labDate = new JLabel("Fecha: ");
		
		labPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		dataPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		
		campaign.setFont(App.DEFAULT_FONT);
		service.setFont(App.DEFAULT_FONT);
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Client client = new Client();
				client.setId(new Dni(clientId));
								
				if (checkData()) {
					
					Service ms = (Service)service.getSelectedItem();
					ms.setCampaign(((Campaign)campaign.getSelectedItem()).toString());
					ms.setDate(dch.getDate());
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
		dataPanel.add(dch);
		
		c.setLayout(new BorderLayout(10, 5));
		c.add(labPanel, BorderLayout.WEST);
		c.add(dataPanel);
		c.add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), accept), BorderLayout.SOUTH);
		
		updateServices();
		setLayout(new BorderLayout());
		add(c);
		
		//adding free space
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.SOUTH);

		pack();
	}
	
	private boolean checkData() {
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