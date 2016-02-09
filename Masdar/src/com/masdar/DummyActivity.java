package com.masdar;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.masdar.util.MasdarUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DummyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dummy);

//		TextView textView = (TextView) findViewById(R.id.welcomeText);
//		String userDisplayName = MasdarUtil.readValueByKey("user_name_text", this);
//		textView.setText("Welcome "+userDisplayName+ " \n We will update you with our Beta version soon.");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy, menu);
		return true;
	}

}
