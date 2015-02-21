package com.android.rhinos.gest;

import java.util.Date;

public class RhFile {

	private int id;
	private int idService;
	private String name;
	private Date date;
	
	public RhFile() {
		name = "";
		date = new Date();
	}

	public int getId() {
		return id;
	}

	public int getIdService() {
		return idService;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdService(int idService) {
		this.idService = idService;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}