package com.masdar.scripts;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.masdar.CloudEndpointUtils;
import com.masdar.SocialViewerActivity;
import com.masdar.drawing.DrawingActivity;
import com.masdar.messageEndpoint.MessageEndpoint;
import com.masdar.slook.AirButtonSimpleActivity;
import com.masdar.slook.SLookComment;
import com.masdar.spen.basiceditor.SPen_BasicEditor;
import com.masdar.spen.basicui.SPenBasicUI;
import com.masdar.spen.sammeditor.SPen_AnimationViewer;
import com.masdar.spen.sammeditor.SPen_SAMMEditor;
import com.masdar.util.DownloadHandler;
import com.masdar.util.DownloadHandlerCallback;
import com.samsung.spensdk.SCanvasConstants;

public class InjectedObject{
	
	private Context ctx;
	private String userId;
	
	public InjectedObject(){
		
	}
	
	public InjectedObject(Context ctx){
		this.ctx = ctx;
	}
	
	public void setContext(Context ctx){
		this.ctx = ctx;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JavascriptInterface
	public void makeToast(String toastText){
		Toast t = Toast.makeText(ctx, ""+toastText, Toast.LENGTH_LONG);
		t.show();
	}
	
	@JavascriptInterface
	public String getUserId(){
		return userId;
	}
	
	@JavascriptInterface
	public void OpenDrawingArea(int ideaType){
		//open old Drawing Activity
		Intent drawingIntent = new Intent(ctx , DrawingActivity.class);
		drawingIntent.putExtra("idea_type" , ideaType);
		drawingIntent.putExtra("user_id", getUserId());
		ctx.startActivity(drawingIntent);
	}
	
	@JavascriptInterface
	public void openBasicDrawing(){
		Intent drawingIntent = new Intent(ctx , SPenBasicUI.class);
		//drawingIntent.putExtra("idea_type" , ideaType);
		drawingIntent.putExtra("user_id", getUserId());
		ctx.startActivity(drawingIntent);
	}
	
	@JavascriptInterface
	public void openAnimationActivity(){
		Intent intent = new Intent(ctx, SPen_SAMMEditor.class);	
		intent.putExtra(SPen_SAMMEditor.KEY_EDITOR_VERSION, false);
		intent.putExtra(SPen_SAMMEditor.KEY_EDITOR_GUI_STYLE, SCanvasConstants.SCANVAS_GUI_STYLE_NORMAL);
		intent.putExtra("userId", userId);
		ctx.startActivity(intent);
	}
	
	@JavascriptInterface
	public void openDesignActivity(){
		Intent intent = new Intent(ctx, SPen_BasicEditor.class);	
		intent.putExtra(SPen_SAMMEditor.KEY_EDITOR_VERSION, false);
		intent.putExtra(SPen_SAMMEditor.KEY_EDITOR_GUI_STYLE, SCanvasConstants.SCANVAS_GUI_STYLE_NORMAL);
		ctx.startActivity(intent);
	}
	
	@JavascriptInterface
	public void openAnimationPreviewActivity(String blobKey){
		DownloadHandler downloadHandler = new DownloadHandler();
		downloadHandler.setContext(ctx);
		downloadHandler.previewAnimation(blobKey , new DownloadHandlerCallback() {
			
			@Override
			public void doActions(String response) {
				Intent intent = new Intent(ctx, SPen_AnimationViewer.class);
				intent.putExtra(SPen_AnimationViewer.EXTRA_VIEW_FILE_PATH, response);
				intent.putExtra(SPen_AnimationViewer.EXTRA_PLAY_CANVAS_WIDTH, 1040);
				intent.putExtra(SPen_AnimationViewer.EXTRA_PLAY_CANVAS_HEIGHT, 1660);
				intent.putExtra(SPen_AnimationViewer.EXTRA_CONTENTS_ORIENTATION,false);
				intent.putExtra(SPen_AnimationViewer.EXTRA_SINGLE_SELECTION_LAYER_MODE,false);
				intent.putExtra("PreviewMode", 1);
				ctx.startActivity(intent);
			}
		});
	}
	
	@JavascriptInterface
	public void writeComment(){
		Intent sLookIntent = new Intent(ctx , SLookComment.class);
		ctx.startActivity(sLookIntent);
	}
	
	@JavascriptInterface
	public void sendMessage(){
		try {
			MessageEndpoint.Builder endpointBuilder = new MessageEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
		    		new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) { }
		        });
	
			MessageEndpoint messageEndpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
			messageEndpoint.sendMessageToDevice("Hello World from Script interface ...", "356442052226960").execute();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@JavascriptInterface
	public void openSocialActivity(){
		Intent drawingIntent = new Intent(ctx , AirButtonSimpleActivity.class);
		drawingIntent.putExtra("user_id", getUserId());
		ctx.startActivity(drawingIntent);
	}
	
	@JavascriptInterface
	public String returnJson(){
		return "{\"items\" : [{\"userId\": \"1\" , \"deviceId\": \"123\"} , {\"userId\": \"2\", \"deviceId\": \"123\"} ]}";
	}
	
	@JavascriptInterface
	public void openSocialViewer(final String ideaId , String blobKey){
		DownloadHandler downloadHandler = new DownloadHandler();
		downloadHandler.setContext(ctx);
		downloadHandler.previewAnimation(blobKey , new DownloadHandlerCallback() {
			
			@Override
			public void doActions(String response) {
				Intent intent = new Intent(ctx, SocialViewerActivity.class);
				intent.putExtra("image_file", response);
				intent.putExtra("idea_id", ideaId);
				//
				((Activity)ctx).startActivityForResult(intent , 1);
			}
		});
	}
}