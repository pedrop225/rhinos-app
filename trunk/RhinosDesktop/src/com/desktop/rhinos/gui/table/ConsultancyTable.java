package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import com.desktop.rhinos.gui.Util;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;

public class ConsultancyTable extends RhTable {
	
	private static final long serialVersionUID = 1L;

	private ConsultancyDataCollector display;
	private ConsultancyDataCollector extDisplay;
	
	private JDialog parent;
	
	public ConsultancyTable(ConsultancyDataCollector extDisplay) {
		this.extDisplay = extDisplay;
		init();
	}
	
	public ConsultancyTable(ConsultancyDataCollector extDisplay, JDialog p) {
		parent = p;
		this.extDisplay = extDisplay;
		init();
	}
	
	private void init() {
		tm.addColumn("Asesoría");
		tm.addColumn("Asesor");
		
		String [] i = {"", ""};
		tm.addRow(i);
		
		delete.setVisible(false);
		lookUp.setVisible(false);
		
		display = new ConsultancyDataCollector();
		display.setFieldsEditable(false);
		display.getSearchButton().setVisible(false);
		
		add(Util.packInJP(display), BorderLayout.EAST);
	}
	
	@Override
	public void updateTableData() {
		System.out.println("updateTableData");
	}
	
	@Override
	protected void lookUpSelected() {
		if (extDisplay != null) {
			extDisplay.setConsultancyName(display.getConsultancyName().getText());
			extDisplay.setConsultantName(display.getConsultantName().getText());
			
			if (parent != null)
				parent.dispose();
		}
	}
	
	@Override
	protected void removeSelected() {
		System.out.println("removeSelected");
	}
}