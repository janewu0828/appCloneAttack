package trustedappframework.subprojecttwo.module;

import irdc.ex06_08.R;
import android.content.Context;
import android.widget.Toast;

public class ProjectConfig {
	// get global Application object of the current process
	public static Context mAppContext;

	// get context for AlertDialog
	public static Context mContext;

	// show AlertDialog
	public static AlertDialogManager alert = new AlertDialogManager();

	// dynamic loading class separation (JAR) -----
	public static String class_separation_segment = "encrypted20150515_5.jar";
	public static String class_separation_segment2 = ".jar";
	public static String class_separation_segment3 = ".jar";

	// tracing traitors -----
	public static String personal_key = "personal_key.txt";
	public static String personal_key2 = "personal_key2.txt";
	public static String personal_key3 = "personal_key3.txt";

	public static void checkConnection() {
		// check network setting on device
		ConnectionDetector cd = new ConnectionDetector(mAppContext);
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(mContext,
					mContext.getString(R.string.alert_internet_error_title),
					mContext.getString(R.string.alert_internet_error_msg),
					false);
		}
	}

	public static void showCheckUserCorrect() {
		// show a message of Authentication is successful in first time
		if (ACAPD.isShowTxt) {
			Toast.makeText(
					mAppContext,
					mAppContext.getResources().getString(
							R.string.toast_checkuser_true), Toast.LENGTH_SHORT)
					.show();

			ACAPD.isShowTxt = false;
		}
	}

	public static void showCheckUserError() {
		// show a Alert Dialog that Authentication is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_error_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_error_msg), false);
	}

	public static void showPersonalKeyError() {
		// show a Alert Dialog that Personal Key is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_personal_key_error_title),
				mContext.getResources().getString(
						R.string.alert_personal_key_error_msg), false);
	}

}
