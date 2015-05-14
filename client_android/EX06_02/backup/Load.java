package com.project.interfaces;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import dalvik.system.DexClassLoader;

public class Load
{
  String folderPath = "project";
  String fileName = "";  

  /**
   * @param fileName
   */
  public Load(String fileName)
  {
    super();
    this.fileName = fileName;
  }

  @SuppressLint("NewApi")
  public void loadJar(Context context)
  {
    try
    {
      String sourceFilePath = Environment.getExternalStorageDirectory()
          + File.separator + folderPath + File.separator + fileName;

      // export jar path
      File sourceFile = new File(sourceFilePath);

      // export dex tmp path
      File file = context.getDir("osdk", 0);

      Log.e("path", sourceFile.getAbsolutePath());

      DexClassLoader classLoader = new DexClassLoader(
          sourceFile.getAbsolutePath(), file.getAbsolutePath(), null,
          context.getClassLoader());

      Class<?> libProviderClazz = classLoader
          .loadClass("com.project.interfaces.InterfaceTest");

      // interface
      MainInterface mMainInterface = (MainInterface) libProviderClazz
          .newInstance();

      // return jar result
      // String str = mMainInterface.sayHello();
      // Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

      // exe
      mMainInterface.showBatteryInfo();

      File deleteFile = new File(sourceFile.getAbsolutePath());
      boolean deleted = deleteFile.delete();
      Log.e("deleteFile", String.valueOf(deleted));

    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
