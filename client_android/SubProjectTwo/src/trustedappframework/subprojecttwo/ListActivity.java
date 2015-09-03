package trustedappframework.subprojecttwo;

import static trustedappframework.subprojecttwo.module.ProjectConfig.ProgressDialog;
import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.os.StrictMode;
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

	String server_uri = ACAPDAsyncTask.appSecurityEnhancer_url
			+ "/php/information.php";
	ArrayList<HashMap<String, String>> arrList;

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

	private void initACAPD() {
		// get global Application object of the current process
		mAppContext = getApplicationContext();
		// get context for AlertDialog
		mContext = ListActivity.this;

		ProgressDialog();
		// check network setting on device
		checkConnection();
	}

	private String getJsonData() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());

		String str = "";
		HttpResponse response;
		HttpClient myClient = new DefaultHttpClient();
		HttpPost myConnection = new HttpPost(server_uri);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("UUID", ACAPDAsyncTask
				.getUUID(mAppContext)));
		try {
			myConnection.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			response = myClient.execute(myConnection);
			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("-----------info-----------" + str);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			requestWindowFeature(Window.FEATURE_ACTION_BAR);
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

//							String json_str = getJsonData();
//							try {
//								JSONArray jArray = new JSONArray(json_str);
//
//								for (int i = 0; i < jArray.length(); i++) {
//									JSONObject json = null;
//									json = jArray.getJSONObject(i);
//
//									HashMap<String, String> map1 = new HashMap<String, String>();
//
//									// adding each child node to HashMap key =>
//									// value
//									map1.put("name", json.getString("name"));
//
//									// adding HashList to ArrayList
//									arrList.add(map1);
//								}
//
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//
//							if (!arrList.isEmpty()) {
//							Log.e(TAG,arrList+"");
//						}
								/** here **/
								for (PackageInfo pi : pis) {
									// / Only display non-system app info
									if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
											&& (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
										// // if non-system app info, add into
										// applist
										data.add(new Apps(
												pi.applicationInfo
														.loadIcon(packageManager),
												(String) pi.applicationInfo
														.loadLabel(packageManager),
												pi.applicationInfo.packageName));
									}
								}
//							}

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

								/** here **/
								ACAPDAsyncTask task = new ACAPDAsyncTask(data
										.get(position).getPkg_name());
								task.execute((Void[]) null);
								// ACAPD myACAPD = new ACAPD(data.get(position)
								// .getPkg_name());
								// myACAPD.ACAPD();
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
