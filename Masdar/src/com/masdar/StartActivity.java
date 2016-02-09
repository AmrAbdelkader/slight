package com.masdar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.masdar.clientdao.UserManager;
import com.masdar.entities.userendpoint.model.User;
import com.masdar.util.MasdarUtil;

public class StartActivity extends Activity {

	private boolean connectionStatus = false;
	private NetworkReceiver networkReceiver ;
	private String userId;
	private ProgressDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		loadingDialog = new ProgressDialog(this);
		
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		networkReceiver = new NetworkReceiver();
		this.registerReceiver(networkReceiver, intentFilter);
		
		userId = MasdarUtil.readValueByKey(getString(R.string.user_server_id), this);
		if(userId != null && !userId.isEmpty()){
			//AuthenticaticationTask authTask = new AuthenticaticationTask();
			//authTask.setContext(this);
			//authTask.execute(userId);

			//Amr_Abdelkader update 29/12/2013
			Intent webViewIntent = new Intent(this , MainActivity.class);
			webViewIntent.putExtra("user_id", userId);
			startActivity(webViewIntent);
			
    	}else{
    		startActivity(new Intent(this , AuthActivity.class));
    	}
		finish();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (networkReceiver != null) {
            this.unregisterReceiver(networkReceiver);
        }
	}

	class AuthenticaticationTask extends AsyncTask<String, Void, Boolean>{

		private Context ctx;
		private String userId;
		
		public void setContext(Context ctx){
			this.ctx = ctx;
		}
		
		@Override
		protected Boolean doInBackground(String... userId) {
			boolean isFound = false;
			
			//if(!MasdarUtil.isOnline(getApplicationContext()))
			if(!connectionStatus)
				return null;
			
			User user = UserManager.getUserById(Long.parseLong(userId[0]));
			if(user != null){
				isFound = true;
				this.userId = userId[0];
			}
			return isFound;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result != null){
				if(result){
					Intent mainIntent = new Intent(ctx , MainActivity.class);
					mainIntent.putExtra("user_id", userId);
					startActivity(mainIntent);
					finish();
				}
			}
			else{
				Toast.makeText(getApplicationContext(), "There is no Internet Connection.",Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	class NetworkReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context ctx, Intent intent) {
			ConnectivityManager conn =  (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = conn.getActiveNetworkInfo();
			if(networkInfo != null && networkInfo.isConnected()){
				connectionStatus = true;
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
				}
			}else{
				connectionStatus = false;
				loadingDialog.setTitle("No Internet Connection");
				loadingDialog.setMessage("Trying to connect ...");
				loadingDialog.show();
			}
		}
	}
}
