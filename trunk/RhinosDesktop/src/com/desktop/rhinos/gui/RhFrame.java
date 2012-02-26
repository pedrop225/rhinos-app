package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RhFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mBar;
	
	private JMenu mFile;
	private JMenuItem logOut;
	private JMenuItem exit;
	
	private JMenu mEdit;
	
	private JMenu help;
	private JMenuItem about;
	
	private RhPanel rhPanel;
		
	public RhFrame() {
		init();
		setLocationRelativeTo(null);
	}

	private void init() {
		setTitle("Rhinos Desktop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initMenuBar();
		
		rhPanel = new RhPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(rhPanel);
		
		pack();
	}
	
	public void exit() {
		exit.doClick();
	}
	
	public void setClientsTableData(Object [][] d) {
		rhPanel.cleanClientsData();
		rhPanel.setClientsData(d);
	}
	
	public void showUserBanner() {
		rhPanel.showUserBanner();
	}
	
	private void initMenuBar() {
		mBar = new JMenuBar();
		
		//----------------------------------------
		mFile = new JMenu("Archivo");
		logOut = new JMenuItem("Cerrar Sesion");
		exit = new JMenuItem("Salir");
		
		mFile.add(logOut);
		mFile.add(exit);
		//----------------------------------------
		mEdit = new JMenu("Editar");
		
		//----------------------------------------
		help = new JMenu("Ayuda");
		about = new JMenuItem("Acerca de");
		
		help.add(about);
		
		//----------------------------------------
		mBar.add(mFile);
		mBar.add(mEdit);
		mBar.add(help);
		
		setJMenuBar(mBar);
		initmBarListeners();		
	}
	
	private void initmBarListeners() {
		//----------------------------------------
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//-----------------------------------------
	}
}
