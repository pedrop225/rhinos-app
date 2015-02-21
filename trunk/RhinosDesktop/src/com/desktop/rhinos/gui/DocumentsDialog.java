package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import com.desktop.rhinos.gui.table.DocumentTable;

@SuppressWarnings("serial")
public class DocumentsDialog extends JDialog {
	/**
	 * Create the dialog.
	 */
	public DocumentsDialog(int idService) {
		setTitle("Documentos");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		DocumentTable documentTable = new DocumentTable(idService);
		getContentPane().add(documentTable, BorderLayout.CENTER);
		
		documentTable.updateTableData();
		
		setLocationRelativeTo(null);
	}
}
