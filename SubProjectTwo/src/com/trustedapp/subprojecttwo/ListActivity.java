package com.trustedapp.subprojecttwo;

import java.util.ArrayList;
import java.util.List;

import com.subprojecttwo.module.AlertDialogManager;
import com.subprojecttwo.module.ProgressDialogManager;
import com.subprojecttwo.adapter.AppListAdapter;
import com.subprojecttwo.entity.Apps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListActivity extends Activity {
	private ListView listView;
	public static ProgressDialog progressDialog;
	public static ProgressDialogManager pd;
	public Message msg;
	public Handler handler;
	public static final int DATA_ERROR = 0;
	public static final int DATA_CREATLIST = 1;
	private List<Apps> data = null;// applist
	private String strPackageName;
	// apk-info
	private List<PackageInfo> pis;
	private List<ResolveInfo> appInfo;

	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			// requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.applist);

			pd = new ProgressDialogManager(this, getResources().getString(
					R.string.applist_loading_msg), false);
			progressDialog = pd.getProgressDialog();
			progressDialog.show();
			new Thread(new Runnable() {
				public void run() {
					try {
						PackageManager packageManager = getPackageManager();
						Intent intent = new Intent(Intent.ACTION_MAIN, null);
						intent.addCategory(Intent.CATEGORY_LAUNCHER);
						appInfo = packageManager.queryIntentActivities(intent,
								0);
						pis = packageManager.getInstalledPackages(0);

						if (appInfo != null) {
							data = new ArrayList<Apps>();
							for (ResolveInfo app : appInfo) {
								data.add(new Apps(app.loadIcon(packageManager),
										(String) app.loadLabel(packageManager)));
							}
							msg.what = DATA_CREATLIST;
						} else {
							msg.what = DATA_ERROR;
						}
					} catch (Exception e) {
						msg.what = DATA_ERROR;
					} finally {
						handler.sendMessage(msg);
					}
				}
			}).start();

			msg = Message.obtain();
			handler = new Handler() {
				public void handleMessage(Message message) {
					switch (message.what) {
					case DATA_CREATLIST:// create ListView

						listView = (ListView) findViewById(R.id.apps_listview);

						AppListAdapter adapter = new AppListAdapter(
								ListActivity.this, data, listView);
						listView.setAdapter(adapter);

						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								ActivityInfo activityInfo = appInfo
										.get(position).activityInfo;
								Intent intent = new Intent();
								intent.setClassName(activityInfo.packageName,
										activityInfo.name);
								startActivity(intent);
							}
						});

						break;
					case DATA_ERROR:
						AlertDialogManager alert = new AlertDialogManager();
						alert.showAlertDialog(
								mContext,
								mContext.getResources().getString(
										R.string.alert_applist_loading_title),
								mContext.getResources().getString(
										R.string.alert_applist_loading_msg),
								false);
						break;
					}
					super.handleMessage(message);
					progressDialog.dismiss();
				}
			};
			super.onCreate(savedInstanceState);
			mContext = ListActivity.this;

		} catch (Exception e) {
			finish();
		}
		
	}

}
