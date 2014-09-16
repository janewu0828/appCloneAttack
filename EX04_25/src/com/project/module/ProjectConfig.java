package com.project.module;

import irdc.ex04_25.R;
import android.content.Context;

public class ProjectConfig
{
  // 取得getApplicationContext()資源
  public static Context mAppContext;

  // 顯示AlertDialog所需的資源
  public static Context mContext;

  // 顯示提醒視窗
  public static AlertDialogManager alert = new AlertDialogManager();

  // 用於第一次，是否顯示驗證成功訊息
  public static boolean isShowTxt;

  // 載入部分檔案的完整檔名
  public static String fileName = "output041122.jar";

  public static void checkConnection()
  {
    // TODO Auto-generated method stub
    // 檢查網路組態設置
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
    //顯示非法user提醒視窗
    alert.showAlertDialog(mContext,
        mContext.getResources().getString(R.string.alert_checkuser_error_title),
        mContext.getResources().getString(R.string.alert_checkuser_error_msg),
        false);
  }

}
