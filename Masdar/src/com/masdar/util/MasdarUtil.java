package com.masdar.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.masdar.R;
import com.samsung.spensdk.SCanvasView;

public class MasdarUtil {
	
	public static boolean isOnline(Context ctx) {
	    ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
	public static void saveCanvasImage(SCanvasView mSCanvas , Context context, boolean bSaveOnlyForegroundImage) {
		Bitmap bmCanvas = mSCanvas.getCanvasBitmap(bSaveOnlyForegroundImage);
		
		File sdcard_path = Environment.getExternalStorageDirectory();
		File default_path =  new File(sdcard_path, "SPenSDK");
		String tempSAMMFileName = default_path.getAbsolutePath() + "/" + "drawn_canvas.png";
		MasdarUtil.saveBitmapImage(bmCanvas , context, "drawn_canvas.png");
		//MasdarUtil.saveBitmapImage(bmCanvas , context, tempSAMMFileName);
	}
	
	public static InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.setReadTimeout(3 * 60 * 1000); // 3 minutes read timeout
			httpConn.connect();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}
	
	public static String getDeviceId(Context ctx){
		TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	public static void showDialog(String message , Context ctx) {
	    new AlertDialog.Builder(ctx).setMessage(message).setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int id) {
	            	  dialog.dismiss();
	              }
            }).show();
	}
	
	public static void saveBitmapImage(Bitmap bmpImage , Context currentContext , String fileName) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmpImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			FileOutputStream fileOutputStream = currentContext.openFileOutput(fileName , Context.MODE_PRIVATE);
			fileOutputStream.write(byteArray);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveDataFile(byte[] fileData , String fileName , Context ctx){
		try{
			FileOutputStream fileOutputStream = ctx.openFileOutput(fileName , Context.MODE_PRIVATE);
			fileOutputStream.write(fileData);
			fileOutputStream.close();
		}catch(Exception exc){
			
		}finally{
			
		}
	}
	
	public static void saveKeyValueData(String key , String value , Activity ctx){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String readValueByKey(String key , Activity ctx){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
		return sharedPref.getString(key, "");
	}
	
	public static String readDataFile(String fileName , Context ctx){
		StringBuffer fileContent = new StringBuffer();
		try{
			
			FileInputStream fileInputStream = ctx.openFileInput(fileName);
			int content;
			while((content = fileInputStream.read()) != -1){
				fileContent.append((char) content);
			}
		}catch(Exception exc){
			
		}
		return fileContent.toString();
	}
	
	public static String getCurrentUserId(Context ctx){
		return  MasdarUtil.readValueByKey(ctx.getString(R.string.user_server_id), (Activity)ctx);
	}
	
}
