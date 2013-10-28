package com.aj3.kiss;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.aj3.kiss.R;

public class GroceryActivity extends Activity {
	
	 ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery);
		
		// Get ListView object from xml
		 listView = (ListView) findViewById(R.id.list);
		
		// Defined Array values to show in ListView
		String[] values = new String[] { "Apples", 
										 "Bannas",
										 "Carrots",
										 "Donuts",
										 "Eggs"
										 };

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  android.R.layout.simple_list_item_1, android.R.id.text1, values);
	   

		// Assign adapter to ListView
		listView.setAdapter(adapter); 
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
				startActivity(intent);
				return true;
			case R.id.action_settings:
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
