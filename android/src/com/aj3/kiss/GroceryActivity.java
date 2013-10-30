package com.aj3.kiss;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aj3.kiss.R;

public class GroceryActivity extends ItemListActivity {
	public static final String NAME = "grocery";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery);
	}

	@Override
	protected void onResume() {
		super.onResume();
		DatabaseHelper db = new DatabaseHelper(this);
		List<ListItem> listItems = db.getGrocery();
		db.close();
		
		this.displayList(listItems);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grocery, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_add_item:
				Intent intent = new Intent(this, AddItemActivity.class);
				intent.putExtra(AddItemActivity.ACTIVITY_CALLER, this.NAME);
				startActivityForResult(intent, 0);
				return true;
			case R.id.action_settings:
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void deleteItem(ListItem li) {
		DatabaseHelper db = new DatabaseHelper(this);
		db.deleteGroceryItem(li);
		db.close();
		this.onResume();
		
	}
	
	protected void moveItem(ListItem li) {
		DatabaseHelper db = new DatabaseHelper(this);
		db.addInventoryItem(li);
		db.close();
		this.onResume();
	}

	@Override
	protected void showMoveDialog(final ListItem listItem) {
        // Create an instance of the dialog fragment and show it
    	new AlertDialog.Builder(this)
        .setTitle("Move to Inventory")
        .setMessage("Are you sure you want to move this item to Inventory List?")
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
