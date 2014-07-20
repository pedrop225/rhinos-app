package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddressDataCollector extends JPanel {
	
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
	private JTextField tf_municipio;
	 
	
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
		
		tf_municipio = new JTextField();
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
	
	public String getTipoVia() {
		return (String)r_type.getSelectedItem();
	}
	
	public String getNombreVia() {
		return r_name.getText().trim();
	}

	public String getNumero() {
		return tf_num.getText().trim();
	}

	public String getEscalera() {
		return tf_escalera.getText().trim();
	}

	public String getPortal() {
		return tf_portal.getText().trim();
	}

	public String getPiso() {
		return tf_piso.getText().trim();
	}

	public String getPuerta() {
		return tf_puerta.getText().trim();
	}

	public String getCp() {
		return tf_cp.getText().trim();
	}

	public String getPoblacion() {
		return tf_poblacion.getText().trim();
	}

	public void setTipoVia(String tipo_via) {
		if (tipo_via == null) tipo_via = "";
		r_type.setSelectedItem(tipo_via.toUpperCase());
	}
	
	public void setNombreVia(String nombre_via) {
		r_name.setText(nombre_via);
	}
	
	public void setNumero(String num) {
		this.tf_num.setText(num);
	}

	public void setEscalera(String tf_escalera) {
		this.tf_escalera.setText(tf_escalera);
	}

	public void setPortal(String tf_portal) {
		this.tf_portal.setText(tf_portal);
	}

	public void setPiso(String tf_piso) {
		this.tf_piso.setText(tf_piso);
	}

	public void setPuerta(String tf_puerta) {
		this.tf_puerta.setText(tf_puerta);
	}

	public void setCp(String tf_cp) {
		this.tf_cp.setText(tf_cp);
	}

	public void setPoblacion(String tf_poblacion) {
		this.tf_poblacion.setText(tf_poblacion);
	}
	
	public void setMunicipio(String municipio) {
		this.tf_municipio.setText(municipio);
	}
	
	public String getMunicipio () {
		return tf_municipio.getText().trim();
	}
}
