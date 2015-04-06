package com.project.module;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.project.interfaces.Load;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SendPostRunnable implements Runnable {
	private static final String TAG = "SendPostRunnable";

	private Context appContext;
	private String appid = null;
	private String deviceid = null;
	private String IMEI = null;

	private boolean result = true;

	// download file from server
	private String fileName = "";
	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/web2/";
	private String file_url = appSecurityEnhancer_url + "download/" + fileName;
	private String outputFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/project/";

	/**
	 * @param result
	 */
	public SendPostRunnable(String fileName, Context appContext) {
		super();

		this.fileName = fileName;
		this.appContext = appContext;

		this.appid = getAppId();
		this.deviceid = getDeviceId(appContext);
		this.IMEI = getIMEI(appContext);
	}

	private void sendPostDataToInternet() {
		// CheckUser cu = new CheckUser(appid, deviceid, IMEI);
		// setResult(cu.checkUser());
		// Log.i(TAG, "cu.checkUser()= " + result);

		// if (result) {
		if (new File(outputFilePath + fileName).exists()) {
			Log.e(TAG, "Jar is exist");

			// // decrypt Jar
			// Decrypt decfile = new Decrypt(fileName, outputFilePath,
			// com.project.module.ProjectConfig.personal_key);
			// decfile.decryptJar();
			//
			// // dynamic loading -----
			// String loadFileName = decfile.getOutputFileName();
			// Load ld = new Load(loadFileName, outputFilePath);
			// // Load ld = new Load(loadFileName, outputFilePath);
			// ld.loadJar(appContext);
		} else {
			// Asnyc Dowload
			new DownloadFileFromURL().execute(file_url);
		}
		// }
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.e(TAG, "run()");
		sendPostDataToInternet();

		if (new File(outputFilePath + fileName).exists()) {
			Log.e(TAG, "decrypt Jar");
			
			// decrypt Jar
			Decrypt decfile = new Decrypt(fileName, outputFilePath,
					com.project.module.ProjectConfig.personal_key);
			decfile.decryptJar();

			Log.e(TAG, "dynamic loading");
			
			// dynamic loading -----
			String loadFileName = decfile.getOutputFileName();
			Load ld = new Load(loadFileName, outputFilePath);
			// Load ld = new Load(loadFileName, outputFilePath);
			ld.loadJar(SendPostRunnable.this.appContext);
		}
	}

	public void setResult(boolean result) {
		// TODO Auto-generated method stub
		this.result = result;
	}

	public boolean getResult() {
		// TODO Auto-generated method stub
		return result;
	}

	// AsynTask
	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				// this will be useful so that you can show a tipical 0-100%
				// progress bar
				int lenghtOfFile = conection.getContentLength();

				// download the file
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);

				File outputFileDir = new File(outputFilePath);

				if (!outputFileDir.exists()) {
					outputFileDir.mkdir();
				}

				// Output stream
				OutputStream output = new FileOutputStream(outputFilePath
						+ fileName);

				byte data[] = new byte[2048];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					// After this onProgressUpdate will be called
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// writing data to file
					output.write(data, 0, count);
				}

				// flushing output
				output.flush();

				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e(TAG, "Error: " + e.getMessage());
			}

			return null;
		}

		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String paramString) {
			// dynamic loading -----
			Load ld = new Load(fileName, outputFilePath);
			ld.loadJar(appContext);
		}
	}

	private String getAppId() {
		String appId = "";

		// ---get hash code of apk---
		String PACKAGE_NAME = appContext.getPackageName();
		String apkName = "";
		String apkPath = Environment.getDataDirectory() + "/app/" + apkName;
		File apkFile = null;
		int i = 0;
		int isNext = 0;

//		// ---get apk file name---
//		for (i = 1; isNext < 1; i++) {
//			apkName = PACKAGE_NAME + "-" + i + ".apk";
//			// System.out.println("fileName= " + fileName);
//
//			apkFile = new File(apkPath);
//			// System.out.println("path= " + apkFile.getAbsolutePath());
//
//			if (!apkFile.exists()) {
//				System.out.println("no exists= " + apkFile.toString());
//
//			} else {
//				// System.out.println("exists= " + apkFile.toString());
//				isNext++;
//			}
//		}
//
//		// ---get hash code of apk---
//		try {
//			appId = Hash.sha256(apkFile);
//			// System.out.println("appId= " + appId);
//			// System.out.println("appId length= " + appId.length());
//
//		} catch (Exception e) {
//			// TODO 自動產生的 catch 區塊
//			e.printStackTrace();
//			Log.e(TAG, "Error: " + e.getMessage());
//
//		}
		appId="123";

		return appId;
	}

	private String getDeviceId(Context context2) {
		// TODO Auto-generated method stub
		DeviceUuidFactory DFactory = new DeviceUuidFactory(context2);
		String deviceId = DFactory.getDeviceUuid().toString();

		// System.out.println("deviceId", deviceId);
		// System.out.println("deviceId length", "" + deviceId.length());

		return deviceId;
	}

	private String getIMEI(Context context2) {
		// TODO Auto-generated method stub
		TelephonyManager tM = (TelephonyManager) appContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tM.getDeviceId();

		// System.out.println("IMEI", imei);
		// System.out.println("IMEI" + " length", "" + imei.length());

		return imei;
	}

}
