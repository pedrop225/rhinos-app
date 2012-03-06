package com.android.rhinos.gest;

public class Consultancy extends Client {
	
	private static final long serialVersionUID = 1L;

	private int extId;
	private String consultant;
	
	public Consultancy() {
		extId = -1;
	}
	
	public void setExtId(int extId) {
		this.extId = extId;
	}
	
	public int getExtId() {
		return extId;
	}
	
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	
	public String getConsultant() {
		return consultant;
	}
	
	public void setString(String consultant) {
		this.consultant = consultant;
	}
	
	public String toString() {
		return getName();
	}
}
