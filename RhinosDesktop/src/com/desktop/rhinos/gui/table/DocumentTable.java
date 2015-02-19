package com.desktop.rhinos.gui.table;

@SuppressWarnings("serial")
public class DocumentTable extends RhTable {

	public DocumentTable() {
		tm.addColumn("Nombre");
		tm.addColumn("Fecha");
	}
	
	@Override
	protected void removeSelected() {
		// TODO Auto-generated method stub
		super.removeSelected();
	}
	
	@Override
	protected void lookUpSelected() {
		// TODO Auto-generated method stub
		super.lookUpSelected();
	}
	
	@Override
	public void updateTableData() {
		// TODO Auto-generated method stub
		super.updateTableData();
	}
	
	@Override
	protected float[] getWidthsPrintableView() {
		float[] i={50f, 25f };
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Documents";
	}
}
