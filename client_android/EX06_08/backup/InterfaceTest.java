package com.project.interfaces;

import irdc.ex06_08.EX06_08;

import java.io.File;
import java.text.DecimalFormat;

import com.project.module.ProjectConfig;

import irdc.ex06_08.R;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InterfaceTest implements MainInterface
{
  Context mContext=ProjectConfig.mContext;
  TextView myTextView=EX06_08.myTextView;
  ProgressBar myProgressBar=EX06_08.myProgressBar;
  
  String text = mContext.getResources().getString(R.string.deleted);;
  String txtTotal=mContext.getResources().getString(R.string.total);
  String txtAvailable=mContext.getResources().getString(R.string.available);

  public String sayHello()
  {
    return "hello world";
  }

  public void loadMethod()
  {
    /* 將TextView中及ProgressBar設置為空值及0 */
    myTextView.setText("");
    myProgressBar.setProgress(0);
    /* 判斷記憶卡是否插入 */
    if (Environment.getExternalStorageState().equals(
        Environment.MEDIA_MOUNTED))
    {
      /* 取得 SD CARD 文件路徑一般是 /sdcard */
      File path = Environment.getExternalStorageDirectory();

      /* StatFs看文件系統空間使用狀況 */
      StatFs statFs = new StatFs(path.getPath());
      /* Block 的 size */
      long blockSize = statFs.getBlockSize();
      /* 總 Block 數量 */
      long totalBlocks = statFs.getBlockCount();
      /* 已使用的 Block 數量 */
      long availableBlocks = statFs.getAvailableBlocks();

      String[] total = fileSize(totalBlocks * blockSize);
      String[] available = fileSize(availableBlocks * blockSize);

      /* getMax 取得在 main.xml 文件裡的 ProgressBar 設置的最大值 */
      int ss = myProgressBar.getMax()
          - (Integer.parseInt(available[0])
              * myProgressBar.getMax() / Integer
                .parseInt(total[0]));

      myProgressBar.setProgress(ss);

      String text = txtTotal + total[0] + total[1] + "\n";
      text += txtAvailable + available[0] + available[1];
      myTextView.setText(text);

    } else if (Environment.getExternalStorageState().equals(
        Environment.MEDIA_REMOVED))
    {
      myTextView.setText(text);
    }
  }

  /* 返回為字符串數組[0]為大小[1]為單位KB或MB */
  public String[] fileSize(long size)
  {
    String str = "";
    if (size >= 1024)
    {
      str = "KB";
      size /= 1024;
      if (size >= 1024)
      {
        str = "MB";
        size /= 1024;
      }
    }

    DecimalFormat formatter = new DecimalFormat();
    /* 每3個數字用，分隔如：1,000 */
    formatter.setGroupingSize(0);
    String result[] = new String[2];
    result[0] = formatter.format(size);
    result[1] = str;

    return result;
  }

}
