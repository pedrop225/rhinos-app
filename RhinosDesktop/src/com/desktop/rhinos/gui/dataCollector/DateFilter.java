package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;
import com.toedter.calendar.JCalendar;

public class DateFilter extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JCalendar calendar_1;
	private JCalendar calendar_2;
	private JButton btnUpdate;

	/**
	 * Create the frame.
	 */
	public DateFilter() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());		
		
		JLabel lblDesde = new JLabel("Desde: ");
		lblDesde.setFont(App.DEFAULT_FONT);
		panel_1.add(lblDesde, BorderLayout.NORTH);
		panel_1.add(Util.packInJP(calendar_1 = new JCalendar()));
		
		JLabel lblHasta = new JLabel("Hasta: ");
		lblHasta.setFont(App.DEFAULT_FONT);
		panel_2.add(lblHasta, BorderLayout.NORTH);
		panel_2.add(Util.packInJP(calendar_2 = new JCalendar()));
		
		btnUpdate = new JButton("Actualizar");
		
		add(panel_1, BorderLayout.WEST);
		add(panel_2, BorderLayout.EAST);
		add(Util.packInJP(btnUpdate), BorderLayout.SOUTH);
	}

	public JButton getUpdateButton() {
		return btnUpdate;
	}
	
	public Date getInitialDate() {
		return calendar_1.getDate();
	}
	
	public Date getFinalDate() {
		return calendar_2.getDate();
	}
}
