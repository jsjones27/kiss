package com.example.kiss;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "KissDB";
	private static final String TABLE_ITEM_NAME = "item";
	private static final String TABLE_INVENTORY_NAME = "inventory";
	private static final String TABLE_LIST_NAME = "list";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ITEM_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_ITEM_NAME + " ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, " +
				"category TEXT )";
		String CREATE_INVENTORY_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_INVENTORY_NAME + " ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"item_id INTEGER, " +
				"quantity TEXT )";
		String CREATE_LIST_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_LIST_NAME + " ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"item_id INTEGER, " +
				"quantity TEXT )";
		db.execSQL(CREATE_ITEM_TABLE);
		db.execSQL(CREATE_INVENTORY_TABLE);
		db.execSQL(CREATE_LIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_NAME);
		this.onCreate(db);
	}
}
