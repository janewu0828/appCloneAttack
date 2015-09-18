package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.pd;
import static trustedappframework.subprojecttwo.module.PersonalKeyManager.personal_key;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

//App Clone Attack Prevention and Detection (ACAPD)
public class ACAPDAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "ACAPDAsyncTask";

	private static ProgressDialog progressDialog;

	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/sub_project2";

	public static String outputFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/project/";

	public static SendPostRunnable sr = null;
	public static String fileName = null;
	public static String classStatus = null;
	private String jarFlag = null;

	public static String[] personalKey = new String[personal_key.length];
	private static int enable_block_length = 3;
	public static String[] enable_block = new String[enable_block_length];
	public static String cipher_jar_uri = null;

	/** here **/
	public static String test_session_key = null;

	public ACAPDAsyncTask(String fileName, String classStatus, String jarFlag) {
		super();

		ACAPDAsyncTask.fileName = fileName;
		ACAPDAsyncTask.classStatus = classStatus;
		this.jarFlag = jarFlag;
	}

	@Override
	protected void onPreExecute() {
		sr = new SendPostRunnable();

		progressDialog = pd.getProgressDialog();
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

		if (sr.getAuthStatus()) {
			showCheckUserCorrect();

			// ---download encrypted Jar---
			sr.setJarFlag(jarFlag);
			sr.setFilePath(cipher_jar_uri);
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

		// TracingTraitor.myTracingTraitor(fileName, personalKey, classStatus);
	}

	public static void pd_del() {
		// --- dismiss progressDialog ---
		if (progressDialog != null) {
			progressDialog.dismiss();

		}
	}

	public static String getPersonalKey(int i) {
		return personalKey[i];
	}

	public static void setPersonalKey(String personalKey, int i) {
		ACAPDAsyncTask.personalKey[i] = personalKey;
	}

}
