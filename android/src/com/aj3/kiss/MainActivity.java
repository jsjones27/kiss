package com.aj3.kiss;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import com.aj3.kiss.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/* testing code */
		/*DatabaseHelper db = new DatabaseHelper(this);
		db.resetDatabase();
		db.addItem(new Item("C", "A"));
		db.addItem(new Item("B", "B"));
		db.addInventoryItem(new ListItem(db.getItem(1), 1));
		db.addInventoryItem(new ListItem(db.getItem(2), 1));
		db.deleteInventoryItem(new ListItem(db.getItem(1), 1));
		db.updateInventoryItem(new ListItem(db.getItem(2), 2));
		Vector<ListItem> inventory = db.getInventory();
		for (ListItem listItem : inventory) {
			System.out.println(listItem.getItem().getName());
			System.out.println(listItem.getQuantity());
		}
		db.close();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void jumpToInventory(View view){
		Intent intent = new Intent(this, InventoryActivity.class);
		startActivity(intent);
	}
	
	public void jumpToGrocery(View view){
		Intent intent = new Intent(this, GroceryActivity.class);
		startActivity(intent);
	}

}
