package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Nie;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;
import com.desktop.rhinos.gui.Util;

public class ClientDataCollector extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> idSelector;
	private JLabel labName;
	private JLabel labTel;
	private JLabel labTelAux;
	private JLabel labMail;
	
	private JTextField nif;
	private JTextField name;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	private JTextArea address;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	private JPanel addressPanel;
	
	public ClientDataCollector() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Cliente "));
	}
	
	private void init() {
		
		setLayout(new BorderLayout());
		
		String [] ids = {"", "DNI", "NIE", "CIF"};
		idSelector = new JComboBox<String>(ids);
		labName = new JLabel("Nombre:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		
		nif = new JTextField(AddContract.SFIELD);
		name = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		address = new JTextArea(4, 0);
		address.setWrapStyleWord(true);
		address.setLineWrap(true);
		
		nif.setFont(App.DEFAULT_FONT);
		name.setFont(App.DEFAULT_FONT);
		tel.setFont(App.DEFAULT_FONT);
		telAux.setFont(App.DEFAULT_FONT);
		mail.setFont(App.DEFAULT_FONT);
		address.setFont(App.DEFAULT_FONT);
		
		labsPanel = new JPanel(new GridLayout(0, 1));
		labsPanel.add(Util.packInJP(idSelector));
		labsPanel.add(Util.packInJP(labName));
		labsPanel.add(Util.packInJP(labTel));
		labsPanel.add(Util.packInJP(labTelAux));
		labsPanel.add(Util.packInJP(labMail));
		
		dataPanel = new JPanel(new GridLayout(0, 1));
		dataPanel.add(Util.packInJP(nif));
		dataPanel.add(Util.packInJP(name));
		dataPanel.add(Util.packInJP(tel));
		dataPanel.add(Util.packInJP(telAux));
		dataPanel.add(Util.packInJP(mail));
		
		addressPanel = new JPanel(new BorderLayout());
		addressPanel.setBorder(BorderFactory.createTitledBorder(" Dirección "));
		addressPanel.add(new JScrollPane(address));
				
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(addressPanel, BorderLayout.SOUTH);
	}
	
	public void setFieldsEditable(boolean editable) {
		name.setEditable(editable);
		tel.setEditable(editable);
		telAux.setEditable(editable);
		mail.setEditable(editable);
		address.setEditable(editable);
	}
	
	public JComboBox<String> getIdSelector() {
		return idSelector;
	}
	
	public JTextField getNif() {
		return nif;
	}
	
	public JTextField getClientName() {
		return name;
	}

	public JTextField getTel() {
		return tel;
	}

	public JTextField getTelAux() {
		return telAux;
	}

	public JTextField getMail() {
		return mail;
	}
	
	public JTextArea getAddress() {
		return address;
	}
	
	public boolean checkData() {
		return true;
	}
	
	public Client getClient() {
		Client c = new Client();
		
		String t = nif.getText();
		switch (idSelector.getSelectedIndex()) {
			case 1: c.setId(new Dni(t)); break;
			case 2: c.setId(new Nie(t)); break;
			case 3: c.setId(new Cif(t)); break;
			default: c.setId(new Dni(""));
		}
		
		c.setName(name.getText().trim());
		c.setTlf_1(tel.getText().trim());
		c.setTlf_2(telAux.getText().trim());
		c.setMail(mail.getText().trim());
		c.setAddress(address.getText().trim());
		
		return c;
	}
}