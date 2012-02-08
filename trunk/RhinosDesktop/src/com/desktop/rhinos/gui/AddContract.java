package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class AddContract extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SSFIELD = 4;
	public static final int SFIELD = 10;
	public static final int LFIELD = 20;
	
	private ClientData cliData;
	private ConsultantData conData;
	private ServiceData serData;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JButton accept;
	
	public AddContract() {
		init();
	}
	
	public AddContract(JFrame locIn) {
		this();
		setLocationRelativeTo(locIn);
	}
	
	private void init() {
		setTitle("Añadir Contrato");
		setResizable(false);
		setLayout(new BorderLayout(6, 3));
		
		cliData = new ClientData();
		conData = new ConsultantData();
		serData = new ServiceData();
				
		accept = new JButton("Aceptar");
		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		
		centerPanel.add(cliData);
		centerPanel.add(Util.packInJP(conData), BorderLayout.EAST);
		centerPanel.add(serData, BorderLayout.SOUTH);
		
		southPanel.add(buttons, BorderLayout.EAST);
		
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		pack();
	}
	
	public JComboBox getIdSelector() {
		return cliData.getIdSelector();
	}
	
	public JTextField getClientName() {
		return cliData.getClientName();
	}

	public JTextField getTel() {
		return cliData.getTel();
	}

	public JTextField getTelAux() {
		return cliData.getTelAux();
	}

	public JTextField getMail() {
		return cliData.getMail();
	}

	public JComboBox getStType() {
		return cliData.getStType();
	}

	public JTextField getStName() {
		return cliData.getStName();
	}

	public JTextField getStNumber() {
		return cliData.getStNumber();
	}

	public JTextField getStFloor() {
		return cliData.getStFloor();
	}

	public JTextField getStStairs() {
		return cliData.getStStairs();
	}

	public JTextField getDoor() {
		return cliData.getDoor();
	}

	public JTextField getTown() {
		return cliData.getTown();
	}

	public JTextField getProvince() {
		return cliData.getProvince();
	}

	public JTextField getPostCode() {
		return cliData.getPostCode();
	}
	
	public JButton getAcceptButton() {
		return accept;
	}
	
	public JTextField getConsultant() {
		return conData.getConsultant();
	}

	public JTextField getConsultantPerson() {
		return conData.getConsultantPerson();
	}

	public JTextField getConsultantTel() {
		return conData.getConsultantTel();
	}

	public JTextField getConsultantTelAux() {
		return conData.getConsultantTelAux();
	}

	public JTextField getConsultantMail() {
		return conData.getConsultantMail();
	}

	public JButton getSearchButton() {
		return conData.getSearchButton();
	}
	
	public void setClientEditableFields(boolean editable) {
		cliData.editableFields(editable);
	}
	
	public void setConsultantEditableFields(boolean editable) {
		conData.editableFields(editable);
	}
}

