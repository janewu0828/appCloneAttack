package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.outputFilePath;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.enable_block;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.personalKey;
import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.sr;
import static trustedappframework.subprojecttwo.module.ProjectConfig.showPersonalKeyError;
import trustedappframework.subprojecttwo.interfaces.Load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class TracingTraitor {
	private static final String TAG = "TracingTraitor";

	private static String decryptFileName = "decrypted.jar";

	private File decryptFile;
	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	private boolean isSuccess = false;

	public static void myTracingTraitor(String fileName, String[] key,
			String classStatus) {

		if (new File(outputFilePath + fileName).exists()) {
			// ---broadcast decryption---
			boolean decryptStatus = broadcastDecryption(fileName, key);
			// Log.i(TAG, "decryptFileName= " + decryptFileName);

			if (decryptStatus) {
				boolean loadStatus = false;
				/** here **/
				// if (new File(outputFilePath + decryptFileName).exists()) {
				if (new File(outputFilePath + fileName).exists()) {
					// dynamic loading -----
					Load ld = new Load();
					// loadStatus = ld.loadJar(decryptFileName, outputFilePath,
					// classStatus);
					loadStatus = ld.loadJar(fileName, outputFilePath,
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

	public static boolean broadcastDecryption(String fileName, String[] key) {
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
			// decryptStatus = decryptJar2(fileName, outputFilePath,
			// sessionKey);

			// for test
			decryptStatus = true;

		}

		return decryptStatus;
	}

	public static void tracing(String fileName) {
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

	public static String decryptEB(String[] code, String[] personalKey) {
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

	/** here **/
	// public void decryptJar3(String fileName, String folderPath, String seed)
	// {
	// MCrypt mAES = new MCrypt(seed);
	//
	// try {
	// byte[] key = mAES.getKey().getBytes("UTF-8");
	// byte[] iv = mAES.getIv().getBytes("UTF-8");
	// byte[] b = null;
	// byte[] decryptedData = mAES.decryptCB2(key, iv, b);
	// } catch (Exception e) {
	// // TODO 自動產生的 catch 區塊
	// e.printStackTrace();
	// }
	//
	// }

	public boolean decryptJar2(String fileName, String folderPath, String seed) {
		// 解密保存
		isSuccess = true;

		decryptFile = new File(folderPath, fileName);
		byte[] oldByte = new byte[(int) decryptFile.length()];

		MCrypt mAES = new MCrypt(seed);
		// byte[] decrypted = null;
		try {
			fis = new FileInputStream(decryptFile);
			fis.read(oldByte);

			/** here **/
			byte[] newByte = mAES.decryptCB(oldByte);
			// decrypted = mAES.decrypt2(oldByte);

			// 解密
			decryptFile = new File(folderPath, decryptFileName);
			fos = new FileOutputStream(decryptFile);
			fos.write(newByte);
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.e(TAG, "NullPointerException Error: " + e.getMessage());
		} catch (FileNotFoundException e) {
			isSuccess = false;
			e.printStackTrace();
			Log.e(TAG, "FileNotFoundException Error: " + e.getMessage());
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
			Log.e(TAG, "IOException Error: " + e.getMessage());
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			Log.e(TAG, "Exception Error: " + e.getMessage());
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				System.out.println("IOException Error while closing stream: "
						+ e.getMessage());
			} catch (NullPointerException e) {
				e.printStackTrace();
				Log.e(TAG, "NullPointerException Error: " + e.getMessage());
			} catch (Exception e) {
				isSuccess = false;
				e.printStackTrace();
				Log.e(TAG, "Exception Error: " + e.getMessage());
			}
		}

		if (!isSuccess) {
			Log.e(TAG, "decrypted error, decryptFile path= "
					+ decryptFile.getAbsolutePath().toString());
		}
		return isSuccess;
	}

	// public void decryptJar(String fileName, String folderPath, String seed) {
	// // 解密保存
	// isSuccess = true;
	//
	// decryptFile = new File(folderPath, fileName);
	// byte[] oldByte = new byte[(int) decryptFile.length()];
	//
	// try {
	// fis = new FileInputStream(decryptFile);
	// fis.read(oldByte);
	//
	// // 解密
	// byte[] newByte = AESUtils.decryptFile(seed, oldByte);
	// decryptFile = new File(folderPath, outputFileName);
	// fos = new FileOutputStream(decryptFile);
	// fos.write(newByte);
	//
	// } catch (FileNotFoundException e) {
	// isSuccess = false;
	// e.printStackTrace();
	// } catch (IOException e) {
	// isSuccess = false;
	// e.printStackTrace();
	// } catch (Exception e) {
	// isSuccess = false;
	// e.printStackTrace();
	// }
	//
	// try {
	// fis.close();
	// fos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// Log.e(TAG, "IOException Error: " + e.getMessage());
	// } catch (NullPointerException e) {
	// e.printStackTrace();
	// Log.e(TAG, "NullPointerException Error: " + e.getMessage());
	// }
	//
	// if (!isSuccess) {
	// Log.e(TAG, "decrypted error");
	// // Log.i(TAG, "decrypted path= "
	// // + decryptFile.getAbsolutePath().toString());
	// }
	// }

	public static String xorHex(String a, String b) {
		char[] chars = new char[a.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
		}
		return new String(chars);
	}

	private static int fromHex(char c) {
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

	private static char toHex(int nybble) {
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