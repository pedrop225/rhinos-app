package com.desktop.rhinos.gui.table;

import javax.swing.table.DefaultTableModel;

public class RhTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public RhTableModel() {}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}