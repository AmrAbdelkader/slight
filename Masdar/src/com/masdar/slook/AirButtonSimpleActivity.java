package com.masdar.slook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.masdar.R;
import com.masdar.clientdao.IdeaManager;
import com.masdar.clientdao.UserManager;
import com.masdar.entities.ideaendpoint.model.Idea;
import com.masdar.entities.userendpoint.model.CollectionResponseUser;
import com.masdar.entities.userendpoint.model.User;
import com.masdar.spen.tools.PreferencesOfSAMMOption;
import com.masdar.spen.tools.SPenSDKUtils;
import com.masdar.util.FileUploader;
import com.masdar.util.MasdarUtil;
import com.samsung.android.sdk.look.airbutton.SlookAirButton;
import com.samsung.android.sdk.look.airbutton.SlookAirButton.ItemSelectListener;
import com.samsung.android.sdk.look.airbutton.SlookAirButtonAdapter;
import com.samsung.android.sdk.look.airbutton.SlookAirButtonAdapter.AirButtonItem;
import com.samsung.samm.common.SObjectImage;
import com.samsung.samm.common.SObjectStroke;
import com.samsung.samm.common.SOptionSCanvas;
import com.samsung.spen.settings.SettingFillingInfo;
import com.samsung.spen.settings.SettingStrokeInfo;
import com.samsung.spen.settings.SettingTextInfo;
import com.samsung.spensdk.SCanvasConstants;
import com.samsung.spensdk.SCanvasView;
import com.samsung.spensdk.applistener.ColorPickerColorChangeListener;
import com.samsung.spensdk.applistener.HistoryUpdateListener;
import com.samsung.spensdk.applistener.SCanvasInitializeListener;
import com.samsung.spensdk.applistener.SCanvasModeChangedListener;
import com.samsung.spensdk.applistener.SPenHoverListener;

public class AirButtonSimpleActivity extends Activity {

	private Button btnRecipient;
	private ProgressDialog progressDialog;
	private Activity currentContext;
	private SlookAirButtonAdapter airButtonAdapter;
	private SlookAirButtonAdapter selectedAdapter;
	private Button btnImage;
	private ArrayList<AirButtonItem> usersSelectedList;
	private AlertDialog.Builder dialogBuilder;
	private int currentIndex = -1;
	private final String TAG = "SPenSDK Sample";
	private List<Long> selectedUsersIds;
	private List<String> selectedUsersDeviceIds;
	private String currentUserId;
	private Button uploadButton;
	
	//==============================
	// Intent Parameters
	//==============================	
	public final static String KEY_IMAGE_SAVE_PATH = "SavePath";
	public final static String KEY_IMAGE_SRC_PATH = "FilePathOrigin";

	public final static String KEY_EDITOR_VERSION = "EditorVersion";
	public final static String KEY_EDITOR_GUI_STYLE = "EditorGUIStyle";

	//==============================
	// Application Identifier Setting
	// "SDK Sample Application 1.0"
	//==============================
	private final String APPLICATION_ID_NAME = "SDK Sample Application";
	private final int APPLICATION_ID_VERSION_MAJOR = 1;
	private final int APPLICATION_ID_VERSION_MINOR = 0;
	private final String APPLICATION_ID_VERSION_PATCHNAME = "Debug";

	//==============================
	// Menu
	//==============================
	private final int MENU_FILE_GROUP = 2000;
	private final int MENU_FILE_1 = 2001;
	private final int MENU_FILE_2 = 2002;

	private final int MENU_DATA_GROUP = 3000;
	private final int MENU_DATA_1 = 3001;	
	private final int MENU_DATA_2 = 3002;	



	//==============================
	// Activity Request code
	//==============================
	private final int REQUEST_CODE_INSERT_IMAGE_OBJECT = 100;

	//==============================
	// Variables
	//==============================
	Context mContext = null;

