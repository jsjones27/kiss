package com.aj3.kiss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	private EditText mScanResult;
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_scan_item:
				scanItem();
				return true;
			case R.id.action_settings:
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	//Scans the barcode and returns the product
	public void scanItem(){
		try {
			
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();

			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "ERROR:" + e, Toast.LENGTH_LONG).show();
		}
	}		 

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  mScanResult = (EditText) findViewById(R.id.scan_result_message);

		  if (scanResult != null) {
			  String barcode;
			  barcode= scanResult.getContents();
			  mScanResult.setText(barcode);
		  }
		  else
		  {
		  mScanResult.setText("Error");
		}
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
}
