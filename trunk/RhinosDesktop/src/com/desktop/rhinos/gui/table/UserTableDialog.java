package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.desktop.rhinos.gui.dataCollector.UserDataCollector;

public class UserTableDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public UserTableDialog(JFrame parent, UserDataCollector display, boolean editMode) {
		setModal(true);
		setTitle("Usuarios");
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(new UserTable(display, this, editMode));
		c.add(new JPanel(), BorderLayout.NORTH);
		c.add(new JPanel(), BorderLayout.EAST);
		c.add(new JPanel(), BorderLayout.WEST);
		
		pack();
		
		setLocationRelativeTo(parent);
	}
}
