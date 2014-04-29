package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.table.ServiceTable;
import com.itextpdf.text.Font;

public class ReportDataCollector extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ServiceTable services;
	private DateFilter dateFilter;
	private UChooserLauncher userChooser;
	
	private JRadioButton f_personal;
	private JRadioButton f_total;
	
	private JLabel lblSum;
	
	private User user;
	float sum = 0;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("serial")
	public ReportDataCollector() {
		setLayout(new BorderLayout(0, 0));
				
		dateFilter = new DateFilter();
		services = new ServiceTable("Nif", "Titular", "Importe") {
			
			public void updateTableData() {
				tm.setRowCount(0);
				services.clear();
				
				sum = 0;
				lblSum.setText(formatter.format(sum));

				user = userChooser.getSelectedUser();
				if (user == null)
					user = App.user;
				
				int date_type = dateFilter.getDateType();
				ArrayList<Service> as = MySqlConnector.getInstance().getUserServicesByDate(user, dateFilter.getInitialDate(), 
																							dateFilter.getFinalDate(), date_type);
				filterBackUp = new Object[as.size()][];
				
				for (int i = 0; i < as.size(); i++) {
					
					Service s = as.get(i);
					addServiceToReport(s, i);
				}
				
				//Obteniendo produccion de los nodos directos
				if (f_total.isSelected()) {
					ArrayList<User> arr_u = MySqlConnector.getInstance().getUserChildren(user);
					
					/*removing the first element (selected user)*/
					if (arr_u != null) {
						arr_u.remove(0);
					
						for (User u : arr_u) {
							ArrayList<Service> ser = MySqlConnector.getInstance().getUserServicesByDate(u, dateFilter.getInitialDate(),
																										dateFilter.getFinalDate(), date_type);
							/*
							 * Aumentamos el tamaño del backup de filtros para almacenar los nuevos servicios.
							 * */
							Object [][] __filterBackUp = Arrays.copyOf(filterBackUp, filterBackUp.length + ser.size());
							filterBackUp = __filterBackUp;
							
							for (int i = 0; i < ser.size(); i++) {
								Service s = ser.get(i);
								
								//obteniendo comisiones para el supervisor
								s.setCommission(s.getCommission() * u.getParentProfit() /100);
								
								addServiceToReport(s, i);
							}
						}
					}
				}
			}
			
			protected float[] getWidthsPrintableView() {
				float[] i={9f, 25f, 7f, 10f, 15f, 10f, 10f, 7f};
				return i;
			}
			
			private void addServiceToReport(Service s, int backUpIndex) {
				//indice que modifica la comision en funcion del estado del servicio
				int ind = 0;
				switch (s.getState()) {
				case 0 : ind = 0; break;
				case 1 : ind = 1; break;
				case 2 : ind = 0; break;
				case 3 : ind = -1; 
				}
				
				services.add(s);
				Object [] o = {	s.getId().toString(), 
								s.getTitular(),
								formatter.format(s.getCommission() * ind),
								s.getCampaign(),
								s.getService(), 
								new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()),								 
								new SimpleDateFormat("dd-MM-yyyy").format(s.getExpiryDate()),
								Service.STATES[s.getState()]};
				
				tm.addRow(filterBackUp[backUpIndex] = o);
				
				sum += s.getCommission() * ind;
				lblSum.setText(formatter.format(sum));
			}
		};
		
		userChooser = new UChooserLauncher();
		f_personal = new JRadioButton("Personal", true);
		f_total = new JRadioButton("Total");
		
		ButtonGroup b_group = new ButtonGroup();
		b_group.add(f_personal);
		b_group.add(f_total);
		
		JPanel panel_0 = new JPanel(new GridLayout(0, 1, 0, 2));
		panel_0.add(userChooser);
		panel_0.add(f_personal);
		panel_0.add(f_total);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panel_1 = new JPanel();
		
		panel.add(dateFilter);
		panel.add(panel_0);
		panel_1.setLayout(new BorderLayout());
		panel_1.add(services);
		
		add(panel, BorderLayout.NORTH);
		add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel_2, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("Resumen Facturable: ");
		lblNewLabel.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD));
		panel_2.add(lblNewLabel);
		
		lblSum = new JLabel(formatter.format(sum));
		lblSum.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD, 13));
		lblSum.setForeground(Color.RED);
		
		panel_2.add(lblSum);
		
		dateFilter.getUpdateButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				services.updateTableData();
			}
		});
	}
	
	public void updateServicesTableData() {
		services.updateTableData();
	}
}