class ClientData extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox idSelector;
	private JLabel labName;
	private JLabel labTel;
	private JLabel labTelAux;
	private JLabel labMail;
	
	private JTextField nif;
	private JTextField name;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	
	private JLabel labsStType;
	private JLabel labStName;
	private JLabel labStNumber;
	private JLabel labStFloor;
	private JLabel labStStairs;
	private JLabel labsDoor;
	private JLabel labTown;
	private JLabel labProvince;
	private JLabel labPostcode;
	
	private JComboBox stType;
	private JTextField stName;
	private JTextField stNumber;
	private JTextField stFloor;
	private JTextField stStairs;
	private JTextField door;
	private JTextField town;
	private JTextField province;
	private JTextField postCode;
	
	private JPanel addressPanel_west;
	private JPanel addressPanel_east;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	private JPanel addressPanel;
	
	public ClientData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Cliente "));
	}
	
	private void init() {
		
		setLayout(new BorderLayout());
		
		String [] ids = {"", "Dni", "Nie", "Cif"};
		idSelector = new JComboBox(ids);
		labName = new JLabel("Nombre:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		
		nif = new JTextField(AddContract.SFIELD);
		name = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
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
		
		labsStType = new JLabel("Tipo de vía:");
		labStName = new JLabel("Nombre de vía:");
		labStNumber = new JLabel("Número:");
		labStFloor = new JLabel("Piso:");
		labStStairs = new JLabel("Escalera:");
		labsDoor = new JLabel("Puerta:");
		labTown = new JLabel("Localidad:");
		labProvince = new JLabel("Provincia:");
		labPostcode = new JLabel("Código Postal:");
		
		String [] streetTypes = {"", "Alameda", "Autopista", 
								"Autovía", "Avenida", "Bulevar", 
								"Calle", "Carrer", "Camino", 
								"Carretera", "Glorieta", "Paseo", 
								"Plaza", "Pasaje", "Rambla", 
								"Ronda", "Sector", "Travesía"};
		
		stType = new JComboBox(streetTypes);
		stName = new JTextField(AddContract.LFIELD);
		stNumber = new JTextField(AddContract.SSFIELD);
		stFloor = new JTextField(AddContract.SSFIELD);
		stStairs = new JTextField(AddContract.SSFIELD);
		door = new JTextField(AddContract.SSFIELD);
		town = new JTextField(AddContract.LFIELD);
		province = new JTextField(AddContract.LFIELD);
		postCode = new JTextField(AddContract.SFIELD);
		
		addressPanel_west = new JPanel(new GridLayout(0, 1));
		addressPanel_east = new JPanel(new GridLayout(0, 1));
		
		addressPanel = new JPanel(new BorderLayout());
		addressPanel.setBorder(BorderFactory.createTitledBorder(" Dirección "));
		
		addressPanel_west.add(Util.packInJP(labsStType));
		addressPanel_east.add(Util.packInJP(stType));
		addressPanel_west.add(Util.packInJP(labStName));
		addressPanel_east.add(Util.packInJP(stName));
		addressPanel_west.add(Util.packInJP(labStNumber));
		addressPanel_east.add(Util.packInJP(stNumber));
		addressPanel_west.add(Util.packInJP(labStStairs));
		addressPanel_east.add(Util.packInJP(stStairs));
		addressPanel_west.add(Util.packInJP(labStFloor));
		addressPanel_east.add(Util.packInJP(stFloor));
		addressPanel_west.add(Util.packInJP(labsDoor));
		addressPanel_east.add(Util.packInJP(door));
		addressPanel_west.add(Util.packInJP(labTown));
		addressPanel_east.add(Util.packInJP(town));
		addressPanel_west.add(Util.packInJP(labProvince));
		addressPanel_east.add(Util.packInJP(province));
		addressPanel_west.add(Util.packInJP(labPostcode));
		addressPanel_east.add(Util.packInJP(postCode));
		
		addressPanel.add(addressPanel_west, BorderLayout.WEST);
		addressPanel.add(addressPanel_east, BorderLayout.EAST);
				
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(addressPanel, BorderLayout.SOUTH);
	}
	
	public JComboBox getIdSelector() {
		return idSelector;
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

	public JComboBox getStType() {
		return stType;
	}

	public JTextField getStName() {
		return stName;
	}

	public JTextField getStNumber() {
		return stNumber;
	}

	public JTextField getStFloor() {
		return stFloor;
	}

	public JTextField getStStairs() {
		return stStairs;
	}

	public JTextField getDoor() {
		return door;
	}

	public JTextField getTown() {
		return town;
	}

	public JTextField getProvince() {
		return province;
	}

	public JTextField getPostCode() {
		return postCode;
	}

	public void editableFields(boolean editable) {
		name.setEditable(editable);
		tel.setEditable(editable);
		telAux.setEditable(editable);
		mail.setEditable(editable);
		
		stName.setEditable(editable);
		stNumber.setEditable(editable);
		stFloor.setEditable(editable);
		stStairs.setEditable(editable);
		door.setEditable(editable);
		town.setEditable(editable);
		province.setEditable(editable);
		postCode.setEditable(editable);
	}
}

class ConsultantData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JLabel labCons;
	private JLabel labPerson;
	private JLabel labTel;
	private JLabel labTelAux;
	private JLabel labMail;
	
	private JTextField cons;
	private JTextField person;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	
	private JButton search;
	
	public ConsultantData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Asesoría "));
	}
	
	private void init() {
		labCons = new JLabel("Asesoría:");
		labPerson = new JLabel("Asesor:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		
		cons = new JTextField(AddContract.LFIELD);
		person = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
		labsPanel = new JPanel(new GridLayout(0, 1));
		dataPanel = new JPanel(new GridLayout(0, 1));
		
		labsPanel.add(Util.packInJP(labCons));
		labsPanel.add(Util.packInJP(labPerson));
		labsPanel.add(Util.packInJP(labTel));
		labsPanel.add(Util.packInJP(labTelAux));
		labsPanel.add(Util.packInJP(labMail));
		
		dataPanel.add(Util.packInJP(cons));
		dataPanel.add(Util.packInJP(person));
		dataPanel.add(Util.packInJP(tel));
		dataPanel.add(Util.packInJP(telAux));
		dataPanel.add(Util.packInJP(mail));
		
		search = new JButton("Buscar");
		
		setLayout(new BorderLayout());
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), search), BorderLayout.SOUTH);
	}
	
	public void editableFields(boolean editable) {
		cons.setEditable(editable);
		person.setEditable(editable);
		tel.setEditable(editable);
		telAux.setEditable(editable);
		mail.setEditable(editable);
	}

	public JTextField getConsultant() {
		return cons;
	}

	public JTextField getConsultantPerson() {
		return person;
	}

	public JTextField getConsultantTel() {
		return tel;
	}

	public JTextField getConsultantTelAux() {
		return telAux;
	}

	public JTextField getConsultantMail() {
		return mail;
	}

	public JButton getSearchButton() {
		return search;
	}
}

class ServiceData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static final String [] headers = {"Campaña", "Servicio", "Fecha", "Comisión"};
	private static final Object [][] emptyTable = {{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}};
	
	private JTable table;
	
	public ServiceData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	private void init() {
		
		table = new JTable(emptyTable, headers);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		
		setLayout(new BorderLayout());
		
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(table);
	}
}