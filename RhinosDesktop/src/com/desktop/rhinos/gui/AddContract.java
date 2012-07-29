package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.adobe.acrobat.Viewer;
import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.dataCollector.ClientDataCollector;
import com.desktop.rhinos.gui.dataCollector.ConsultancyDataCollector;
import com.desktop.rhinos.gui.table.ServiceTable;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class AddContract extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SSFIELD = 4;
	public static final int SFIELD = 10;
	public static final int LFIELD = 37;
	
	private ClientDataCollector cliData;
	private ConsultancyDataCollector conData;
	private ServiceTable serData;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JButton accept;
	private JButton print;
	
	private boolean editMode;
	
	public AddContract(JFrame locIn) {
		init();
		setLocationRelativeTo(locIn);
	}
	
	public AddContract(JFrame locIn, String id) {
		init();
		setLocationRelativeTo(locIn);	
		prepareContract(id);
	}
	
	private void init() {
		setTitle((editMode) ? "Editar Cliente" : "A�adir Contrato");
		setIconImage(new ImageIcon(AddContract.class.getResource("/icons/rhinos.png")).getImage());
		setResizable(false);
		setLayout(new BorderLayout());
		
		cliData = new ClientDataCollector();
		conData = new ConsultancyDataCollector();
		serData = new ServiceTable();
				
		accept = new JButton("Aceptar");
		print = new JButton("Version Imprimible");
		print.setVisible(false);
		
		JPanel buttons = new JPanel();
		buttons.add(accept);
		buttons.add(print);
		
		setFieldsEditable(false);
		conData.setFieldsEditable(false);
		
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		
		centerPanel.add(cliData);
		centerPanel.add(Util.packInJP(conData), BorderLayout.EAST);
		centerPanel.add(serData, BorderLayout.SOUTH);
		
		southPanel.add(buttons, BorderLayout.EAST);
		
		add(centerPanel);
		add(southPanel, BorderLayout.SOUTH);
		
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkData()) {
					Client c = cliData.getClient();
					c.setConsultancy(conData.getConsultancy().getExtId());
					if (editMode)
						MySqlConnector.getInstance().editClient(c);
					else
						MySqlConnector.getInstance().addClient(c);
					
					dispose();
				}
			}
		});
		
		cliData.getNif().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				checkClientId();
			}
		});
		
		cliData.getIdSelector().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkClientId();
			}
		});
		
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!editMode)
					openPrintableView();
			}
		});
		
		pack();
	}

	private void checkClientId() {
		Id id;
		cliData.getNif().setText(cliData.getNif().getText().toUpperCase());
		String t = cliData.getNif().getText();
		switch (cliData.getIdSelector().getSelectedIndex()) {
			case 1: id = new Dni(t); break;
			case 2: id = new Nie(t); break;
			case 3: id = new Cif(t); break;
			default: id = new Dni("");
		}
		
		boolean v = id.isValid();
		
		if (v) {
			Client c = MySqlConnector.getInstance().clientExists(id.toString());
			if (c != null) {
				prepareContract(c);
			}
			else if (editMode) {
				JOptionPane.showMessageDialog(this, "Error: No se han registrado servicios para este cliente.", 
											  	"Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				setFieldsEditable(v);
		}
		else	
			setFieldsEditable(false);
	}
	
	private boolean checkData() {
		return (cliData.checkData() && conData.checkData() && serData.checkData());
	}
	
	private void prepareEditableContract(Client c) {		
		accept.setVisible(true);
		print.setVisible(false);
		cliData.setFieldsEditable(true);
	}
	
	private void prepareContract(String id) {
		MySqlConnector con =  MySqlConnector.getInstance();
		Client c = con.clientExists(id);
		conData.setData(c.getConsultancy());
		prepareContract(c);
	}
	
	private void prepareContract(Client c) {
		cliData.getNif().setText(c.getId().toString());
		cliData.getClientName().setText(c.getName());
		cliData.getTel().setText(c.getTlf_1());
		cliData.getTelAux().setText(c.getTlf_2());
		cliData.getMail().setText(c.getMail());
		cliData.getAddress().setText(c.getAddress());
		
		cliData.getNif().setEditable(false);
		cliData.getIdSelector().setVisible(false);
		
		serData.updateTableData(c.getId().toString());
		
		if (editMode)
			prepareEditableContract(c);
		else
			prepareNonEditableContract(c);
	}
	
	private void prepareNonEditableContract(Client c) {
		conData.getSearchButton().setVisible(false);		
		serData.addServiceActivated(true);
		print.setVisible(true);
	}
	
	private void setFieldsEditable(boolean editable) {
		cliData.setFieldsEditable(editable);
		accept.setVisible(editable);
	}
	
	public JComboBox getIdSelector() {
		return cliData.getIdSelector();
	}
	public JTextField getNif() {
		return cliData.getNif();
	}
	
	public JTextField getClientName() {
		return cliData.getClientName();
	}

	public JTextField getTel() {
		return cliData.getTel();
	}

	public JTextField getTelAux() {
		return cliData.getTelAux();
	}

	public JTextField getMail() {
		return cliData.getMail();
	}
	
	public JButton getAcceptButton() {
		return accept;
	}
	
	public JTextField getConsultancyName() {
		return conData.getConsultancyName();
	}

	public JTextField getConsultantName() {
		return conData.getConsultantName();
	}

	public JTextField getConsultancyTel() {
		return conData.getTel();
	}

	public JTextField getConsultancyTelAux() {
		return conData.getTelAux();
	}

	public JTextField getConsultancyMail() {
		return conData.getMail();
	}

	public JButton getSearchButton() {
		return conData.getSearchButton();
	}
	
	public void setEditMode(boolean editMode) {
		setTitle("Editar Cliente");
		this.editMode = editMode;
		print.setVisible(!editMode);
	}
	
	private void openPrintableView(){
		JFrame d = new JFrame();
		d.setSize(540, 480);
		d.setTitle("Version Imprimible");
		d.setLayout(new BorderLayout());
		
		try {
			//Creating document
			Document doc = new Document(PageSize.A4, 25, 25, 25, 25);
			File temp = File.createTempFile(new GregorianCalendar().getTimeInMillis()+"", ".pdf");
			temp.deleteOnExit();
			
			PdfWriter.getInstance(doc, new FileOutputStream(temp));
			doc.open();
			
			Font nf = new Font(Font.FontFamily.UNDEFINED, 11, Font.NORMAL);
			Font bf = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
			
			Phrase p1 = new Phrase(getPrintableTitle(), new Font(Font.FontFamily.UNDEFINED, 24, Font.BOLD));			
			
			PdfPCell p2   = new PdfPCell(new Phrase("Id:  ", bf));
			PdfPCell p2_0 = new PdfPCell(new Phrase(cliData.getNif().getText(), nf));
			PdfPCell p3   = new PdfPCell(new Phrase("Nombre:  ", bf));
			PdfPCell p3_0 = new PdfPCell(new Phrase(cliData.getClientName().getText(), nf));
			PdfPCell p4   = new PdfPCell(new Phrase("Tel�fono:  ", bf));
			PdfPCell p4_0 = new PdfPCell(new Phrase(cliData.getTel().getText(), nf));
			PdfPCell p5   = new PdfPCell(new Phrase("Tel�fono Aux:  ", bf));
			PdfPCell p5_0 = new PdfPCell(new Phrase(cliData.getTelAux().getText(), nf));
			PdfPCell p6   = new PdfPCell(new Phrase("E-mail:  ", bf));
			PdfPCell p6_0 = new PdfPCell(new Phrase(cliData.getMail().getText(), nf));
			PdfPCell p7   = new PdfPCell(new Phrase("Direcci�n:  ", bf));
			PdfPCell p7_0 = new PdfPCell(new Phrase(cliData.getAddress().getText(), nf));
			
			p2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p2_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p3.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p3_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p4.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p4_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p5.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p5_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p6.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p6_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p7.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p7_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			
			PdfPTable hTable = new PdfPTable(2);
			hTable.addCell(p2);
			hTable.addCell(p2_0);
			hTable.addCell(p3);
			hTable.addCell(p3_0);
			hTable.addCell(p4);
			hTable.addCell(p4_0);
			hTable.addCell(p5);
			hTable.addCell(p5_0);
			hTable.addCell(p6);
			hTable.addCell(p6_0);
			hTable.addCell(p7);
			hTable.addCell(p7_0);
			
			hTable.setWidthPercentage(100f);
			float [] w = {25f, 80f};
			hTable.setWidths(w);
		
			Paragraph parag = new Paragraph();
			parag.add(hTable);	
            
			doc.add(new Paragraph(p1));
			doc.add(new Paragraph(new Phrase("\nCliente:\n\n", new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD))));
			doc.add(parag);
			doc.add(new Paragraph(new Phrase("Asesor�a:\n", new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD))));
			doc.add(parag);
            
			doc.close();
			
			//Reading document
			Viewer v = new Viewer();
			v.setDocumentInputStream(new FileInputStream(temp));
			v.activate();
			d.add(v);
		} 
		catch (Exception e) {e.printStackTrace();}
		
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}
	
	protected float[] getWidthsPrintableView() {
		return null;
	}
	
	protected String getPrintableTitle(){
		return cliData.getClientName().getText();
	}
}