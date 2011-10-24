package com.android.rhinos.gest;


public class Dni extends Id {

	private static final long serialVersionUID = 1L;

	public Dni(String id) {
		super(id);
	}
	
	@Override
	protected boolean test1() {
		return id.matches("[0-9]{8}[a-zA-Z]");
	}
	
	@Override
	protected boolean test2() {
		try {
			int pos = Integer.parseInt(id.substring(0, 8)) % 23;
			return (NIF_STRING_ASOCIATION.charAt(pos) == id.charAt(8));
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