	private String  mSrcImageFilePath = null;
	private Rect	mSrcImageRect = null;
	private int mEditorGUIStyle = SCanvasConstants.SCANVAS_GUI_STYLE_NORMAL;
	private boolean mbSingleSelectionFixedLayerMode = false;

	private FrameLayout		mLayoutContainer;
	private RelativeLayout	mCanvasContainer;
	private SCanvasView		mSCanvas;
	private ImageView		mPenOnlyBtn;
	private ImageView		mPenBtn;
	private ImageView		mEraserBtn;
	private ImageView		mTextBtn;
	private ImageView		mFillingBtn;
	private ImageView		mInsertBtn;
	private ImageView		mColorPickerBtn;
	private ImageView		mUndoBtn;
	private ImageView		mRedoBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airbutton_simple);
		currentContext = this;
		selectedUsersIds = new ArrayList<Long>();
		selectedUsersDeviceIds = new ArrayList<String>();
		
		Intent callerIntent = getIntent();
		currentUserId = callerIntent.getStringExtra("user_id"); 
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.setCanceledOnTouchOutside(false);
		
		uploadButton = (Button) findViewById(R.id.uploadButton);
		uploadButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				uploadSocialIdea();
			}
		});

		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int which) {
				selectedAdapter.removeItem(currentIndex);
				selectedUsersIds.remove(currentIndex);
				selectedUsersDeviceIds.remove(currentContext);
				
				Toast.makeText(currentContext , "Removed !", Toast.LENGTH_SHORT).show();
			}			
		});
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int which) {
			}			
		});
		
		// Loading Following List
		//new FollowingRetreiver().execute("6241757859676160");
		new FollowingRetreiver().execute(currentUserId);

		Button btnMenu = (Button) findViewById(R.id.btn_menu);
		btnRecipient = (Button) findViewById(R.id.btn_recipient);
		btnRecipient.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				airButtonAdapter.addItem(new AirButtonItem(getResources()
						.getDrawable(R.drawable.recipient), "Test", null));
			}
		});
		btnImage = (Button) findViewById(R.id.btn_image);
		createImageListWidgetFromView(btnImage);
		
		//S-Pen SDK Area
		mContext = this;

		//------------------------------------
		// UI Setting
		//------------------------------------		
		mPenOnlyBtn = (ImageView) findViewById(R.id.penOnlyBtn);
		mPenOnlyBtn.setOnClickListener(mPenOnlyBtnClickListener);

		mPenBtn = (ImageView) findViewById(R.id.penBtn);
		mPenBtn.setOnClickListener(mBtnClickListener);
		mPenBtn.setOnLongClickListener(mBtnLongClickListener);
		mEraserBtn = (ImageView) findViewById(R.id.eraseBtn);
		mEraserBtn.setOnClickListener(mBtnClickListener);
		mEraserBtn.setOnLongClickListener(mBtnLongClickListener);
		mTextBtn = (ImageView) findViewById(R.id.textBtn);
		mTextBtn.setOnClickListener(mBtnClickListener);
		mTextBtn.setOnLongClickListener(mBtnLongClickListener);
		mFillingBtn = (ImageView) findViewById(R.id.fillingBtn);
		mFillingBtn.setOnClickListener(mBtnClickListener);
		mFillingBtn.setOnLongClickListener(mBtnLongClickListener);

		mColorPickerBtn = (ImageView) findViewById(R.id.colorPickerBtn);
		mColorPickerBtn.setOnClickListener(mColorPickerListener);

		mInsertBtn = (ImageView) findViewById(R.id.insertBtn);
		mInsertBtn.setOnClickListener(mInsertBtnClickListener);

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

		Intent intent = getIntent();
		mSrcImageFilePath = intent.getStringExtra(KEY_IMAGE_SRC_PATH);

		mbSingleSelectionFixedLayerMode = intent.getBooleanExtra(KEY_EDITOR_VERSION, mbSingleSelectionFixedLayerMode);
		mEditorGUIStyle = intent.getIntExtra(KEY_EDITOR_GUI_STYLE, mEditorGUIStyle);

		// If initial image exist, resize the canvas size
		if(mSrcImageFilePath!=null){
			mSrcImageRect = getMiniumCanvasRect(mSrcImageFilePath, 20);

			// Place SCanvasView In the Center
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)mCanvasContainer.getLayoutParams();		
			layoutParams.width = mSrcImageRect.right-mSrcImageRect.left;
			layoutParams.height= mSrcImageRect.bottom-mSrcImageRect.top;
			layoutParams.gravity = Gravity.CENTER; 
			mCanvasContainer.setLayoutParams(layoutParams);

			// Set Background of layout container
			mLayoutContainer.setBackgroundResource(R.drawable.bg_edit);
		}

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

				// Set Initial Setting View Size
				mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_EXT);

				// Set Editor Version (mEditorGUIStyle)	
				// - SCanvasConstants.SCANVAS_GUI_STYLE_NORMAL;
				// - SCanvasConstants.SCANVAS_GUI_STYLE_KIDS;
				mSCanvas.setSCanvasGUIStyle(mEditorGUIStyle);

				// Set Pen Only Mode with Finger Control
				mSCanvas.setFingerControlPenDrawing(true);
				mPenOnlyBtn.setImageResource(R.drawable.selector_penonly);

				// Set Editor GUI Style (mbSingleSelectionFixedLayerMode)
				// - true :  S Pen SDK 2.2 (Single selection, Fixed layer Editor : Image-Text-Stroke ordering)
				// - false : S Pen SDK 2.3 (Multi-selection, Flexible layer Editor : Input ordering)				
				mSCanvas.setSingleSelectionFixedLayerMode(mbSingleSelectionFixedLayerMode);

				// Update button state
				updateModeState();

				// Load the file & set Background Image
				if(mSrcImageFilePath!=null){

					if(SCanvasView.isSAMMFile(mSrcImageFilePath)){
						loadSAMMFile(mSrcImageFilePath);
						// Set the editing rect after loading
					}
					else{					
						// set BG Image
						if(!mSCanvas.setBGImagePath(mSrcImageFilePath)){
							Toast.makeText(mContext, "Fail to set Background Image Path.", Toast.LENGTH_LONG).show();
						}
					}
				}

				// Restore last setting information
				// mSCanvas.restoreSettingViewStatus();
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


		//------------------------------------------------
		// Color Picker Listener 
		//------------------------------------------------
		mSCanvas.setColorPickerColorChangeListener(new ColorPickerColorChangeListener(){
			@Override
			public void onColorPickerColorChanged(int nColor) {

				int nCurMode = mSCanvas.getCanvasMode();
				if(nCurMode==SCanvasConstants.SCANVAS_MODE_INPUT_PEN) {
					SettingStrokeInfo strokeInfo = mSCanvas.getSettingViewStrokeInfo();
					if(strokeInfo != null) {
						strokeInfo.setStrokeColor(nColor);	
						mSCanvas.setSettingViewStrokeInfo(strokeInfo);
					}	
				}
				else if(nCurMode==SCanvasConstants.SCANVAS_MODE_INPUT_ERASER) {
					// do nothing
				}
				else if(nCurMode==SCanvasConstants.SCANVAS_MODE_INPUT_TEXT){
					SettingTextInfo textInfo = mSCanvas.getSettingViewTextInfo();
					if(textInfo != null) {
						textInfo.setTextColor(nColor);
						mSCanvas.setSettingViewTextInfo(textInfo);
					}
				}
				else if(nCurMode==SCanvasConstants.SCANVAS_MODE_INPUT_FILLING) {
					SettingFillingInfo fillingInfo = mSCanvas.getSettingViewFillingInfo();
					if(fillingInfo != null) {
						fillingInfo.setFillingColor(nColor);
						mSCanvas.setSettingViewFillingInfo(fillingInfo);
					}
				}	
			}			
		});	

		mUndoBtn.setEnabled(false);
		mRedoBtn.setEnabled(false);
		mPenBtn.setSelected(true);
		mSCanvas.setSCanvasHoverPointerStyle(SCanvasConstants.SCANVAS_HOVERPOINTER_STYLE_SPENSDK);

		mSCanvas.setSPenHoverListener(new SPenHoverListener() {

			boolean isPenButtonDown = false;
			@Override
			public boolean onHover(View view, MotionEvent event) {				
				return false;
			}

			@Override
			public void onHoverButtonDown(View view, MotionEvent event) {
				isPenButtonDown= true;
			}

			@Override
			public void onHoverButtonUp(View view, MotionEvent event) {
				if(isPenButtonDown==false)	// ignore button up event if button was not pressed on hover
					return;
				isPenButtonDown = false;

				boolean bIncludeDefinedSetting = true;
				boolean bIncludeCustomSetting = true;
				boolean bIncludeEraserSetting = true;
				SettingStrokeInfo settingInfo = mSCanvas.getSettingViewNextStrokeInfo(bIncludeDefinedSetting, bIncludeCustomSetting, bIncludeEraserSetting);
				if(settingInfo!=null) {
					if(mSCanvas.setSettingViewStrokeInfo(settingInfo)) {
						int nPreviousMode = mSCanvas.getCanvasMode();
						// Mode Change : Pen => Eraser					
						if(nPreviousMode == SCanvasConstants.SCANVAS_MODE_INPUT_PEN
								&& settingInfo.getStrokeStyle()==SObjectStroke.SAMM_STROKE_STYLE_ERASER){
							// Change Mode
							mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_ERASER);
							// Show Setting View
							if(mSCanvas.isSettingViewVisible(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN)){
								mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, false);
								mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, true);
							}
							updateModeState();
						}
						// Mode Change : Eraser => Pen 
						if(nPreviousMode == SCanvasConstants.SCANVAS_MODE_INPUT_ERASER
								&& settingInfo.getStrokeStyle()!=SObjectStroke.SAMM_STROKE_STYLE_ERASER){
							// Change Mode
							mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_PEN);
							// Show Setting View
							if(mSCanvas.isSettingViewVisible(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER)){
								mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, false);
								mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, true);
							}
							updateModeState();
						}		
					}
				}
			}
		});

		// Caution:
		// Do NOT load file or start animation here because we don't know canvas size here.
		// Start such SCanvasView Task at onInitialized() of SCanvasInitializeListener
		//End of S-Pen SDK Area
	}

	public SlookAirButton createImageListWidgetFromView(View v) {
		usersSelectedList = new ArrayList<AirButtonItem>();
		selectedAdapter = new SlookAirButtonAdapter(usersSelectedList);
		SlookAirButton airButtonWidget = new SlookAirButton(v,
				selectedAdapter, SlookAirButton.UI_TYPE_LIST);
		airButtonWidget.setItemSelectListener(new ItemSelectListener() {

			@Override
			public void onItemSelected(View view, int index, Object arg2) {
				if(index != -1){
					currentIndex = index;
					String userName = airButtonAdapter.getItem(index).getDescription();
					dialogBuilder.setMessage("Remove " + userName + " from drawing list?");
					dialogBuilder.show();
				}
			}
		});
		airButtonWidget.setGravity(SlookAirButton.GRAVITY_LEFT);
		airButtonWidget.setDirection(SlookAirButton.DIRECTION_UPPER);
		airButtonWidget.setPosition(0, -50);
		return airButtonWidget;
	}

	class FollowingRetreiver extends AsyncTask<String, Void, List<User>> {

		@Override
		protected void onPreExecute() {
			//progressDialog.show();
		}

		@Override
		protected List<User> doInBackground(String... userId) {
			List<User> retreivedUsers = null;
			try {
				CollectionResponseUser users = UserManager.getFollowingUsers(Long.valueOf(userId[0]));
				if (users != null) {
					retreivedUsers = users.getItems();
				}
			} catch (Exception e) {
				Log.d("FollowingRetreiver.doInBackground-Exception-Message",e.getMessage());
			}
			return retreivedUsers;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(List<User> result) {
			if (result != null && result.size() != 0) {
				btnRecipient.setText(result.size() + " Following List");
				new ProfilePicDownloader().execute(result);
			}else if(result == null){
				btnRecipient.setText("0 Following List");
				Toast.makeText(getApplicationContext(), "you didn't follow any user yet !",Toast.LENGTH_SHORT).show();
			}
		}
	}

	class ProfilePicDownloader extends
			AsyncTask<List<User>, Void, ArrayList<AirButtonItem>> {

		@Override
		protected ArrayList<AirButtonItem> doInBackground(
				List<User>... usersList) {
			ArrayList<AirButtonItem> itemList = new ArrayList<AirButtonItem>();
			try {
				for (User userObject : usersList[0]) {
					InputStream in = MasdarUtil
							.OpenHttpConnection("http://graph.facebook.com/"+ userObject.getFbId()+ "/picture?type=large");
					Drawable userProfilePic = BitmapDrawable.createFromStream(
							in, null);
					if (userProfilePic != null) {
						itemList.add(new AirButtonItem(userProfilePic,
								userObject.getUserName(), userObject));
					} else {
						itemList.add(new AirButtonItem(getResources()
								.getDrawable(R.drawable.recipient), userObject
								.getUserName(), userObject));
					}
				}
			} catch (NotFoundException exc) {
				Log.d("ProfilePicDownloader.doInBackground-NotFoundException-Message",
						exc.getMessage());
			} catch (IOException exc) {
				Log.d("ProfilePicDownloader.doInBackground-IOException-Message",
						exc.getMessage());
			} catch (Exception exc) {
				Log.d("ProfilePicDownloader.doInBackground-Exception-Message",
						exc.getMessage());
			}
			return itemList;
		}

		@Override
		protected void onPostExecute(ArrayList<AirButtonItem> result) {
			try {
				airButtonAdapter = new SlookAirButtonAdapter(result);
				SlookAirButton airButtonWidget = new SlookAirButton(
						btnRecipient, airButtonAdapter,
						SlookAirButton.UI_TYPE_LIST);
				airButtonWidget.setDirection(SlookAirButton.DIRECTION_LOWER);
				airButtonWidget.setItemSelectListener(new ItemSelectListener() {

					@Override
					public void onItemSelected(View arg0, int index, Object arg2) {
						if (index != -1) {
							AirButtonItem airButtonItem = airButtonAdapter.getItem(index);
							if (!usersSelectedList.contains(airButtonItem)) {
								usersSelectedList.add(airButtonItem);
								User u = (User) airButtonItem.getData();
								selectedUsersIds.add(Long.valueOf(u.getUserId()));
								selectedUsersDeviceIds.add(u.getDeviceId());
								//btnImage.setText(usersSelectedList.size());
							}
						}
					}
				});
				progressDialog.dismiss();
				Toast.makeText(
						currentContext,
						"Please hover and press the S-pen side-button on each button",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.d("ProfilePicDownloader.onPostExecute-Exception-Message",
						e.getMessage());
			}
		}
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

	private OnClickListener mBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int nBtnID = v.getId();
			// If the mode is not changed, open the setting view. If the mode is same, close the setting view. 
			if(nBtnID == mPenBtn.getId()){				
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_PEN){
					mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_EXT);
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
			else if(nBtnID == mFillingBtn.getId()){
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_FILLING){
					mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_NORMAL);
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_FILLING);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING, false);										
					updateModeState();
					Toast.makeText(mContext, "Tap Canvas to fill color", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	private OnLongClickListener mBtnLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

			int nBtnID = v.getId();
			// If the mode is not changed, open the setting view. If the mode is same, close the setting view. 
			if(nBtnID == mPenBtn.getId()){				
				mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_MINI);
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_PEN){					
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_PEN);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_PEN, true);					
					updateModeState();
				}
				return true;
			}
			else if(nBtnID == mEraserBtn.getId()){
				mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_MINI);
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_ERASER){
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER);
				}
				else {
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_ERASER);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_ERASER, true);
					updateModeState();
				}
				return true;
			}
			else if(nBtnID == mTextBtn.getId()){
				mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_MINI);
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_TEXT){
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_TEXT);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_TEXT, true);										
					updateModeState();
					Toast.makeText(mContext, "Tap Canvas to insert Text", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
			else if(nBtnID == mFillingBtn.getId()){
				mSCanvas.setSettingViewSizeOption(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING, SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_MINI);
				if(mSCanvas.getCanvasMode()==SCanvasConstants.SCANVAS_MODE_INPUT_FILLING){
					mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING);
				}
				else{
					mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_FILLING);
					mSCanvas.showSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING, true);										
					updateModeState();
					Toast.makeText(mContext, "Tap Canvas to fill color", Toast.LENGTH_SHORT).show();
				}
				return true;
			}

			return false;
		}
	};

	// insert image
	private OnClickListener mInsertBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mInsertBtn)) {	
				callGalleryForInputImage(REQUEST_CODE_INSERT_IMAGE_OBJECT);
			}
		}
	};



	// color picker mode
	private OnClickListener mPenOnlyBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mPenOnlyBtn)) {
				// Toggle
				boolean bIsPenOnly = !mSCanvas.isFingerControlPenDrawing(); 
				mSCanvas.setFingerControlPenDrawing(bIsPenOnly);

				if(bIsPenOnly)
					mPenOnlyBtn.setImageResource(R.drawable.selector_penonly);
				else
					mPenOnlyBtn.setImageResource(R.drawable.selector_penonly_n);
			}
		}
	};

	// color picker mode
	private OnClickListener mColorPickerListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mColorPickerBtn)) {
				// Toggle
				boolean bIsColorPickerMode = !mSCanvas.isColorPickerMode(); 
				mSCanvas.setColorPickerMode(bIsColorPickerMode);
			}
		}
	};

	// Update tool button
	private void updateModeState(){
		SPenSDKUtils.updateModeState(mSCanvas, null, null, mPenBtn, mEraserBtn, mTextBtn, mFillingBtn, mInsertBtn, mColorPickerBtn, null);
	}	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode==RESULT_OK){
			if(data == null)
				return;

			if(requestCode == REQUEST_CODE_INSERT_IMAGE_OBJECT) {    			
				Uri imageFileUri = data.getData();
				if(imageFileUri == null)
					return;
				String imagePath = SPenSDKUtils.getRealPathFromURI(this, imageFileUri);
				insertImageObject(imagePath);	
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){	

		SubMenu fileMenu = menu.addSubMenu("File");
		fileMenu.add(MENU_FILE_GROUP, MENU_FILE_1, 1, "Menu1");
		fileMenu.add(MENU_FILE_GROUP, MENU_FILE_2, 2, "Menu2");

		SubMenu dataMenu = menu.addSubMenu("Data");		
		dataMenu.add(MENU_DATA_GROUP, MENU_DATA_1, 1, "Data1");
		dataMenu.add(MENU_DATA_GROUP, MENU_DATA_2, 2, "Data2");

		return super.onCreateOptionsMenu(menu);
	} 


	@Override
	public boolean onMenuOpened(int featureId, Menu menu){
		super.onMenuOpened(featureId, menu);

		if (menu == null) 
			return true;


		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)	{
		super.onOptionsItemSelected(item);

		switch(item.getItemId()) {
		case MENU_FILE_1:
			Toast.makeText(mContext, "File1 menu item selected.", Toast.LENGTH_SHORT).show();
			break;
		case MENU_FILE_2:
			Toast.makeText(mContext, "File2 menu item selected.", Toast.LENGTH_SHORT).show();
			break;
		case MENU_DATA_1:
			Toast.makeText(mContext, "Data1 menu item selected.", Toast.LENGTH_SHORT).show();
			break;
		case MENU_DATA_2:
			Toast.makeText(mContext, "Data2 menu item selected.", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}

	// Call Gallery
	private void callGalleryForInputImage(int nRequestCode){
		try {
			Intent galleryIntent;
			galleryIntent = new Intent(); 
			galleryIntent.setAction(Intent.ACTION_GET_CONTENT);				
			galleryIntent.setType("image/*");
			galleryIntent.setClassName("com.cooliris.media", "com.cooliris.media.Gallery");
			startActivityForResult(galleryIntent, nRequestCode);
		} catch(ActivityNotFoundException e) {
			Intent galleryIntent;
			galleryIntent = new Intent();
			galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
			galleryIntent.setType("image/*");
			startActivityForResult(galleryIntent, nRequestCode);
			e.printStackTrace();
		}		
	}

	// Get the minimum image scaled rect which is fit to current screen 
	Rect getMiniumCanvasRect(String strImagePath, int nMargin){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		int nScreenWidth =  displayMetrics.widthPixels - nMargin*2;
		int nScreenHeight = displayMetrics.heightPixels - nMargin*2;

		// Make more small for screen rotation T.T
		if(nScreenWidth<nScreenHeight)
			nScreenHeight = nScreenWidth;
		else
			nScreenWidth = nScreenHeight;

		int nImageWidth = nScreenWidth;
		int nImageHeight = nScreenHeight;	
		if(strImagePath!=null){
			BitmapFactory.Options opts = SPenSDKUtils.getBitmapSize(strImagePath);
			nImageWidth = opts.outWidth;
			nImageHeight = opts.outHeight;
		}		


		float fResizeWidth = (float) nScreenWidth / nImageWidth;
		float fResizeHeight = (float) nScreenHeight / nImageHeight;
		float fResizeRatio;

		// Fit to Height
		if(fResizeWidth>fResizeHeight){
			fResizeRatio = fResizeHeight;
		}
		// Fit to Width
		else {	
			fResizeRatio = fResizeWidth;
		}

		return new Rect(0,0, (int)(nImageWidth*fResizeRatio), (int)(nImageHeight*fResizeRatio));
	}

	// Load SAMM file
	boolean loadSAMMFile(String strFileName){
		if(mSCanvas.isAnimationMode()){
			// It must be not animation mode.
		}
		else {
			// set progress dialog
			mSCanvas.setProgressDialogSetting(R.string.load_title, R.string.load_msg, ProgressDialog.STYLE_HORIZONTAL, false);

			// canvas option setting
			SOptionSCanvas canvasOption = mSCanvas.getOption();					
			if(canvasOption == null)
				return false;
			canvasOption.mSAMMOption.setConvertCanvasSizeOption(PreferencesOfSAMMOption.getPreferenceLoadCanvasSize(mContext));
			canvasOption.mSAMMOption.setConvertCanvasHorizontalAlignOption(PreferencesOfSAMMOption.getPreferenceLoadCanvasHAlign(mContext));
			canvasOption.mSAMMOption.setConvertCanvasVerticalAlignOption(PreferencesOfSAMMOption.getPreferenceLoadCanvasVAlign(mContext));
			canvasOption.mSAMMOption.setDecodePriorityFGData(PreferencesOfSAMMOption.getPreferenceDecodePriorityFGData(mContext));
			// option setting
			mSCanvas.setOption(canvasOption);					

			// show progress for loading data
			if(mSCanvas.loadSAMMFile(strFileName, true, true, true)){
				// Loading Result can be get by callback function
			}
			else{
				Toast.makeText(this, "Load AMS File("+ strFileName +") Fail!", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		return true;
	}

	// insert Image Object	
	boolean insertImageObject(String imagePath){
		// Check Valid Image File
		if(!SPenSDKUtils.isValidImagePath(imagePath))
		{
			Toast.makeText(this, "Invalid image path or web image", Toast.LENGTH_LONG).show();	
			return false;
		}

		// canvas option setting
		SOptionSCanvas canvasOption = mSCanvas.getOption();					
		if(canvasOption == null)
			return false;

		if(canvasOption.mSAMMOption == null)
			return false;

		canvasOption.mSAMMOption.setContentsQuality(PreferencesOfSAMMOption.getPreferenceSaveImageQuality(mContext));
		// option setting
		mSCanvas.setOption(canvasOption);

		RectF rectF = getDefaultImageRect(imagePath);
		int nContentsQualityOption = canvasOption.mSAMMOption.getContentsQuality();
		SObjectImage sImageObject = new SObjectImage(nContentsQualityOption);
		sImageObject.setRect(rectF);
		sImageObject.setImagePath(imagePath);

		if(mSCanvas.insertSAMMImage(sImageObject, true)){
			//Toast.makeText(this, "Insert image file("+ imagePath +") Success!", Toast.LENGTH_SHORT).show();
			return true;
		}
		else{
			Toast.makeText(this, "Insert image file("+ imagePath +") Fail!", Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	private void uploadSocialIdea(){
		if(selectedUsersIds.size() > 0){
			progressDialog.show();
			MasdarUtil.saveCanvasImage(mSCanvas , currentContext ,  true);
			String fileName = "drawn_canvas.png";
			String fileHeaderName = "image_file";
			final FileUploader fileUploader = new FileUploader(currentContext , fileName, fileHeaderName);
			fileUploader.uploadFile(new Callable<String>() {
				
				@Override
				public String call() throws Exception {
					
					Log.i("Uploading-File" , "Uplaoding file completed");
					String responseData = fileUploader.getBlobKey();
					if(!responseData.isEmpty()){
						Idea newIdea = new Idea();
						newIdea.setIdeaType(2); 		//Social Idea Type
						newIdea.setIdeaHeader("");
						newIdea.setIdeaBlobKey(responseData);
						newIdea.setUserId(Long.valueOf(currentUserId));
						newIdea.setVotingNumber(Long.valueOf(0));
						if(IdeaManager.createNewSocialIdea(newIdea, getUsersString(selectedUsersIds))){
							Log.i("Idea_Creation", "Idea created successfully !");
						}else{
							Log.i("Idea_Creation", "Something went wrong while creating new idea.");
						}
					}
					return null;
				}
			});
		}else{
			Toast.makeText(getApplicationContext(), "There is no users attached to the idea !",Toast.LENGTH_SHORT).show();
		}
	}
	
	String getUsersString(List<Long> usersIds){
		StringBuilder stringBuilder = new StringBuilder();
		for (Long userId : usersIds) {
			stringBuilder.append(userId+",");
		}
		return stringBuilder.substring(0, stringBuilder.toString().length() - 1 );
	}
	
	// get default image rect 
	RectF getDefaultImageRect(String strImagePath){
		// Rect Region : Consider image real size
		BitmapFactory.Options opts = SPenSDKUtils.getBitmapSize(strImagePath);
		int nImageWidth = opts.outWidth;
		int nImageHeight = opts.outHeight;
		int nScreenWidth = mSCanvas.getWidth();
		int nScreenHeight = mSCanvas.getHeight();    			
		int nBoxRadius = (nScreenWidth>nScreenHeight) ? nScreenHeight/4 : nScreenWidth/4;
		int nCenterX = nScreenWidth/2;
		int nCenterY = nScreenHeight/2;
		if(nImageWidth > nImageHeight)
			return new RectF(nCenterX-nBoxRadius,nCenterY-(nBoxRadius*nImageHeight/nImageWidth),nCenterX+nBoxRadius,nCenterY+(nBoxRadius*nImageHeight/nImageWidth));
		else
			return new RectF(nCenterX-(nBoxRadius*nImageWidth/nImageHeight),nCenterY-nBoxRadius,nCenterX+(nBoxRadius*nImageWidth/nImageHeight),nCenterY+nBoxRadius);
	}
}
