package trustedappframework.subprojecttwo.module;

import trustedappframework.subprojecttwo.R;
import android.content.Context;
import android.util.Log;

public class ProjectConfig {
	private static final String TAG = "ProjectConfig";

	// get global Application object of the current process
	public static Context mAppContext;

	// get context for AlertDialog
	public static Context mContext;

	// show AlertDialog
	private static AlertDialogManager alert = new AlertDialogManager();

	public static ProgressDialogManager pd;
	
	public static void ProgressDialog() {
		pd = new ProgressDialogManager(mContext);
		pd.onCreateDialog(R.drawable.acapd,
				mContext.getString(R.string.progress_loading_title),
				mContext.getString(R.string.progress_loading_msg), false);
	}

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

	public static void showCheckUserCorrect() {
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_true_msg), true);
		Log.e(TAG, "showCheckUserCorrect");
	}

	public static void showCheckUserError() {
		// show a Alert Dialog that Authentication is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_error_msg), false);
		Log.e(TAG, "showCheckUserError");
	}

}
