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
		setTitle("A�adir Contrato");
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
		labTel = new JLabel("Tel�fono:");
		labTelAux = new JLabel("Tel�fono Aux:");
		labMail = new JLabel("Mail:");
		
		nif = new JTextField(AddContract.SFIELD);
		name = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
		labsPanel = new JPanel(new GridLayout(6, 1));
		labsPanel.add(Util.packInJP(idSelector));
		labsPanel.add(Util.packInJP(labName));
		labsPanel.add(Util.packInJP(labTel));
		labsPanel.add(Util.packInJP(labTelAux));
		labsPanel.add(Util.packInJP(labMail));
		
		dataPanel = new JPanel(new GridLayout(6, 1));
		dataPanel.add(Util.packInJP(nif));
		dataPanel.add(Util.packInJP(name));
		dataPanel.add(Util.packInJP(tel));
		dataPanel.add(Util.packInJP(telAux));
		dataPanel.add(Util.packInJP(mail));
		
		labsStType = new JLabel("Tipo de v�a:");
		labStName = new JLabel("Nombre de v�a:");
		labStNumber = new JLabel("N�mero:");
		labStFloor = new JLabel("Piso:");
		labStStairs = new JLabel("Escalera:");
		labsDoor = new JLabel("Puerta:");
		labTown = new JLabel("Localidad:");
		labProvince = new JLabel("Provincia:");
		labPostcode = new JLabel("C�digo Postal:");
		
		String [] streetTypes = {"", "Alameda", "Autopista", 
								"Autov�a", "Avenida", "Bulevar", 
								"Calle", "Carrer", "Camino", 
								"Carretera", "Glorieta", "Paseo", 
								"Plaza", "Pasaje", "Rambla", 
								"Ronda", "Sector", "Traves�a"};
		
		stType = new JComboBox(streetTypes);
		stName = new JTextField(AddContract.LFIELD);
		stNumber = new JTextField(AddContract.SSFIELD);
		stFloor = new JTextField(AddContract.SSFIELD);
		stStairs = new JTextField(AddContract.SSFIELD);
		door = new JTextField(AddContract.SSFIELD);
		town = new JTextField(AddContract.LFIELD);
		province = new JTextField(AddContract.LFIELD);
		postCode = new JTextField(AddContract.SFIELD);
		
		addressPanel_west = new JPanel(new GridLayout(9, 1));
		addressPanel_east = new JPanel(new GridLayout(9, 1));
		
		addressPanel = new JPanel(new BorderLayout());
		addressPanel.setBorder(BorderFactory.createTitledBorder(" Direcci�n "));
		
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
	private JLabel labMail;
	
	private JTextField cons;
	private JTextField person;
	private JTextField tel;
	private JTextField mail;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	
	private JButton buscar;
	
	public ConsultantData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Asesor�a "));
	}
	
	public void editableFields(boolean editable) {
		cons.setEditable(editable);
		person.setEditable(editable);
		tel.setEditable(editable);
		mail.setEditable(editable);
	}
	
	private void init() {
		labCons = new JLabel("Asesor�a:");
		labPerson = new JLabel("Asesor:");
		labTel = new JLabel("Tel�fono:");
		labMail = new JLabel("Mail:");
		
		cons = new JTextField(AddContract.LFIELD);
		person = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
		labsPanel = new JPanel(new GridLayout(4, 1));
		dataPanel = new JPanel(new GridLayout(4, 1));
		
		labsPanel.add(Util.packInJP(labCons));
		labsPanel.add(Util.packInJP(labPerson));
		labsPanel.add(Util.packInJP(labTel));
		labsPanel.add(Util.packInJP(labMail));
		
		dataPanel.add(Util.packInJP(cons));
		dataPanel.add(Util.packInJP(person));
		dataPanel.add(Util.packInJP(tel));
		dataPanel.add(Util.packInJP(mail));
		
		buscar = new JButton("Buscar");
		
		setLayout(new BorderLayout());
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), buscar), BorderLayout.SOUTH);
	}
	
	
}

class ServiceData extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static final String [] headers = {"Campa�a", "Servicio", "Fecha", "Comisi�n"};
	private static final Object [][] emptyTable = {{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}};
	
	private JTable table;
	
	public ServiceData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	private void init() {
		
		table = new JTable(emptyTable, headers);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		
		setLayout(new BorderLayout());
		
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(table);
	}
}