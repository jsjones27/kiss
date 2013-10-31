package com.aj3.kiss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.aj3.kiss.R;

public class AddItemActivity extends Activity {
	public final static String ACTIVITY_CALLER = "activityCaller";

	// Values for email and password at the time of the login attempt.
	private String mName;
//	private String mCategory;
//	private int mQuantity;

	// UI references.
	private EditText mNameView;
	private EditText mCategoryView;
	private EditText mQuantityView;
//	private View mAddItemFormView;
//	private View mAddItemStatusView;
//	private TextView mAddItemStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_item);

		mNameView = (EditText) findViewById(R.id.name);
		mNameView.setText(mName);

		mCategoryView = (EditText) findViewById(R.id.category);
		
		mQuantityView = (EditText) findViewById(R.id.quantity);

//		mAddItemFormView = findViewById(R.id.login_form);
//		mAddItemStatusView = findViewById(R.id.login_status);
//		mAddItemStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.comfirm_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addItem();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}
	
	public void addItem() {
		if(checkIfValid()) {
			Intent intent = getIntent();
			String callSource = intent.getStringExtra(this.ACTIVITY_CALLER);
			if(callSource.equals(InventoryActivity.NAME)){
//				Toast.makeText(getApplicationContext(), "Adding Item to " + callSource, Toast.LENGTH_LONG).show();
				this.addItemToInventory();
			}else if(callSource.equals(GroceryActivity.NAME)) {
//				Toast.makeText(getApplicationContext(), "Adding Item to " + callSource, Toast.LENGTH_LONG).show();
				this.addItemToGrocery();
			}
			this.finish();
		} else {
			
		}
		
	}
	
	public boolean checkIfValid(){
		if(mNameView.getText().toString().trim().isEmpty() || 
				mCategoryView.getText().toString().trim().isEmpty() || 
				mQuantityView.getText().toString().trim().isEmpty()) {
			new AlertDialog.Builder(this)
	        .setTitle("Blank Fields")
	        .setMessage("You cannot leave fields blank")
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            }
	         })
	         .show();
//			Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_LONG).show();
			return false;
		} else {
//			Toast.makeText(getApplicationContext(), "True" + mNameView.toString(), Toast.LENGTH_LONG).show();
			return true;
		}
	}

	public void addItemToInventory() {
		Item item = new Item();
		item.setName(mNameView.getText().toString());
		item.setCategory(mCategoryView.getText().toString());
		
		ListItem listItem = new ListItem();
		listItem.setItem(item);
		listItem.setQuantity(Double.valueOf(mQuantityView.getText().toString()));
		
		DatabaseHelper db = new DatabaseHelper(this);
		db.addInventoryItem(listItem);
		db.close();
		
//		Toast.makeText(getApplicationContext(), "Received " + item.getName(), Toast.LENGTH_LONG).show();
	}
	
	public void addItemToGrocery() {
		Item item = new Item();
		item.setName(mNameView.getText().toString());
		item.setCategory(mCategoryView.getText().toString());
		
		ListItem listItem = new ListItem();
		listItem.setItem(item);
		listItem.setQuantity(Double.valueOf(mQuantityView.getText().toString()));
		
		DatabaseHelper db = new DatabaseHelper(this);
		db.addGroceryItem(listItem);
		db.close();
		
//		Toast.makeText(getApplicationContext(), "Received " + item.getName(), Toast.LENGTH_LONG).show();
	}
	
	private class GetItemFromUpc extends AsyncTask <String, Void, String> {
		// to use: new GetItemFromUpc().execute(UPC_GOES_HERE);
		
		@Override
		protected String doInBackground(String... upc) {
			try {
				URL url = new URL("http://www.searchupc.com/handlers/upcsearch.ashx?request_type=1&access_token=E3EEF9D9-77FA-4362-BA41-12723A8048B0&upc=" + upc[0]);
				URLConnection conn = url.openConnection();
				InputStreamReader isr = new InputStreamReader(conn.getInputStream());
				BufferedReader rd = new BufferedReader(isr);
				rd.readLine();
				return rd.readLine().split(",")[0];
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		protected void onPostExecute(String itemName) {
			return;
		}
	}
}
