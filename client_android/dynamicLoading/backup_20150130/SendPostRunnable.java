package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ACAPD.appSecurityEnhancer_url;
import static trustedappframework.subprojecttwo.module.ACAPD.outputFilePath;
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
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;

public class SendPostRunnable implements Runnable {
	private static final String TAG = "SendPostRunnable";

	private String appId = null;
	private String appId2 = null;
	private String UUID = null;
	// private String IMEI = null;

	private boolean authStatus = false;
	private boolean tracingStatus = false;

	// 主要是記錄用戶會話過程中的一些用戶的基本訊息
	private HashMap<String, String> session;

	// download file from server
	private String fileName = null;
	private String personalKey = null;
	private String file_url = appSecurityEnhancer_url + "download/" + fileName;

	public SendPostRunnable(String fileName) {
		super();
		this.fileName = fileName;
		this.appId = getAppId(mAppContext);
		this.appId2 = getAppId2(mAppContext);
		this.UUID = getUUID(mAppContext);
		// this.IMEI = getIMEI(mAppContext);
	}

	@Override
	public void run() {
		if (personalKey == null)
			sendPostDataToInternet();
		else
			sendPostDataToInternet2();
	}

	private void sendPostDataToInternet() {

		// System.out.println("appId= " + appId + ", appId length= "
		// + appId.length());
		// System.out.println("appId2= " + appId2 + ", appId2 length= "
		// + appId2.length());
		// System.out.println("UUID= " + UUID + ", UUID length= " +
		// UUID.length());
		// System.out.println("IMEI= " + IMEI + ", IMEI length= " +
		// IMEI.length());

		// check user -----
		// CheckUser cu = new CheckUser(appId, appId2, UUID, IMEI);
		CheckUser cu = new CheckUser(appId, appId2, UUID);
		authStatus = cu.checkUser();
		// Log.i(TAG, "auth= " + auth);

		if (authStatus) {
			if (!(new File(outputFilePath + fileName).exists())) {
				// Asnyc Dowload -----
				new DownloadFileFromURL().execute(file_url);
				Log.i(TAG, "download encrypted Jar");
			}

			session = cu.getSession();
			// System.out.println("s_sessionid= " + session.get("s_sessionid"));
		}
	}

	private void sendPostDataToInternet2() {
		Tracing trace = new Tracing(fileName, personalKey, session);
		tracingStatus = trace.tracingLog();
		// Log.i(TAG, "Tracing: " + tracingStatus);
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

	public void setSession(HashMap<String, String> session) {
		this.session = session;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setPersonalKey(String personalKey) {
		this.personalKey = personalKey;
	}

	public boolean getAuthStatus() {
		return authStatus;
	}

	public boolean getTracingStatus() {
		return tracingStatus;
	}

	public HashMap<String, String> getSession() {
		return session;
	}

	private String getAppId(Context context) {
		String str = "";

		// ---get hash code of apk---
		String PACKAGE_NAME = context.getPackageName();
		String apkName = "";
		String apkPath = "";
		File apkFile = null;
		int i = 0;
		int isNext = 0;

		// ---get apk file name---
		for (i = 1; isNext < 1; i++) {
			apkName = PACKAGE_NAME + "-" + i + ".apk";
			// System.out.println("fileName= " + apkName);

			apkPath = Environment.getDataDirectory() + "/app/" + apkName;
			apkFile = new File(apkPath);
			// System.out.println("path= " + apkFile.getAbsolutePath());

			if (!apkFile.exists()) {
				// System.out.println("no exists= " +
				// apkFile.getAbsolutePath());

			} else {
				// System.out.println("exists= " + apkFile.getAbsolutePath());
				isNext++;
			}
		}

		// ---get hash code of apk---
		try {
			str = Hash.sha256(apkFile);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}

		return str;
	}

	private String getAppId2(Context context) {
		String str = "";

		String PACKAGE_NAME = context.getPackageName();
		// System.out.println("package name= " + PACKAGE_NAME);

		try {
			str = AeSimpleSHA1.SHA1(PACKAGE_NAME);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return str;
	}

	private static String uniqueID = null;
	private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

	public synchronized static String getUUID(Context context) {
		if (uniqueID == null) {
			SharedPreferences sharedPrefs = context.getSharedPreferences(
					PREF_UNIQUE_ID, Context.MODE_PRIVATE);
			uniqueID = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
			if (uniqueID == null) {
				uniqueID = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
				Editor editor = sharedPrefs.edit();
				editor.putString(PREF_UNIQUE_ID, uniqueID);
				editor.commit();
			}
		}

		return uniqueID;
	}

	// private String getUUID2(Context context) {
	// DeviceUuidFactory DFactory = new DeviceUuidFactory(context);
	// String str = DFactory.getDeviceUuid().toString();
	//
	// return str;
	// }

	// private String getIMEI(Context context) {
	// TelephonyManager tM = (TelephonyManager) context
	// .getSystemService(Context.TELEPHONY_SERVICE);
	// String str = tM.getDeviceId();
	//
	// return str;
	// }

}
