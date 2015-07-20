package trustedappframework.subprojecttwo;

import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;

import java.util.ArrayList;
import java.util.List;

import trustedappframework.subprojecttwo.adapter.AppListAdapter;
import trustedappframework.subprojecttwo.entity.Apps;
import trustedappframework.subprojecttwo.module.AlertDialogManager;
import trustedappframework.subprojecttwo.module.ProgressDialogManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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

	// apk-info
	private List<PackageInfo> pis;
	private List<ResolveInfo> appInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initACAPD();

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

							// display system app info
							// for (ResolveInfo app : appInfo) {
							// data.add(new Apps(app.loadIcon(packageManager),
							// (String) app.loadLabel(packageManager)));
							// }

							for (int i = 0; i < pis.size(); i++) {
								PackageInfo packageInfo = pis.get(i);
								Apps tmpInfo = new Apps();
								tmpInfo.app_name = packageInfo.applicationInfo
										.loadLabel(getPackageManager())
										.toString();
								tmpInfo.app_icon = packageInfo.applicationInfo
										.loadIcon(getPackageManager());
								// Only display non-system app info
								if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
									// if non-system app info, add into applist
									data.add(tmpInfo);
								}
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

								// here
								AlertDialogManager alert = new AlertDialogManager();
								alert.showAlertDialog(
										ListActivity.this,
										getResources()
												.getString(
														R.string.alert_applist_loading_title),
										getResources()
												.getString(
														R.string.alert_applist_loading_msg),
										false);
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

		} catch (Exception e) {
			finish();
		}

	}

	private void initACAPD() {
		// get global Application object of the current process
		mAppContext = getApplicationContext();
		// get context for AlertDialog
		mContext = ListActivity.this;	
		// check network setting on device
		checkConnection();
	}
}
