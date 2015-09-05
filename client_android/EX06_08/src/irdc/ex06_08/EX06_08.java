package irdc.ex06_08;

import static trustedappframework.subprojecttwo.module.PersonalKeyManager.checkPersonalKey;
import static trustedappframework.subprojecttwo.module.PersonalKeyManager.personal_key;
import static trustedappframework.subprojecttwo.module.ProjectConfig.checkConnection;
import static trustedappframework.subprojecttwo.module.ProjectConfig.classStatus;
import static trustedappframework.subprojecttwo.module.ProjectConfig.initProgressDialog;
import static trustedappframework.subprojecttwo.module.ProjectConfig.jarFlag;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;
import static trustedappframework.subprojecttwo.module.ProjectConfig.class_separation_segment;
import static trustedappframework.subprojecttwo.module.ProjectConfig.test_class_separation_segment;
import static trustedappframework.subprojecttwo.module.ProjectConfig.test_jarFlag;
import trustedappframework.subprojecttwo.module.ACAPDAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EX06_08 extends Activity
{
  private static ACAPDAsyncTask task;

  private Button myButton;
  public static ProgressBar myProgressBar;
  public static TextView myTextView;

  private void initACAPD()
  {
    // get global Application object of the current process
    mAppContext = getApplicationContext();
    // get context for AlertDialog
    mContext = EX06_08.this;

    initProgressDialog();

    // get array
    class_separation_segment = getResources().getStringArray(
        R.array.class_separation_segment_file_name);
    jarFlag = getResources().getStringArray(R.array.jarFlag);
    classStatus = getResources()
        .getStringArray(R.array.classStatus);
    test_class_separation_segment = getResources().getStringArray(
        R.array.test_file_name);
    test_jarFlag = getResources().getStringArray(
        R.array.test_jarFlag);
    personal_key = getResources().getStringArray(
        R.array.personal_key_file_name);

    // check network setting on device
    checkConnection();

    // check personal key on device
    checkPersonalKey();

  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    initACAPD();

    myButton = (Button) findViewById(R.id.myButton);
    myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
    myTextView = (TextView) findViewById(R.id.myTextView);

    myButton.setOnClickListener(new Button.OnClickListener()
    {

      @Override
      public void onClick(View arg0)
      {
        // App Clone Attack Prevention and Detection (ACAPD)
        task = new ACAPDAsyncTask(test_class_separation_segment[0],
            classStatus[0], test_jarFlag[0]);
        // task = new ACAPDAsyncTask(class_separation_segment[0],
        // classStatus[0],
        // jarFlag[0]);
        task.execute((Void[]) null);
      }

    });

  }

}