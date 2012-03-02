package com.desktop.rhinos.gui.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Util;

public abstract class RhTable extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected JTable table;
	protected RhTableModel tm;
	
	protected JButton lookUp;
	protected JButton delete;
	
	public RhTable() {
		init();
	}
	
	private void init() {
		tm = new RhTableModel();
		lookUp = new JButton("Ver");
		delete = new JButton("Eliminar");
		
		table = new JTable(tm);
		table.setFont(App.DEFAULT_FONT);
		table.getTableHeader().setFont(App.DEFAULT_FONT.deriveFont(Font.BOLD).deriveFont(12f));
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
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
					case KeyEvent.VK_F5: updateTableData(); break;
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
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table));
		add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), lookUp, delete), BorderLayout.SOUTH);
	}
	
	protected void removeSelected() {}
	protected void lookUpSelected() {}
	
	public void updateTableData() {}
}

class RhTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public RhTableModel() {}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
