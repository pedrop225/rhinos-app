package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddressDataCollector extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] ROAD_TYPE = {	"ACCESO", "ACERA", "ALAMEDA", "AUTOPISTA", 
												"AUTOVIA", "AVENIDA", "C. COMERCIAL", "CALLE", 
												"CALLEJÓN", "CAMINO", "CAÑADA",
												"CARRETERA", "CUESTA", "GLORIETA", "PASADIZO", "PASAJE", 
												"PASEO", "PLAZA", "RAMBLA", "RONDA", 
												"SENDERO", "TRAVESÍA", "URBANIZACIÓN", "VÍA"};
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	 
	
	public AddressDataCollector() {
		setPreferredSize(new Dimension(540, 150));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		add(panel_1, BorderLayout.NORTH);
		
		JComboBox<String> comboBox = new JComboBox<String>(ROAD_TYPE);
		panel_1.add(comboBox);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(25);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("Num");
		panel_3.add(lblNewLabel);
		
		textField_1 = new JTextField();
		panel_3.add(textField_1);
		textField_1.setColumns(5);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel lblNewLabel_1 = new JLabel("Portal");
		panel_4.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		panel_4.add(textField_3);
		textField_3.setColumns(5);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		
		JLabel lblNewLabel_2 = new JLabel("Escalera");
		panel_5.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		panel_5.add(textField_2);
		textField_2.setColumns(5);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		JLabel lblPiso = new JLabel("Piso");
		panel_6.add(lblPiso);
		
		textField_4 = new JTextField();
		panel_6.add(textField_4);
		textField_4.setColumns(5);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		
		JLabel lblPuerta = new JLabel("Puerta");
		panel_7.add(lblPuerta);
		
		textField_5 = new JTextField();
		panel_7.add(textField_5);
		textField_5.setColumns(5);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		
		JLabel lblPoblacion = new JLabel("Población");
		panel_8.add(lblPoblacion);
		
		textField_8 = new JTextField();
		panel_8.add(textField_8);
		textField_8.setColumns(25);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		panel_2.add(lblMunicipio);
		
		textField_6 = new JTextField();
		panel_2.add(textField_6);
		textField_6.setColumns(25);
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9);
		
		JLabel lblCp = new JLabel("CP");
		panel_9.add(lblCp);
		
		textField_7 = new JTextField();
		panel_9.add(textField_7);
		textField_7.setColumns(10);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
