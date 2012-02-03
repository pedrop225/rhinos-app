package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable, Comparable<Client> {

	private static final long serialVersionUID = 1L;
	
	private Id id;
	private String name;
	private String tlf_1;
	private String tlf_2;
	private String mail;
	private String address;
	
	private ArrayList<Service> services;
	
	public Client() {
		services = new ArrayList<Service>();
	}
	
	public Id getId() {
		return id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	@Override
	public String toString() {
		return name;
	}
}
