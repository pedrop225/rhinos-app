package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Client implements Serializable, Comparable<Client> {

	private static final long serialVersionUID = 1L;
	
	private Id id;
	private Date bdate;
	private String name;
	private String tlf_1;
	private String tlf_2;
	private String mail;
	
	//address
	private String dir_tipo_via;
	private String dir_nombre_via;
	private String dir_numero;
	private String dir_portal;
	private String dir_escalera;
	private String dir_piso;
	private String dir_puerta;
	private String dir_poblacion;
	private String dir_municipio;
	private String dir_cp;
	
	private int consultancy;
	
	private ArrayList<Service> services;
	
	public Client() {
		services = new ArrayList<Service>();
		consultancy = -1;
	}
	
	public Id getId() {
		return id;
	}
	
	public Date getBDate() {
		return bdate;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Service> getServices() {
		return services;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
	public void setBDate(Date bdate) {
		this.bdate = bdate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setServices(ArrayList<Service> services) {
		this.services = new ArrayList<Service>(services);
	}

	public String getTlf_1() {
		return tlf_1;
	}

	public void setTlf_1(String tlf_1) {
		this.tlf_1 = tlf_1;
	}

	public String getTlf_2() {
		return tlf_2;
	}

	public void setTlf_2(String tlf_2) {
		this.tlf_2 = tlf_2;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getConsultancy() {
		return consultancy;
	}
	
	public void setConsultancy(int consultancy) {
		this.consultancy = consultancy;
	}
	
	@Override
	public int compareTo(Client another) {
		return id.compareTo(another.id);
	}
	
	@Override
	public boolean equals(Object o) {
		Client u;
		try {
			u = (Client)o;
		}
		catch (ClassCastException e){ return false;}
		
		return compareTo(u) == 0;
	}
	
	public void clear() {
		id = null;
		name = null;
		tlf_1 = null;
		tlf_2 = null;
		mail = null;
	}
	
	public String getDirTipoVia() {
		return dir_tipo_via;
	}

	public String getDirNombreVia() {
		return dir_nombre_via;
	}

	public String getDirNumero() {
		return dir_numero;
	}

	public String getDirPortal() {
		return dir_portal;
	}

	public String getDirEscalera() {
		return dir_escalera;
	}

	public String getDirPiso() {
		return dir_piso;
	}

	public String getDirPuerta() {
		return dir_puerta;
	}

	public String getDirPoblacion() {
		return dir_poblacion;
	}

	public String getDirMunicipio() {
		return dir_municipio;
	}

	public String getDirCp() {
		return dir_cp;
	}

	public void setDirTipoVia(String dir_tipo_via) {
		this.dir_tipo_via = dir_tipo_via;
	}

	public void setDirNombreVia(String dir_nombre_via) {
		this.dir_nombre_via = dir_nombre_via;
	}

	public void setDirNumero(String dir_numero) {
		this.dir_numero = dir_numero;
	}

	public void setDirPortal(String dir_portal) {
		this.dir_portal = dir_portal;
	}

	public void setDirEscalera(String dir_escalera) {
		this.dir_escalera = dir_escalera;
	}

	public void setDirPiso(String dir_piso) {
		this.dir_piso = dir_piso;
	}

	public void setDirPuerta(String dir_puerta) {
		this.dir_puerta = dir_puerta;
	}

	public void setDirPoblacion(String dir_poblacion) {
		this.dir_poblacion = dir_poblacion;
	}

	public void setDirMunicipio(String dir_municipio) {
		this.dir_municipio = dir_municipio;
	}

	public void setDirCp(String dir_cp) {
		this.dir_cp = dir_cp;
	}

	@Override
	public String toString() {
		return name;
	}
}
