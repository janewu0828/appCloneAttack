package irdc.EX05_04;

import static com.project.module.ProjectConfig.isShowTxt;
import static com.project.module.ProjectConfig.fileName;
import static com.project.module.ProjectConfig.checkConnection;
import static com.project.module.ProjectConfig.mAppContext;
import static com.project.module.ProjectConfig.mContext;
import static com.project.module.ProjectConfig.showCheckuserError;

import com.project.module.SendPostRunnable;

import android.app.Activity;
/*必需引用content.Intent類別來開啟email client*/
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EX05_04 extends Activity
{

  /* 宣告四個EditText一個Button以及四個String變數 */
  public static EditText mEditText01;
  public static EditText mEditText02;
  public static EditText mEditText03;
  public static EditText mEditText04;
  private Button mButton01;
  public static String[] strEmailReciver;
  public static String strEmailSubject;
  public static String[] strEmailCc;
  public static String strEmailBody;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main2);

    // get global Application object of the current process
    mAppContext = getApplicationContext();
    // get context for AlertDialog
    mContext = EX05_04.this;
    // show a message of authentication is successful in first time
    isShowTxt = true;
    // check network setting on device
    checkConnection();

    /* 透過findViewById建構子來建構Button物件 */
    mButton01 = (Button) findViewById(R.id.myButton1);
    /* 透過findViewById建構子來建構所有EditText物件 */
    mEditText01 = (EditText) findViewById(R.id.myEditText1);
    mEditText02 = (EditText) findViewById(R.id.myEditText2);
    mEditText03 = (EditText) findViewById(R.id.myEditText3);
    mEditText04 = (EditText) findViewById(R.id.myEditText4);
    /* 設定OnKeyListener,當key事件發生時進行反應 */
    mEditText01.setOnKeyListener(new EditText.OnKeyListener()
    {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event)
      {
        // TODO Auto-generated method stub
        return true;
      }
    });

    /* 設定onClickListener 讓使用者點選Button時送出email */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        // ---check user, download file and dynamic loading---
        SendPostRunnable sr = new SendPostRunnable(fileName,
            getApplicationContext());

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
            Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.toast_checkuser_true),
                Toast.LENGTH_SHORT).show();
          // show a message of Authentication is successful in first time
          isShowTxt = false;

        } else
        {
          // show a Alert Dialog that Authentication is failed
          showCheckuserError();
        }
      }
    });
  }
}
