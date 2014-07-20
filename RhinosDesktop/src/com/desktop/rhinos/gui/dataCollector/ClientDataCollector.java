package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private JLabel labMail;
	
	private JTextField nif;
	private JTextField name;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	private AddressDataCollector addressPanel;
	
	public ClientDataCollector() {
		init();
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	}
	
	private void init() {
		
		setLayout(new BorderLayout());
		
		String [] ids = {"", "DNI", "NIE", "CIF"};
		idSelector = new JComboBox<String>(ids);
		labName = new JLabel("Nombre:");
		labTel = new JLabel("Teléfonos:");
		labMail = new JLabel("Mail:");
		
		nif = new JTextField(AddContract.SFIELD);
		name = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
		nif.setFont(App.DEFAULT_FONT);
		name.setFont(App.DEFAULT_FONT);
		tel.setFont(App.DEFAULT_FONT);
		telAux.setFont(App.DEFAULT_FONT);
		mail.setFont(App.DEFAULT_FONT);
		
		labsPanel = new JPanel(new GridLayout(0, 1));
		labsPanel.add(Util.packInJP(idSelector));
		labsPanel.add(Util.packInJP(labName));
		labsPanel.add(Util.packInJP(labTel));
	//	labsPanel.add(Util.packInJP(labTelAux));
		labsPanel.add(Util.packInJP(labMail));
		
		JPanel tels = new JPanel(new FlowLayout(FlowLayout.LEADING));
		tels.add(tel);
		tels.add(telAux);
		
		dataPanel = new JPanel(new GridLayout(0, 1));
		dataPanel.add(Util.packInJP(nif));
		dataPanel.add(Util.packInJP(name));
		dataPanel.add(tels);
		//dataPanel.add(Util.packInJP(telAux));
		dataPanel.add(Util.packInJP(mail));
		
		/*
		addressPanel = new JPanel(new BorderLayout());
		addressPanel.setBorder(BorderFactory.createTitledBorder(" Dirección "));
		addressPanel.add(new JScrollPane(address));*/
				
		addressPanel = new AddressDataCollector();
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(addressPanel, BorderLayout.SOUTH);
	}
	
	public void setFieldsEditable(boolean editable) {
		name.setEditable(editable);
		tel.setEditable(editable);
		telAux.setEditable(editable);
		mail.setEditable(editable);
		addressPanel.setFieldsEditable(editable);
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
	
	//address
	public String getNombreVia() {
		return addressPanel.getNombreVia();
	}
	
	public void setNombreVia(String nombre_via) {
		addressPanel.setNombreVia(nombre_via);
	}
	
	public String getTipoVia() {
		return addressPanel.getTipoVia();
	}
	
	public void setTipoVia(String tipo_via) {
		addressPanel.setTipoVia(tipo_via);
	}
	
	public String getAddress() {
		return addressPanel.getNombreVia();
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
		
		c.setDirTipoVia(addressPanel.getTipoVia());
		c.setDirNombreVia(addressPanel.getNombreVia());
		c.setDirNumero(addressPanel.getNumero());
		c.setDirPortal(addressPanel.getPortal());
		c.setDirEscalera(addressPanel.getEscalera());
		c.setDirPiso(addressPanel.getPiso());
		c.setDirPuerta(addressPanel.getPuerta());
		c.setDirPoblacion(addressPanel.getPoblacion());
		c.setDirMunicipio(addressPanel.getMunicipio());
		c.setDirCp(addressPanel.getCp());
		
		/*
		 * 	private String dir_numero;
	private String dir_portal;
	private String dir_escalera;
	private String dir_piso;
	private String dir_puerta;
	private String dir_poblacion;
	private String dir_municipio;
	private String dir_cp;
		 */
		
		return c;
	}
}