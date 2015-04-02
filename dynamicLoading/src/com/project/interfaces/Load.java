package com.project.interfaces;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import dalvik.system.DexClassLoader;

public class Load {
	private static final String TAG = "Load";

	private String fileName = "";
	private String folderPath = "";

	/**
	 * @param fileName
	 */
	public Load(String fileName, String folderPath) {
		super();
		this.fileName = fileName;
		this.folderPath = folderPath;
	}

	@SuppressLint("NewApi")
	public void loadJar(Context context) {
		try {
			String sourceFilePath = folderPath + fileName;

			// export jar path
			File sourceFile = new File(sourceFilePath);
			Log.e(TAG, "path= " + sourceFile.getAbsolutePath());

			// export dex tmp path
			File file = context.getDir("osdk", 0);

			DexClassLoader classLoader = new DexClassLoader(
					sourceFile.getAbsolutePath(), file.getAbsolutePath(), null,
					context.getClassLoader());

			Class<?> libProviderClazz = classLoader
					.loadClass("com.project.interfaces.InterfaceTest");

			// interface
			MainInterface mMainInterface = (MainInterface) libProviderClazz
					.newInstance();

			// return jar result
			String str = mMainInterface.sayHello();
			Context mContext=com.project.module.ProjectConfig.mAppContext;
			Log.e(TAG, "jar content= " + str);
			Toast.makeText(mContext, "jar content= " + str, Toast.LENGTH_SHORT).show();


			// exe
			mMainInterface.loadMethod();

			File deleteFile = new File(sourceFile.getAbsolutePath());
			boolean deleted = deleteFile.delete();
			Log.e(TAG, "deleteFile= " + String.valueOf(deleted));

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
	}

}
