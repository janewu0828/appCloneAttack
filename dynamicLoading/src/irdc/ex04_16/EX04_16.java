package irdc.ex04_16;

import static com.project.module.ProjectConfig.mAppContext;
import static com.project.module.ProjectConfig.mContext;
import static com.project.module.ProjectConfig.isShowTxt;
import static com.project.module.ProjectConfig.fileName;
import static com.project.module.ProjectConfig.checkConnection;
import static com.project.module.ProjectConfig.showCheckuserError;
import static com.project.module.ProjectConfig.showPersonalKey;

import java.io.File;

import com.project.interfaces.Load;
import com.project.module.Decrypt;

import com.project.module.SendPostRunnable;

/* import相關class */
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

public class EX04_16 extends Activity {
	private static final String TAG = "EX04_16";	
	
	public static Drawable d01;
	public static Drawable d02;
	public static Drawable d03;
	public static int mys1 = 0;
	public static int ans = 0;
	public static boolean mI01;
	public static boolean mI02;
	public static boolean mI03;

	/* 宣告物件變數 */
	public static ImageView mImageView01;
	public static ImageView mImageView02;
	public static ImageView mImageView03;
	private Button mButton;
	public static TextView mText;
	public static int choiceStatus = 0;

	/*
	 * 宣告長度為3的int陣列，並將三張牌的id放入 R.drawable.p01：紅心A R.drawable.p02：黑桃2
	 * R.drawable.p03：梅花3 R.drawable.p04：撲克牌背面
	 */
	public static int[] s1 = new int[] { R.drawable.p01, R.drawable.p02,
			R.drawable.p03 };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get global Application object of the current process
		mAppContext = EX04_16.this.getApplicationContext();
		// get context for AlertDialog
		mContext = EX04_16.this;
		// show a message of authentication is successful in first time
		isShowTxt = true;
		// // check network setting on device
		// checkConnection();
		// input decryption key
		showPersonalKey();

		/* 取得相關物件 */
		mText = (TextView) findViewById(R.id.mText);
		mImageView01 = (ImageView) findViewById(R.id.mImage01);
		mImageView02 = (ImageView) findViewById(R.id.mImage02);
		mImageView03 = (ImageView) findViewById(R.id.mImage03);
		mButton = (Button) findViewById(R.id.mButton);
		/* 執行洗牌程式 */
		randon();

		/* 替mImageView01加入onClickListener */
		mImageView01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (choiceStatus == 0) {
					Log.i(TAG, "card_1");
					loadImageView(false, true, true, s1[0]);
				}
			}
		});

		/* 替mImageView02加入onClickListener */
		mImageView02.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (choiceStatus == 0) {
					Log.i(TAG, "card_2");
					loadImageView(true, false, true, s1[1]);
				}
			}
		});

		/* 替mImageView03加入onClickListener */
		mImageView03.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (choiceStatus == 0) {
					Log.i(TAG, "card_3");
					loadImageView(true, true, false, s1[2]);
				}
			}
		});

		/* 替mButton加入onClickListener，使其按下後三張牌都翻為背面且重新洗牌 */
		mButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mText.setText(getResources().getString(R.string.str_title));

				mImageView01.setImageDrawable(getResources().getDrawable(
						R.drawable.p04));
				mImageView02.setImageDrawable(getResources().getDrawable(
						R.drawable.p04));
				mImageView03.setImageDrawable(getResources().getDrawable(
						R.drawable.p04));
				mImageView01.setAlpha(255);
				mImageView02.setAlpha(255);
				mImageView03.setAlpha(255);
				randon();
				choiceStatus = 0;
			}
		});
	}

	private void loadImageView(boolean mImg01, boolean mImg02, boolean mImg03,
			int mysol01) {
		d01 = getResources().getDrawable(s1[0]);
		d02 = getResources().getDrawable(s1[1]);
		d03 = getResources().getDrawable(s1[2]);

		// ---check user, download file and dynamic loading---
		SendPostRunnable sr = new SendPostRunnable(fileName);

		// start a Thread, the data to be transferred into the Runnable, so that
		// Thread execute
		Thread t = new Thread(sr);
		t.start();

		try {
			// wait Thread t
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Error: "+e.getMessage());
		}
		
//		String outputFilePath = Environment.getExternalStorageDirectory()
//				.getAbsolutePath() + "/project/";
//
//		String loadFileName = "";
//		if (new File(outputFilePath + fileName).exists()) {
//			Log.i(TAG, "Jar is exist");
//			
//			// decrypt Jar -----
//			Decrypt decfile = new Decrypt(fileName, outputFilePath,
//					com.project.module.ProjectConfig.personal_key);
//			decfile.decryptJar();
//			Log.i(TAG, "decrypted Jar");
//
//			// dynamic loading -----
//			loadFileName = decfile.getOutputFileName();
//			Load ld = new Load(loadFileName, outputFilePath);
//			ld.loadJar();
//		}

		if (sr.getResult()) {
			if (isShowTxt)
				Toast.makeText(
						mAppContext,
						getResources().getString(R.string.toast_checkuser_true),
						Toast.LENGTH_SHORT).show();
			// show a message of Authentication is successful in first time
			isShowTxt = false;

			mI01 = mImg01;
			mI02 = mImg02;
			mI03 = mImg03;
			mys1 = mysol01;
			ans = R.drawable.p01;
			choiceStatus = 1;

		} else {
			// show a Alert Dialog that Authentication is failed
			showCheckuserError();
		}

	}

	/* 重新洗牌的程式 */
	private void randon() {
		for (int i = 0; i < 3; i++) {
			int tmp = s1[i];
			int s = (int) (Math.random() * 2);
			s1[i] = s1[s];
			s1[s] = tmp;
		}
	}
}