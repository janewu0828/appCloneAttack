package irdc.ex06_02;

import static com.project.module.ProjectConfig.mAppContext;
import static com.project.module.ProjectConfig.mContext;
import static com.project.module.ProjectConfig.isShowTxt;
import static com.project.module.ProjectConfig.fileName;
import static com.project.module.ProjectConfig.checkConnection;
import static com.project.module.ProjectConfig.showCheckuserError;

import com.project.module.SendPostRunnable;

/* import相關class */
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EX06_02 extends Activity
{
  /* 變數宣告 */
  public static int intLevel;
  public static int intScale;
  private Button mButton01;

  /* create BroadcastReceiver */
  public static BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context context, Intent intent)
    {
      String action = intent.getAction();
      /*
       * 如果捕捉到的action是ACTION_BATTERY_CHANGED， 就執行onBatteryInfoReceiver()
       */
      if (Intent.ACTION_BATTERY_CHANGED.equals(action))
      {
        intLevel = intent.getIntExtra("level", 0);
        intScale = intent.getIntExtra("scale", 100);

        Log.i("event", "取得電量");

        // ---check user, download file and dynamic loading---
        SendPostRunnable sr = new SendPostRunnable(fileName,
            mAppContext);

        // start a Thread, the data to be transferred into the Runnable, so that Thread execute
        Thread t = new Thread(sr);
        t.start();

        try
        {
          // wait Thread t
          t.join();
        } catch (InterruptedException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        if (sr.getResult())
        {
          if (isShowTxt)
            Toast.makeText(
                mContext,
                mContext.getResources().getString(
                    R.string.toast_checkuser_true), Toast.LENGTH_SHORT)
                .show();
          // show a message of Authentication is successful in first time
          isShowTxt = false;

        } else
        {
          // show a Alert Dialog that Authentication is failed
          showCheckuserError();
        }

      }
    }
  };

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);

    // get global Application object of the current process
    mAppContext = getApplicationContext();
    // get context for AlertDialog
    mContext = EX06_02.this;
    // show a message of authentication is successful in first time
    isShowTxt = true;
    // check network setting on device
    checkConnection();

    /* 初始化Button，並設定按下後的動作 */
    mButton01 = (Button) findViewById(R.id.myButton1);
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        /* 註冊一個系統 BroadcastReceiver，作為存取電池計量之用 */
        registerReceiver(mBatInfoReceiver, new IntentFilter(
            Intent.ACTION_BATTERY_CHANGED));
      }
    });

  }

}