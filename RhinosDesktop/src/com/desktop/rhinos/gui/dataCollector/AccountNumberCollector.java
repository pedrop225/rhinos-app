package com.desktop.rhinos.gui.dataCollector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.android.rhinos.gest.AccountNumber;

public class AccountNumberCollector extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtIban;
	private JTextField tf_entidad;
	private JTextField tf_oficina;
	private JTextField tf_dc;
	private JTextField tf_cc;
	
	private JTextField d_iban;
	private JTextField d_entidad;
	private JTextField d_oficina;
	private JTextField d_dc;
	private JTextField d_cc;
	
	private AccountNumber aNumber;

	/**
	 * Create the panel.
	 */
	public AccountNumberCollector() {
		setPreferredSize(new Dimension(500, 90));
		setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		txtIban = new JTextField();
		txtIban.setFont(new Font("Monospaced", Font.BOLD, 11));
		txtIban.setEnabled(false);
		txtIban.setText("IBAN");
		txtIban.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(txtIban);
		txtIban.setColumns(5);
		
		tf_entidad = new JTextField();
		tf_entidad.setFont(new Font("Monospaced", Font.BOLD, 11));
		tf_entidad.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(tf_entidad);
		tf_entidad.setColumns(5);
		
		tf_oficina = new JTextField();
		tf_oficina.setFont(new Font("Monospaced", Font.BOLD, 11));
		tf_oficina.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(tf_oficina);
		tf_oficina.setColumns(5);
		
		tf_dc = new JTextField();
		tf_dc.setFont(new Font("Monospaced", Font.BOLD, 11));
		tf_dc.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(tf_dc);
		tf_dc.setColumns(3);
		
		tf_cc = new JTextField();
		tf_cc.setFont(new Font("Monospaced", Font.BOLD, 11));
		tf_cc.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(tf_cc);
		tf_cc.setColumns(12);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		d_iban = new JTextField();
		d_iban.setFont(new Font("Monospaced", Font.BOLD, 11));
		d_iban.setEnabled(false);
		d_iban.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(d_iban);
		d_iban.setColumns(5);
		
		d_entidad = new JTextField();
		d_entidad.setFont(new Font("Monospaced", Font.BOLD, 11));
		d_entidad.setEnabled(false);
		d_entidad.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(d_entidad);
		d_entidad.setColumns(5);
		
		d_oficina = new JTextField();
		d_oficina.setFont(new Font("Monospaced", Font.BOLD, 11));
		d_oficina.setEnabled(false);
		d_oficina.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(d_oficina);
		d_oficina.setColumns(5);
		
		d_dc = new JTextField();
		d_dc.setFont(new Font("Monospaced", Font.BOLD, 11));
		d_dc.setEnabled(false);
		d_dc.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(d_dc);
		d_dc.setColumns(3);
		
		d_cc = new JTextField();
		d_cc.setFont(new Font("Monospaced", Font.BOLD, 11));
		d_cc.setEnabled(false);
		d_cc.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(d_cc);
		d_cc.setColumns(12);
		
		KeyListener l4 = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				JTextField tf = (JTextField)e.getSource();
				textFieldTransferFocus(tf, 4);
			}
		};
		tf_entidad.addKeyListener(l4);
		tf_oficina.addKeyListener(l4);
		
		KeyListener l2 = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				JTextField tf = (JTextField)e.getSource();
				textFieldTransferFocus(tf, 2);
			}
		};
		tf_dc.addKeyListener(l2);
		
		KeyListener l10 = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				JTextField tf = (JTextField)e.getSource();
				textFieldTransferFocus(tf, 10);
			}
		};
		tf_cc.addKeyListener(l10);
		
		aNumber = new AccountNumber();
	}
	
	private void execValidation() {
		updateAccountNumber();
		Color c;
		if (aNumber.validate()) {
			
			d_iban.setText(aNumber.getIban());
			d_entidad.setText(aNumber.getEntidad()+"");
			d_oficina.setText(aNumber.getOficina()+"");
			d_dc.setText(aNumber.getDc()+"");
			d_cc.setText(aNumber.getCc()+"");
			
			c = Color.GREEN.darker().darker();
		}
		else {
			d_iban.setText("");
			d_entidad.setText("");
			d_oficina.setText("");
			d_dc.setText("");
			d_cc.setText("");
			
			c = Color.RED.darker();
		}
		
		tf_entidad.setForeground(c);
		tf_oficina.setForeground(c);
		tf_dc.setForeground(c);
		tf_cc.setForeground(c);
	}
	
	private void textFieldTransferFocus(JTextField tf, int numChars) {
		if (tf.getText().length() >= numChars)
			tf.transferFocus();
		execValidation();
	}
	
	private void updateAccountNumber() {
		aNumber.update(	tf_entidad.getText(), tf_oficina.getText(), 
						tf_dc.getText(), tf_cc.getText());
	}
}
