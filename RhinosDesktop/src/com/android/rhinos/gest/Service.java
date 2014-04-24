
package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.Date;

public class Service implements Comparable<Service>, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int PENDING = 0;
	public static final int VERIFIED = 1;
	public static final int CANCELLED = 2;
	public static final int RETURNED = 3;
	
	public static final String[] STATES = {"Pendiente", "Verificado", "Anulado", "Devuelto"};
	
	private int extId;
	private double commission;
	private String service;
	private String campaign;
	private Date date;
	private Date expiryDate;
	private int state;
	private String notes;
	
	private String titular;
	private Id id;
	
	public Service() {
		commission = 0;
		service =  "";
		campaign = "";
		date = new Date();
		expiryDate = new Date();
		state = PENDING;
		notes = "";
	}

	public Service(String service, double commission) {
		this();
		this.service = service;
		this.commission = commission;
	}
	
	public int getExtId() {
		return extId;
	}

	public void setExtId(int extId) {
		this.extId = extId;
	}
	
	public Service(String service, String commission) {
		this.service = service;
		this.commission = Integer.parseInt(commission.trim());
	}

	public double getCommission() {
		return commission;
	}
	
	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiry) {
		this.expiryDate = expiry;
	}

	@Override
	public int compareTo(Service arg0) {
		return (new Double(commission).compareTo(arg0.commission)) * -1;
	}
	
	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	@Override
	public String toString() {
		return service;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}	
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
