package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;

public class AddConsultancy extends JFrame {

	private static final long serialVersionUID = 1L;

	private ConsultancyDataCollector dc;
	private JButton add;
	
	public AddConsultancy(JFrame loc) {
		init();
		setLocationRelativeTo(loc);
	}
	
	private void init() {
		setTitle("Añadir Asesoría");
		setIconImage(new ImageIcon(AddConsultancy.class.getResource("/icons/rhinos.png")).getImage());
		setResizable(false);
		
		dc = new ConsultancyDataCollector();
		dc.getSearchButton().setVisible(false);
		
		add = new JButton("Añadir");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MySqlConnector.getInstance().addConsultancy(dc.getConsultancy());
				dispose();
			}
		});
		
		setLayout(new BorderLayout());
		
		add(dc);
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		
		dc.getSearchButton().getParent().add(add);
		
		pack();
	}
}
