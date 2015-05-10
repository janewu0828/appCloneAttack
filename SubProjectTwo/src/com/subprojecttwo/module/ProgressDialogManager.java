package com.subprojecttwo.module;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {
	private ProgressDialog progressDialog;

	public ProgressDialogManager(Context context, String str, boolean cancel) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(str);
		progressDialog.setCancelable(cancel);
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}
}
