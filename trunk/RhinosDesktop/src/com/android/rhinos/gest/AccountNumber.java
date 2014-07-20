package com.android.rhinos.gest;

import java.math.BigInteger;
import java.text.DecimalFormat;

public class AccountNumber {

	private static final DecimalFormat DF_2 = new DecimalFormat("00");

	private String pais = "ES";
	private String iban = "00";
	private String entidad;
	private String oficina;
	private String dc;
	private String cc;
	
	public AccountNumber() {}
	
	public void update(String ent, String ofi, String dc, String cc) {
		entidad = ent;
		oficina = ofi;
		this.dc = dc;
		this.cc = cc;
	}
	
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getIban() {
		BigInteger bi = new BigInteger(entidad+oficina+dc+cc+"14"+"28"+"00"); 
		BigInteger aux = new BigInteger("98").subtract(bi.mod(new BigInteger("97")));
		return pais+DF_2.format(aux);
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad (String entidad) {
		this.entidad = (entidad.matches("[0-9]{4}")) ? entidad : "";
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = (oficina.matches("[0-9]{4}")) ? oficina : "";
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = (dc.matches("[0-9]{2}")) ? dc : "";
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = (cc.matches("[0-9]{10}")) ? cc : "";
	}

	public boolean validate() {
		if (!checkRegex())
			return false;
		
		if (!checkDc()){
			return false;
		}
		
		return true;
	}
	
	private boolean checkRegex() {
		String str = entidad +" "+ oficina +" "+ dc +" "+cc;
		return 	str.matches("[0-9]{4} [0-9]{4} [0-9]{2} [0-9]{10}");
	}
	
	private boolean checkDc() {
		
		String dc_1 = calculateDc("00"+entidad+oficina);
		String dc_2 = calculateDc(cc);
		
		return (dc_1+dc_2).equals(dc);
	}
	
	private String calculateDc(String cad) {
		final int PESOS[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
		int d = 0;
		for (int i = 0; i < cad.length(); i++)
			d += Integer.parseInt(cad.charAt(i)+"") * PESOS[i];
		
		d = 11 - (d % 11);
		
		if (d == 10) 
			d = 1;
		else if (d == 11) 
			d = 0;
		
		return d+"";
	}
	
	@Override
	public String toString() {
		return getIban()+" "+entidad+" "+oficina+" "+dc+" "+cc;
	}
}
