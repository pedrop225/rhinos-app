package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
	private JComboBox<String> r_type;
	private JTextField r_name;
	private JTextField tf_num;
	private JTextField tf_escalera;
	private JTextField tf_portal;
	private JTextField tf_piso;
	private JTextField tf_puerta;
	private JTextField tf_cp;
	private JTextField tf_poblacion;
	 
	
	public AddressDataCollector() {
		setPreferredSize(new Dimension(540, 155));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		add(panel_1, BorderLayout.NORTH);
		
		r_type = new JComboBox<String>(ROAD_TYPE);
		r_type.setEditable(false);
		panel_1.add(r_type);
		
		r_name = new JTextField();
		panel_1.add(r_name);
		r_name.setColumns(25);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("Num");
		panel_3.add(lblNewLabel);
		
		tf_num = new JTextField();
		panel_3.add(tf_num);
		tf_num.setColumns(5);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel lblNewLabel_1 = new JLabel("Portal");
		panel_4.add(lblNewLabel_1);
		
		tf_portal = new JTextField();
		panel_4.add(tf_portal);
		tf_portal.setColumns(5);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		
		JLabel lblNewLabel_2 = new JLabel("Escalera");
		panel_5.add(lblNewLabel_2);
		
		tf_escalera = new JTextField();
		panel_5.add(tf_escalera);
		tf_escalera.setColumns(5);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		JLabel lblPiso = new JLabel("Piso");
		panel_6.add(lblPiso);
		
		tf_piso = new JTextField();
		panel_6.add(tf_piso);
		tf_piso.setColumns(5);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		
		JLabel lblPuerta = new JLabel("Puerta");
		panel_7.add(lblPuerta);
		
		tf_puerta = new JTextField();
		panel_7.add(tf_puerta);
		tf_puerta.setColumns(5);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		
		JLabel lblPoblacion = new JLabel("Población");
		panel_8.add(lblPoblacion);
		
		tf_poblacion = new JTextField();
		panel_8.add(tf_poblacion);
		tf_poblacion.setColumns(25);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		panel_2.add(lblMunicipio);
		
		JTextField tf_municipio = new JTextField();
		panel_2.add(tf_municipio);
		tf_municipio.setColumns(25);
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9);
		
		JLabel lblCp = new JLabel("CP");
		panel_9.add(lblCp);
		
		tf_cp = new JTextField();
		panel_9.add(tf_cp);
		tf_cp.setColumns(10);
	}

	public void setFieldsEditable(boolean e) {
		r_type.setEnabled(e);
		r_name.setEditable(e);
		tf_num.setEditable(e);
		tf_escalera.setEditable(e);
		tf_portal.setEditable(e);
		tf_piso.setEditable(e);
		tf_puerta.setEditable(e);
		tf_cp.setEditable(e);
		tf_poblacion.setEditable(e);
		
	}
	
	public JTextField getRoadNameField() {
		return r_name;
	}

}
