package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;
import com.desktop.rhinos.gui.Util;

public class ConsultancyDataCollector extends JPanel {
	
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
	
	public ConsultancyDataCollector() {
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