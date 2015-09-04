package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.outputFilePath;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.enable_block;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.test_session_key;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.personalKey;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.sr;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;

import java.io.File;

import trustedappframework.subprojecttwo.interfaces.Load;
import android.util.Log;

public class TracingTraitor {
	private static final String TAG = "TracingTraitor";

	public static boolean loadStatus = false;

	public static void myTracingTraitor(String fileName, String[] key,
			String classStatus) {

		if (new File(outputFilePath + fileName).exists()) {
			// ---broadcast decryption---
			String decryptFileName = broadcastDecryption(fileName, key);
			Log.i(TAG, "decryptFileName= " + decryptFileName);

			if (decryptFileName != null) {

				if (new File(outputFilePath + decryptFileName).exists()) {
					// dynamic loading -----
					Load ld = new Load();
					loadStatus = ld.loadJar(decryptFileName, outputFilePath,
							classStatus);
				}
				Log.i(TAG, "loadStatus=" + loadStatus);

				if (loadStatus) {
					// ---tracing traitors---
					tracing(fileName);
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

		ACAPDAsyncTask.pd_del();
		Log.i(TAG, "ccccccccccccccccccccccc");
	}

	public static String broadcastDecryption(String fileName, String[] key) {
		String str = null;
		Decrypt decfile = new Decrypt();

		// if (new File(outputFilePath + fileName).exists()) {
		// decrypt EB(session_key) -----
		String session_key = decfile.decryptSessionKey(enable_block, key);
		// Log.i(TAG, "session_key= " + session_key);
		/** here **/
		String temp = "0";
		for (int i = 0; i < session_key.length(); i++)
			temp += "0";
		// Log.i(TAG, "session_key= " + session_key+", temp= "+temp);
		if (session_key.equals(temp) || session_key == temp) {
			showPersonalKeyError();
			Log.e(TAG, "session_key is error");
			// session_key = test_session_key;
			// Log.i(TAG, "failed, modifiy session_key= " + session_key);
		} else {
			Log.i(TAG, "session_key= " + session_key);

			decfile.decryptJar2(fileName, outputFilePath, session_key);
			str = decfile.getOutputFileName();
		}

		// } else {
		// Log.i(TAG, "encrypted Jar is not exist");
		// }

		return str;
	}

	public static void tracing(String fileName) {
		sr.setFileName(fileName);
		sr.setPostStatus(2);

		Thread t = new Thread(sr);
		t.start();
		Log.e(TAG, "tracing start");

		try {
			// wait Thread t
			t.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());

		}

		Log.i(TAG, "tracing= " + sr.getTracingStatus());
		if (sr.getTracingStatus()) {
			/** here **/
			// // ---key update---
			String keyStatus = sr.getSession().get(
					"s_personal_key_update_status");
			Log.i(TAG, "keyStatus=" + keyStatus);

			if (keyStatus == "true") {
				PersonalKeyManager.update(sr.getSession(), personalKey);
				Log.e(TAG, "key update");
			}
		}

	}

}
