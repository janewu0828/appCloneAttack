package com.project.module;

import static com.project.module.ProjectConfig.mAppContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SendPostRunnable implements Runnable {
	private static final String TAG = "SendPostRunnable";

	private String appId = null;
	private String UUID = null;
	private String IMEI = null;

	private boolean result = false;
	private HashMap<String, String> session;

	// download file from server
	private String fileName = "";
	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/web2/";
	private String file_url = appSecurityEnhancer_url + "download/" + fileName;
	public static String outputFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/project/";

	public SendPostRunnable(String fileName) {
		super();
		this.fileName = fileName;
		this.appId = getAppId(mAppContext);
		this.UUID = getUUID(mAppContext);
		this.IMEI = getIMEI(mAppContext);
	}

	private void sendPostDataToInternet() {

		System.out.println("appId= " + appId + ", appId length= "
				+ appId.length());

		String appId2 = getAppId2(mAppContext);
		System.out.println("appId2= " + appId2 + ", appId2 length= "
				+ appId2.length());

		System.out.println("IMEI= " + IMEI + ", IMEI length= " + IMEI.length());
		System.out.println("UUID= " + UUID + ", UUID length= " + UUID.length());

		// check user -----
		CheckUser cu = new CheckUser(appId, UUID, IMEI);
		result = cu.checkUser();
		Log.i(TAG, "auth= " + result);

		if (result) {
			if (!(new File(outputFilePath + fileName).exists())) {
				// Asnyc Dowload -----
				new DownloadFileFromURL().execute(file_url);
				Log.i(TAG, "download encrypted Jar");
			}

			session = cu.getSession();
			// System.out.println("s_sessionid= " + session.get("s_sessionid"));
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendPostDataToInternet();
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
			// to-do
		}
	}

	public boolean getResult() {
		return result;
	}

	public HashMap<String, String> getSession() {
		return session;
	}

	private String getAppId(Context context) {
		String str = "";

		String PACKAGE_NAME = context.getPackageName();
		Log.i(TAG, "package name= " + PACKAGE_NAME);

		try {
			str = AeSimpleSHA1.SHA1(str);
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

		return str;
	}

	private String getAppId2(Context context) {
		String str = "";

		// ---get hash code of apk---
		String PACKAGE_NAME = context.getPackageName();
		String apkName = "";
		String apkPath = Environment.getDataDirectory() + "/app/" + apkName;
		File apkFile = null;
		int i = 0;
		int isNext = 0;

		// ---get apk file name---
		for (i = 1; isNext < 1; i++) {
			apkName = PACKAGE_NAME + "-" + i + ".apk";
			// System.out.println("fileName= " + fileName);

			apkFile = new File(apkPath);
			// System.out.println("path= " + apkFile.getAbsolutePath());

			if (!apkFile.exists()) {
				System.out.println("no exists= " + apkFile.toString());

			} else {
				// System.out.println("exists= " + apkFile.toString());
				isNext++;
			}
		}

		// ---get hash code of apk---
		try {
			str = Hash.sha256(apkFile);
			// System.out.println("appId= " + appId);
			// System.out.println("appId length= " + appId.length());

		} catch (Exception e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());

		}

		return str;
	}

	private String getUUID(Context context) {
		// TODO Auto-generated method stub
		DeviceUuidFactory DFactory = new DeviceUuidFactory(context);
		String str = DFactory.getDeviceUuid().toString();

		return str;
	}

	private String getIMEI(Context context) {
		// TODO Auto-generated method stub
		TelephonyManager tM = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tM.getDeviceId();

		return str;
	}

}
