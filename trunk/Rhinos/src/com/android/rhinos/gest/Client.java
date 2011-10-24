package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Client implements Serializable, Comparable<Client> {

	private static final long serialVersionUID = 1L;
	
	private Id id;
	private String name;
	private String tlf_1;
	private String tlf_2;
	private String mail;
	private String address;
	private Date date;
	private int comm;
	
	private ArrayList<Service> services;
	
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
	
	public int getComm() {
		return comm;
	}
	
	public Date getDate() {
		return date;
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

	public void setComm(int comm) {
		this.comm = comm;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return name;
	}
}
