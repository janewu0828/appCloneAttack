package com.project.interfaces;

import com.project.module.ProjectConfig;

import irdc.ex06_02.EX06_02;
import irdc.ex06_02.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class InterfaceTest implements MainInterface
{
  Context mContext = ProjectConfig.mContext;
  AlertDialog d;  

  int intLevel = EX06_02.intLevel;
  int intScale = EX06_02.intScale;

  public String sayHello()
  {
    return "hello world";
  }


  @Override
  public void loadMethod()
  {
    // TODO Auto-generated method stub
    /* create 跳出的對話視窗 */
    d = new AlertDialog.Builder(mContext).create();
    d.setTitle(R.string.str_dialog_title);

    /* 將取得的電池計量顯示於Dialog中 */
    d.setMessage(mContext.getResources().getString(R.string.str_dialog_body)
        + String.valueOf(intLevel * 100 / intScale) + "%");

    /* 設定返回主畫面的按鈕 */
    d.setButton(mContext.getResources().getString(R.string.str_button2),
        new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            /* 反註冊Receiver，並關閉對話視窗 */
            mContext.unregisterReceiver(EX06_02.mBatInfoReceiver);
            d.dismiss();
          }
        });
    d.show();
  }

  @Override
  public void setmContext(Context mContext)
  {
    // TODO Auto-generated method stub
    this.mContext=mContext;
  }

  @Override
  public void setIntLevel(int intLevel)
  {
    // TODO Auto-generated method stub
    this.intLevel=intLevel;
  }

  @Override
  public void setIntScale(int intScale)
  {
    // TODO Auto-generated method stub
    this.intScale=intScale;
  }

  @Override
  public Context getmContext()
  {
    // TODO Auto-generated method stub
    return mContext;
  }

  @Override
  public int getIntLevel()
  {
    // TODO Auto-generated method stub
    return intLevel;
  }

  @Override
  public int getIntScale()
  {
    // TODO Auto-generated method stub
    return intScale;
  }

}
