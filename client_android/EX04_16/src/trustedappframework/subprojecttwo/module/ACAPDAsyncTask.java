package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.pd;
import static trustedappframework.subprojecttwo.module.ProjectConfig.updateProgressDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ACAPDAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "ACAPDAsyncTask";

	private ProgressDialog progressDialog;

	private SendPostRunnable sr = null;
	private ACAPD myACAPD = null;
	private String fileName = null;
	private int classStatus = 0;
	private int test_id = 0;
	private String key = null;

	public ACAPDAsyncTask(String fileName, String key, int classStatus,int test_id) {
		super();

		this.fileName = fileName;
		this.key = key;
		this.classStatus = classStatus;
		this.test_id = test_id;

		myACAPD = new ACAPD(fileName);
		sr = ACAPD.sr;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = pd.getProgressDialog();
		int progressDialog_checkUser = 0;
		updateProgressDialog(progressDialog_checkUser);
		progressDialog.show();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// ---check user ---
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
		// App Clone Attack Prevention and Detection (ACAPD)
		myACAPD.loadACAPD(fileName, key, classStatus, test_id);

		if (progressDialog != null) {
			progressDialog.dismiss();

		}
	}

}
