package trustedappframework.subprojecttwo;

import static trustedappframework.subprojecttwo.module.ProjectConfig.ProgressDialog;
import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import trustedappframework.subprojecttwo.adapter.AppListAdapter;
import trustedappframework.subprojecttwo.entity.Apps;
import trustedappframework.subprojecttwo.module.ACAPDAsyncTask;

public class ListActivity extends Activity {
	private static final String TAG = "ListActivity";

	private ListView listView;
	public static ProgressDialog progressDialog;
	public static LoadingProgressDialog pd;
	public Message msg;
	public Handler handler;
	public static final int DATA_ERROR = 0;
	public static final int DATA_CREATLIST = 1;
	List<Apps> data = null; // applist
	String strPackageName;
	// apk-info
	List<PackageInfo> pis;
	List<ResolveInfo> appInfo;

	ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

	private void initACAPD() {
		// get global Application object of the current process
		mAppContext = getApplicationContext();
		// get context for AlertDialog
		mContext = ListActivity.this;
		
		ProgressDialog();
		// check network setting on device
		checkConnection();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.applist);

			initACAPD();

			pd = new LoadingProgressDialog(this, "Loading...", false);
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

							/** display system app info **/
							// for (ResolveInfo app : appInfo) {
							//
							// data.add(new
							// Apps(app.loadIcon(packageManager),
							// (String) app.loadLabel(packageManager)));
							// }

							/** here **/
							for (PackageInfo pi : pis) {
								// / Only display non-system app info
								if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
										&& (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
									// // if non-system app info, add into
									// applist
									data.add(new Apps(pi.applicationInfo
											.loadIcon(packageManager),
											(String) pi.applicationInfo
													.loadLabel(packageManager),
											pi.applicationInfo.packageName));
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
					case DATA_CREATLIST: // create ListView

						listView = (ListView) findViewById(R.id.apps_listview);

						AppListAdapter adapter = new AppListAdapter(
								ListActivity.this, data, listView);

						listView.setAdapter(adapter);

						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								
								/**here**/
								ACAPDAsyncTask task = new ACAPDAsyncTask(data.get(position)
										.getPkg_name());
								task.execute((Void[]) null);
//								ACAPD myACAPD = new ACAPD(data.get(position)
//										.getPkg_name());
//								myACAPD.ACAPD();
							}
						});

						break;
					case DATA_ERROR:
						showErrorDialog("Error Dialog");
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

	protected void showErrorDialog(String strMsg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error");
		builder.setMessage(strMsg);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public class LoadingProgressDialog {
		private ProgressDialog progressDialog;

		public LoadingProgressDialog(Context context, String str, boolean cancel) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(str);
			progressDialog.setCancelable(cancel);
		}

		public ProgressDialog getProgressDialog() {
			return progressDialog;
		}
	}
}
