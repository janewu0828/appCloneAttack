package com.project.module;

import irdc.ex04_16.R;
import android.content.Context;
import android.os.Environment;

public class ProjectConfig {
	// get global Application object of the current process
	public static Context mAppContext;

	// get context for AlertDialog
	public static Context mContext;

	// show AlertDialog
	public static AlertDialogManager alert = new AlertDialogManager();

	// show a message of authentication is successful in first time
	public static boolean isShowTxt;

	// dynamic loading filename(JAR) -----
	public static String fileName = "encrypt.jar";	
	public static String filePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/project/";
	public static String loadFileName = "";

	public static String personal_key = "";

	public static void checkConnection() {
		// TODO Auto-generated method stub
		// check network setting on device
		ConnectionDetector cd = new ConnectionDetector(mAppContext);
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(mContext,
					mContext.getString(R.string.alert_internet_error_title),
					mContext.getString(R.string.alert_internet_error_msg),
					false);
		}
	}

	public static void showCheckUserError() {
		// TODO Auto-generated method stub
		// show a Alert Dialog that Authentication is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_error_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_error_msg), false);
	}

	public static void showPersonalKey() {
		// TODO Auto-generated method stub
		AlertDialogManager alert = new AlertDialogManager();
		alert.showAlertEditDialog(mContext, "Input Personal Key", "key", false);
	}

	public static void showPersonalKeyError(String msg) {
		// TODO Auto-generated method stub
		// show a Alert Dialog that Personal Key is failed
		alert.showAlertDialog(mContext, "Personal Key Error", msg, false);
	}

}
