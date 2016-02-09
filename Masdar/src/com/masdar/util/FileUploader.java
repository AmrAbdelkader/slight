package com.masdar.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.masdar.R;
import com.masdar.clientdao.IdeaManager;
import com.masdar.entities.ideaendpoint.model.Idea;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class FileUploader {
	
	private HttpURLConnection con;
	private DataOutputStream dos;
	private FileInputStream fileInputStream;
	private String blobKey = null;
	private int storageType;	
	private String fileName;
	private String fileHeaderName;
	private Context ctx;
	private FileUploader currentFileUploader;
	
	
	public FileUploader(Context ctx , String fileName , String fileHeaderName){
		this.ctx = ctx;
		this.fileName = fileName;
		this.fileHeaderName = fileHeaderName;
		currentFileUploader = this;
		fileInputStream = loadPrivateFile(fileName);
	}
	
	public FileUploader(Context ctx , String fileName , String fileHeaderName , int storageType){
		this.ctx = ctx;
		this.fileName = fileName;
		this.fileHeaderName = fileHeaderName;
		this.storageType = storageType;
		currentFileUploader = this;
		if(storageType == 0){
			fileInputStream = LoadInternalFile(fileName);
		}else if(storageType == 1){
			fileInputStream = loadCachedFile(fileName);
		}else if(storageType == 2){
			fileInputStream = loadExternalFile(fileName);
		}
	}
	
	public String getBlobKey(){
		return blobKey;
	}

	public void uploadFile(Callable<String> responseHandler){
		try {
			UploadUrlGenerator uploadGeneator = new UploadUrlGenerator();
			uploadGeneator.setResponseHandler(responseHandler);
			uploadGeneator.execute();
		}catch(Exception exc){
			Log.d("UploadFile_Exception-message", exc.getMessage());
		}	
	}
	
	public void uploadIdea(final int ideaType , final String userId){
		Callable<String> responseHandler = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				try {
					Log.i("Uploading-File" , "Uplaoding file completed");
					String responseData = currentFileUploader.getBlobKey();
					if(!responseData.isEmpty()){
						Idea newIdea = new Idea();
						newIdea.setIdeaType(ideaType);
						newIdea.setIdeaHeader("");
						newIdea.setIdeaBlobKey(responseData);
						newIdea.setUserId(Long.valueOf(userId));
						newIdea.setVotingNumber(Long.valueOf(0));
						if(IdeaManager.createNewIdea(newIdea)){
							Log.i("Idea_Creation", "Idea created successfully !");
						}else{
							Log.i("Idea_Creation", "Something went wrong while creating new idea.");
						}
					}
				} catch (NumberFormatException e) {
					Log.d("uploadIdea_Exception-message", e.getMessage());
				}
				return null;
			}
		};
		uploadFile(responseHandler);
	}
	
	FileInputStream loadExternalFile(String path){
		FileInputStream loadedFile = null;
		try {
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				loadedFile = new FileInputStream(new File(path));
			}
		} catch (FileNotFoundException e) {
			Log.d("loadExternalFile-Exception: " ,e.getMessage());
			e.printStackTrace();
		}
		return loadedFile;
	}
	
	FileInputStream loadPrivateFile(String path){
		FileInputStream fis = null;
		try {
			fis = ctx.openFileInput(path);
		} catch (FileNotFoundException e) {
			Log.d("loadPrivateFile-Exception: " ,e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			Log.d("loadPrivateFile-Exception: " ,e.getMessage());
		}
		return fis;
	}
	
	FileInputStream loadCachedFile(String path){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(ctx.getCacheDir() , path));
		} catch (FileNotFoundException e) {
			Log.d("loadCachedFile-Exception: " ,e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			Log.d("loadCachedFile-Exception: " ,e.getMessage());
		}
		return fis;
	}
	
	FileInputStream LoadInternalFile(String path){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(ctx.getFileStreamPath(path));
		} catch (Exception e) {
			Log.d("LoadInternalFile-Exception: " ,e.getMessage());
			e.printStackTrace();
		}
		
		return fis;
	}
	
	class UploadUrlGenerator extends AsyncTask<Void, Void, String>{
		
		private Callable<String> responseHandler;
		
		public void setResponseHandler(Callable<String> responseHandler) {
			this.responseHandler = responseHandler;
		}
		
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String responseText = "";
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(ctx.getResources().getString(R.string.BLOB_GENERATOR_URL));
				ResponseHandler<String> response = new BasicResponseHandler();
				responseText = client.execute(request , response);
			} catch (ClientProtocolException e) {
				Log.d("UploadGenerator_doInBackground_Exception-message", e.getMessage());
			} catch (IOException e) {
				Log.d("UploadGenerator_doInBackground_Exception-message", e.getMessage());
			}
			return responseText;
		}

		
		@Override
		@SuppressWarnings("unchecked")
		protected void onPostExecute(String result) {
			FileUploaderProcess fileUploadProcess = new FileUploaderProcess();
			fileUploadProcess.setUploadUrl(result);
			fileUploadProcess.execute(responseHandler);
		}
	}
	
	class FileUploaderProcess extends AsyncTask<Callable<String>, Void, Void>{

		private String uploadUrl;
		
		public void setUploadUrl(String uploadUrl){
			this.uploadUrl = uploadUrl;
		}
		
		@Override
		protected Void doInBackground(Callable<String>... responseHandler) {
			try{
				//file = ctx.getFileStreamPath(fileName);
				//fileInputStream = new FileInputStream(file);
				URL url = new URL(uploadUrl);
				con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setUseCaches(false);
				con.setRequestMethod("POST");
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("ENCTYPE", "multipart/form-data");
				con.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
				con.setRequestProperty(fileHeaderName , fileName);
				dos = new DataOutputStream(con.getOutputStream());
				dos.writeBytes("--*****\r\n");
				dos.writeBytes("Content-Disposition: form-data; name=\""+fileHeaderName+"\"; filename=\""+fileName+"\"\r\n");
				dos.writeBytes("\r\n");
				int bytesAvailable = fileInputStream.available();
				int maxBufferSize = 1 * 1024 * 1024;
				int bufferSize = Math.min(bytesAvailable, maxBufferSize);
				byte [] buffer = new byte[bufferSize];
				int bytesRead = fileInputStream.read(buffer , 0 , bufferSize);
				while(bytesRead > 0){
					dos.write(buffer , 0 , bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable , maxBufferSize);
					bytesRead = fileInputStream.read(buffer , 0 , bufferSize);
				}
				dos.writeBytes("\r\n");
				dos.writeBytes("--*****--\r\n");
				int serverResponseCode = con.getResponseCode();
				String serverResponseMessage = con.getResponseMessage(); 
				Log.i("Uploading-File", "HTTP response is "+serverResponseCode + " : "+ serverResponseMessage);
				if(serverResponseCode == 200){
					blobKey = readResponse();
					responseHandler[0].call();
				}
				fileInputStream.close();
				dos.flush();
				dos.close();
			}catch(Exception exc){
				Log.d("FileUploaderProcess_doInBackground_Exception-message", exc.getMessage());
			}finally{
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			((Activity)ctx).finish();
		}
	}	
	
	public String readResponse() {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			InputStream is = new BufferedInputStream(con.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = br.readLine()) != null){
				Log.i("Servlet-data", line);
				stringBuffer.append(line);
			}
			
		} catch (IOException e) {
			Log.d("readResponse_Exception-message", e.getMessage());
		}
		return stringBuffer.toString().trim();
	}
}
