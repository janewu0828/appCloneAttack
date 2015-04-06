package com.project.interfaces;

import static com.project.module.ProjectConfig.mAppContext;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
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
	public void loadJar() {
		Context context = mAppContext;
		try {
			String sourceFilePath = folderPath + fileName;

			// export jar path
			File sourceFile = new File(sourceFilePath);
			// System.out.println("path= " + sourceFile.getAbsolutePath());

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
			Toast.makeText(context, "jar content= " + str, Toast.LENGTH_SHORT)
					.show();
			Log.i(TAG, "jar content= " + str);

			// exe
			mMainInterface.loadMethod();
			Log.i(TAG, "finished");

			File deleteFile = new File(sourceFile.getAbsolutePath());
			boolean deleted = deleteFile.delete();
			Log.i(TAG, "deleted= " + String.valueOf(deleted) + ", file= "
					+ sourceFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
	}

}
