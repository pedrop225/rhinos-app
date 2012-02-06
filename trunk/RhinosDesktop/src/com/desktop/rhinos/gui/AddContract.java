package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		accept = new JButton("Aceptar");

		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		
		centerPanel = new JPanel();
		southPanel = new JPanel(new BorderLayout());
		
		centerPanel.add(cliData);
		southPanel.add(buttons, BorderLayout.EAST);
		
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		
		pack();
	}
}

class ClientData extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int SSFIELD = 4;
	private static final int SFIELD = 10;
	private static final int LFIELD = 20;
	
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
		setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
	}
	
	private void init() {
		
		setLayout(new BorderLayout());
		
		String [] ids = {"", "Dni", "Nie", "Cif"};
		idSelector = new JComboBox(ids);
		labName = new JLabel("Nombre:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		
		nif = new JTextField(SFIELD);
		name = new JTextField(LFIELD);
		tel = new JTextField(SFIELD);
		telAux = new JTextField(SFIELD);
		mail = new JTextField(LFIELD);
		
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
		
		labsStType = new JLabel("Tipo de vía:");
		labStName = new JLabel("Nombre de via:");
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
		stName = new JTextField(LFIELD);
		stNumber = new JTextField(SSFIELD);
		stFloor = new JTextField(SSFIELD);
		stStairs = new JTextField(SSFIELD);
		door = new JTextField(SSFIELD);
		town = new JTextField(LFIELD);
		province = new JTextField(LFIELD);
		postCode = new JTextField(SFIELD);
		
		addressPanel_west = new JPanel(new GridLayout(9, 1));
		addressPanel_east = new JPanel(new GridLayout(9, 1));
		
		addressPanel = new JPanel(new BorderLayout());
		addressPanel.setBorder(BorderFactory.createTitledBorder("Dirección"));
		
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
		
		editableFields(false);
		setFieldProperties();
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(addressPanel, BorderLayout.SOUTH);
	}
	
	private void editableFields(boolean editable) {
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
