package trustedappframework.subprojecttwo.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import android.annotation.SuppressLint;
import android.util.Log;

public class Decrypt {
	private static final String TAG = "Decrypt";

	private String outputFileName = "decrypted.jar";
	private File decryptFile;

	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	private boolean isSuccess = false;

	public String xor(String a, String b) {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < a.length(); k++)
			sb.append((a.charAt(k) ^ b.charAt(k
					+ (Math.abs(a.length() - b.length())))));
		String result;
		result = sb.toString();
		return result;
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

	/** here **/
	@SuppressLint("NewApi")
	public boolean decryptJar2(String fileName, String folderPath,
			String session_key) {
		// 解密保存
		isSuccess = true;

		decryptFile = new File(folderPath, fileName);
		byte[] oldByte = new byte[(int) decryptFile.length()];

		MCrypt mAES = new MCrypt(session_key);
		// byte[] decrypted = null;
		try {
			fis = new FileInputStream(decryptFile);
			fis.read(oldByte);

			/** here **/
			// decrypted = mAES.decrypt2(oldByte);
			try {
				String decrypted = new String(oldByte, StandardCharsets.UTF_8);
				byte[] newByte = mAES.decryptCB3(decrypted);
				File decryptFile333 = new File(folderPath, "test456.jar");
				Log.i(TAG,
						"decryptFile333 path= "
								+ decryptFile333.getAbsolutePath());
				fos = new FileOutputStream(decryptFile333);
				fos.write(newByte);
			} catch (Exception e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
				Log.e(TAG, "decryptFile333 error= " + e.getMessage());
			}

			try {
				File decryptFile444 = new File(folderPath, "test789.jar");
				Log.i(TAG,
						"decryptFile444 path= "
								+ decryptFile444.getAbsolutePath());
				fos = new FileOutputStream(decryptFile444);
				mAES.DecryptFile(session_key, decryptFile, decryptFile444);
			} catch (Exception e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
				Log.e(TAG, "decryptFile444 error= " + e.getMessage());
			}

			try {
				File decryptFile555 = new File(folderPath, "test012.jar");
				Log.i(TAG,
						"decryptFile555 path= "
								+ decryptFile555.getAbsolutePath());
				fos = new FileOutputStream(decryptFile555);
				mAES.DecryptFile2(session_key, decryptFile, decryptFile555);
			} catch (Exception e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
				Log.e(TAG, "decryptFile555 error= " + e.getMessage());
			}

			byte[] newByte = mAES.decryptCB(oldByte);

			// 解密
			decryptFile = new File(folderPath, outputFileName);
			fos = new FileOutputStream(decryptFile);
			fos.write(newByte);
			fis.close();
			fos.close();
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
		}

		if (!isSuccess) {
			Log.e(TAG, "decrypted error, decryptFile path= "
					+ decryptFile.getAbsolutePath().toString());
		}
		return isSuccess;
	} // end_decryptJar2

	public boolean decryptJar(String fileName, String folderPath, String seed) {
		// 解密保存
		isSuccess = true;

		decryptFile = new File(folderPath, fileName);
		byte[] oldByte = new byte[(int) decryptFile.length()];

		try {
			fis = new FileInputStream(decryptFile);
			fis.read(oldByte);

			// 解密
			byte[] newByte = AESUtils.decryptFile(seed, oldByte);
			decryptFile = new File(folderPath, outputFileName);
			fos = new FileOutputStream(decryptFile);
			fos.write(newByte);

		} catch (FileNotFoundException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}

		try {
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "IOException Error: " + e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.e(TAG, "NullPointerException Error: " + e.getMessage());
		}

		if (!isSuccess) {
			Log.e(TAG, "decrypted error");
			Log.i(TAG, "decrypted path= "
					+ decryptFile.getAbsolutePath().toString());
		}
		return isSuccess;
	} // end_decryptJar

	public String xorHex(String a, String b) {
		// TODO: Validation
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

	private char toHex(int nybble) {
		if (nybble < 0 || nybble > 15) {
			throw new IllegalArgumentException();
		}
		return "0123456789abcdef".charAt(nybble);
	}

	public String getOutputFileName() {
		return outputFileName;
	}

}
