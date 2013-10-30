package com.example.kiss;

import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;


public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "KissDB";
	private static final String TABLE_ITEM = "item";
	private static final String TABLE_INVENTORY = "inventory";
	private static final String TABLE_GROCERY = "grocery";
	private static final String TABLE_CATEGORY = "category";
	private static final String KEY_ID = "id";
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_CATEGORY_ID = "category_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_QUANTITY = "quantity";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createItemTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEM + " ( " +
				KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT UNIQUE, " +
				KEY_CATEGORY_ID + " INTEGER )";
		String createInventoryTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_INVENTORY + " ( " +
				KEY_ITEM_ID + " INTEGER, " +
				KEY_QUANTITY + " REAL )";
		String createGroceryTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_GROCERY + " ( " +
				KEY_ITEM_ID + " INTEGER, " +
				KEY_QUANTITY + " REAL )";
		String createCategoryTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + " ( " +
				KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT UNIQUE )";
		db.execSQL(createItemTableQuery);
		db.execSQL(createInventoryTableQuery);
		db.execSQL(createGroceryTableQuery);
		db.execSQL(createCategoryTableQuery);
	}

	private void dropTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTables(db);
		this.onCreate(db);
	}
	
	public void resetDatabase() {
		SQLiteDatabase db = this.getWritableDatabase();
		dropTables(db);
		this.onCreate(db);
	}
	
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
	
	public int addCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, category.getName());
		
		return (int) db.insert(TABLE_CATEGORY, null, values);
	}
	
	public int addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_CATEGORY_ID, item.getCategory().getId());
		
		return (int) db.insert(TABLE_ITEM, null, values);
	}
	
	public void addInventoryItem(ListItem item) {
		addTableItem(TABLE_INVENTORY, item);
	}
	
	public void addGroceryItem(ListItem item) {
		addTableItem(TABLE_GROCERY, item);
	}
	
	private void addTableItem(String tableName, ListItem listItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_ID, listItem.getItem().getId());
		values.put(KEY_QUANTITY, listItem.getQuantity());	
		
		db.insert(tableName, null, values);
	}
	
	private Category getCategory(String query) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		
		cursor.moveToFirst();
		
		Category category = new Category();
		category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		category.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		return category;
	}
	
	private Category getCategoryById(int categoryId) {
		String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + KEY_ID + " = " + categoryId;
		return getCategory(query);
	}
	
	public Category getCategoryByName(String categoryName) {
		String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + KEY_NAME + " = '" + categoryName + "'";
		return getCategory(query);
	}
	
	private Item getItem(String query) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}

		cursor.moveToFirst();

		Item item = new Item();
		item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		item.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		item.setCategory(getCategoryById(cursor.getInt(cursor.getColumnIndex(KEY_CATEGORY_ID))));

		return item;
	}
	
	private Item getItemById(int itemId) {		
		String query = "SELECT * FROM " + TABLE_ITEM + " WHERE " + KEY_ID + " = " + itemId;
		return getItem(query);
	}
	
	public Item getItemByName(String itemName) {
		String query = "SELECT * FROM " + TABLE_ITEM + " WHERE " + KEY_NAME + " = '" + itemName + "'";
		return getItem(query);
	}
	
	public ListItem getGroceryItem(Item item) {
		return getListItem(TABLE_GROCERY, item);
	}
	
	public ListItem getInventoryItem(Item item) {
		return getListItem(TABLE_INVENTORY, item);
	}
	
	private ListItem getListItem(String tableName, Item item) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = "SELECT * FROM " + tableName + " WHERE " + KEY_ITEM_ID + " = " + item.getId();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}

		cursor.moveToFirst();

		ListItem listItem = new ListItem();
		listItem.setItem(getItemById(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID))));
		listItem.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)));

		return listItem;
	}
	
	public Vector<ListItem> getGrocery() {
		return getList(TABLE_GROCERY);
	}
	
	public Vector<ListItem> getInventory() {
		return getList(TABLE_INVENTORY);
	}
	
	private Vector<ListItem> getList(String tableName) {
		Vector<ListItem> list = new Vector<ListItem>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = "SELECT * FROM " + tableName;
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
			do {
				ListItem listItem = new ListItem();
				listItem.setItem(getItemById(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID))));
				listItem.setQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_QUANTITY)));
				
				list.add(listItem);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	
	public int updateItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_CATEGORY_ID, item.getCategory().getId());
		
		return db.update(TABLE_ITEM, values, KEY_ID + " = ?", new String[] { String.valueOf(item.getId()) });
	}
	
	public int updateGroceryItem(ListItem listItem) {
		return updateListItem(TABLE_GROCERY, listItem);
	}
	
	public int updateInventoryItem(ListItem listItem) {
		return updateListItem(TABLE_INVENTORY, listItem);
	}
	
	private int updateListItem(String tableName, ListItem listItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_QUANTITY, listItem.getQuantity());
		
		return db.update(tableName, values, KEY_ITEM_ID + " = ?", new String[] { String.valueOf(listItem.getItem().getId()) });
	}
	
	public void deleteGroceryItem(ListItem listItem) {
		deleteListItem(TABLE_GROCERY, listItem);
	}
	
	public void deleteInventoryItem(ListItem listItem) {
		deleteListItem(TABLE_INVENTORY, listItem);
	}
	
	private void deleteListItem(String tableName, ListItem listItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, KEY_ITEM_ID + " = ?", new String[] {String.valueOf(listItem.getItem().getId()) });
	}
}
