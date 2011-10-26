package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.Date;

public class Service implements Comparable<Service>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int commission;
	private String service;
	private String campaign;
	private Date date;
	
	public Service(String service, int commission) {
		this.service = service;
		this.commission = commission;
	}
	
	public Service(String service, String commission) {
		this.service = service;
		this.commission = Integer.parseInt(commission.trim());
	}

	public int getCommission() {
		return commission;
	}
	
	public void setCommission(int commission) {
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

	//ordering by commissions
	@Override
	public int compareTo(Service arg0) {
		return (new Integer(commission).compareTo(arg0.commission)) * -1;
	}
	
	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	@Override
	public String toString() {
		return ""+service+"\t"+ commission;
	}
}
