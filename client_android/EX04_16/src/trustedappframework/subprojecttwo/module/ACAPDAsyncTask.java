package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.pd;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.updateProgressDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ACAPDAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "ACAPDAsyncTask";

	private ProgressDialog progressDialog;

	public static SendPostRunnable sr = null;
	private String fileName = null;
	public static String classStatus = null;
//	private String key = null;
	private String jarFlag = null;
	
//	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/sub_project2/";
	public static String appSecurityEnhancer_url = "http://10.211.55.8/sub_project2/";

//	public ACAPDAsyncTask(String fileName, String key, String jarFlag,String test_id) {
	public ACAPDAsyncTask(String fileName, String classStatus, String jarFlag) {
		super();

		this.fileName = fileName;
//		this.key = key;
		this.classStatus=classStatus;
		this.jarFlag = jarFlag;

//		myACAPD = new TracingTraitor(fileName);
//		sr = TracingTraitor.sr;
	}

	@Override
	protected void onPreExecute() {
		sr = new SendPostRunnable();
		
		progressDialog = pd.getProgressDialog();
		int progressDialog_checkUser = 0;
		updateProgressDialog(progressDialog_checkUser);
		progressDialog.show();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// ---check user ---
		sr.setFileName(fileName);
		sr.setPostStatus(0);

		// start a Thread, the data to be transferred into the Runnable
		Thread t = new Thread(sr);
		t.start();
		// Log.i(TAG, "checkUser start");

		try {
			// wait Thread t
			t.join();
			// Log.i(TAG, "checkUser join");
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
//		// App Clone Attack Prevention and Detection (TracingTraitor)
//		myACAPD.loadACAPD(fileName, key, classFlag, testFlag);
		
		if (sr.getAuthStatus()) {
			showCheckUserCorrect();

			// ---download encrypted Jar---
			sr.setJarFlag(jarFlag);
			sr.setFilePath(appSecurityEnhancer_url + "download/"
					+ fileName);
			// Log.i(TAG,"ProjectConfig.test[test_id]= "+ProjectConfig.test[test_id]);
			sr.setPostStatus(1);

			Thread t2 = new Thread(sr);
			t2.start();
			// Log.i(TAG, "download start");

			try {
				// wait Thread t
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}

		} else {
			showCheckUserError();

		}
		
		TracingTraitor.myTracingTraitor(fileName, ProjectConfig.personal_key, classStatus);

		// --- dismiss progressDialog ---
		if (progressDialog != null) {
			progressDialog.dismiss();

		}
	}

}
