package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class RhTable extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected JTable table;
	protected RhTableModel tm;
	
	protected JButton lookUp;
	protected JButton delete;
	
	protected JTextField filter;
	protected JLabel filterLab;
	protected Object [][] filterBackUp;
	
	
	public RhTable() {
		init();
	}
	
	private void init() {
		tm = new RhTableModel();
		lookUp = new JButton("Ver");
		delete = new JButton("Eliminar");
		filter = new JTextField(35);
		filterLab = new JLabel(new ImageIcon(RhTable.class.getResource("/icons/Search/Search_16x16.png")));
		filterBackUp = null;
		
		table = new JTable(tm);
		table.setFont(App.DEFAULT_FONT);
		table.getTableHeader().setFont(App.DEFAULT_FONT.deriveFont(java.awt.Font.BOLD).deriveFont(12f));
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		filter.setBackground(Color.LIGHT_GRAY);
		filter.setHorizontalAlignment(JTextField.CENTER);
		filter.setFont(App.DEFAULT_FONT);
		filterLab.setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD));

		lookUp.setFont(App.DEFAULT_FONT);
		delete.setFont(App.DEFAULT_FONT);
		
		//removing ENTER behavior
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() > 1) {
					lookUpSelected();
				}
			}
		});
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE: removeSelected(); break;
					case KeyEvent.VK_ENTER: lookUpSelected(); break;
					case KeyEvent.VK_F5: filter.setText(""); updateTableData(); break;
					case KeyEvent.VK_P:
						if (e.getModifiers() == KeyEvent.CTRL_MASK)
							openPrintableView();
						break;
					case KeyEvent.VK_F:
						if (e.getModifiers() == KeyEvent.CTRL_MASK) {
							filter.getParent().setVisible(true);
							filter.requestFocus();
						}
						break;
						
					default:
				}
			}
		});
		
		lookUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lookUpSelected();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSelected();
			}
		});
		
		filter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					filter.getParent().setVisible(false);
					filter.setText("");
				}
				
				if (filterBackUp != null) {
					applyFilter();
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), filterLab, filter), BorderLayout.NORTH);
		add(new JScrollPane(table));
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), lookUp, delete), BorderLayout.SOUTH);
		
		filter.getParent().setVisible(false);	
	}
	
	private void applyFilter() {
		tm.setRowCount(0);
		
		for(Object [] o : filterBackUp) {
			if (rowMatches(o))
				tm.addRow(o);
		}
	}
	
	private boolean rowMatches(Object [] o) {
		for (Object i : o)
			if (i.toString().matches("(?i).*"+filter.getText().trim()+".*"))
				return true;
		return false;
	}
	
	private void openPrintableView(){
		
		
		/*
		 * Pasamos a usar Lector de pdf, definido por el sistema en uso.
		 * 
		JFrame d = new JFrame();
		d.setSize(540, 480);
		d.setTitle("Version Imprimible");
		d.getContentPane().setLayout(new BorderLayout());*/
		
		try {
			//Creating document
			Document doc = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
			File temp = File.createTempFile(new GregorianCalendar().getTimeInMillis()+"", ".pdf");
			temp.deleteOnExit();
			
			PdfWriter.getInstance(doc, new FileOutputStream(temp));
			doc.open();
			
			Font nf = new Font(Font.FontFamily.UNDEFINED, 11, Font.NORMAL);
			Font bf = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
			
			Phrase p1 = new Phrase(getPrintableTitle(), new Font(Font.FontFamily.UNDEFINED, 24, Font.BOLD));
			Phrase p2   = new Phrase("Nombre:  ", bf);
			Phrase p2_0 = new Phrase(App.user.getName(), nf);
		
			Paragraph parag = new Paragraph();
			parag.add(p2);
			parag.add(p2_0);
			
			Phrase p3   = new Phrase("E-mail:  ", bf);
			Phrase p3_0 = new Phrase(App.user.getMail(), nf);
		
			Paragraph parag_0 = new Paragraph();
			parag_0.add(p3);
			parag_0.add(p3_0);
			
			
            PdfPTable pTable = new PdfPTable(tm.getColumnCount());
            pTable.setWidthPercentage(100f);
            
            if (getWidthsPrintableView() != null)
            	pTable.setWidths(getWidthsPrintableView());
            
            for (int i = 0; i < tm.getColumnCount(); i++) {
           		Phrase p = new Phrase(tm.getColumnName(i), bf);
        		PdfPCell cell = new PdfPCell(p);
        		cell.setGrayFill(.8f);
            	pTable.addCell(cell);
            }
            	
            for (int i = 0; i < tm.getRowCount(); i++)
            	for (int j = 0; j < tm.getColumnCount(); j++) {
            		Phrase p = new Phrase(tm.getValueAt(i, j)+"", nf);
            		PdfPCell cell = new PdfPCell(p);
            		cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
          
            		pTable.addCell(cell);
            	}
            
			doc.add(new Paragraph(p1));
			doc.add(new Paragraph("\n"));
			doc.add(parag);
			doc.add(parag_0);
			doc.add(new Paragraph("\n"));
            doc.add(pTable);
            
			doc.close();
			
			//abriendo pdf con lector definido por el sistema.
			Desktop.getDesktop().open(temp);
			
			/*
			//Reading document
			Viewer v = new Viewer();
			v.setDocumentInputStream(new FileInputStream(temp));
			v.activate();
			d.getContentPane().add(v);*/
		} 
		catch (Exception e) {e.printStackTrace();}
		
		/*
		d.setLocationRelativeTo(null);
		d.setVisible(true);*/
	}
	
	protected float[] getWidthsPrintableView() {
		return null;
	}
	
	protected String getPrintableTitle(){
		return "";
	}
	
	protected void removeSelected() {}
	protected void lookUpSelected() {}
	
	public void updateTableData() {}
}
