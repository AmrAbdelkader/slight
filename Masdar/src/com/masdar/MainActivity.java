package com.masdar;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.masdar.RegisterActivity.State;
import com.masdar.entities.ideaendpoint.model.Idea;
import com.masdar.messageEndpoint.MessageEndpoint;
import com.masdar.messageEndpoint.model.CollectionResponseMessageData;
import com.masdar.messageEndpoint.model.MessageData;
import com.masdar.scripts.InjectedObject;
import com.masdar.util.MasdarUtil;
import com.masdar.webview.CustomizedWebView;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	private CustomizedWebView mainView;
	static final int SOCIAL_ACTIVITY_CODE = 1;
	MessageEndpoint messageEndpoint;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!MasdarUtil.isOnline(this)) {
			Toast.makeText(getApplicationContext(),
					"There is no internet connection.", Toast.LENGTH_LONG)
					.show();
		}

		// read userId from incoming intent
		String userId = getIntent().getStringExtra("user_id");
		//mainView = (WebView) findViewById(R.id.mainView);
		mainView = (CustomizedWebView) findViewById(R.id.mainView);
		
		WebSettings webSettings = mainView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			webSettings.setAllowUniversalAccessFromFileURLs(true);
			webSettings.setAllowFileAccessFromFileURLs(true);
			//webSettings.setLightTouchEnabled(true);
			webSettings.setSupportZoom(false);
		}
		//mainView.setWebChromeClient(new ExtendedChromeClient());
		InjectedObject injectedObject = new InjectedObject(this);
		injectedObject.setUserId(userId);
		mainView.addJavascriptInterface(injectedObject, "injectedObject");
		mainView.setWebChromeClient(new ExtendedChromeClient());
		mainView.loadData("", "text/html", null);

		mainView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				return false;
			}
		});
		
		mainView.loadUrl("file:///android_asset/index.html");
		
		//Testing Area
		MessageEndpoint.Builder endpointBuilder = new MessageEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
	    		new JacksonFactory(),
	        new HttpRequestInitializer() {
	          public void initialize(HttpRequest httpRequest) { }
	        });

		messageEndpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	    //End Testing Area
	}

	//Testing Area
	private void showDialog(String message) {
		new AlertDialog.Builder(this)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).show();
	}
	@Override
	protected void onNewIntent(Intent intent) {
		Toast.makeText(getApplicationContext(), intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
	}

	private class QueryMessagesTask extends AsyncTask<Void, Void, CollectionResponseMessageData> {
		Exception exceptionThrown = null;
		MessageEndpoint messageEndpoint;

		public QueryMessagesTask(Activity activity, MessageEndpoint messageEndpoint) {
			this.messageEndpoint = messageEndpoint;
		}

		@Override
		protected CollectionResponseMessageData doInBackground(Void... params) {
			try {
				CollectionResponseMessageData messages = messageEndpoint.listMessages().setLimit(5).execute();
				return messages;
			} catch (IOException e) {
				exceptionThrown = e;
				return null;
				// Handle exception in PostExecute
			}
		}

		protected void onPostExecute(CollectionResponseMessageData messages) {
			// Check if exception was thrown
			if (exceptionThrown != null) {
				Log.e(RegisterActivity.class.getName(),
						"Exception when listing Messages", exceptionThrown);
				showDialog("Failed to retrieve the last 5 messages from "
						+ "the endpoint at " + messageEndpoint.getBaseUrl()
						+ ", check log for details");
			} else {
				TextView messageView = (TextView) findViewById(R.id.msgView);
				messageView.setText("Last 5 Messages read from "
						+ messageEndpoint.getBaseUrl() + ":\n");
				for (MessageData message : messages.getItems()) {
					messageView.append(message.getMessage() + "\n");
				}
			}
		}
	}
	//End Testing Area
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mainView.canGoBack()) {
			mainView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class ExtendedChromeClient extends WebChromeClient {

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			// TODO Auto-generated method stub
			return super.onJsAlert(view, url, message, result);
		}
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == SOCIAL_ACTIVITY_CODE){
			if(resultCode == RESULT_OK){
				Toast.makeText(getApplicationContext(), "Social Activity posted ...",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}
