package com.aj3.kiss.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aj3.kiss.R;
import com.aj3.kiss.R.id;
import com.aj3.kiss.R.layout;
import com.aj3.kiss.R.menu;
import com.aj3.kiss.helpers.DatabaseHelper;
import com.aj3.kiss.models.Item;
import com.aj3.kiss.models.ListItem;

import java.util.List;

public class InventoryActivity extends ItemListActivity {
	public static final String NAME = "inventory";

//	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		DatabaseHelper db = new DatabaseHelper(this);
		List<ListItem> listItems = db.getInventory();
		db.close();
		
		// Check all items in inventory to see if they should be added to grocery list.
		for (ListItem li : listItems) {
			if (li.getQuantity() < Item.THRESHOLD_QUANTITY) {
				ListItem l = new ListItem(li.getItem(), Item.INITIAL_QUANTITY);
				db.addGroceryItem(l);
			}
		}
		
		this.displayList(listItems);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		Intent intent;
		switch (item.getItemId()) {
			case R.id.action_add_item:
				intent = new Intent(this, AddItemActivity.class);
				intent.putExtra(AddItemActivity.ACTIVITY_CALLER, InventoryActivity.NAME);
				startActivity(intent);
				return true;
			case R.id.action_settings:
				return true;
			case android.R.id.home:
				intent = new Intent(this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void deleteItem(ListItem li) {
		DatabaseHelper db = new DatabaseHelper(this);
		db.deleteInventoryItem(li);
		db.close();
		this.onResume();
		
	}
	
	/**
	 * Moves a list Item to the GroceryList
	 * @param li
	 */
	protected void moveItem(ListItem li) {
		DatabaseHelper db = new DatabaseHelper(this);
		db.addGroceryItem(li);
		db.close();
		this.onResume();
	}

	@Override
	protected void showMoveDialog(final ListItem listItem) {
		// Create an instance of the dialog fragment and show it
		new AlertDialog.Builder(this)
		.setTitle("Move to Grocery")
		.setMessage("Are you sure you want to move this item to Grocery List?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				moveItem(listItem);
				deleteItem(listItem);
			}
		 })
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing
			}
		 })
		 .show();
		
	}
}
