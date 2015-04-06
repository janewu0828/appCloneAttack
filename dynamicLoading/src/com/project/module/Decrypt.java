package com.project.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class Decrypt {
	private static final String TAG = "Decrypt";
	private static String seed = "";

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

	public void decryptJar() {
		// 解密保存
		isSuccess = true;

		decryptFile = new File(folderPath, fileName);
		byte[] oldByte = new byte[(int) decryptFile.length()];

		try {
			fis = new FileInputStream(decryptFile);
			fis.read(oldByte);

			// 解密
			byte[] newByte = AESUtils.decryptVoice(seed, oldByte);
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
		}

		if (isSuccess)
			Log.i(TAG, "解密成功, decrypted path= "
					+ decryptFile.getAbsolutePath().toString());
		else
			Log.e(TAG, "解密失敗");
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

}
