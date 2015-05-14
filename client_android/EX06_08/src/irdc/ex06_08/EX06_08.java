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

    // get global Application object of the current process
    mAppContext = getApplicationContext();
    // get context for AlertDialog
    mContext = EX06_08.this;
    // show a message of authentication is successful in first time
    isShowTxt = true;
    // check network setting on device
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

        // start a Thread, the data to be transferred into the Runnable, so that
        // Thread execute
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
                    R.string.toast_checkuser_true),
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