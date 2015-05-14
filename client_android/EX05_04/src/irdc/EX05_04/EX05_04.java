package irdc.EX05_04;

import static trustedappframework.subprojecttwo.module.ProjectConfig.class_separation_segment;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.personal_key;
import trustedappframework.subprojecttwo.module.ACAPD;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EX05_04 extends Activity
{
  private ACAPD myACAPD;

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
        return true;
      }
    });

    /* 設定onClickListener 讓使用者點選Button時送出email */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        myACAPD = new ACAPD(class_separation_segment, personal_key);
      }
    });
  }
}
