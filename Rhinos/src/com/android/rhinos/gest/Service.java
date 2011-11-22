
package com.android.rhinos.gest;

import java.io.Serializable;
import java.util.Date;

public class Service implements Comparable<Service>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int extId;
	private int commission;
	private String service;
	private String campaign;
	private String tlf_1;
	private String tlf_2;
	private Date date;
	
	public Service() {
		commission = 0;
		service =  "";
		campaign = "";
		tlf_1 = "";
		tlf_2 = "";
		date = new Date();
	}

	public int getExtId() {
		return extId;
	}

	public void setExtId(int extId) {
		this.extId = extId;
	}

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

	@Override
	public String toString() {
		return service;
	}
}
