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
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import android.annotation.SuppressLint;
import android.util.Log;

public class TracingTraitor {
	private static final String TAG = "TracingTraitor";
	
	private String outputFileName = "decrypted.jar";
	private boolean isSuccess = false;	
	private boolean loadStatus = false;

	public void myTracingTraitor(String fileName, String[] key,
			String classStatus) {

		if (new File(outputFilePath + fileName).exists()) {
			// ---broadcast decryption---
			String decryptFileName = broadcastDecryption(fileName, key);
			// Log.i(TAG, "decryptFileName= " + decryptFileName);

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
		Log.i(TAG, "loading end");
	}

	public String broadcastDecryption(String fileName, String[] key) {
		String str = null;
//		Decrypt decfile = new Decrypt();

		// decrypt EB(session_key) -----
//		String session_key = decfile.decryptSessionKey(enable_block, key);
		String session_key = decryptSessionKey(enable_block, key);
		String temp = "0";
		for (int i = 1; i < session_key.length(); i++)
			temp += "0";
		// Log.i(TAG, "session_key= " + session_key+", temp= "+temp);

		if (session_key.equals(temp) || session_key == temp) {
			showPersonalKeyError();
			Log.e(TAG, "session_key is error");

//			/** here **/
//			session_key = ACAPDAsyncTask.test_session_key;
//			Log.i(TAG, "new session_key= " + session_key);
		} else {
			Log.i(TAG, "session_key= " + session_key);

			/** here **/
			// // for test
			// str = fileName;

			// boolean des_status = decfile.decryptJar(fileName, outputFilePath,
			// session_key);
//			boolean des_status = decfile.decryptJar3(fileName, outputFilePath,
//					session_key);
//			if (des_status)
//				str = decfile.getOutputFileName();
			boolean des_status = decryptJar3(fileName, outputFilePath,
					session_key);
			if (des_status)
				str = getOutputFileName();
		}

		return str;
	}

	public void tracing(String fileName) {
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
	
	public String decryptSessionKey(String[] code, String[] personalKey) {
		String str = "0";

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
				str += "0";
			// Log.i(TAG, "init_str= " + str);
			for (int i = 0; i < decrypted.length; i++) {
				str = xorHex(str, decrypted[i]);
				// Log.i(TAG, "str= " + str);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "session_key is error, Exception= " + e.getMessage());
		}

		return str;
	}
	
	public boolean decryptJar3(String fileName, String outputFilePath,
			String session_key){
		try {
			isSuccess = true;
			byte[] key = fileName.getBytes("UTF-8");
			new AESFiles().decrypt(new File(outputFilePath
					+ fileName), new File(outputFilePath
					+ outputFileName), key);
		} catch (UnsupportedEncodingException e) {
			isSuccess = false;
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		return isSuccess;
	}

//	/** here **/
//	@SuppressLint("NewApi")
//	public boolean decryptJar2(String fileName, String folderPath,
//			String session_key) {
//		// 解密保存
//		isSuccess = true;
//
//		decryptFile = new File(folderPath, fileName);
//		byte[] oldByte = new byte[(int) decryptFile.length()];
//
//		MCrypt mAES = new MCrypt(session_key);
//		// byte[] decrypted = null;
//		try {
//			fis = new FileInputStream(decryptFile);
//			fis.read(oldByte);
//
//			/** here **/
//			// decrypted = mAES.decrypt2(oldByte);
////			try {
////				String decrypted = new String(oldByte, StandardCharsets.UTF_8);
////				byte[] newByte = mAES.decryptCB3(decrypted);
////				File decryptFile333 = new File(folderPath, "test456.jar");
////				Log.i(TAG,
////						"decryptFile333 path= "
////								+ decryptFile333.getAbsolutePath());
////				fos = new FileOutputStream(decryptFile333);
////				fos.write(newByte);
////			} catch (Exception e) {
////				// TODO 自動產生的 catch 區塊
////				e.printStackTrace();
////				Log.e(TAG, "decryptFile333 error= " + e.getMessage());
////			}
//
//			try {
//				File decryptFile444 = new File(folderPath, "test789.jar");
//				Log.i(TAG,
//						"decryptFile444 path= "
//								+ decryptFile444.getAbsolutePath());
//				fos = new FileOutputStream(decryptFile444);
//				mAES.DecryptFile(session_key, decryptFile, decryptFile444);
//			} catch (Exception e) {
//				// TODO 自動產生的 catch 區塊
//				e.printStackTrace();
//				Log.e(TAG, "decryptFile444 error= " + e.getMessage());
//			}
//
//			try {
//				File decryptFile555 = new File(folderPath, "test012.jar");
//				Log.i(TAG,
//						"decryptFile555 path= "
//								+ decryptFile555.getAbsolutePath());
//				fos = new FileOutputStream(decryptFile555);
//				mAES.DecryptFile2(session_key, decryptFile, decryptFile555);
//			} catch (Exception e) {
//				// TODO 自動產生的 catch 區塊
//				e.printStackTrace();
//				Log.e(TAG, "decryptFile555 error= " + e.getMessage());
//			}
//
//			byte[] newByte = mAES.decryptCB(oldByte);
//
//			// 解密
//			decryptFile = new File(folderPath, outputFileName);
//			fos = new FileOutputStream(decryptFile);
//			fos.write(newByte);
//			fis.close();
//			fos.close();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			Log.e(TAG, "NullPointerException Error: " + e.getMessage());
//		} catch (FileNotFoundException e) {
//			isSuccess = false;
//			e.printStackTrace();
//			Log.e(TAG, "FileNotFoundException Error: " + e.getMessage());
//		} catch (IOException e) {
//			isSuccess = false;
//			e.printStackTrace();
//			Log.e(TAG, "IOException Error: " + e.getMessage());
//		} catch (Exception e) {
//			isSuccess = false;
//			e.printStackTrace();
//			Log.e(TAG, "Exception Error: " + e.getMessage());		
//		}
//
//		if (!isSuccess) {
//			Log.e(TAG, "decrypted error, decryptFile path= "
//					+ decryptFile.getAbsolutePath().toString());
//		}
//		return isSuccess;
//	} // end_decryptJar2

//	public boolean decryptJar(String fileName, String folderPath, String seed) {
//		// 解密保存
//		isSuccess = true;
//
//		decryptFile = new File(folderPath, fileName);
//		byte[] oldByte = new byte[(int) decryptFile.length()];
//
//		try {
//			fis = new FileInputStream(decryptFile);
//			fis.read(oldByte);
//
//			// 解密
//			byte[] newByte = AESUtils.decryptFile(seed, oldByte);
//			decryptFile = new File(folderPath, outputFileName);
//			fos = new FileOutputStream(decryptFile);
//			fos.write(newByte);
//
//		} catch (FileNotFoundException e) {
//			isSuccess = false;
//			e.printStackTrace();
//		} catch (IOException e) {
//			isSuccess = false;
//			e.printStackTrace();
//		} catch (Exception e) {
//			isSuccess = false;
//			e.printStackTrace();
//		}
//
//		try {
//			fis.close();
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.e(TAG, "IOException Error: " + e.getMessage());
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			Log.e(TAG, "NullPointerException Error: " + e.getMessage());
//		}
//
//		if (!isSuccess) {
//			Log.e(TAG, "decrypted error");
//			Log.i(TAG, "decrypted path= "
//					+ decryptFile.getAbsolutePath().toString());
//		}
//		return isSuccess;
//	} // end_decryptJar

	public String xorHex(String a, String b) {
		// TODO: Validation
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

	public String getOutputFileName() {
		return outputFileName;
	}

}
