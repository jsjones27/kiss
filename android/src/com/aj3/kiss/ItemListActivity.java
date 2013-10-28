package com.aj3.kiss;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemListActivity extends Activity {
	public static final String NAME = "itemList";

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void displayList(List<ListItem> items) {

		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.list);

		List<String> values = new ArrayList<String>();
		for (ListItem li : items) {
			values.add(li.getItem().getName());
		}
		
		
		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  android.R.layout.simple_list_item_1, android.R.id.text1, values);
	   

		// Assign adapter to ListView
		listView.setAdapter(adapter); 
		/* 
		// ListView Item Click Listener
		listView.setOnClickListener(new OnClickListener() {

			  public void onItemClick(AdapterView<?> parent, View view,
				 int position, long id) {
				
			   // ListView Clicked item index
			   int itemPosition	 = position;
			   
			   // ListView Clicked item value
			   String  itemValue	= (String) listView.getItemAtPosition(position);
				  
				// Show Alert 
				Toast.makeText(getApplicationContext(),
				  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
				  .show();
			 
			  }

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}

		 }); 
		 */
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.inventory, menu);
//		return true;
//	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle presses on the action bar items
//		switch (item.getItemId()) {
//			case R.id.action_add_item:
//				Intent intent = new Intent(this, AddItemActivity.class);
//				intent.putExtra(AddItemActivity.ACTIVITY_CALLER, this.NAME);
//				startActivityForResult(intent, 0);
//				return true;
//			case R.id.action_settings:
//				
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
}
