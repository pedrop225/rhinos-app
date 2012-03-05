package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;

public class ConsultancyTableDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public ConsultancyTableDialog(JFrame parent, ConsultancyDataCollector display, boolean editMode) {
		setModal(true);
		setTitle("Asesorías");
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(new ConsultancyTable(display, this, editMode));
		c.add(new JPanel(), BorderLayout.NORTH);
		c.add(new JPanel(), BorderLayout.EAST);
		c.add(new JPanel(), BorderLayout.WEST);
		
		pack();
		
		setLocationRelativeTo(parent);
	}
}
