package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.outputFilePath;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class PersonalKeyManager {
	private static final String TAG = "PersonalKeyManager";
	
	// tracing traitors -----
	public static String[] personal_key = null;
	
	public static void checkPersonalKey() {
		for (int i = 0; i < personal_key.length; i++) {
			String key = PersonalKeyManager.read(personal_key[i]);
			ACAPDAsyncTask.setPersonalKey(key, i);

			if (ACAPDAsyncTask.getPersonalKey(i).length() > 0
					&& !ACAPDAsyncTask.getPersonalKey(i).trim().isEmpty()) {
				// Log.e(TAG, "personalKey[" + i + "]= "
				// + ACAPDAsyncTask.getPersonalKey(i));
			} else {
				showPersonalKeyError();
				Log.e(TAG, "Error: " + "personalKey= null");
			}
		}

	}

	public static String read(String fileName) {
		String str = "";
		try {
			// 建立FileReader物件，並設定讀取的檔案為SD卡中的txt檔案
			FileReader fr = new FileReader(outputFilePath + fileName);
			// 將BufferedReader與FileReader做連結
			BufferedReader br = new BufferedReader(fr);

			String temp = br.readLine(); // readLine()讀取一整行
			while (temp != null) {
				str += temp;
				temp = br.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
		return str;
	}

	public static void update(HashMap<String, String> session,
			String[] personal_key) {
		ArrayList<String> newKeyList = new ArrayList<String>(); // 指定是String的型態
		newKeyList.add(session.get("s_personal_key").toString());
		newKeyList.add(session.get("s_personal_key2").toString());
		newKeyList.add(session.get("s_personal_key3").toString());

		try {
			for (int i = 0; i < personal_key.length; i++) {
				// System.out.println("personal_keyN=" + i + ", "
				// + personal_keyN[i]);
				// System.out
				// .println("newKeyList=" + i + ", " + newKeyList.get(i));

				FileWriter fw = new FileWriter(
						outputFilePath + personal_key[i], false);
				BufferedWriter bw = new BufferedWriter(fw); // 將BufferedWeiter與FileWrite物件做連結
				bw.write(newKeyList.get(i));
				bw.newLine();
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}

	}
}
