package com.project.module;

import irdc.ex06_02.R;
import android.content.Context;

public class ProjectConfig
{
  // get global Application object of the current process
  public static Context mAppContext;

  // get context for AlertDialog
  public static Context mContext;

  // show AlertDialog
  public static AlertDialogManager alert = new AlertDialogManager();

  // show a message of authentication is successful in first time
  public static boolean isShowTxt;

  // ---dynamic loading file(JAR)---
  public static String fileName = "output0510.jar";

  public static void checkConnection()
  {
    // TODO Auto-generated method stub
    // check network setting on device
    ConnectionDetector cd = new ConnectionDetector(mAppContext);
    if (!cd.isConnectingToInternet())
    {
      alert.showAlertDialog(mContext,
          mContext.getString(R.string.alert_internet_error_title),
          mContext.getString(R.string.alert_internet_error_msg), false);
    }
  }

  public static void showCheckuserError()
  {
    // TODO Auto-generated method stub
    // show a Alert Dialog that Authentication is failed
    alert.showAlertDialog(
        mContext,
        mContext.getResources().getString(
            R.string.alert_checkuser_error_title),
        mContext.getResources().getString(
            R.string.alert_checkuser_error_msg), false);
  }

}
