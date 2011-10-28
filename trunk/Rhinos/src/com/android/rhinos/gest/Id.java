package com.android.rhinos.gest;

import java.io.Serializable;

public abstract class Id implements Serializable, Comparable<Id> {
	
	protected static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";
	private static final long serialVersionUID = 1L;
	
	public static final int DNI = 0;
	public static final int NIE = 1;
	public static final int CIF = 2;
	
	public static final String [] TYPES = {"DNI", "NIE", "CIF"};
	protected String id;
	
	public Id(String id) {
		this.id = id;
	}
	
	public int getType() {
		return 0;
	}
	
	public boolean isValid() {
		return (test1() && test2());
	};
	
	protected boolean test1(){
		return true;
	}
	
	protected boolean test2() {
		return true;
	}
	
	@Override
	public int compareTo(Id another) {
		return (id.compareTo(another.id));
	}
	
	@Override
	public String toString() {
		return id;
	}
}
