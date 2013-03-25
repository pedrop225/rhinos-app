package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.table.ServiceTable;
import com.itextpdf.text.Font;

public class ReportDataCollector extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ServiceTable services;
	private DateFilter dateFilter;
	private JLabel lblSum;
	
	/**
	 * Create the panel.
	 */
	public ReportDataCollector() {
		setLayout(new BorderLayout(0, 0));
				
		dateFilter = new DateFilter();
		services = new ServiceTable("Nif", "Titular", "Importe") {
			private static final long serialVersionUID = 1L;
			
			public void updateTableData() {
				tm.setRowCount(0);
				ids.clear();
				
				ArrayList<Service> as = MySqlConnector.getInstance().getUserServicesByDate(App.user, dateFilter.getInitialDate(), 
																									 dateFilter.getFinalDate());
				filterBackUp = new Object[as.size()][];

				int sum = 0;
				for (int i = 0; i < as.size(); i++) {
					Service s = as.get(i);
					ids.add(s.getExtId());
					Object [] o = {	s.getId().toString(), 
									s.getTitular(),
									s.getCommission(),
									s.getCampaign(),
									s.getService(), 
									new SimpleDateFormat("dd-MM-yyyy").format(s.getDate()),								 
									new SimpleDateFormat("dd-MM-yyyy").format(s.getExpiryDate())};
					
					tm.addRow(filterBackUp[i] = o);
					
					sum += s.getCommission();
					lblSum.setText(sum+" €");
				}		
			}
			
			protected float[] getWidthsPrintableView() {
				float[] i={10f, 20f, 5f, 10f, 15f, 7f, 7f};
				return i;
			}
		};
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panel_1 = new JPanel();
		
		panel.add(dateFilter);
		panel_1.setLayout(new BorderLayout());
		panel_1.add(services);
		
		add(panel, BorderLayout.NORTH);
		add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel_2, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("Resumen Facturable: ");
		lblNewLabel.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD));
		panel_2.add(lblNewLabel);
		
		lblSum = new JLabel("0.00 €");
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
