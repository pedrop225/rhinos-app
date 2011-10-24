package com.android.rhinos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME 	= "DBRhinos.db";
	private static final int DB_VERSION 	= 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//activating foreign keys
		db.execSQL("PRAGMA FOREIGN_KEYS=ON");
		
		//Creating Campaigns table
		db.execSQL("CREATE TABLE Campaigns (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"name TEXT NOT NULL UNIQUE)");
		
		//Creating CampaignsInfo table
		db.execSQL("CREATE TABLE CampInfo (" +
						"_id INTEGER NOT NULL, " +
						"service TEXT NOT NULL, " +
						"commission INTEGER NOT NULL, " +
						"PRIMARY KEY (_id, service), " +
						"FOREIGN KEY (_id) REFERENCES Campaigns(_id) ON DELETE CASCADE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE ON CASCADE Campaigns;");
		onCreate(db);
	}

}
