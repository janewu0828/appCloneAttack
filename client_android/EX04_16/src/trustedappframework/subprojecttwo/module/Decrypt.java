package trustedappframework.subprojecttwo.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
		String str = "0000000000000000";
		AESUtils mAES = new AESUtils(personalKey[0]);
		try {

//			 Log.i(TAG, "code=" + code[0]);
//			 Log.i(TAG, "code=" + code[1]);
//			 Log.i(TAG, "code=" + code[2]);
			String[] decrypted = new String[code.length];
			for (int i = 0; i < decrypted.length; i++) {
				mAES.setPersonalKey(personalKey[i]);
				decrypted[i] = new String(mAES.decryptEB(code[i]));
				Log.i(TAG, "decrypted[" + i + "]" + "= " + decrypted[i]);
			}
			for (int i = 0; i < decrypted.length; i++) {
				str = xorHex(str, decrypted[i]);
				Log.i(TAG, "str = " + str);
			}

			Log.i(TAG, "session_key= " + str);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

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
		// return "0123456789ABCDEF".charAt(nybble);
		return "0123456789abcdef".charAt(nybble);
	}

	public void decryptJar(String fileName, String folderPath, String seed) {
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
			// Log.i(TAG, "decrypted path= "
			// + decryptFile.getAbsolutePath().toString());
		}
	}

	public String getOutputFileName() {
		return outputFileName;
	}

}
