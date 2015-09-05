package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.pd;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;

public class ACAPDAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "ACAPDAsyncTask";

	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/sub_project2/";
	
	SendPostRunnable sr;

	private ProgressDialog progressDialog;

	private String pkg_name = null;
	private String appId = null;
	private String appId2 = null;
	private String UUID = null;

	public ACAPDAsyncTask(String pkg_name) {
		super();

		this.pkg_name = pkg_name;
		this.appId = getAppId(mAppContext, pkg_name);
		this.appId2 = getAppId2(mAppContext, pkg_name);
		this.UUID = getUUID(mAppContext);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = pd.getProgressDialog();
		progressDialog.show();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// http post Runnable
		sr = new SendPostRunnable(appId, appId2, UUID);

		// start a Thread, the data to be transferred into the Runnable
		Thread t = new Thread(sr);
		t.start();
		// Log.i(TAG, "checkUser start");

		try {
			// wait Thread t
			t.join();
			// Log.i(TAG, "checkUser join");
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (sr.getAuthStatus()) {
			showCheckUserCorrect();

		} else {
			showCheckUserError();

		}

		if (progressDialog != null) {
			progressDialog.dismiss();

		}
	}

	private String getAppId(Context context, String pkg_name) {
		String str = "";

		// ---get hash code of apk---
		String apkName = "";
		String apkPath = "";
		File apkFile = null;
		int i = 0;
		int isNext = 0;

		// ---get apk file name---
		for (i = 1; isNext < 1; i++) {
			apkName = pkg_name + "-" + i + ".apk";
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
			System.out.println("AppId= " + str);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}

		return str;
	}

	private String getAppId2(Context context, String pkg_name) {
		String str = "";

		try {
			str = AeSimpleSHA1.SHA1(pkg_name);
			System.out.println("AppId2= " + str);
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

		System.out.println("UUID= " + uniqueID);

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
