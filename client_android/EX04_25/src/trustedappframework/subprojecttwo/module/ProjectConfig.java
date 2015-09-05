package trustedappframework.subprojecttwo.module;

import irdc.ex04_25.R;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ProjectConfig {
	private static final String TAG = "ProjectConfig";

	// get global Application object of the current process
	public static Context mAppContext;

	// get context for AlertDialog
	public static Context mContext;

	// show AlertDialog
	private static AlertDialogManager alert = new AlertDialogManager();

	// show ProgressDialog
	public static ProgressDialogManager pd;

	// dynamic loading class separation (JAR) -----
	public static String[] class_separation_segment = null;
	public static String[] jarFlag = null;
	public static String[] classStatus = null;
	public static String[] test_class_separation_segment = null;
	public static String[] test_jarFlag = null;

	public static void checkConnection() {
		// check network setting on device
		ConnectionDetector cd = new ConnectionDetector(mAppContext);
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(mContext,
					mContext.getString(R.string.alert_internet_error_title),
					mContext.getString(R.string.alert_internet_error_msg),
					false);
			Log.e(TAG, "checkConnectionError");
		}
	}

	public static void initProgressDialog() {
		pd = new ProgressDialogManager(mContext);
		pd.onCreateDialog(R.drawable.acapd,
				mContext.getString(R.string.progress_loading_title),
				mContext.getString(R.string.progress_loading_msg), false);
	}

	public static void showPersonalKeyError() {
		// show a Alert Dialog that Personal Key is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_personal_key_error_title),
				mContext.getResources().getString(
						R.string.alert_personal_key_error_msg), false);
		Log.e(TAG, "showPersonalKeyError");
	}

	public static void showCheckUserCorrect() {
		Toast.makeText(mAppContext,
				mAppContext.getString(R.string.toast_checkuser_true),
				Toast.LENGTH_SHORT).show();

		Log.e(TAG, "showCheckUserCorrect, " + "toast_checkuser_true");
	}

	public static void showCheckUserError() {
		// show a Alert Dialog that Authentication is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_error_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_error_msg), false);
		Log.e(TAG, "showCheckUserError");
	}
	
	public static void showLoadJarError() {
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_load_jar_error_title),
				mContext.getResources().getString(
						R.string.alert_load_jar_error_msg), false);
		Log.e(TAG, "showLoadJarError");
	}

	public static void showToast(String str) {
		Toast.makeText(mAppContext, str, Toast.LENGTH_SHORT).show();
		Log.e(TAG, "showToast, str= " + str);
	}

}
