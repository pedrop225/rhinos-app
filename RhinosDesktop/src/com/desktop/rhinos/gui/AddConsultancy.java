package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;

public class AddConsultancy extends JDialog {

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
		setModal(true);
		
		dc = new ConsultancyDataCollector();
		dc.getSearchButton().setVisible(false);
		
		add = new JButton("Añadir Asesoría");
		add.setFont(App.DEFAULT_FONT);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MySqlConnector.getInstance().addConsultancy(dc.getConsultancy());
				dispose();
			}
		});
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(dc);
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(new JPanel(), BorderLayout.EAST);
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		getContentPane().add(new JPanel(), BorderLayout.SOUTH);
		
		dc.getSearchButton().getParent().add(add);
		
		pack();
	}
}
