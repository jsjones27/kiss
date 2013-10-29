package com.aj3.kiss;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aj3.kiss.R;
import java.util.List;

public class InventoryActivity extends ItemListActivity {
	public static final String NAME = "inventory";

//	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		DatabaseHelper db = new DatabaseHelper(this);
		List<ListItem> listItems = db.getInventory();
		db.close();
		
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
		switch (item.getItemId()) {
			case R.id.action_add_item:
				Intent intent = new Intent(this, AddItemActivity.class);
				intent.putExtra(AddItemActivity.ACTIVITY_CALLER, this.NAME);
				startActivity(intent);
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
		db.deleteInventoryItem(li);
		db.close();
		this.onResume();
		
	}
}
