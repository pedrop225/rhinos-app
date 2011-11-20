package com.android.rhinos.gest;

public class User extends Client {

	private static final int DEFAULT = 0;
	private static final int ROOT = 9177;
	
	private static final long serialVersionUID = 1L;
	
	private int extId;
	private int type;
	
	public User() {
		super();
		type = DEFAULT;
	}

	public int getExtId() {
		return extId;
	}

	public void setExtId(int extId) {
		this.extId = extId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isRoot() {
		return (type == ROOT);
	}
}
