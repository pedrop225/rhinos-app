package com.desktop.rhinos.gui.dataCollector;

import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JLabel;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class UserChooser extends JDialog {

	private static final long serialVersionUID = 3049466173724139108L;

	public UserChooser() {
		getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblUserName = new JLabel(" ");
		lblUserName.setFont(App.DEFAULT_FONT);
		panel.add(lblUserName);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout());
		
		String [][] t= {{"A"}, {"D", "C"}, {"E"}};
		Hashtable<String, String[]> h = new Hashtable<String, String[]>();
		h.put("Admin", t[0]);
		h.put("A", t[1]);
		h.put("B", t[2]);
//		h.put();
		JTree userTree = new JTree(h);
		panel_1.add(userTree);
	}
	
	public static void main(String[] args) {
		UserChooser ch = new UserChooser();
		ch.setSize(540, 480);
		
		ch.setVisible(true);
	}
}
