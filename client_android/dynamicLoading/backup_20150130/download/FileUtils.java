package com.project.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Environment;

public class FileUtils {
	private String SDPATH;

	private int FILESIZE = 4 * 1024;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 得到當前外部存放裝置的目錄( /SDCARD )
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * 在SD卡上創建檔
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上創建目錄
	 * 
	 * @param dirName
	 * @return
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判斷SD卡上的資料夾是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 將一個InputStream裡面的資料寫入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
}
