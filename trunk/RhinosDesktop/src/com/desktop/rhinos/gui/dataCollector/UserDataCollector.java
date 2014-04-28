package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.AddContract;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.interfaces.UserDisplay;
import javax.swing.JCheckBox;

public class UserDataCollector extends JPanel implements UserDisplay {
	
	private static final long serialVersionUID = 1L;

	private JLabel labType;
	private JLabel labUser;
	private JLabel labName;
	private JLabel labMail;
	
	private JComboBox<String> type;
	private JTextField user;
	private JTextField name;
	private JTextField mail;
	private JButton password;
	
	private JPanel labsPanel;
	private JPanel dataPanel;
		
	private JButton ok_button;
	
	private User _user;
	private JCheckBox checkMail;
	
	public UserDataCollector() {
		init();
		setBorder(new TitledBorder(null, "Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}
	
	private void init() {
		_user = new User();
		labType = new JLabel("Tipo:");
		labUser = new JLabel("Usuario:");
		labName = new JLabel("Nombre:");
		labMail = new JLabel("Mail:");
		
		type = new JComboBox<String>();
	
		for (int i = 0; i < User.USER_TYPES.length; i++)
			type.addItem(User.USER_TYPES[i]);
		
		user = new JTextField(AddContract.SFIELD);
		name = new JTextField(AddContract.LFIELD);
		mail = new JTextField(AddContract.LFIELD);
		password = new JButton("Reset Password");
		ok_button = new JButton("Aceptar");
		
		type.setFont(App.DEFAULT_FONT);
		user.setFont(App.DEFAULT_FONT);
		name.setFont(App.DEFAULT_FONT);
		mail.setFont(App.DEFAULT_FONT);
		password.setFont(App.DEFAULT_FONT);
		ok_button.setFont(App.DEFAULT_FONT);
		password.setFocusable(false);
		
		labsPanel = new JPanel(new GridLayout(0, 1));
		dataPanel = new JPanel(new GridLayout(0, 1));
		
		labsPanel.add(Util.packInJP(labType));
		labsPanel.add(Util.packInJP(labUser));
		labsPanel.add(Util.packInJP(labName));
		labsPanel.add(Util.packInJP(labMail));
		labsPanel.add(Util.packInJP(new JLabel())); //ocupando el espacio sobrante
		labsPanel.add(Util.packInJP(new JLabel())); //ocupando el espacio sobrante
		
		dataPanel.add(Util.packInJP(type));
		dataPanel.add(Util.packInJP(user));
		dataPanel.add(Util.packInJP(name));
		dataPanel.add(Util.packInJP(mail));
		
		checkMail = new JCheckBox("Enviar mail", true);
		dataPanel.add(checkMail);
		dataPanel.add(Util.packInJP(password));
				
		setLayout(new BorderLayout());
		
		add(labsPanel, BorderLayout.WEST);
		add(dataPanel);
		
		JPanel bot = new JPanel(new BorderLayout());
		bot.add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), ok_button), BorderLayout.SOUTH);
		add(bot, BorderLayout.SOUTH);
	}
	
	public void setFieldsEditable(boolean editable) {
		type.setEnabled(editable);
		user.setEditable(editable);
		name.setEditable(editable);
		mail.setEditable(editable);
		password.setEnabled(editable);
	}

	public void setUserType(int t) {
		type.setSelectedItem(User.USER_TYPES[t]);
	}
	
	public int getUserType() {
		return type.getSelectedIndex();
	}

	public void setUserField(String t) {
		user.setText(t);
	}
	
	public JTextField getUserField() {
		return user;
	}

	public JTextField getNameField() {
		return name;
	}

	public JTextField getMailField() {
		return mail;
	}
	
	public boolean checkData() {
		return true;
	}
	
	@Override
	public void setData(User c) {
		if (c != null) {
			_user = c;
			type.setSelectedItem(User.USER_TYPES[c.getType()]);
			user.setText(c.getUser());
			name.setText(c.getName());
			mail.setText(c.getMail());
		}
		else {
			type.setSelectedIndex(0);
			user.setText("");
			name.setText("");
			mail.setText("");
		}
	}
	
	public void setData(int id) {
		User u = MySqlConnector.getInstance().getUserById(id);
		setData(u);
	}
	
	public User getUser() {
		_user.setType(type.getSelectedIndex());
		_user.setUser(user.getText().trim());
		_user.setName(name.getText().trim());
		_user.setMail(mail.getText().trim());
		return _user;
	}
	
	public JButton getOkButton() {
		return ok_button;
	}
	
	public JButton getResetPasswordButton() {
		return password;
	}
	
	public JCheckBox getMailCheck() {
		return checkMail;
	}
	
	public boolean isSendMailSelected() {
		return checkMail.isSelected();
	}
}