package com.masdar;

import java.util.HashMap;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.masdar.clientdao.IdeaManager;
import com.masdar.entities.ideaendpoint.model.Idea;
import com.masdar.spen.tools.PreferencesOfSAMMOption;
import com.masdar.spen.tools.SPenSDKUtils;
import com.masdar.util.FileUploader;
import com.masdar.util.MasdarUtil;
import com.samsung.samm.common.SOptionSCanvas;
import com.samsung.spensdk.SCanvasConstants;
import com.samsung.spensdk.SCanvasView;
import com.samsung.spensdk.applistener.HistoryUpdateListener;
import com.samsung.spensdk.applistener.SCanvasInitializeListener;
import com.samsung.spensdk.applistener.SCanvasModeChangedListener;

public class SocialViewerActivity extends Activity {

	private Button uploadButton;
	private Idea currentIdea;
	
	private final String TAG = "SPenSDK Sample";

	//==============================
	// Application Identifier Setting
	// "SDK Sample Application 1.0"
	//==============================
	private final String APPLICATION_ID_NAME = "SDK Sample Application";
	private final int APPLICATION_ID_VERSION_MAJOR = 1;
	private final int APPLICATION_ID_VERSION_MINOR = 0;
	private final String APPLICATION_ID_VERSION_PATCHNAME = "Debug";

	//==============================
	// Variables
	//==============================
	Context mContext = null;

	private FrameLayout	mLayoutContainer;
	private RelativeLayout	mCanvasContainer;
	private SCanvasView		mSCanvas;
	private ImageView		mPenBtn;
	private ImageView		mEraserBtn;
	private ImageView		mTextBtn;
	private ImageView		mUndoBtn;
	private ImageView		mRedoBtn;
	
	
	
