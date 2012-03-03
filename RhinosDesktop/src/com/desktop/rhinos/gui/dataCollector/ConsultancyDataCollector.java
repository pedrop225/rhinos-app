package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.table.ConsultancyTableDialog;

public class ConsultancyDataCollector extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JLabel labName;
	private JLabel labConsultant;
	private JLabel labTel;
	private JLabel labTelAux;
	private JLabel labMail;
	
	private JTextField name;
	private JTextField consultant;
	private JTextField tel;
	private JTextField telAux;
	private JTextField mail;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
	
	private JButton search;
	
	private ConsultancyDataCollector _this = this;
	
	public ConsultancyDataCollector() {
		init();
		setBorder(BorderFactory.createTitledBorder(" Asesoría "));
	}
	
	private void init() {
		labName = new JLabel("Nombre:");
		labConsultant = new JLabel("Asesor:");
		labTel = new JLabel("Teléfono:");
		labTelAux = new JLabel("Teléfono Aux:");
		labMail = new JLabel("Mail:");
		
		name = new JTextField(AddContract.LFIELD);
		consultant = new JTextField(AddContract.LFIELD);
		tel = new JTextField(AddContract.SFIELD);
		telAux = new JTextField(AddContract.SFIELD);
		mail = new JTextField(AddContract.LFIELD);
		
		name.setFont(App.DEFAULT_FONT);
		consultant.setFont(App.DEFAULT_FONT);
		tel.setFont(App.DEFAULT_FONT);
		telAux.setFont(App.DEFAULT_FONT);
		mail.setFont(App.DEFAULT_FONT);
		
		labsPanel = new JPanel(new GridLayout(0, 1));
		dataPanel = new JPanel(new GridLayout(0, 1));
		
		labsPanel.add(Util.packInJP(labName));
		labsPanel.add(Util.packInJP(labConsultant));
		labsPanel.add(Util.packInJP(labTel));
		labsPanel.add(Util.packInJP(labTelAux));
		labsPanel.add(Util.packInJP(labMail));
		
		dataPanel.add(Util.packInJP(name));
		dataPanel.add(Util.packInJP(consultant));
		dataPanel.add(Util.packInJP(tel));
		dataPanel.add(Util.packInJP(telAux));
		dataPanel.add(Util.packInJP(mail));
		
		search = new JButton("Buscar");
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ConsultancyTableDialog(null, _this).setVisible(true);
			}
		});
		
		setLayout(new BorderLayout());
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), search), BorderLayout.SOUTH);
	}
	
	public void setFieldsEditable(boolean editable) {
		name.setEditable(editable);
		consultant.setEditable(editable);
		tel.setEditable(editable);
		telAux.setEditable(editable);
		mail.setEditable(editable);
	}

	public void setConsultancyName(String t) {
		name.setText(t);
	}
	
	public JTextField getConsultancyName() {
		return name;
	}

	public void setConsultantName(String t) {
		consultant.setText(t);
	}
	
	public JTextField getConsultantName() {
		return consultant;
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

	public JButton getSearchButton() {
		return search;
	}
	
	public boolean checkData() {
		return true;
	}
}