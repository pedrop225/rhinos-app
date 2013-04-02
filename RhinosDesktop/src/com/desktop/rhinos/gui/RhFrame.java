package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.table.ConsultancyTableDialog;
import com.desktop.rhinos.gui.table.UserTableDialog;
import javax.swing.SwingConstants;
import java.awt.Color;

public class RhFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mBar;
	private JToolBar tBar;
	
	private JMenu mFile;
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
	private JMenuItem addUser;
	private JMenuItem editUser;
	
	private JMenu help;
	private JMenuItem about;
	
	private JButton bLogOut;
	private JButton bClients;
	private JButton bAddContract;
	private JButton bSettings;
	private JButton bHelp;
	
	private RhPanel rhPanel;
	
	private Logger log;
	private MySqlConnector mySql;
	
	private RhFrame _this = this;
		
	/**
	 * Constructor..
	 * Configura la ventana de la aplicacion centrada en medio de la pantalla.
	 * */
	public RhFrame() {
		init();
		setLocationRelativeTo(null);
	}

	/*
	 * Configura los elementos fundamentales, titulo, icono, y cierre
	 * */
	private void init() {
		setTitle("Rhinos Desktop");
		setIconImage(new ImageIcon(RhFrame.class.getResource("/icons/rhinos.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creando ventana de login para el usuario
		log = new Logger(this);
		mySql = MySqlConnector.getInstance();
		
		rhPanel = new RhPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(rhPanel);
		
		//inicializando barra de menu
		initMenuBar();
		initToolBar();
		
		pack();
	}
	
	private void initToolBar() {
		tBar = new JToolBar();
		tBar.setBackground(Color.LIGHT_GRAY);
		tBar.setOrientation(SwingConstants.VERTICAL);
		tBar.setFloatable(false);
		tBar.setRollover(true);
		
		bLogOut = new JButton();
		bLogOut.setBackground(Color.LIGHT_GRAY);
		bLogOut.setFocusable(false);
		bLogOut.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/LogOut/Log Out_24x24.png")));
		bLogOut.addActionListener(logOut.getActionListeners()[0]);
		
		bClients = new JButton();
		bClients.setBackground(Color.LIGHT_GRAY);
		bClients.setFocusable(false);
		bClients.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/User/User_24x24.png")));
		bClients.addActionListener(edClients.getActionListeners()[0]);
		
		bAddContract = new JButton();
		bAddContract.setBackground(Color.LIGHT_GRAY);
		bAddContract.setFocusable(false);
		bAddContract.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Add/Add_24x24.png")));
		bAddContract.addActionListener(addContract.getActionListeners()[0]);

		
		bSettings = new JButton();
		bSettings.setBackground(Color.LIGHT_GRAY);
		bSettings.setFocusable(false);
		bSettings.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Settings/Settings_24x24.png")));
//		bSettings.addActionListener(logOut.getActionListeners()[0]);
		
		bHelp = new JButton();
		bHelp.setBackground(Color.LIGHT_GRAY);
		bHelp.setFocusable(false);
		bHelp.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Help/Help_24x24.png")));
//		bHelp.addActionListener(logOut.getActionListeners()[0]);
		
		tBar.add(bLogOut);
		tBar.add(bClients);
		tBar.add(bAddContract);
		tBar.add(bSettings);
		tBar.add(bHelp);

		getContentPane().add(tBar, BorderLayout.WEST);
	}
	
	/*
	 * Configura los elementos fundamentales de la barra de menu
	 * */
	private void initMenuBar() {
		mBar = new JMenuBar();
		
		//----------------------------------------
		mFile = new JMenu("Archivo");
		mFile.setIcon(null);
		logOut = new JMenuItem("Cerrar Sesión");
		logOut.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/LogOut/Log Out_24x24.png")));
		exit = new JMenuItem("Salir");
		
		mFile.setFont(App.DEFAULT_FONT);
		logOut.setFont(App.DEFAULT_FONT);
		exit.setFont(App.DEFAULT_FONT);
		
		mFile.add(logOut);
		mFile.add(new JSeparator());
		mFile.add(exit);
		//----------------------------------------
		mEdit = new JMenu("Editar");
		edClients = new JMenuItem("Cliente .."); 
		edClients.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/User/User_24x24.png")));
		edContracts = new JMenu("Contratos");
		edContracts.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Globe/Globe_24x24.png")));
		addContract = new JMenuItem("Añadir Contrato");
		addContract.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Add/Add_16x16.png")));
		edConsultancy = new JMenu("Asesorías");
		edConsultancy.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Archive/Archive_24x24.png")));
		addConsultancy = new JMenuItem("Añadir Asesoría");
		editConsultancy = new JMenuItem("Editar Asesorías");
		edCampaigns = new JMenu("Campañas");
		edUsers = new JMenu("Usuarios");
		addUser = new JMenuItem("Añadir Usuario");
		editUser = new JMenuItem("Editar Usuarios");
		
		mEdit.setFont(App.DEFAULT_FONT);
		edClients.setFont(App.DEFAULT_FONT);
		edContracts.setFont(App.DEFAULT_FONT);
		addContract.setFont(App.DEFAULT_FONT);
		edConsultancy.setFont(App.DEFAULT_FONT);
		addConsultancy.setFont(App.DEFAULT_FONT);
		editConsultancy.setFont(App.DEFAULT_FONT);
		edCampaigns.setFont(App.DEFAULT_FONT);
		edUsers.setFont(App.DEFAULT_FONT);
		addUser.setFont(App.DEFAULT_FONT);
		editUser.setFont(App.DEFAULT_FONT);
	
		mEdit.add(edClients);
		mEdit.add(edContracts);
		edContracts.add(addContract);
		mEdit.add(edConsultancy);
		edConsultancy.add(addConsultancy);
		edConsultancy.add(editConsultancy);
		mEdit.add(edCampaigns);
		mEdit.add(edUsers);
		edUsers.add(addUser);
		edUsers.add(editUser);
		//----------------------------------------
		help = new JMenu("Ayuda");
		about = new JMenuItem("Acerca de ..");
		about.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Help/Help_24x24.png")));
		
		help.setFont(App.DEFAULT_FONT);
		about.setFont(App.DEFAULT_FONT);
		
		help.add(about);
		
		//----------------------------------------
		mBar.add(mFile);
		mBar.add(mEdit);
		mBar.add(help);
		
		setJMenuBar(mBar);
		
		//Configurando listeners
		initmBarListeners();
	}
	
	/*
	 * Comprueba los permisos para el usuario que accede al sistema.
	 * */
	private void chechUserType() {
		boolean r = App.user.isRoot();
		
		//ocultando menus para usuarios NO root
		edConsultancy.setVisible(r);
		edCampaigns.setVisible(r);
		edUsers.setVisible(r);	
	}
	
	
	/*
	 * Lanzando listeners
	 * */
	private void initmBarListeners() {
		//----------------------------------------
		/*
		 * Accion para el boton de aceptar.
		 * En caso de un correcto logueo: 
		 * Oculta la ventana de login, eliminando los datos introducidos en ella.
		 * Lanza la actualizacion de los datos e informacion del usuario correspondiente.
		 * */
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	

				if (mySql.login(log.getUserString(), log.getPasswordString())) {
					log.setVisible(false);
					log.clear();
					showUserBanner();
					chechUserType();
					updateClientsTableData();
					
					/*
					 * Solo actualizar la tabla a la que se accede por defecto, las siguientes se actualizarán
					 * automaticamente en el momento que se acceda a ellas.
					 * */
				//	updateServicesTableData();
					validate();				
				}
			}
		});
		
		/*
		 * Se encarga del cierre de sesion desde la barra de menu.
		 * Deja el sistema listo para un nuevo loggin
		 * */
		logOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				App.user.clear();
				rhPanel.clear();
				log.setVisible(true);
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
		
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddAccount(_this).setVisible(true);
			}
		});
		
		editUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserTableDialog(_this, null, true).setVisible(true);
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
	
	public Logger getLogger() {
		return log;
	}
}
