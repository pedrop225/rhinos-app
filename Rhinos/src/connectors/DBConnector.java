package connectors;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.rhinos.DatabaseHelper;
import com.android.rhinos.gest.Campaign;
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
	
	public boolean insertCampaign(Campaign camp) {
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
	
	private void activateFlags() {
		db.execSQL("PRAGMA FOREIGN_KEYS=ON");
	}
}
