package com.android.rhinos.gest;

public class User extends Client {

	private static final long serialVersionUID = 1L;
	
	public static final String[] USER_TYPES = {"Usuario", "Super", "Admin"};
	
	private static final int DEFAULT = 0;
	private static final int SUPER = 1;
	private static final int ROOT = 2;
	
	public static final int ONLINE = 0;
	public static final int OFFLINE = 1;
		
	private String user;
	private int extId;
	private int type;
	private int connectionMode;
	
	//beneficio para el supervisor (%)
	private int p_profit;
	
	public User() {
		super();
		type = DEFAULT;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
	
	public void setParentProfit(int p_profit) {
		this.p_profit = p_profit;
	}
	
	public int getParentProfit() {
		return p_profit;
	}
	
	public void doRoot() {
		this.type = ROOT;
	}
	
	public boolean isRoot() {
		return (type == ROOT);
	}
	
	public boolean isSuper() {
		return (type == SUPER);
	}

	public int getConnectionMode() {
		return connectionMode;
	}

	public void setConnectionMode(int connectionMode) {
		this.connectionMode = connectionMode;
	}
	
	public boolean isOnline() {
		return (connectionMode == ONLINE);
	}
	
	public void clear() {
		super.clear();
		user = "";
		extId = -1;
		type = DEFAULT;
		connectionMode = -1;
	}
	
	@Override
	public int compareTo(Client another) {
		User u;
		try {
			u = (User)another;
		}
		catch (ClassCastException e){
			return -1;
		}
		return user.compareTo(u.user);
	}
	
	@Override
	public String toString() {
		return super.toString()+" (@"+user+")";
	}
}
