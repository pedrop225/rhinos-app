package connectors;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.rhinos.DatabaseHelper;
import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Cif;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public class DBConnector implements Connector {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	
	public DBConnector(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public boolean login(String user, String password) {
		return true;
	}
	
	//campaigns table
	public void clearCampaigns() {
		db = dbHelper.getWritableDatabase();
		activateFlags();
		
		db.execSQL("DELETE FROM Campaigns");
		
		//closing database;
		db.close();
	}
	
	public boolean addCampaign(Campaign camp) {
		try {
			db = dbHelper.getWritableDatabase();
			activateFlags();
			
			//inserting into campaigns
			ContentValues cv = new ContentValues();
			cv.put("name", camp.getName());
			
			db.insert("Campaigns", "name", cv);
			
			Cursor c = db.query("Campaigns", 
								new String[]{"_id"}, 
								"name= '"+camp.getName()+"'", 
								null, null, null, null);
			
			c.moveToFirst();
			int c_id = c.getInt(0);
			c.close();
		
			//inserting into campinfo
			for (Service s : camp.getServices().values()) {
				cv.clear();
				cv.put("_id", c_id);
				cv.put("service", s.getService());
				cv.put("commission", s.getCommission());
				
				db.insert("CampInfo", "service", cv);
			}
			
			//closing database
			db.close();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<Campaign> getCampaigns() {
		ArrayList<Campaign> r = new ArrayList<Campaign>();
		db = dbHelper.getReadableDatabase();
		
		Cursor c = db.query("Campaigns NATURAL JOIN CampInfo", null, null, null, null, null, "name");
		
		if (c.moveToFirst()) {
			
			String name = c.getString(1);
			String old = name;
			Campaign camp = new Campaign(name);

			for (int i = 0; i < c.getCount(); i++) {
				
				if (!(name = c.getString(1)).equals(old)) {
					r.add(camp);
					old = name;
					camp = new Campaign(name);
				}
				
				camp.addService(c.getString(2), new Service(c.getString(2), c.getString(3)));
				c.moveToNext();
			}
			r.add(camp);
		}
		
		//closing database
		c.close();
		db.close();
		
		return r;
	}
	
	public boolean addClient(Client c) {
		db = dbHelper.getWritableDatabase();
		activateFlags();
		boolean tr = true;
		
		ContentValues cv = new ContentValues();
		cv.put("_id", c.getId().toString());
		cv.put("_idType", c.getId().getType());
		cv.put("name", c.getName());
		cv.put("tlf_1", c.getTlf_1());
		cv.put("tlf_2", c.getTlf_2());
		cv.put("mail", c.getMail());
		cv.put("address", c.getAddress());
		
		try {
			db.insert("Clients", "name", cv);
		}
		catch (SQLException e) {
			tr = false;
		}
		finally {
			db.close();
		}
		
		return tr;
	}

	public ArrayList<Client> getClients() {
		ArrayList<Client> tr = new ArrayList<Client>();
		db = dbHelper.getReadableDatabase();
				
		Cursor c = db.query("Clients", null, null, null, null, null, "name");
		
		if (c.moveToFirst()) {
			
			for (int i = 0; i < c.getCount(); i++) {
				Client cl = new Client();
				
				switch (c.getInt(1)) {
					case Id.DNI: cl.setId(new Dni(c.getString(0))); break;
					case Id.NIE: cl.setId(new Nie(c.getString(0))); break;
					case Id.CIF: cl.setId(new Cif(c.getString(0))); break;
				}
				cl.setName(c.getString(2));
				cl.setTlf_1(c.getString(3));
				cl.setTlf_2(c.getString(4));
				cl.setMail(c.getString(5));
				cl.setAddress(c.getString(6));
				
				c.moveToNext();
				tr.add(cl);
			}
		}
		
		//closing database
		c.close();
		db.close();
		
		return tr;
	}
	
	@Override
	public ArrayList<Client> getCampaignClients(Campaign campaign) {
		ArrayList<Client> tr = new ArrayList<Client>();
		db = dbHelper.getReadableDatabase();
		
		String [] cols = new String[] {"clients._id", 
										"_idType", 
										"name", 
										"clients.tlf_1", 
										"clients.tlf_2", 
										"mail",
										"address"};
		
		String sel = "(clients._id = _idClient) and (campaign = '"+campaign.getName()+"')";
		
		Cursor c = db.query(true, "Clients,Services", cols , sel, null, null, null, "name", null);
		
		if (c.moveToFirst()) {
			
			for (int i = 0; i < c.getCount(); i++) {
				Client cl = new Client();
				
				switch (c.getInt(1)) {
					case Id.DNI: cl.setId(new Dni(c.getString(0))); break;
					case Id.NIE: cl.setId(new Nie(c.getString(0))); break;
					case Id.CIF: cl.setId(new Cif(c.getString(0))); break;
				}
				cl.setName(c.getString(2));
				cl.setTlf_1(c.getString(3));
				cl.setTlf_2(c.getString(4));
				cl.setMail(c.getString(5));
				cl.setAddress(c.getString(6));
				
				c.moveToNext();
				tr.add(cl);
			}
		}
		
		//closing database
		c.close();
		db.close();
		
		return tr;
	}
	
	@Override
	public Client clientExists(String _id) {
		db = dbHelper.getReadableDatabase();
		
		Client client = null;
		Cursor c = db.query("Clients", null, "_id='"+_id+"'", null, null, null, null);
		
		if (c.moveToFirst()) {
			client = new Client();
			client.setName(c.getString(2));
			client.setTlf_1(c.getString(3));
			client.setTlf_2(c.getString(4));
			client.setMail(c.getString(5));
			client.setAddress(c.getString(6));
		}
		
		c.close();
		db.close();
		return client;
	}
	
	@Override
	public boolean addService(Service s, Client c) {
		db = dbHelper.getWritableDatabase();
		activateFlags();
		boolean tr = true;
		
		ContentValues cv = new ContentValues();
		cv.put("_idClient", c.getId().toString());
		cv.put("service", s.getService());
		cv.put("campaign", s.getCampaign());
		cv.put("tlf_1", s.getTlf_1());
		cv.put("tlf_2", s.getTlf_2());
		cv.put("commission", s.getCommission());
		cv.put("date", s.getDate().toGMTString());
		
		try {
			db.insert("Services", "service", cv);
		}
		catch (SQLException e) {
			tr = false;
		}
		finally {
			db.close();
		}
		
		return tr;
	}
	
	public ArrayList<Service> getServices(String _id) {
		db = dbHelper.getReadableDatabase();
		ArrayList<Service> tr = new ArrayList<Service>();
		
		Cursor c = db.query("Services", null, "_idClient='"+_id+"'", null, null, null, "commission");
		
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				Service s = new Service(c.getString(2), c.getInt(6));
				s.setCampaign(c.getString(3));
				s.setDate(new Date(c.getString(7)));
				s.setTlf_1(c.getString(4));
				s.setTlf_2(c.getString(5));
				
				tr.add(s);
				c.moveToNext();
			}
		}
		
		c.close();
		db.close();
		return tr;
	}
	
	@Override
	public int getSumCommissions(Client client) {
		db = dbHelper.getReadableDatabase();
		
		Cursor c = db.query("Services", new String[] {"sum(commission)"}, "_idClient='"+client.getId()+"'", null, null, null, null);
		
		int res = 0;
		if (c.moveToFirst()) {
			res = c.getInt(0);
		}
		
		c.close();
		db.close();
		return res;
	}
	
	@Override
	public void deleteService(Service service) {
		db = dbHelper.getWritableDatabase();
		
		db.delete("Services", "(date='"+service.getDate().toGMTString()+"' AND service='"+service.getService()+"')", null);
		db.close();
	}
	
	@Override
	public void deleteClient(String id) {
		// TODO Auto-generated method stub
		
	}
	
	private void activateFlags() {
		db.execSQL("PRAGMA FOREIGN_KEYS=ON");
	}
	
	@Override
	public void changePassword(String user, String newpass) {}
	
	@Override
	public boolean createAccount(User u, String pass) {
		return false;
	}
	
	@Override
	public String getCurrentVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
