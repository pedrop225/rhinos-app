package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;

public class RhFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mBar;
	
	private JMenu mFile;
	private JMenuItem update;
	private JMenuItem logOut;
	private JMenuItem exit;
	
	private JMenu mEdit;
	private JMenuItem edClients;
	private JMenu edContracts;
	private JMenuItem addContract;
	private JMenu edCampaigns;
	private JMenu edUsers;
	
	private JMenu help;
	private JMenuItem about;
	
	private RhPanel rhPanel;
	
	private Logger log;
	private MySqlConnector mySql;
		
	public RhFrame() {
		init();
		setLocationRelativeTo(null);
	}

	private void init() {
		setTitle("Rhinos Desktop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		log = new Logger(this);
		mySql = MySqlConnector.getInstance();
		
		initMenuBar();
		
		rhPanel = new RhPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(rhPanel);
		
		pack();
	}
	
	private void initMenuBar() {
		mBar = new JMenuBar();
		
		//----------------------------------------
		mFile = new JMenu("Archivo");
		logOut = new JMenuItem("Cerrar Sesión");
		update = new JMenuItem("Actualizar");
		exit = new JMenuItem("Salir");
		
		mFile.setFont(App.DEFAULT_FONT);
		update.setFont(App.DEFAULT_FONT);
		logOut.setFont(App.DEFAULT_FONT);
		exit.setFont(App.DEFAULT_FONT);
		
		mFile.add(logOut);
		mFile.add(update);
		mFile.add(new JSeparator());
		mFile.add(exit);
		//----------------------------------------
		mEdit = new JMenu("Editar");
		edClients = new JMenuItem("Cliente .."); 
		edContracts = new JMenu("Contratos");
		addContract = new JMenuItem("Añadir Contrato");
		edCampaigns = new JMenu("Campañas");
		edUsers = new JMenu("Usuarios");
		
		mEdit.setFont(App.DEFAULT_FONT);
		edClients.setFont(App.DEFAULT_FONT);
		edContracts.setFont(App.DEFAULT_FONT);
		addContract.setFont(App.DEFAULT_FONT);
		edCampaigns.setFont(App.DEFAULT_FONT);
		edUsers.setFont(App.DEFAULT_FONT);
	
		mEdit.add(edClients);
		mEdit.add(edContracts);
		edContracts.add(addContract);
		mEdit.add(edCampaigns);
		mEdit.add(edUsers);
		//----------------------------------------
		help = new JMenu("Ayuda");
		about = new JMenuItem("Acerca de ..");
		
		help.setFont(App.DEFAULT_FONT);
		about.setFont(App.DEFAULT_FONT);
		
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
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	

				if (mySql.login(log.getUserString(), log.getPasswordString())) {
					log.setVisible(false);
					log.clear();
					showUserBanner();
					updateClientsTableData();
					validate();				
				}
			}
		});
		
		logOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				App.user.clear();
				rhPanel.clear();
				log.setVisible(true);
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateClientsTableData();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		addContract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rhPanel.addContract();
			}
		});
	}
	
	public void exit() {
		exit.doClick();
	}
	
	public void updateClientsTableData() {
		rhPanel.updateClientsTableData();
	}
	
	public void showUserBanner() {
		rhPanel.showUserBanner();
	}
	
	public void setUpdateAction(ActionListener e) {
		update.addActionListener(e);
	}
	
	public Logger getLogger() {
		return log;
	}
}