	void setForegroundImage(String imagePath){
		//Uri imageFileUri = data.getData();
		//if(imageFileUri == null)
		//	return;
		
		
		//String strBackgroundImagePath = SPenSDKUtils.getRealPathFromURI(this, imageFileUri);
		String strBackgroundImagePath = imagePath;

		// Check Valid Image File
		if(!SPenSDKUtils.isValidImagePath(strBackgroundImagePath))
		{
			Toast.makeText(this, "Invalid image path or web image", Toast.LENGTH_LONG).show();	
			return;
		}

		Bitmap loadBitmap = SPenSDKUtils.getSafeResizingBitmap(strBackgroundImagePath, mSCanvas.getWidth(), mSCanvas.getHeight(), true);

		// canvas option setting
		SOptionSCanvas canvasOption = mSCanvas.getOption();					
		if(canvasOption == null)
			return;
		canvasOption.mSAMMOption.setConvertCanvasSizeOption(PreferencesOfSAMMOption.getPreferenceLoadCanvasSize(mContext));
		// option setting
		mSCanvas.setOption(canvasOption);

		// Set SCanvas
		if(!mSCanvas.setClearImageBitmap(loadBitmap)){
			Toast.makeText(mContext, "Fail to set Background Image Path.", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_viewer);
		
		Intent intent = getIntent();
		final String imagePath = intent.getStringExtra("image_file");
		final String currentIdeaId = intent.getStringExtra("idea_id");
		
		//loading current Idea
		new GetCurrentIdea().execute(currentIdeaId);
		
		uploadButton = (Button) findViewById(R.id.uploadButton);
		uploadButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//progressDialog.show();
				MasdarUtil.saveCanvasImage(mSCanvas , mContext ,  true);
				String fileName = "drawn_canvas.png";
				String fileHeaderName = "image_file";
				final FileUploader fileUploader = new FileUploader(mContext , fileName, fileHeaderName);
				fileUploader.uploadFile(new Callable<String>() {
					
					@Override
					public String call() throws Exception {
						
						Log.i("Uploading-File" , "Uplaoding file completed");
						String responseData = fileUploader.getBlobKey();
						if(!responseData.isEmpty()){
							if(currentIdea != null){
								currentIdea.setIdeaBlobKey(responseData);
								if(IdeaManager.updateIdea(currentIdea)){
									Log.d("updateIdea: " , "Idea Updated Successfully");
									setResult(RESULT_OK, getIntent());
								}else{
									Log.i("Idea_Creation", "Something went wrong while updating idea.");
								}
							}
						}
						return null;
					}
				});
			}
		});
		
		mContext = this;

		//------------------------------------
		// UI Setting
		//------------------------------------
		mPenBtn = (ImageView) findViewById(R.id.penBtn);
		mPenBtn.setOnClickListener(mBtnClickListener);
		mEraserBtn = (ImageView) findViewById(R.id.eraseBtn);
		mEraserBtn.setOnClickListener(mBtnClickListener);
		mTextBtn = (ImageView) findViewById(R.id.textBtn);
		mTextBtn.setOnClickListener(mBtnClickListener);

		mUndoBtn = (ImageView) findViewById(R.id.undoBtn);
		mUndoBtn.setOnClickListener(undoNredoBtnClickListener);
		mRedoBtn = (ImageView) findViewById(R.id.redoBtn);
		mRedoBtn.setOnClickListener(undoNredoBtnClickListener);

		//------------------------------------
		// Create SCanvasView
		//------------------------------------
		mLayoutContainer = (FrameLayout) findViewById(R.id.layout_container);
		mCanvasContainer = (RelativeLayout) findViewById(R.id.canvas_container);

		// Add SCanvasView under minSDK 14(AndroidManifext.xml)
		// mSCanvas = new SCanvasView(mContext);        
		// mCanvasContainer.addView(mSCanvas);

		// Add SCanvasView under minSDK 10(AndroidManifext.xml) for preventing text input error
		mSCanvas = new SCanvasView(mContext);
		mSCanvas.addedByResizingContainer(mCanvasContainer);

		//------------------------------------
		// SettingView Setting
		//------------------------------------
		// Resource Map for Layout & Locale
		HashMap<String,Integer> settingResourceMapInt = SPenSDKUtils.getSettingLayoutLocaleResourceMap(true, true, true, true);
		// Talk & Description Setting by Locale
		SPenSDKUtils.addTalkbackAndDescriptionStringResourceMap(settingResourceMapInt);
		// Resource Map for Custom font path
		HashMap<String,String> settingResourceMapString = SPenSDKUtils.getSettingLayoutStringResourceMap(true, true, true, true);
		// Create Setting View
		mSCanvas.createSettingView(mLayoutContainer, settingResourceMapInt, settingResourceMapString);

		//====================================================================================
		//
		// Set Callback Listener(Interface)
		//
		//====================================================================================
		//------------------------------------------------
		// SCanvas Listener
		//------------------------------------------------
		mSCanvas.setSCanvasInitializeListener(new SCanvasInitializeListener() {
			@Override
			public void onInitialized() { 
				//--------------------------------------------
				// Start SCanvasView/CanvasView Task Here
				//--------------------------------------------
				// Application Identifier Setting
				if(!mSCanvas.setAppID(APPLICATION_ID_NAME, APPLICATION_ID_VERSION_MAJOR, APPLICATION_ID_VERSION_MINOR,APPLICATION_ID_VERSION_PATCHNAME))
					Toast.makeText(mContext, "Fail to set App ID.", Toast.LENGTH_LONG).show();

				// Set Title
				if(!mSCanvas.setTitle("SPen-SDK Test"))
					Toast.makeText(mContext, "Fail to set Title.", Toast.LENGTH_LONG).show();

				// Set Pen Only Mode with Finger Control
				mSCanvas.setFingerControlPenDrawing(true);

				setForegroundImage(imagePath);
				
				// Update button state
				updateModeState();
			}
		});

		//------------------------------------------------
		// History Change Listener
		//------------------------------------------------
		mSCanvas.setHistoryUpdateListener(new HistoryUpdateListener() {
			@Override
			public void onHistoryChanged(boolean undoable, boolean redoable) {
				mUndoBtn.setEnabled(undoable);
				mRedoBtn.setEnabled(redoable);
			}
		});


		//------------------------------------------------
		// SCanvas Mode Changed Listener 
		//------------------------------------------------
		mSCanvas.setSCanvasModeChangedListener(new SCanvasModeChangedListener() {

			@Override
			public void onModeChanged(int mode) {
				updateModeState();
			}

			@Override
			public void onMovingModeEnabled(boolean bEnableMovingMode) {
				updateModeState();
			}

			@Override
			public void onColorPickerModeEnabled(boolean bEnableColorPickerMode) {
				updateModeState();
			}
		});

		mUndoBtn.setEnabled(false);
		mRedoBtn.setEnabled(false);
		mPenBtn.setSelected(true);

		// Caution:
		// Do NOT load file or start animation here because we don't know canvas size here.
		// Start such SCanvasView Task at onInitialized() of SCanvasInitializeListener
		
	}

	@Override
	protected void onDestroy() {	
		super.onDestroy();
		// Release SCanvasView resources
		if(!mSCanvas.closeSCanvasView())
			Log.e(TAG, "Fail to close SCanvasView");
	}

	@Override
	public void onBackPressed() {
		SPenSDKUtils.alertActivityFinish(this, "Exit");
	} 


	private OnClickListener undoNredoBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mUndoBtn)) {
				mSCanvas.undo();
			}
			else if (v.equals(mRedoBtn)) {
				mSCanvas.redo();
			}
			mUndoBtn.setEnabled(mSCanvas.isUndoable());
			mRedoBtn.setEnabled(mSCanvas.isRedoable());
		}
	};

	OnClickListener mBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int nBtnID = v.getId();
			// If the mode is not changed, open the setting view. If the mode is same, close the setting view. 
			if(nBtnID == mPenBtn.getId()){				
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_PEN){
					mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_NORMAL);
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_PEN);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, false);					
					updateModeState();
				}
			}
			else if(nBtnID == mEraserBtn.getId()){
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_ERASER){
					mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_NORMAL);
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER);
				}
				else {
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_ERASER);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, false);
					updateModeState();
				}
			}
			else if(nBtnID == mTextBtn.getId()){
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_TEXT){
					mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_NORMAL);
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_TEXT);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT, false);										
					updateModeState();
					Toast.makeText(mContext, "Tap Canvas to insert Text", Toast.LENGTH_SHORT).show();
				}
			}		
		}
	};

	// Update tool button
	private void updateModeState(){
		SPenSDKUtils.updateModeState(mSCanvas, null, null, mPenBtn, mEraserBtn, mTextBtn, null, null, null, null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.social_viewer, menu);
		return true;
	}
	
	class GetCurrentIdea extends AsyncTask<String, Void, Idea>{

		@Override
		protected Idea doInBackground(String... ideaId) {
			Idea returnedIdea = null;
			returnedIdea = IdeaManager.getIdeaById(Long.valueOf(ideaId[0]));
			return returnedIdea;
		}

		@Override
		protected void onPostExecute(Idea result) {
			currentIdea = result;
		}
	}

}
