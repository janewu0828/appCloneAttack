package trustedappframework.subprojecttwo.module;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;

public class AuthInfo {
	private static final String TAG = "AuthInfo";
	
	public static String getAppId(Context context) {
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

	public static String getAppId2(Context context) {
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
