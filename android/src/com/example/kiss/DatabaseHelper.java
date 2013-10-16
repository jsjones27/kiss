package com.example.kiss;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;


public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "KissDB";
	private static final String TABLE_ITEM = "item";
	private static final String TABLE_INVENTORY = "inventory";
	private static final String TABLE_LIST = "list";
	private static final String KEY_ID = "id";
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_QUANTITY = "quantity";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ITEM_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_ITEM + " ( " +
				KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT, " +
				KEY_CATEGORY + " TEXT )";
		String CREATE_INVENTORY_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_INVENTORY + " ( " +
				KEY_ITEM_ID + " INTEGER, " +
				KEY_QUANTITY + " TEXT )";
		String CREATE_LIST_TABLE = "CREATE IF NOT EXISTS TABLE " + TABLE_LIST + " ( " +
				KEY_ITEM_ID + " INTEGER, " +
				KEY_QUANTITY + " TEXT )";
		db.execSQL(CREATE_ITEM_TABLE);
		db.execSQL(CREATE_INVENTORY_TABLE);
		db.execSQL(CREATE_LIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
		this.onCreate(db);
	}
	
	public void addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_CATEGORY, item.getCategory());
		
		db.insert(TABLE_ITEM, null, values);
	}
	
	public void addItemToInventory(Item item, int quantity) {
		addItemToTable(TABLE_INVENTORY, item, quantity);
	}
	
	public void addItemToList(Item item, int quantity) {
		addItemToTable(TABLE_LIST, item, quantity);
	}
	
	private void addItemToTable(String table_name, Item item, int quantity) {
		/*if (table_name != TABLE_INVENTORY && table_name != TABLE_LIST) {
			throw new Exception("Invalid table name");
		}*/
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		if (item.getId() == Item.NO_ID) {
			addItem(item);
		}
		
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_ID, item.getId());
		values.put(KEY_QUANTITY, quantity);	
		
		db.insert(table_name, null, values);
	}
}
