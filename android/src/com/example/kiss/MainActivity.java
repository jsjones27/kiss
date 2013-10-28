package com.example.kiss;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				URL url;
			      HttpURLConnection conn;
			      BufferedReader rd;
			      String line;
			      String result = "";
			      try {
			         url = new URL("http://www.searchupc.com/handlers/upcsearch.ashx?request_type=1&access_token=E3EEF9D9-77FA-4362-BA41-12723A8048B0&upc=9781477274156");
			         rd = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
			         
			         while ((line = rd.readLine()) != null) {
			             String[] RowData = line.split(",");
			             System.out.println(RowData[0]);
			            // do something with "data" and "value"
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		thread.start();*/
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
