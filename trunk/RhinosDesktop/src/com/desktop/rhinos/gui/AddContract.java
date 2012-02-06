package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;

public class AddContract extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 720;
	private static final int HEIGHT = 680;
	
	private ClientData cliData;
	private ConsultantData conData;
	private ServiceData serData;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JButton accept;
	
	public AddContract() {
		init();
		setVisible(true);
	}
	
	private void init() {
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout(6, 3));
		
		cliData = new ClientData();
		accept = new JButton("Aceptar");

		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		
		centerPanel.add(cliData);
		southPanel.add(buttons, BorderLayout.EAST);
		
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
	}
}

class ClientData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JComboBox idSelector;
	private JLabel labName;
	private JLabel labTel;
	private JLabel labTelAux;
	private JLabel labMail;
	private JLabel labAddress;
	
	private JTextField nif;
	private JTextField name;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	private JTextArea address;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	
	public ClientData() {
		init();
		setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
	}
	
	private void init() {
		
		setLayout(new BorderLayout(6, 3));
		
		String [] ids = {"Dni", "Nie", "Cif"};
		idSelector = new JComboBox(ids);
		labName = new JLabel("Nombre:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		labAddress = new JLabel("Dirección:");
		
		nif = new JTextField();
		name = new JTextField();
		tel = new JTextField();
		telAux = new JTextField();
		mail = new JTextField();
		address = new JTextArea();
		
		labsPanel = new JPanel(new GridLayout(6, 1, 6, 6));
		labsPanel.add(idSelector);
		labsPanel.add(labName);
		labsPanel.add(labTel);
		labsPanel.add(labTelAux);
		labsPanel.add(labMail);
		labsPanel.add(labAddress);
		
		dataPanel = new JPanel(new GridLayout(6, 1, 6, 6));
		dataPanel.add(nif);
		dataPanel.add(name);
		dataPanel.add(tel);
		dataPanel.add(telAux);
		dataPanel.add(mail);
		dataPanel.add(new JScrollPane(address));
		
		editableFields(false);
		setFieldProperties();
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
	}
	
	private void editableFields(boolean enabled) {
		name.setEditable(enabled);
		tel.setEditable(enabled);
		telAux.setEditable(enabled);
		mail.setEditable(enabled);
		address.setEditable(enabled);
	}
	
	private void setFieldProperties() {
		
		nif.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				Id id;
				switch (idSelector.getSelectedIndex()) {
					case 0: id = new Dni(nif.getText());
							break;
							
					case 1: id = new Nie(nif.getText());
							break;
					default:
							id = new Cif(nif.getText());
							break;
				}
				
				editableFields(id.isValid());
			}
		});
	}
}

class ConsultantData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public ConsultantData() {
		init();
	}
	
	private void init() {
		
	}
}

class ServiceData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public ServiceData() {
		init();
	}
	
	private void init() {
		
	}
}
