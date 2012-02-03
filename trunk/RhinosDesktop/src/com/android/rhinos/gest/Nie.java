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
		return id.matches("[XYZ][0-9]{7}[A-Z]");
	}
	
	@Override
	protected boolean test2() {
		try {
			String temp = id;
			switch (id.charAt(0)) {
				case 'X' : temp = "0"+id.substring(1, 8); break;
				case 'Y' : temp = "1"+id.substring(1, 8); break;
				case 'Z' : temp = "2"+id.substring(1, 8);
			}
			int pos = Integer.parseInt(temp.substring(0, 8)) % 23;
			return (NIF_STRING_ASOCIATION.charAt(pos) == id.charAt(8));
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
