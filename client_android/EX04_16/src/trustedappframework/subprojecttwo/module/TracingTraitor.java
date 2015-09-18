package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.outputFilePath;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.enable_block;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.personalKey;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.sr;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;

import trustedappframework.subprojecttwo.interfaces.Load;

import java.io.File;
import java.io.UnsupportedEncodingException;
import android.util.Log;

public class TracingTraitor {
	private static final String TAG = "TracingTraitor";

	private String decryptFileName = "decrypted.jar";

	public void myTracingTraitor(String fileName, String[] key,
			String classStatus) {

		if (new File(outputFilePath + fileName).exists()) {
			// ---broadcast decryption---
			boolean decryptStatus = broadcastDecryption(fileName, key);
			// Log.i(TAG, "decryptFileName= " + decryptFileName);

			if (decryptStatus) {
				boolean loadStatus = false;

				if (new File(outputFilePath + decryptFileName).exists()) {
					// dynamic loading -----
					Load ld = new Load();
					loadStatus = ld.loadJar(decryptFileName, outputFilePath,
							classStatus);
					Log.i(TAG, "loadStatus=" + loadStatus);
				}

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
		Log.i(TAG, "loading end");
	}

	public boolean broadcastDecryption(String fileName, String[] key) {
		boolean decryptStatus = false;

		// decrypt EB(session_key) -----
		String sessionKey = decryptEB(enable_block, key);
		String temp = "0";
		for (int i = 1; i < sessionKey.length(); i++)
			temp += "0";
		// Log.i(TAG, "sessionKey= " + sessionKey+", temp= "+temp);

		if (sessionKey.equals(temp) || sessionKey == temp) {
			showPersonalKeyError();
			Log.e(TAG, "sessionKey is error");

		} else {
			Log.i(TAG, "sessionKey= " + sessionKey);

			/** here **/
			sessionKey = ACAPDAsyncTask.test_session_key;
			Log.i(TAG, "new sessionKey= " + sessionKey);

			decryptStatus = decryptCB(fileName, outputFilePath, sessionKey);
		}

		return decryptStatus;
	}

	public void tracing(String fileName) {
		sr.setFileName(fileName);
		sr.setPostStatus(2);

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

		Log.i(TAG, "tracing= " + sr.getTracingStatus());
		if (sr.getTracingStatus()) {
			/** here **/
			// ---key update---
			String keyStatus = sr.getSession().get(
					"s_personal_key_update_status");
			Log.i(TAG, "keyStatus=" + keyStatus);

			if (keyStatus == "true") {
				PersonalKeyManager.update(sr.getSession(), personalKey);
				Log.e(TAG, "key update");
			}
		}
	}

	public String decryptEB(String[] code, String[] personalKey) {
		String sessionKey = "0";

		MCrypt mAES = new MCrypt(personalKey[0]);
		try {
			// for (int i = 0; i < code.length; i++) {
			// Log.i(TAG, "code= " + code[i]);
			// Log.i(TAG, "key= " + personalKey[i]);
			// }

			String[] decrypted = new String[code.length];
			for (int i = 0; i < decrypted.length; i++) {
				mAES.setKey(personalKey[i]);
				// Log.i(TAG, "key= " + mAES.getKey());
				decrypted[i] = new String(mAES.decryptEB(code[i]));
				Log.i(TAG, "decrypted[" + i + "]" + "= " + decrypted[i]);
			}

			for (int i = 1; i < decrypted[0].length(); i++)
				sessionKey += "0";
			// Log.i(TAG, "init_str= " + str);
			for (int i = 0; i < decrypted.length; i++) {
				sessionKey = xorHex(sessionKey, decrypted[i]);
				// Log.i(TAG, "str= " + str);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "sessionKey is error, Exception= " + e.getMessage());
		}

		return sessionKey;
	}

	public boolean decryptCB(String fileName, String outputFilePath,
			String sessionKey) {
		try {
			byte[] key = sessionKey.getBytes("UTF-8");
			new AESFiles().decrypt(new File(outputFilePath + fileName),
					new File(outputFilePath + decryptFileName), key);
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			return false;
		}

	}

	public String xorHex(String a, String b) {
		char[] chars = new char[a.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
		}
		return new String(chars);
	}

	private int fromHex(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		throw new IllegalArgumentException();
	}

	private char toHex(int nybble) {
		if (nybble < 0 || nybble > 15) {
			throw new IllegalArgumentException();
		}
		return "0123456789abcdef".charAt(nybble);
	}

	public String xor(String a, String b) {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < a.length(); k++)
			sb.append((a.charAt(k) ^ b.charAt(k
					+ (Math.abs(a.length() - b.length())))));
		String result;
		result = sb.toString();
		return result;
	}

}
