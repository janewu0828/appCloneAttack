package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showToast;
import irdc.ex04_16.R;

import java.io.File;

import trustedappframework.subprojecttwo.interfaces.Load;
import android.os.Environment;
import android.util.Log;

// App Clone Attack Prevention and Detection (ACAPD)
public class ACAPD {
	private static final String TAG = "ACAPD";

	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/sub_project2/";
	public static String outputFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/project/";

	public static SendPostRunnable sr;

	public static String loadPersonalKey;
	private static int enable_block_length = 3;
	public static String[] enable_block = new String[enable_block_length];

	public static boolean downloadStatus = false;

	public ACAPD() {
		// send http post Runnable
		sr = new SendPostRunnable();
	}

	public void loadACAPD(String class_separation_segment, String key) {
		// check network setting on device
		checkConnection();

		// input decryption key
		loadPersonalKey = PersonalKeyManager.read(key);
		if (loadPersonalKey.length() > 0 && !loadPersonalKey.trim().isEmpty()) {
			// Log.i(TAG, "loadPersonalKey= " + loadPersonalKey);

			// ---check user and download encrypted Jar---
			boolean auth = identityAuthenticate(class_separation_segment);
			Log.i(TAG, "auth= " + auth);

			if (auth) {
				// broadcast encryption, dynamic loading, tracing log, key
				// update
				loadACAPD2(class_separation_segment, loadPersonalKey);
			}

		} else {
			showPersonalKeyError();
			Log.e(TAG, "Error: " + "loadPersonalKey= null");
		}
	}

	public static void loadACAPD2(String class_separation_segment, String key) {
		if (downloadStatus) {
			// broadcast encryption
			String decryptFileName = broadcastEncryption(
					class_separation_segment, key);
			// dynamic loading
			if (decryptFileName != null) {
				boolean loadStatus = dynamicLoading(decryptFileName, key);
				// Log.i(TAG, "loadStatus=" + loadStatus);

				if (loadStatus) {
					// tracing traitors
					tracingLog(class_separation_segment);

					// key update info
					String keyStatus = sr.getSession().get(
							"s_personal_key_update_status");
					// Log.i(TAG, "keyStatus=" + keyStatus);

					if (keyStatus == "true") {
						PersonalKeyManager.update(sr.getSession(),
								ProjectConfig.personal_key);
						// Log.e(TAG, "key update");
					}
				} else {
					showPersonalKeyError();
					Log.e(TAG, "Error: " + "decryptFileName is not exist, "
							+ decryptFileName);
				}

			} else {
				Log.i(TAG, "decryptFileName= null");
			}
		}
	}

	public boolean identityAuthenticate(String class_separation_segment) {
		// ---check user ---
		sr.setPostStatus(0);

		// start a Thread, the data to be transferred into the Runnable
		Thread t = new Thread(sr);
		t.start();
		Log.i(TAG, "start checkUser");

		try {
			// wait Thread t
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		if (sr.getAuthStatus()) {
			showCheckUserCorrect();

			// ---check encrypted Jar---
			if (!(new File(outputFilePath + class_separation_segment).exists())) {
				// Log.i(TAG, "encrypted Jar is not exist");

				// ---download encrypted Jar---
				sr.setFileName(class_separation_segment);
				sr.setPostStatus(1);

				Thread t2 = new Thread(sr);
				t2.start();
				// Log.e(TAG, "start download");

				try {
					// wait Thread t
					t2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}
			} else {
				downloadStatus = true;
				// Log.i(TAG, "encrypted Jar is exist, downloadStatus= "
				// + downloadStatus);
			}

			return true;
		} else {
			// show a Alert Dialog that Authentication is failed
			showCheckUserError();

			return false;
		}
	}

	public static String broadcastEncryption(String class_separation_segment,
			String key) {
		String fileName = null;
		Decrypt decfile = new Decrypt();

		if (new File(outputFilePath + class_separation_segment).exists()) {
			// Log.i(TAG, "encrypted Jar is exist");

			// decrypt EB(session_key) -----
			String session_key = decfile.decryptSessionKey(enable_block, key);
			// Log.i(TAG, "session_key=" + session_key);
			// session_key="589040359334464";

			// decrypt CB(Jar) -----
			decfile.decryptJar(class_separation_segment, outputFilePath,
					session_key);

			fileName = decfile.getOutputFileName();
		} else {
			Log.i(TAG, "encrypted Jar is not exist");
		}

		return fileName;
	}

	public static boolean dynamicLoading(String loadFileName, String personalKey) {
		if (new File(outputFilePath + loadFileName).exists()) {
			// dynamic loading -----
			Load ld = new Load();
			ld.loadJar(loadFileName, outputFilePath);

			return true;
		} else {

			return false;
		}

	}

	public static void tracingLog(String fileName) {
		sr.setFileName(fileName);
		sr.setPostStatus(2);

		Thread t = new Thread(sr);
		t.start();
		Log.i(TAG, "start tracing");

		try {
			// wait Thread t
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		// Log.i(TAG, "tracing= " + sr.getTracingStatus());
	}
}
