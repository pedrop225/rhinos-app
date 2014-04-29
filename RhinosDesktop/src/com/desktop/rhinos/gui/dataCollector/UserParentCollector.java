package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.UserHierarchyDialog;
import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.interfaces.UserDisplay;

public class UserParentCollector extends JPanel implements UserDisplay {

	private static final long serialVersionUID = 1L;

	private JLabel l_name;
	private JLabel l_super;
	private JLabel l_comm;
	
	private JTextField tf_name;
	private UChooserLauncher tf_launcher;
	private JTextField  tf_comm;
	
	private JButton accept;
	
	private User user;
	
	public UserParentCollector() {
		super(new BorderLayout());
		init();
	}
	
	private void init() {		
		l_name = new JLabel("Usuario");
		l_super = new JLabel("Supervisor");
		l_comm = new JLabel("Beneficio");
		
		l_name.setFont(App.DEFAULT_FONT);
		l_super.setFont(App.DEFAULT_FONT);
		l_comm.setFont(App.DEFAULT_FONT);
		
		tf_name = new JTextField();
		tf_launcher = new UChooserLauncher();
		tf_comm = new JTextField();
		
		tf_name.setFont(App.DEFAULT_FONT);
		tf_comm.setFont(App.DEFAULT_FONT);
		
		accept = new JButton("Aceptar");
		
		JPanel labl_grid = new JPanel(new GridLayout(0, 1, 0, 2));
		JPanel data_grid = new JPanel(new GridLayout(0, 1, 0, 2));
		labl_grid.add(l_name); 	
		data_grid.add(tf_name);
		labl_grid.add(l_super);	
		data_grid.add(tf_launcher);
		labl_grid.add(l_comm);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(tf_comm);
		p.add(new JLabel(" %"), BorderLayout.EAST);
		data_grid.add(p);
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int idParent = tf_launcher.getSelectedUser().getExtId();
					double p_profit = Double.parseDouble(tf_comm.getText());
					
					MySqlConnector.getInstance().editUserParent(user.getExtId(), idParent, p_profit);
					setFieldsEditable(false);
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Error: \""+tf_comm.getText()+"\" no es una cifra válida..", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		JPanel aux = new JPanel(new BorderLayout(25, 0));
		aux.add(labl_grid);
		aux.add(data_grid, BorderLayout.EAST);
		
		add(Util.packInJP(aux));
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), accept), BorderLayout.SOUTH);
		
		tf_name.setEditable(false);
		setFieldsEditable(false);
	}

	@Override
	public void setData(User u) {
		user = u;
		tf_name.setText(user.getName().toUpperCase());
				
		//Obteniendo datos del padre
		User parent = MySqlConnector.getInstance().getUserParent(user.getExtId());
		tf_launcher.setUser(parent);
		
		if (parent != null) {
			String p_profit = new DecimalFormat("0.00").format(parent.getParentProfit()).replace(',', '.');
			tf_comm.setText(p_profit);
		}
		else
			tf_comm.setText("");
	}

	@Override
	public void setFieldsEditable(boolean e) {
		tf_launcher.setFieldsEditable(e);
		tf_comm.setEditable(e);
		
		accept.setVisible(e);
	}
}

@SuppressWarnings("serial")
class UChooserLauncher extends JPanel {
	
	private JTextField t_field;
	private JButton btn;
	
	private UserHierarchyDialog uhd;
	private User user;
	
	public UChooserLauncher() {
		super(new BorderLayout());
		t_field = new JTextField(25);
		t_field.setEditable(false);
		t_field.setFont(App.DEFAULT_FONT);

		btn = new JButton(new ImageIcon(UChooserLauncher.class.getResource("/icons/hierarchy.png")));
		btn.setFocusable(false);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				uhd = new UserHierarchyDialog();
				uhd.setUser(App.user);
				uhd.setVisible(true);
				
				new Thread() {
					public void run() {
						while (uhd.isVisible()) {
							try {
								Thread.sleep(500);
							}
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						if (uhd.getExitMode() == JOptionPane.OK_OPTION) {
							setUser(uhd.getSelectedUser());
						}
					};
				}.start();
			}
		});
		
		add(t_field);
		add(btn, BorderLayout.EAST);
	}
	
	public void setFieldsEditable(boolean e) {
		btn.setEnabled(e);
	}
	
	public void setTextFieldColumns(int columns) {
		t_field.setColumns(columns);
	}
	
	public User getSelectedUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
		t_field.setText((u != null) ? user.getName().toUpperCase() : "");
	}
}