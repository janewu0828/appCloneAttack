package irdc.ex04_25;

import static com.project.module.ProjectConfig.mAppContext;
import static com.project.module.ProjectConfig.mContext;
import static com.project.module.ProjectConfig.isShowTxt;
import static com.project.module.ProjectConfig.fileName;
import static com.project.module.ProjectConfig.checkConnection;
import static com.project.module.ProjectConfig.showCheckuserError;
import com.project.module.SendPostRunnable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EX04_25 extends Activity
{
  public static View myview;
  
  protected Button mButton1, mButton2;
  public static TextView mTextView1;
  public static RadioGroup mRadioGroup1;
  public static boolean mUserChoice = false;
  public static int mMyChoice = -2;
  public static int intTimes = 0;
  public static RadioButton mRadio1, mRadio2, mRadio3;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    // get global Application object of the current process
    mAppContext = getApplicationContext();
    // get context for AlertDialog
    mContext = EX04_25.this;
    // show a message of authentication is successful in first time
    isShowTxt = true;
    // check network setting on device
    checkConnection();
    
    mButton1 = (Button) findViewById(R.id.myButton1);
    mButton2 = (Button) findViewById(R.id.myButton2);
    mTextView1 = (TextView) findViewById(R.id.myTextView1);
    
    /* RadioGroup Widget */
    mRadioGroup1 = (RadioGroup) findViewById(R.id.myRadioGroup1);
    
    mRadio1 = (RadioButton) findViewById(R.id.myOption1);
    mRadio2 = (RadioButton) findViewById(R.id.myOption2);
    mRadio3 = (RadioButton) findViewById(R.id.myOption3);
    
    /* 取得三個RadioButton的ID，並存放置整數陣列中 */
    int[] aryChoose =
    {
        mRadio1.getId(), mRadio2.getId(), mRadio3.getId()
    };
    
    /* 以亂數的方式指定哪一個為系統猜測的答案 */
    int intRandom = (int) (Math.random() * 3);
    mMyChoice = aryChoose[intRandom];
    
    /* 回答按鈕事件 */
    mButton1.setOnClickListener(mChoose);
    
    /* 清空按鈕事件 */
    mButton2.setOnClickListener(mClear);
    
    /* RadioGruop當User變更選項後的事件處理 */
    mRadioGroup1.setOnCheckedChangeListener(mChangeRadio);
  }
  
  /* RadioGruop當User變更選項後的事件處理 */
  private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener()
  {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
      // TODO Auto-generated method stub
      if (checkedId == mMyChoice)
      {
        /* User猜對了 */
        mUserChoice = true;
      }
      else
      {
        /* User猜錯了 */
        mUserChoice = false;
      }
    }
  };
  
  /* 回答按鈕事件 */
  private Button.OnClickListener mChoose = new Button.OnClickListener()
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
      }
      catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      if (sr.getResult())
      {
        if (isShowTxt)
          Toast.makeText(mContext,
              mContext.getResources().getString(R.string.toast_checkuser_true),
              Toast.LENGTH_SHORT).show();
        // show a message of Authentication is successful in first time
        isShowTxt = false;
        
        myview = v;
        
      }
      else
      {
        // show a Alert Dialog that Authentication is failed
        showCheckuserError();
      }
    }
  };
  
  /* 清空按鈕事件 */
  private Button.OnClickListener mClear = new Button.OnClickListener()
  {
    @Override
    public void onClick(View v)
    {
      // TODO Auto-generated method stub
      mUserChoice = false;
      intTimes = 0;
      mRadio1 = (RadioButton) findViewById(R.id.myOption1);
      mRadio2 = (RadioButton) findViewById(R.id.myOption2);
      mRadio3 = (RadioButton) findViewById(R.id.myOption3);
      int[] aryChoose =
      {
          mRadio1.getId(), mRadio2.getId(), mRadio3.getId()
      };
      int intRandom = (int) (Math.random() * 3);
      mMyChoice = aryChoose[intRandom];
      
      mTextView1.setText(R.string.hello);
      mRadioGroup1.clearCheck();
    }
  };
  
}