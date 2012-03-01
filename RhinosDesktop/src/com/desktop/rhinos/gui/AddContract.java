package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;

public class AddContract extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SSFIELD = 4;
	public static final int SFIELD = 10;
	public static final int LFIELD = 37;
	
	private ClientData cliData;
	private ConsultantData conData;
	private ServiceData serData;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JButton accept;
	
	public AddContract(JFrame locIn) {
		init();
		setLocationRelativeTo(locIn);
	}
	
	public AddContract(JFrame locIn, String id) {
		init();
		setLocationRelativeTo(locIn);
		prepareNonEditableContract(id);
	}
	
	private void init() {
		setTitle("Añadir Contrato");
		setLayout(new BorderLayout());
		
		cliData = new ClientData();
		conData = new ConsultantData();
		serData = new ServiceData();
				
		accept = new JButton("Aceptar");
		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		
		setFieldsEditable(false);
		
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
				if (checkData()) {
					MySqlConnector.getInstance().addClient(cliData.getClient());
					dispose();
				}
			}
		});
		
		cliData.getNif().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				checkClientId();
			}
		});
		
		cliData.getIdSelector().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkClientId();
			}
		});
		
		pack();
	}

	private void checkClientId() {
		Id id;
		cliData.getNif().setText(cliData.getNif().getText().toUpperCase());
		String t = cliData.getNif().getText();
		switch (cliData.getIdSelector().getSelectedIndex()) {
			case 1: id = new Dni(t); break;
			case 2: id = new Nie(t); break;
			case 3: id = new Cif(t);
			default: id = new Dni("");
		}
		
		boolean v = id.isValid();
		
		if (v) {
			Client c = MySqlConnector.getInstance().clientExists(id.toString());
			if (c != null) {
				prepareNonEditableContract(c);
			}
			else
				setFieldsEditable(v);
		}
		else	
			setFieldsEditable(v);
	}
	
	private boolean checkData() {
		return (cliData.checkData() && conData.checkData() && serData.checkData());
	}
	
	private void prepareNonEditableContract(String id) {
		MySqlConnector con =  MySqlConnector.getInstance();
		Client c = con.clientExists(id);
		
		prepareNonEditableContract(c);
	}
	
	private void prepareNonEditableContract(Client c) {
		
		cliData.getNif().setEditable(false);
		cliData.getIdSelector().setVisible(false);
		conData.getSearchButton().setVisible(false);
		
		cliData.getNif().setText(c.getId().toString());
		cliData.getClientName().setText(c.getName());
		cliData.getTel().setText(c.getTlf_1());
		cliData.getTelAux().setText(c.getTlf_2());
		cliData.getMail().setText(c.getMail());
		cliData.getAddress().setText(c.getAddress());
		
		serData.updateData(c.getId().toString());
	}
	
	private void setFieldsEditable(boolean editable) {
		cliData.setFieldsEditable(editable);
		conData.setFieldsEditable(editable);
		accept.setVisible(editable);
	}
	
	public JComboBox getIdSelector() {
		return cliData.getIdSelector();
	}
	public JTextField getNif() {
		return cliData.getNif();
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
	private JTextArea address;
	
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
	
	public JComboBox getIdSelector() {
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
			case 3: c.setId(new Cif(t));
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
		
		cons.setFont(App.DEFAULT_FONT);
		person.setFont(App.DEFAULT_FONT);
		tel.setFont(App.DEFAULT_FONT);
		telAux.setFont(App.DEFAULT_FONT);
		mail.setFont(App.DEFAULT_FONT);
		
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
	
	public void setFieldsEditable(boolean editable) {
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
	
	public boolean checkData() {
		return true;
	}
}

class ServiceData extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private ServiceTable st;
	private JButton addService;
	
	private ArrayList<Integer> ids;
	
	public ServiceData() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Servicios "));
	}
	
	private void init() {
		st = new ServiceTable();
		table = new JTable(st);
		ids = new ArrayList<Integer>();
		addService = new JButton("Nuevo");
		
		table.setFont(App.DEFAULT_FONT);
		table.getTableHeader().setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(0, 75));
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE: removeSelected(); break;
					default:
				}
			}
		});
		
		addService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Adding new Service ..");
			}
		});
		
		setLayout(new BorderLayout());
	
		add(new JScrollPane(table));
		add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), addService), BorderLayout.EAST);
	}
	
	public boolean checkData() {
		return (st.getRowCount() > 0);
	}
	
	public void updateData(String id) {
		st.setRowCount(0);
		ids.clear();
		
		ArrayList<Service> as = MySqlConnector.getInstance().getServices(id);
		
		for (Service s : as) {
			ids.add(s.getExtId());
			Object [] o = {s.getCampaign(), s.getService(), new SimpleDateFormat("dd-MM-yyyy").format(s.getDate())};
			st.addRow(o);
		}
	}
	
	public void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(1);
			String name = (String)table.getValueAt(r, c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el servicio \""+name+"\"? ", "Elimindo servicio ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
				
				Service s = new Service();
				s.setExtId(ids.get(r));
				
				st.removeRow(r);
				MySqlConnector.getInstance().deleteService(s);
			}
		}
	}
}

class ServiceTable extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public ServiceTable() {
		addColumn("Campaña");
		addColumn("Servicio");
		addColumn("Fecha");
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}

class ServiceEditor extends JDialog {

	private static final long serialVersionUID = 1L;

	public ServiceEditor() {
		init();
	}
	
	private void init() {
		setModal(true);
		
		pack();
	}
}