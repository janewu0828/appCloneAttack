package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPD.outputFilePath;

import java.io.BufferedReader;
import java.io.FileReader;

import android.util.Log;

public class PersonalKeyManager {
	private static final String TAG = "PersonalKeyManager";

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
			Log.e(TAG, "error: " + e.getMessage());
		}
		return str;
	}
}
