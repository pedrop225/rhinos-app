package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.dataCollector.ClientDataCollector;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;
import com.desktop.rhinos.gui.table.ServiceTable;

public class AddContract extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SSFIELD = 4;
	public static final int SFIELD = 10;
	public static final int LFIELD = 37;
	
	private ClientDataCollector cliData;
	private ConsultancyDataCollector conData;
	private ServiceTable serData;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JButton accept;
	
	private boolean editMode;
	
	public AddContract(JFrame locIn) {
		init();
		setLocationRelativeTo(locIn);
	}
	
	public AddContract(JFrame locIn, String id) {
		init();
		setLocationRelativeTo(locIn);		
		prepareContract(id);
	}
	
	private void init() {
		setTitle((editMode) ? "Editar Cliente" : "Añadir Contrato");
		setResizable(false);
		setLayout(new BorderLayout());
		
		cliData = new ClientDataCollector();
		conData = new ConsultancyDataCollector();
		serData = new ServiceTable();
				
		accept = new JButton("Aceptar");
		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		
		setFieldsEditable(false);
		conData.setFieldsEditable(false);
		
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
					Client c = cliData.getClient();
					c.setConsultancy(conData.getConsultancy().getExtId());
					if (editMode)
						MySqlConnector.getInstance().editClient(c);
					else
						MySqlConnector.getInstance().addClient(c);
					
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
			case 3: id = new Cif(t); break;
			default: id = new Dni("");
		}
		
		boolean v = id.isValid();
		
		if (v) {
			Client c = MySqlConnector.getInstance().clientExists(id.toString());
			if (c != null) {
				prepareContract(c);
			}
			else if (editMode) {
				JOptionPane.showMessageDialog(this, "Error: No se han registrado servicios para este cliente.", 
											  	"Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				setFieldsEditable(v);
		}
		else	
			setFieldsEditable(false);
	}
	
	private boolean checkData() {
		return (cliData.checkData() && conData.checkData() && serData.checkData());
	}
	
	private void prepareEditableContract(Client c) {		
		accept.setVisible(true);
		cliData.setFieldsEditable(true);
	}
	
	private void prepareContract(String id) {
		MySqlConnector con =  MySqlConnector.getInstance();
		Client c = con.clientExists(id);
		conData.setData(c.getConsultancy());
		prepareContract(c);
	}
	
	private void prepareContract(Client c) {
		cliData.getNif().setText(c.getId().toString());
		cliData.getClientName().setText(c.getName());
		cliData.getTel().setText(c.getTlf_1());
		cliData.getTelAux().setText(c.getTlf_2());
		cliData.getMail().setText(c.getMail());
		cliData.getAddress().setText(c.getAddress());
		
		cliData.getNif().setEditable(false);
		cliData.getIdSelector().setVisible(false);
		
		serData.updateTableData(c.getId().toString());
		
		if (editMode)
			prepareEditableContract(c);
		else
			prepareNonEditableContract(c);
	}
	
	private void prepareNonEditableContract(Client c) {
		conData.getSearchButton().setVisible(false);		
		serData.addServiceActivated(true);
	}
	
	private void setFieldsEditable(boolean editable) {
		cliData.setFieldsEditable(editable);
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
	
	public JTextField getConsultancyName() {
		return conData.getConsultancyName();
	}

	public JTextField getConsultantName() {
		return conData.getConsultantName();
	}

	public JTextField getConsultancyTel() {
		return conData.getTel();
	}

	public JTextField getConsultancyTelAux() {
		return conData.getTelAux();
	}

	public JTextField getConsultancyMail() {
		return conData.getMail();
	}

	public JButton getSearchButton() {
		return conData.getSearchButton();
	}
	
	public void setEditMode(boolean editMode) {
		setTitle("Editar Cliente");
		this.editMode = editMode;
	}
}