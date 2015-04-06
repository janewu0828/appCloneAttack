package com.project.interfaces;

import static com.project.module.ProjectConfig.mAppContext;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
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
			Log.e(TAG, "jar content= " + str);

			Looper.prepare();
			Toast.makeText(context, "jar content= " + str, Toast.LENGTH_SHORT)
					.show();
			Looper.loop();// 进入loop中的循环，查看消息队列

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
