package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserCorrect;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showCheckUserError;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;

import java.io.File;
import java.util.HashMap;

import trustedappframework.subprojecttwo.interfaces.Load;
import android.os.Environment;
import android.util.Log;

// App Clone Attack Prevention and Detection (ACAPD)
public class ACAPD {
	private static final String TAG = "ACAPD";

	public static String appSecurityEnhancer_url = "http://140.118.19.64:8081/sub_project2/";
	public static String outputFilePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/project/";

	private SendPostRunnable sr;
	private Thread t;

	// show a message of authentication is successful in first time
	public static boolean isShowTxt = false;

	public ACAPD(String class_separation_segment, String personal_key) {
		// check network setting on device
		checkConnection();

		// show a message of authentication is successful in first time
		isShowTxt = true;

		// input decryption key
		personal_key = loadPersonalKey(personal_key);
		if (personal_key != null) {
			Log.i(TAG, "personalKey= " + personal_key);

			// ---check user and download encrypted Jar---
			boolean auth = loadACAPD(class_separation_segment);
			Log.i(TAG, "auth= " + auth);

			// broadcast encryption
			String decryptFileName = broadcastEncryption(
					class_separation_segment, personal_key);
			// dynamic loading
			if (decryptFileName != null) {
				boolean loadStatus = dynamicLoading(decryptFileName,
						personal_key);
				// tracing traitors
				tracingLog(class_separation_segment, personal_key,
						sr.getSession());
				if (!loadStatus) {
					showPersonalKeyError();
					Log.e(TAG, "Error: " + decryptFileName);
				}
			}
		}
	}

	public boolean loadACAPD(String class_separation_segment) {
		// ---check user and download encrypted Jar---
		sr = new SendPostRunnable(class_separation_segment);
		// start a Thread, the data to be transferred into the Runnable,
		// Thread execute
		t = new Thread(sr);
		t.start();

		try {
			// wait Thread t
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		if (sr.getAuthStatus()) {
			showCheckUserCorrect();

			return true;
		} else {
			// show a Alert Dialog that Authentication is failed
			showCheckUserError();

			return false;
		}
	}

	public String loadPersonalKey(String fileName) {
		// tracing traitor identity
		String personalKey = PersonalKeyManager.read(fileName);
		// Log.i(TAG, "personal_key= " + personal_key);

		return personalKey;
	}

	public String broadcastEncryption(String class_separation_segment,
			String personalKey) {
		String fileName = null;

		if (new File(outputFilePath + class_separation_segment).exists()) {
			// Log.i(TAG, "Jar is exist");

			// decrypt EB(session_key) -----
			String session_key = Decrypt.decryptSessionKey(
					sr.getSession().get("s_enable_block"),
					sr.getSession().get("s_enable_block2"), sr.getSession()
							.get("s_enable_block3"), personalKey);
			System.out.println("session_key=" + session_key);
			// session_key="589040359334464";

			// decrypt CB(Jar) -----
			Decrypt decfile = new Decrypt(class_separation_segment,
					outputFilePath, session_key);
			decfile.decryptJar();

			fileName = decfile.getOutputFileName();
		}

		return fileName;
	}

	public boolean dynamicLoading(String loadFileName, String personalKey) {
		if (new File(outputFilePath + loadFileName).exists()) {
			// dynamic loading -----
			Load ld = new Load(loadFileName, outputFilePath);
			ld.loadJar();

			return true;
		} else {

			return false;
		}

	}

	public void tracingLog(String fileName, String personalKey,
			HashMap<String, String> session) {
		sr.setFileName(fileName);
		sr.setPersonalKey(personalKey);
		sr.setSession(session);
		t = new Thread(sr);
		t.start();

		try {
			// wait Thread t
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		Log.i(TAG, "Tracing: " + sr.getTracingStatus());
	}

}
