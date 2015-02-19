package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.DocumentsDialog;
import com.desktop.rhinos.gui.Util;
import com.toedter.calendar.JDateChooser;

public class ServiceDataCollector extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public static final int ACCEPTED = 0;
	public static final int CANCELLED = -1;
	
	private JComboBox<Object> campaign;
	private JComboBox<Service> service;
	private CommissionEditor commission;
	private JDateChooser dch;
	private JDateChooser expiryDch;
	private JComboBox<String> state;
	
	private JLabel labCampaign;
	private JLabel labService;
	private JLabel labCommission;
	private JLabel labDate;
	private JLabel labExpiry;
	private JLabel labState;
	
	private JPanel labPanel;
	private JPanel dataPanel;
	
	private UChooserLauncher uchooser;
	
	private JButton accept;
	
	//indica el modo en que se oculta la ventana (ACEPTAR/CANCELAR)
	private int exitMode = CANCELLED;
	
	private JPanel c;
	
	private String clientId;
	
	//guarda el indice externo del servicio a modificar. En caso de ser -1, inserta
	//un nuevo registro en la base de datos como normalmente
	private int toModify = -1;
	
	//notas del servicio
	private JTextArea notes;
	
	private JButton btnDocs;
	private JButton btnScan;
	private JButton btnPdf;
		
	public ServiceDataCollector(String _c) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServiceDataCollector.class.getResource("/icons/Globe/Globe_16x16.png")));
		clientId = _c;
		init();
		setLocationRelativeTo(null);
	}
	
	private void init() {
		setModal(true);
		setTitle("Configurar Servicio");
		setResizable(false);
		
		c = new JPanel(new BorderLayout());
		c.setBorder(BorderFactory.createTitledBorder(" Servicio "));
		
		state = new JComboBox<String>();
		state.addItem(Service.STATES[Service.PENDING]);
		state.addItem(Service.STATES[Service.VERIFIED]);
		state.addItem(Service.STATES[Service.CANCELLED]);
		state.addItem(Service.STATES[Service.RETURNED]);
		
		campaign = new JComboBox<Object>(importUserCampaigns().toArray());
		service = new JComboBox<Service>();
		commission = new CommissionEditor();
		commission.setEnabled(false);

		dch = new JDateChooser(new Date());
		dch.setFont(App.DEFAULT_FONT);
		dch.setDateFormatString("dd/MM/yyyy");
		dch.getDateEditor().setEnabled(false);
		
		expiryDch = new JDateChooser(new Date());
		expiryDch.setFont(App.DEFAULT_FONT);
		expiryDch.setDateFormatString("dd/MM/yyyy");
		expiryDch.getDateEditor().setEnabled(false);
		
		campaign.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateServices();
			}
		});
		
		service.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateCommission();
			}
		});
		
		accept = new JButton("Guardar");
		
		labState = new JLabel("Estado: ");
		labCampaign = new JLabel("Campaña: ");
		labService = new JLabel("Servicio: ");
		labDate = new JLabel("Fecha: ");
		labExpiry = new JLabel("Vencimiento: ");
		
		labPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		dataPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		
		state.setFont(App.DEFAULT_FONT);
		campaign.setFont(App.DEFAULT_FONT);
		service.setFont(App.DEFAULT_FONT);
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitMode = ACCEPTED;
				Client client = new Client();
				client.setId(new Dni(clientId));
								
				if (checkData()) {
					
					Service ms = (Service)service.getSelectedItem();
					ms.setCampaign(((Campaign)campaign.getSelectedItem()).toString());
					ms.setDate(dch.getDate());
					ms.setExpiryDate(expiryDch.getDate());
					ms.setState(state.getSelectedIndex());
					ms.setNotes(notes.getText().trim().toUpperCase());
					
					if ((ms.getCommission() == -1) || (App.user.isRoot()))
						ms.setCommission(Double.parseDouble(commission.getText()));
					
					//toModify < 0 siempre que se inserte un servicio por primera vez
					if (toModify < 0) {
						User user = uchooser.getSelectedUser();
						/* si no hay usuario seleccionado se toma el usuario actual*/
						if (user == null)
							user = App.user;
						
						MySqlConnector.getInstance().addService(user.getExtId(), ms, client);
					}
					//modificacion del servicio.. toModify almacenara la id del servicio a modificar
					else 
						MySqlConnector.getInstance().editService(toModify, ms.getState(), ms.getNotes());
					
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Error: \""+commission.getText()+"\" no es una cifra válida..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		labPanel.add(labState);
		labPanel.add(labCampaign);
		labPanel.add(labService);
		
		labCommission = new JLabel("Comisi\u00F3n:");
		labPanel.add(labCommission);
		labPanel.add(labDate);
		labPanel.add(labExpiry);
		
		dataPanel.add(state);
		dataPanel.add(campaign);
		dataPanel.add(service);
		
		dataPanel.add(commission);
		commission.setColumns(10);
		dataPanel.add(dch);
		dataPanel.add(expiryDch);
		
		c.setLayout(new BorderLayout(10, 5));
		c.add(labPanel, BorderLayout.WEST);
		c.add(dataPanel);
		c.add(Util.packInJP(new FlowLayout(FlowLayout.LEFT), accept), BorderLayout.SOUTH);
		
		updateServices();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(c, BorderLayout.CENTER);
		
		uchooser = new UChooserLauncher();
		getContentPane().add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), uchooser), BorderLayout.NORTH);
		
		//adding free space
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Notas del Servicio", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		notes = new JTextArea(0, 40);
		notes.setFont(App.DEFAULT_FONT);
		notes.setBackground(UIManager.getColor("Button.background"));
		notes.setEditable(false);
		notes.setTabSize(3);
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		panel.add(new JScrollPane(notes), BorderLayout.CENTER);
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		btnDocs = new JButton("");
		btnDocs.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/document.png")));
		panel_1.add(btnDocs);
		btnDocs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DocumentsDialog().setVisible(true);
			}
		});
		
		btnScan = new JButton("");
		btnScan.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/scanner.png")));
		panel_1.add(btnScan);
		
		btnPdf = new JButton("");
		btnPdf.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/pdf.png")));
		panel_1.add(btnPdf);
		btnPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fch = new JFileChooser();
				fch.setFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
				
				if (fch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = fch.getSelectedFile();
					System.out.println(f.getName());
				}
			}
		});

		btnDocs.setEnabled(false);
		btnScan.setEnabled(false);
		btnPdf.setEnabled(false);
		
		pack();
	}
	
	/**
	 * Configura el ServiceDataCollector para editar servicios ya existentes
	 * Retira la posibilidad de determinar servicios como DEVUELTOS, el usuario debe
	 * dar de alta uno nuevo y devolverlo para que queden reflejados todos los movimientos.
	 * Boton de Guardar, cambiado a Modificar
	 * Se invalidan los dateChooser para evitar cambios en las fechas.
	 */
	public void setEditMode(Service s) {
		toModify = s.getExtId();
		
		state.setSelectedItem(Service.STATES[s.getState()]);
		
		int ind;
		for (ind = 0; ind < campaign.getItemCount(); ind++)
			if (((Campaign)campaign.getItemAt(ind)).getName().equals(s.getCampaign()))
				break;
		campaign.setSelectedIndex(ind);
		
		for (ind = 0; ind < service.getItemCount(); ind++)
			if (((Service)service.getItemAt(ind)).getService().equals(s.getService()))
				break;
		service.setSelectedIndex(ind);
		
		commission.setText(new DecimalFormat("0.00").format(s.getCommission()).replace(',', '.'));
		
		/*
		 * En caso de que el servicio seleccionado, pertenezca a una campaña
		 * actualmente no disponible para el usuario se lanzara una excepcion en
		 * este punto del programa.
		 * */
				
		dch.setDate(s.getDate());
		expiryDch.setDate(s.getExpiryDate());
		notes.setText(s.getNotes());
		
		if (!App.user.isRoot())
			commission.setEnabled(false); 
		
		campaign.setEnabled(false);
		service.setEnabled(false);
		dch.setEnabled(false);
		expiryDch.setEnabled(false);
		notes.setEditable(true);

		uchooser.setFieldsEditable(false);
		
		btnDocs.setEnabled(true);
		btnScan.setEnabled(true);
		btnPdf.setEnabled(true);
		
		accept.setText("Modificar");
	}
	
	private boolean checkData() {
		try {
			Double.parseDouble(commission.getText());
		}
		catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private ArrayList<Campaign> importUserCampaigns() {
		return MySqlConnector.getInstance().getCampaigns(App.user);
	}
	
	private void updateServices() {
		service.removeAllItems();
		
		for (Service s : ((Campaign)campaign.getSelectedItem()).getServices().values()) {
			service.addItem(s);
		}
	}
	
	private void updateCommission() {
		Service s = (Service)service.getSelectedItem();
		
		if ((s != null) && (s.getCommission() != -1)) {
			commission.setEnabled(false);
			commission.setText(s.getCommission()+"");
		}
		else {
			commission.setEnabled(true);
			commission.setText("");
		}
	}
	
	public int getExitMode() {
		return exitMode;
	}
	
	@SuppressWarnings("serial")
	class CommissionEditor extends JPanel {
		
		private JTextField tf_comm;
		private JButton bt_activate;
				
		public CommissionEditor() {
			super(new BorderLayout());
			tf_comm = new JTextField();
			tf_comm.setEnabled(false);
			tf_comm.setFont(App.DEFAULT_FONT);
			bt_activate = new JButton(new ImageIcon(CommissionEditor.class.getResource("/icons/modify.png")));

			bt_activate.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean e = tf_comm.isEnabled();
					tf_comm.setEnabled(!e);
					//edicion de comisiones
					if (e) {
						bt_activate.setIcon(new ImageIcon(CommissionEditor.class.getResource("/icons/modify.png")));
						if (checkData()) {
						
							MySqlConnector.getInstance().editServiceCommission(toModify, Double.parseDouble(getText()));
						}
						else {
							JOptionPane.showMessageDialog(null, "Error: \""+commission.getText()+"\" no es una cifra válida..", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
						bt_activate.setIcon(new ImageIcon(CommissionEditor.class.getResource("/icons/validate.png")));
				}
			});
			
			add(tf_comm);
			add(bt_activate, BorderLayout.EAST);
		}
		
		public String getText() {
			return tf_comm.getText();
		}
		
		public void setColumns(int cols) {
			tf_comm.setColumns(cols);
		}
		
		public void setText(String t) {
			tf_comm.setText(t);
		}
		
		public void setEnabled(boolean e) {
			bt_activate.setEnabled(e);
		}
	}
}