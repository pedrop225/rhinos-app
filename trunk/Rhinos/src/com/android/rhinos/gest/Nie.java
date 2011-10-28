package com.android.rhinos.gest;

public class Nie extends Id {

	private static final long serialVersionUID = 1L;

	public Nie(String id) {
		super(id);
	}
	
	@Override
	public int getType() {
		return Id.NIE;
	}
	
	@Override
	protected boolean test1() {
		return id.matches("X[0-9]{7}[A-Z]");
	}
	
	@Override
	protected boolean test2() {
		try {
			int pos = Integer.parseInt(id.substring(1, 8)) % 23;
			return (NIF_STRING_ASOCIATION.charAt(pos) == id.charAt(8));
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
