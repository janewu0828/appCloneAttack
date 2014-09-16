package irdc.ex06_08;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EX06_08 extends Activity
{
  private Button myButton;
  public static ProgressBar myProgressBar;
  public static TextView myTextView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // 取得getApplicationContext()資源
    mAppContext = getApplicationContext();
    // 顯示AlertDialog所需的資源
    mContext = EX06_08.this;
    // 顯示第一次驗證成功訊息
    isShowTxt = true;
    // 檢查網路組態設置
    checkConnection();

    myButton = (Button) findViewById(R.id.myButton);
    myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
    myTextView = (TextView) findViewById(R.id.myTextView);

    myButton.setOnClickListener(new Button.OnClickListener()
    {

      @Override
      public void onClick(View arg0)
      {

        // check user , download file and dynamic loading-----
        SendPostRunnable sr = new SendPostRunnable(fileName,
            getApplicationContext());

        // 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
        Thread t = new Thread(sr);
        t.start();

        try
        {
          // wait thread t
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
                    R.string.toast_checkuser_true),
                Toast.LENGTH_SHORT).show();
          // 第一次顯示驗證成功訊息後，不再顯示
          isShowTxt = false;

        } else
        {
          showCheckuserError();
        }
      }

    });

  }

}