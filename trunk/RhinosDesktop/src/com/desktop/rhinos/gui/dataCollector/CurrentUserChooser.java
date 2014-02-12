package com.desktop.rhinos.gui.dataCollector;

import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JLabel;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class CurrentUserChooser extends JDialog {

	private static final long serialVersionUID = 3049466173724139108L;

	public CurrentUserChooser() {
		getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblUserName = new JLabel(" ");
		lblUserName.setFont(App.DEFAULT_FONT);
		panel.add(lblUserName);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout());
		
		JTree userTree = new JTree();
		panel_1.add(userTree);
	}
}
