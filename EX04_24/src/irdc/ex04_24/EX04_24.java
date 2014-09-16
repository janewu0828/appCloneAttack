package irdc.ex04_24;

import static com.project.module.ProjectConfig.mAppContext;
import static com.project.module.ProjectConfig.mContext;
import static com.project.module.ProjectConfig.isShowTxt;
import static com.project.module.ProjectConfig.fileName;
import static com.project.module.ProjectConfig.checkConnection;
import static com.project.module.ProjectConfig.showCheckuserError;
import com.project.module.SendPostRunnable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EX04_24 extends Activity {
	public static boolean turnleft;
	public static int widthOrig1;
	public static int heightOrig1;
	public static Bitmap mySourceBmp1;

	private Button mButton1;
	private Button mButton2;
	public static TextView mTextView1;
	public static ImageView mImageView1;
	public static int ScaleTimes;
	public static int ScaleAngle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 取得getApplicationContext()資源
	    mAppContext = getApplicationContext();
	    // 顯示AlertDialog所需的資源
	    mContext = EX04_24.this;
	    // 顯示第一次驗證成功訊息
	    isShowTxt = true;
	    // 檢查網路組態設置
	    checkConnection();

		mButton1 = (Button) findViewById(R.id.myButton1);
		mButton2 = (Button) findViewById(R.id.myButton2);
		mTextView1 = (TextView) findViewById(R.id.myTextView1);
		mImageView1 = (ImageView) findViewById(R.id.myImageView1);
		ScaleTimes = 1;
		ScaleAngle = 1;

		final Bitmap mySourceBmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.hippo);

		final int widthOrig = mySourceBmp.getWidth();
		final int heightOrig = mySourceBmp.getHeight();

		/* 程式剛執行，載入預設的Drawable */
		mImageView1.setImageBitmap(mySourceBmp);

		/* 向左選轉按鈕 */
		mButton1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ScaleAngle--;
				turnleft = true;
				setWidthOrig1(widthOrig);
				setHeightOrig1(heightOrig);
				setMySourceBmp1(mySourceBmp);
				loadImageView();
			}
		});

		/* 向右選轉按鈕 */
		mButton2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ScaleAngle++;
				turnleft = false;
				setWidthOrig1(widthOrig);
				setHeightOrig1(heightOrig);
				setMySourceBmp1(mySourceBmp);
				loadImageView();
			}
		});
	}

	private void loadImageView() {
		// check user , download file and dynamic loading-----
		SendPostRunnable sr = new SendPostRunnable(fileName,
				getApplicationContext());

		// 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
		Thread t = new Thread(sr);
		t.start();

		try {
			// wait thread t
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (sr.getResult()) {
			if (isShowTxt)
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(R.string.toast_checkuser_true),
						Toast.LENGTH_SHORT).show();
			// 第一次顯示驗證成功訊息後，不再顯示
			isShowTxt = false;			
			
		} else {
			showCheckuserError();
		}
	}	

	public static int getWidthOrig1() {
		return widthOrig1;
	}

	public static void setWidthOrig1(int widthOrig1) {
		EX04_24.widthOrig1 = widthOrig1;
	}

	public static int getHeightOrig1() {
		return heightOrig1;
	}

	public static void setHeightOrig1(int heightOrig1) {
		EX04_24.heightOrig1 = heightOrig1;
	}

	public static Bitmap getMySourceBmp1() {
		return mySourceBmp1;
	}

	public static void setMySourceBmp1(Bitmap mySourceBmp1) {
		EX04_24.mySourceBmp1 = mySourceBmp1;
	}

}