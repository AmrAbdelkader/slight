package com.masdar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.masdar.clientdao.UserManager;
import com.masdar.entities.userendpoint.model.User;
import com.masdar.util.FileUploader;
import com.masdar.util.MasdarUtil;


public class AuthActivity extends FragmentActivity {

	private boolean isResumed = false;
	private MenuItem settings;
	private UiLifecycleHelper uiHelper;
	private static final int SPLASH = 0;
	private static final int SELECTION = 1;
	private static final int SETTINGS = 2;
	private static final int FRAGMENT_COUNT = SETTINGS +1;
	private EditText displayName;
	private EditText mobNo;
	private ProfilePictureView profilePictureView;
	private Context authActivity = this;
	private String fbEmail;
	private String fbId;
	private String profPicBlobKey;
	private ProgressDialog progressDialog;
	
	
	private Context currentContext;
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		    
			@Override
		    public void call(Session session, SessionState state, Exception exception) {
		        onSessionStateChange(session, state, exception);
		    }
		};	
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		
		currentContext = this;
		uiHelper = new UiLifecycleHelper(this, callback);
		
	    uiHelper.onCreate(savedInstanceState);
		
	    displayName = (EditText) findViewById(R.id.displayName);
	    mobNo = (EditText) findViewById(R.id.mobNumber);
	    profilePictureView = (ProfilePictureView) findViewById(R.id.profilePic);
		profilePictureView.setCropped(true);
	    
	    FragmentManager fm = getSupportFragmentManager();
	    fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
	    fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
	    fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);
	    
	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();	
    	 
	    Button activateButton = (Button) findViewById(R.id.activateButton);
	    activateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String displayNameText = displayName.getText().toString(); 
				if(!displayNameText.isEmpty()){
					User newUser = new User();
					newUser.setEmail(fbEmail);
					newUser.setUserName(displayNameText);
					newUser.setFbId(Long.valueOf(fbId));
					newUser.setMobNo(mobNo.getText().toString());
					newUser.setJoinDate(getCurrentDate());
					newUser.setDeviceId(MasdarUtil.getDeviceId(getApplicationContext()));
					newUser.setGplusId(Long.valueOf(0));
					DeviceRegistrationTask regTask = new DeviceRegistrationTask();
					//regTask.setContext(authActivity);
					regTask.execute(newUser);
					
					MasdarUtil.saveKeyValueData(getResources().getString(R.string.user_email), fbEmail, (Activity)authActivity);
					
					//Amr_Abdelkader - used in DummyActivity
					MasdarUtil.saveKeyValueData("user_name_text", displayNameText, (Activity)authActivity);
					
//					Intent webViewIntent = new Intent(authActivity , MainActivity.class);
//					webViewIntent.putExtra("user_id", userId);
//					startActivity(webViewIntent);
				}else{
					Toast.makeText(getApplicationContext(), "You must enter a Display Name",Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	    //registering this device in Google Cloud Messaging Service
	    registerDevice();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    // only add the menu when the selection fragment is showing
	    if (fragments[SELECTION].isVisible()) {
	        //if (menu.size() == 0) {
	            settings = menu.add(R.string.logout);
	        //}
	        return true;
	    } else {
	        menu.clear();
	        settings = null;
	    }
	    return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.equals(settings)) {
	        showFragment(SETTINGS, true);
	        return true;
	    }
	    return false;
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	        showFragment(SELECTION, false);
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
	        showFragment(SPLASH, false);
	        //authButton.setReadPermissions(Arrays.asList("email"));
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	            // If the session state is open:
	            // Show the authenticated fragment
	        	showFragment(SELECTION, false);
	        	makeRequest(session);
	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(SPLASH, false);
	        }
	    }
	}
	
	private void makeRequest(final Session session){
		progressDialog.setMessage("Retrieving Facebook data ...");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if(session == Session.getActiveSession()){
					if(user != null){
						fbId = user.getId();
						if(user.getProperty("email") != null){
							fbEmail = user.getProperty("email").toString();
						}
						displayName.setText(user.getFirstName() +" "+ user.getLastName());
						profilePictureView.setProfileId(fbId);
						progressDialog.dismiss();
					}
				}
				if(response.getError() != null){
					//Error Handling
				}
			}
		});
		Bundle params = request.getParameters();
		params.putString("fields", "email,first_name,last_name,name");
		request.setParameters(params);
		request.executeAsync();
	}
	
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (int i = 0; i < fragments.length; i++) {
	        if (i == fragmentIndex) {
	            transaction.show(fragments[i]);
	        } else {
	            transaction.hide(fragments[i]);
	        }
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    transaction.commit();
	}
	
	private String getPrimaryEmail(){
		
		String primaryEmail = null;
		Account[] accounts = AccountManager.get(getApplicationContext()).getAccountsByType("com.google");
		if(accounts.length != 0){
			primaryEmail = accounts[0].name;
		}else{
			//handle if there is no primary email account
		}
		return primaryEmail;
	}
	
	private List<String> getDeviceAccounts(){
		ArrayList<String> emails = new ArrayList<String>();
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        emails.add(account.name);
		    }
		}
		return emails;
	}
	
	private String getCurrentDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
		return simpleDateFormat.format(new Date());
	}
	
	private void registerDevice() {
		//register this device in google cloud messaging service
		try {
		      GCMIntentService.register(getApplicationContext());
		      //MasdarUtil.showDialog("Device registered successfully!", getApplicationContext());
		      //Toast alert = Toast.makeText(getApplicationContext(), "Device registered successfully !",Toast.LENGTH_SHORT);
			  //alert.show();
		    } catch (Exception e) {
		      Log.e(RegisterActivity.class.getName(),
		          "Exception received when attempting to register for Google Cloud "
		              + "Messaging. Perhaps you need to set your virtual device's "
		              + " target to Google APIs? "
		              + "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
		              + " for more information.", e);
		      MasdarUtil.showDialog("There was a problem when attempting to register for "
		          + "Google Cloud Messaging. If you're running in the emulator, "
		          + "is the target of your virtual device set to 'Google APIs?' "
		          + "See the Android log for more details.",getApplicationContext());
		    }
	}	
	
	class DeviceRegistrationTask extends AsyncTask<User, Void, User>{

		@Override
		protected User doInBackground(final User... user) {
			try{
				final User newUser = user[0];
				ImageView fbImage = ((ImageView) profilePictureView.getChildAt(0));
				Bitmap bitmap = ((BitmapDrawable) fbImage.getDrawable()).getBitmap();
				String fileName = "prof-pic.jpg";
				String fileHeaderName = "image_file";
				MasdarUtil.saveBitmapImage(bitmap, getApplicationContext(), fileName);
				final FileUploader fileUploader = new FileUploader(currentContext ,fileName, fileHeaderName , 0);
				fileUploader.uploadFile(new Callable<String>() {
					
					@Override
					public String call() throws Exception {
						Log.i("Uploading-File" , "Uplaoding file completed");
						String responseData = fileUploader.getBlobKey();
						if(!responseData.isEmpty()){
							//profPicBlobKey = responseData;
							newUser.setProfilePic(responseData);
							User createdUser = UserManager.createNewUser(newUser);
							if(createdUser != null){
								Log.d("user creation", "user created successfully!");
								MasdarUtil.saveKeyValueData(getString(R.string.user_server_id), createdUser.getUserId().toString(), (Activity)authActivity);
								
								progressDialog.dismiss();
								
								Intent webViewIntent = new Intent(getApplicationContext() , MainActivity.class);
								webViewIntent.putExtra("user_id", createdUser.getUserId().toString());
								startActivity(webViewIntent);
								finish();
							}else{
								Log.e("user creation", "something goes wrong while creating user !!");
							}
						}
						return null;
					}
				});
			}catch(Exception exc){
				Log.d("DeviceRegistrationTask_doInBackground_Exception-message", exc.getMessage());
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Please Wait ...");
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(User result) {
			//progressDialog.dismiss();
		}

		
	}
}
