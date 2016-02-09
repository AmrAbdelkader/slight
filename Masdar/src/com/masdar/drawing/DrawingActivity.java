package com.masdar.drawing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.internal.cu;
import com.masdar.R;
import com.masdar.clientdao.IdeaManager;
import com.masdar.entities.ideaendpoint.model.Idea;
import com.masdar.util.FileUploader;
import com.masdar.util.MasdarUtil;
import com.samsung.samm.common.SObject;
import com.samsung.samm.common.SObjectStroke;
import com.samsung.spenemulatorlibrary.ActivityWithSPenLayer;
import com.samsung.spensdk.SCanvasView;
import com.samsung.spensdk.applistener.SObjectUpdateListener;
import com.samsung.spensdk.applistener.SPenDetachmentListener;
import com.samsung.spensdk.applistener.SPenTouchListener;


public class DrawingActivity extends ActivityWithSPenLayer {
	
	private Button uploadButton;
	private RelativeLayout mCanvasContainer;
	private SCanvasView mSCanvas;
	private IdeaType ideaType;
	private LinearLayout headerLayout;
	private EditText ideaHeader;
	private String ideaHeaderText;
	private String userId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawing_fragment);
		
		headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
		ideaHeader = (EditText) findViewById(R.id.ideaHeader);
		
		Button plusButton = (Button) findViewById(R.id.plusButton);
		plusButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Test Button Clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		userId = getIntent().getStringExtra("user_id");
		Intent callingIntent = getIntent();
		int ideaTypeValue = callingIntent.getIntExtra("idea_type", 0);
		switch(ideaTypeValue){
			case 0:
				headerLayout.setBackgroundColor(Color.RED);
				ideaType = IdeaType.GENERAL_IDEA;
			break;
			
			case 1:
				headerLayout.setBackgroundColor(Color.MAGENTA);
				ideaType = IdeaType.DESIGN;
			break;
			
			case 2:
				headerLayout.setBackgroundColor(Color.CYAN);
				ideaType = IdeaType.SOCIAL_ACTIVITY;
			break;
			
			case 3:
				headerLayout.setBackgroundColor(Color.DKGRAY);
				ideaType = IdeaType.ANIMATION;
			break;			
		}
		
		uploadButton = (Button) findViewById(R.id.uploadButton);
		uploadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ideaHeaderText = ideaHeader.getText().toString();
				if(ideaHeaderText == null || ideaHeaderText.isEmpty()){
					Toast alert = Toast.makeText(getApplicationContext(),"Give your idea a name", Toast.LENGTH_SHORT);
					alert.show();
				}else{
					//1-grab drawn graphics and save it locally.
					MasdarUtil.saveCanvasImage(mSCanvas , DrawingActivity.this ,  true);
					//2-create Http post request with created image file
					String fileName = "drawn_canvas.png";
					String fileHeaderName = "image_file";
					final FileUploader fileUploader = new FileUploader(getApplicationContext(), fileName, fileHeaderName);
					fileUploader.uploadFile(new Callable<String>(){
						
						@Override
						public String call(){
							try {
								Log.i("Uploading-File" , "Uplaoding file completed");
								String responseData = fileUploader.getBlobKey();
								if(!responseData.isEmpty()){
									Idea newIdea = new Idea();
									newIdea.setIdeaType(ideaType.getValue());
									newIdea.setIdeaHeader(ideaHeaderText);
									newIdea.setIdeaBlobKey(responseData);
									newIdea.setUserId(Long.valueOf(userId));
									if(IdeaManager.createNewIdea(newIdea)){
										Log.i("Idea_Creation", "Idea created successfully !");
									}else{
										Log.i("Idea_Creation", "Something went wrong while creating new idea.");
									}
								}
							} catch (NumberFormatException e) {
								Log.d("Exception-message", e.getMessage());
							}
							return null;
						}
					});
					
				}
			}
		});
		
		mCanvasContainer = (RelativeLayout) findViewById(R.id.canvas_container);
		mSCanvas = new SCanvasView(this);
		//Bitmap backImage = BitmapFactory.decodeFile("C:/Users/Amr_Abdelkader/Desktop/texture 2.PNG");
		//mSCanvas.setBackgroundImage(backImage);
		
		mSCanvas.setSPenDetachmentListener(new SPenDetachmentListener() {
			
			@Override
			public void onSPenDetached(boolean isDetached) {
				if(isDetached){
					Toast alert= Toast.makeText(getApplicationContext() , "S Pen Detached from the device" , Toast.LENGTH_SHORT);
					alert.show();
				}else{
					Toast alert= Toast.makeText(getApplicationContext() , "S Pen not Detached from the device" , Toast.LENGTH_SHORT);
					alert.show();
				}
			}
		});
		
		mSCanvas.setSObjectUpdateListener(new SObjectUpdateListener() {
			
			@Override
			public boolean onSObjectStrokeInserting(SObjectStroke arg0) {
				Toast alert= Toast.makeText(getApplicationContext() , "New Stroke inserted" , Toast.LENGTH_SHORT);
				alert.show();
				return false;
			}
			
			@Override
			public void onSObjectSelected(SObject arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSObjectInserted(SObject arg0, boolean arg1, boolean arg2,
					boolean arg3) {
				Toast alert= Toast.makeText(getApplicationContext() , "New SObject inserted" , Toast.LENGTH_SHORT);
				alert.show();
			}
			
			@Override
			@Deprecated
			public void onSObjectInserted(SObject arg0, boolean arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSObjectDeleted(SObject arg0, boolean arg1, boolean arg2,
					boolean arg3, boolean arg4) {
				
			}
			
			@Override
			@Deprecated
			public void onSObjectDeleted(SObject arg0, boolean arg1, boolean arg2,
					boolean arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSObjectClearAll(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSObjectChanged(SObject arg0, boolean arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mSCanvas.setSPenTouchListener(new SPenTouchListener() {
			
			@Override
			public boolean onTouchPenEraser(View arg0, MotionEvent arg1) {
				return false;
			}
			
			@Override
			public boolean onTouchPen(View view, MotionEvent motionEvent) {
				float x = motionEvent.getX();
				float y = motionEvent.getRawY();
				
				return false;
			}
						
			@Override
			public boolean onTouchFinger(View arg0, MotionEvent arg1) {
				return false;
			}
			
			@Override
			public void onTouchButtonUp(View arg0, MotionEvent arg1) {
				Toast t = Toast.makeText(getApplicationContext(), "touch up", Toast.LENGTH_SHORT);
				t.show();
			}
			
			@Override
			public void onTouchButtonDown(View arg0, MotionEvent arg1) {
				Toast t = Toast.makeText(getApplicationContext(), "touch Down", Toast.LENGTH_SHORT);
				t.show();
			}
		});
		
		mCanvasContainer.addView(mSCanvas);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
