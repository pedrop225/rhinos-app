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
import java.awt.Toolkit;

public class ServiceDataCollector extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<Object> campaign;
	private JComboBox<Service> service;
	private JDateChooser dch;
	private JDateChooser expiryDch;
	private JComboBox<String> state;
	
	private JLabel labCampaign;
	private JLabel labService;
	private JLabel labDate;
	private JLabel labExpiry;
	private JLabel labState;
	
	private JPanel labPanel;
	private JPanel dataPanel;
	
	private JButton accept;
	
	private JPanel c;
	
	private String clientId;
	
	//guarda el indice externo del servicio a modificar. En caso de ser -1, inserta
	//un nuevo registro en la base de datos como normalmente
	private int toModify = -1;
	
	public ServiceDataCollector(String _c) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServiceDataCollector.class.getResource("/icons/Globe/Globe_16x16.png")));
		clientId = _c;
		init();
		setLocationRelativeTo(null);
	}
	
	private void init() {
		setModal(true);
		setTitle("Configurar Servicio");
		setResizable(false);
		
		c = new JPanel(new BorderLayout());
		c.setBorder(BorderFactory.createTitledBorder(" Servicio "));
		
		state = new JComboBox<String>();
		state.addItem("Pendiente");
		state.addItem("Verificado");
		state.addItem("Anulado");
		state.addItem("Devuelto");
		
		campaign = new JComboBox<Object>(importUserCampaigns().toArray());
		service = new JComboBox<Service>();

		dch = new JDateChooser(new Date());
		dch.setFont(App.DEFAULT_FONT);
		dch.setDateFormatString("dd/MM/yyyy");
		dch.getDateEditor().setEnabled(false);
		
		expiryDch = new JDateChooser(new Date());
		expiryDch.setFont(App.DEFAULT_FONT);
		expiryDch.setDateFormatString("dd/MM/yyyy");
		expiryDch.getDateEditor().setEnabled(false);
		
		campaign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateServices();
			}
		});
		
		accept = new JButton("Guardar");
		
		labState = new JLabel("Estado: ");
		labCampaign = new JLabel("Campaña: ");
		labService = new JLabel("Servicio: ");
		labDate = new JLabel("Fecha: ");
		labExpiry = new JLabel("Vencimiento: ");
		
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
					ms.setExpiryDate(expiryDch.getDate());
					ms.setState(state.getSelectedIndex());
										
					if (toModify < 0)
						MySqlConnector.getInstance().addService(ms, client);
					else
						MySqlConnector.getInstance().editServiceState(toModify, ms.getState());
					
					dispose();
				}
			}
		});
		
		labPanel.add(labState);
		labPanel.add(labCampaign);
		labPanel.add(labService);
		labPanel.add(labDate);
		labPanel.add(labExpiry);
		
		dataPanel.add(state);
		dataPanel.add(campaign);
		dataPanel.add(service);
		dataPanel.add(dch);
		dataPanel.add(expiryDch);
		
		c.setLayout(new BorderLayout(10, 5));
		c.add(labPanel, BorderLayout.WEST);
		c.add(dataPanel);
		c.add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), accept), BorderLayout.SOUTH);
		
		updateServices();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(c);
		
		//adding free space
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		getContentPane().add(new JPanel(), BorderLayout.EAST);
		getContentPane().add(new JPanel(), BorderLayout.SOUTH);

		pack();
	}
	
	/**
	 * Configura el ServiceDataCollector para editar servicios ya existentes
	 * Retira la posibilidad de determinar servicios como DEVUELTOS, el usuario debe
	 * dar de alta uno nuevo y devolverlo para que queden reflejados todos los movimientos.
	 * Boton de Guardar, cambiado a Modificar
	 * Se invalidan los dateChooser para evitar cambios en las fechas.
	 */
	public void setEditMode(Service s) {
		toModify = s.getExtId();
		state.setSelectedIndex(s.getState());
		
		int ind;
		for (ind = 0; ind < campaign.getItemCount(); ind++)
			if (((Campaign)campaign.getItemAt(ind)).getName().equals(s.getCampaign()))
				break;
		campaign.setSelectedIndex(ind);
		
		for (ind = 0; ind < service.getItemCount(); ind++)
			if (((Service)service.getItemAt(ind)).getService().equals(s.getService()))
				break;
		service.setSelectedIndex(ind);
				
		dch.setDate(s.getDate());
		expiryDch.setDate(s.getExpiryDate());
		
		state.removeItemAt(3); //devuelto
		campaign.setEnabled(false);
		service.setEnabled(false);
		dch.setEnabled(false);
		expiryDch.setEnabled(false);
		accept.setText("Modificar");
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