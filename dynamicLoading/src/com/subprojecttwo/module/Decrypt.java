package com.subprojecttwo.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class Decrypt {
	private static final String TAG = "Decrypt";
	private String seed = "";

	private String fileName = "";
	private String outputFileName = "decrypt.jar";
	private String folderPath = "";
	private File decryptFile;

	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	boolean isSuccess = false;

	public Decrypt(String fileName, String folderPath, String seed) {
		super();

		this.fileName = fileName;
		this.folderPath = folderPath;
		this.seed = seed;
	}

	public static String xor(String a, String b) {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < a.length(); k++)
			sb.append((a.charAt(k) ^ b.charAt(k
					+ (Math.abs(a.length() - b.length())))));
		String result;
		result = sb.toString();
		return result;
	}

	public static String decryptSessionKey(String code, String code2,
			String code3, String personal_key) {
		String str = null;
		AESUtils mAES = new AESUtils(personal_key);
		try {
			String decrypted = new String(mAES.decryptEB(code));
			String decrypted2 = new String(mAES.decryptEB(code2));
			String decrypted3 = new String(mAES.decryptEB(code3));
			// System.out.println("decrypted= " + decrypted);
			// System.out.println("decrypted2= " + decrypted2);
			// System.out.println("decrypted3= " + decrypted3);

			// XOR (decimal)
			CharSequence c2 = decrypted2.subSequence(0, 15);
			CharSequence c3 = decrypted3.subSequence(0, 15);
			// System.out.println("c2= " + c2);
			// System.out.println("c3= " + c3);

			long l1 = Long.parseLong(decrypted);
			long l2 = Long.parseLong(c2.toString());
			long l3 = Long.parseLong(c3.toString());
			// System.out.println("l1= " + l1);
			// System.out.println("l2= " + l2);
			// System.out.println("l3= " + l3);
			long session_key = (l1 ^ l2) ^ l3;
			str = String.valueOf(session_key);

		} catch (Exception e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

		return str;
	}

	public void decryptJar() {
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

		if (isSuccess)
			Log.i(TAG, "解密成功");
		// Log.i(TAG, "解密成功, decrypted path= "
		// + decryptFile.getAbsolutePath().toString());
		else {
			Log.e(TAG, "解密失敗");
		}
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

}
