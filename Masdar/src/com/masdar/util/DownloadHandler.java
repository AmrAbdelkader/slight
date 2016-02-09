package com.masdar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.masdar.spen.sammeditor.SPen_AnimationViewer;

public class DownloadHandler {

	private Context ctx;
	private DownloadHandlerCallback downloadHandlerCallback;

	public void setContext(Context ctx) {
		this.ctx = ctx;
	}

	public void previewAnimation(String blobKey , DownloadHandlerCallback callback) {
		this.downloadHandlerCallback = callback;
		new BlobRequester().execute(blobKey);
	}

	class BlobRequester extends AsyncTask<String, Void, String> {

		private String tempSAMMFileName;
		private ProgressDialog mDialog;
		//private DownloadHandlerCallback callableAction;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(ctx);
			mDialog.setMessage("Please wait...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(String... blobKey) {
			final String URL = "http://masdarengine.appspot.com/BlobServe?blob-key="
					+ blobKey[0];
			try {
				InputStream in = MasdarUtil.OpenHttpConnection(URL);
				File sdcard_path = Environment.getExternalStorageDirectory();
				File default_path = new File(sdcard_path, "SPenSDK");
				String mTempAMSFolderPath = default_path.getAbsolutePath();
				tempSAMMFileName = mTempAMSFolderPath + "/"+ "tempsammfile.png";

				FileOutputStream outputStream = new FileOutputStream(new File(tempSAMMFileName));
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = in.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

			} catch (Exception ex) {
				Log.d("download-handler_Exception-Message", ex.getMessage());
			}
			return tempSAMMFileName;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				downloadHandlerCallback.doActions(result);
			} catch (Exception e) {
				Log.d("DownloadHandler.BlobRequester.onPostExecute" , e.getMessage());
				e.printStackTrace();
			}
			mDialog.dismiss();
		}

	}
}
