package connectors;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.rhinos.DatabaseHelper;
import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.android.rhinos.gest.Service;

public class DBConnector implements Connector {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	
	public DBConnector(Context context) {
		dbHelper = new DatabaseHelper(context);
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
		
		Cursor c = db.query("Campaigns NATURAL JOIN CampInfo", null, null, null, null, null, null);
		
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
		
		ContentValues cv = new ContentValues();
		cv.put("_id", c.getId().toString());
		cv.put("_idType", c.getId().getType());
		cv.put("name", c.getName());
		cv.put("tlf_1", c.getTlf_1());
		cv.put("tlf_2", c.getTlf_2());
		cv.put("mail", c.getMail());
		cv.put("address", c.getAddress());
		
		db.insert("Clients", "name", cv);
		db.close();
		
		return true;
	}

	public ArrayList<Client> getClients() {
		ArrayList<Client> tr = new ArrayList<Client>();
		
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query("Clients", null, null, null, null, null, null);
		
		if (c.moveToFirst()) {
			
			for (int i = 0; i < c.getCount(); i++) {
				Client cl = new Client();
				
				switch (c.getInt(1)) {
					case Id.DNI: cl.setId(new Dni(c.getString(0))); break;
					case Id.NIE: cl.setId(new Nie(c.getString(0))); break;
					case Id.CIF: cl.setId(new Dni(c.getString(0))); break;
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
		
		Cursor c = db.query("Clients", null, "_id='"+_id+"'", null, null, null, null);
		
		if (c.moveToFirst()) {
			Client client = new Client();
			client.setName(c.getString(2));
			client.setTlf_1(c.getString(3));
			client.setTlf_2(c.getString(4));
			client.setMail(c.getString(5));
			client.setAddress(c.getString(6));
			c.close();
			return client;
		}
		
		c.close();
		return null;
	}
	
	private void activateFlags() {
		db.execSQL("PRAGMA FOREIGN_KEYS=ON");
	}
}
