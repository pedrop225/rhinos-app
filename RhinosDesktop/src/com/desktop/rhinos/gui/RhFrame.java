package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.table.ConsultancyTableDialog;

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
	private JMenu edConsultancy;
	private JMenuItem addConsultancy;
	private JMenuItem editConsultancy;
	private JMenu edCampaigns;
	private JMenu edUsers;
	
	private JMenu help;
	private JMenuItem about;
	
	private RhPanel rhPanel;
	
	private Logger log;
	private MySqlConnector mySql;
	
	private RhFrame _this = this;
		
	public RhFrame() {
		init();
		setLocationRelativeTo(null);
	}

	private void init() {
		setTitle("Rhinos Desktop");
		setIconImage(new ImageIcon(RhFrame.class.getResource("/icons/rhinos.png")).getImage());
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
		edConsultancy = new JMenu("Asesorías");
		addConsultancy = new JMenuItem("Añadir Asesoría");
		editConsultancy = new JMenuItem("Editar Asesorías");
		edCampaigns = new JMenu("Campañas");
		edUsers = new JMenu("Usuarios");
		
		mEdit.setFont(App.DEFAULT_FONT);
		edClients.setFont(App.DEFAULT_FONT);
		edContracts.setFont(App.DEFAULT_FONT);
		addContract.setFont(App.DEFAULT_FONT);
		edConsultancy.setFont(App.DEFAULT_FONT);
		addConsultancy.setFont(App.DEFAULT_FONT);
		editConsultancy.setFont(App.DEFAULT_FONT);
		edCampaigns.setFont(App.DEFAULT_FONT);
		edUsers.setFont(App.DEFAULT_FONT);
	
		mEdit.add(edClients);
		mEdit.add(edContracts);
		edContracts.add(addContract);
		mEdit.add(edConsultancy);
		edConsultancy.add(addConsultancy);
		edConsultancy.add(editConsultancy);
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
	
	private void chechUserType() {
		boolean r = App.user.isRoot();
		
		edCampaigns.setVisible(r);
		edUsers.setVisible(r);	
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
					chechUserType();
					updateClientsTableData();
					updateServicesTableData();
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
				updateServicesTableData();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		edClients.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddContract ac = new AddContract(_this);
				ac.setEditMode(true);
				ac.setVisible(true);
			}
		});
		
		addContract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rhPanel.addContract();
			}
		});
		
		addConsultancy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddConsultancy(_this).setVisible(true);
			}
		});
		
		editConsultancy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ConsultancyTableDialog(_this, null, true).setVisible(true);
			}
		});
	}
	
	public void exit() {
		exit.doClick();
	}
	
	public void updateClientsTableData() {
		rhPanel.updateClientsTableData();
	}
	
	public void updateServicesTableData() {
		rhPanel.updateServicesTableData();
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
