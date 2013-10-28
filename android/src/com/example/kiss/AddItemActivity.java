package com.example.kiss;

import com.google.zxing.client.android.Intents.Scan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String mName;
	private String mCategory;
	private int mQuantity;

	// UI references.
	private EditText mNameView;
	private EditText mCategoryView;
	private EditText mQuantityView;
	private View mAddItemFormView;
	private View mAddItemStatusView;
	private TextView mAddItemStatusMessageView;
	private TextView mScanItemLabel;
	private TextView mScanItemResultView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_item);

		mNameView = (EditText) findViewById(R.id.name);
		mNameView.setText(mName);

		mCategoryView = (EditText) findViewById(R.id.category);
		
		mQuantityView = (EditText) findViewById(R.id.quantity);
		
	/*	mCategoryView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
*/
		mAddItemFormView = findViewById(R.id.login_form);
		mAddItemStatusView = findViewById(R.id.login_status);
		mAddItemStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		
		mScanItemResultView = (TextView)findViewById(R.id.scan_result_message);

		findViewById(R.id.comfirm_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addItemToInventory();
					}
				});
		
		findViewById(R.id.scan_button).setOnClickListener(
				new View.OnClickListener(){
					@Override
					public void onClick(View view){
						scanItem();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
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
		
		Toast.makeText(getApplicationContext(), "Received " + item.getName(), Toast.LENGTH_LONG).show();
	}
	
	//Scans the barcode of the Items
	public void scanItem() {
		try
		{
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE","QR_CODE_MODE,PRODUCT_MODE");
			startActivityForResult(intent,0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Error:" + e,Toast.LENGTH_LONG).show();
		}
	}
	
	//Handle the result of the scanning
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         mScanItemResultView.setText(intent.getStringExtra("SCAN_RESULT"));
		         //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         
		      } else if (resultCode == RESULT_CANCELED) {
		         mScanItemResultView.setText("Scan Cancelled");
		      }
		   }
		}
}
