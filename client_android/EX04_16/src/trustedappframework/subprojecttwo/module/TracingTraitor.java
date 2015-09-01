package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.personal_key;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.updateProgressDialog;

import java.io.File;

import trustedappframework.subprojecttwo.interfaces.Load;
import android.os.Environment;
import android.util.Log;

public class TracingTraitor {
	private static final String TAG = "TracingTraitor";
	
	public static String outputFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/project/";
	public static String fileName = null;
	public static int jarFlag = 0;
	public static int classStatus = 0;
	public static int test_id = 0;

	public static SendPostRunnable sr;

	public static String[] personalKey = new String[personal_key.length];
	public static String loadPersonalKey = null;
	private static int enable_block_length = 3;
	public static String[] enable_block = new String[enable_block_length];
	public static String cipher_jar_uri = null;

	public static boolean downloadStatus = false, loadStatus = false;
	private final static int progressDialog_checkJar = 1,
			progressDialog_dynamicLoadingJar = 2;
	
	public static String broadcastDecryption(String fileName, String[] key) {
		String str = null;
		personalKey=ProjectConfig.personal_key;
		Decrypt decfile = new Decrypt();

//		if (new File(outputFilePath + fileName).exists()) {
			// decrypt EB(session_key) -----
			String session_key = decfile.decryptSessionKey(enable_block, key);
			Log.i(TAG, "session_key= " + session_key);

			if (session_key != null) {
				/** here **/
				// decrypt CB(Jar) -----
				// decfile.decryptJar(fileName, outputFilePath, session_key);
				str = decfile.getOutputFileName();
			} else {
				showPersonalKeyError();
				Log.e(TAG, "session_key is null");
			}

//		} else {
//			Log.i(TAG, "encrypted Jar is not exist");
//		}

		return str;
	}

	public static void myTracingTraitor(String fileName, String[] key,
			String classStatus) {
//		if (downloadStatus) {
//			updateProgressDialog(progressDialog_checkJar);

		if (new File(outputFilePath + fileName).exists()) {
			// ---broadcast encryption---
			String decryptFileName = broadcastDecryption(fileName, key);
//			int test_id=0;
//			 String decryptFileName =
//			 ProjectConfig.class_separation_segment[test_id];
			 Log.i(TAG,"decryptFileName= "+decryptFileName);
			 
			// ---dynamic loading---
			if (decryptFileName != null) {
				updateProgressDialog(progressDialog_dynamicLoadingJar);

				if (new File(outputFilePath + decryptFileName).exists()) {
					// dynamic loading -----
					Load ld = new Load();
					loadStatus = ld.loadJar(decryptFileName, outputFilePath,
							classStatus);
				}
				// Log.i(TAG, "loadStatus=" + loadStatus);

				if (loadStatus) {
					// ---tracing traitors---
					tracing(fileName);

					// ---key update---
					String keyStatus = sr.getSession().get(
							"s_personal_key_update_status");
					// Log.i(TAG, "keyStatus=" + keyStatus);

					if (keyStatus == "true") {
						PersonalKeyManager.update(sr.getSession(),
								ProjectConfig.personal_key);
						// Log.e(TAG, "key update");
					}
				} else {
					Log.e(TAG, "Error: " + "decryptFileName is not exist, "
							+ decryptFileName);

				}

			} else {

				Log.i(TAG, "decryptFileName= null");
			}
		} else {
			Log.i(TAG, "encrypted Jar is not exist");
		}
//		}

	}

	public static void tracing(String fileName) {
		ACAPDAsyncTask.sr.setFileName(fileName);
		ACAPDAsyncTask.sr.setPostStatus(2);

		Thread t = new Thread(sr);
		t.start();
		// Log.e(TAG, "tracing start");

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
