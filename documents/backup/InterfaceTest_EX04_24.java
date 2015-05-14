package trustedappframework.subprojecttwo.interfaces;

import trustedappframework.subprojecttwo.module.ProjectConfig;

import irdc.ex04_24.EX04_24;
import irdc.ex04_24.R;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class InterfaceTest implements MainInterface {
	Bitmap resizedBitmap;
	BitmapDrawable myNewBitmapDrawable;
	String result = "";

	boolean turnleft = EX04_24.turnleft;

	int ScaleAngle = EX04_24.ScaleAngle;
	int ScaleTimes = EX04_24.ScaleTimes;

	int widthOrig = EX04_24.getWidthOrig1();
	int heightOrig = EX04_24.getHeightOrig1();

	Bitmap mySourceBmp1 = EX04_24.mySourceBmp1;
	ImageView mImageView1 = EX04_24.mImageView1;
	TextView mTextView1 = EX04_24.mTextView1;
	String strDone = ProjectConfig.mContext.getResources().getString(
			R.string.str_done);

	public String sayHello() {
		return "hello world";
	}

	@SuppressWarnings("deprecation")
	public void loadMethod() {
		if (turnleft) {
			if (ScaleAngle < -5) {
				ScaleAngle = -5;
			}
			Log.i("event", "turnLeft+" + ScaleAngle);
		} else {
			if (ScaleAngle > 5) {
				ScaleAngle = 5;
			}
			Log.i("event", "turnRight+" + ScaleAngle);
		}

		/* ScaleTimes=1，維持1:1的寬高比例 */
		int newWidth = widthOrig * ScaleTimes;
		int newHeight = heightOrig * ScaleTimes;

		float scaleWidth = ((float) newWidth) / widthOrig;
		float scaleHeight = ((float) newHeight) / heightOrig;

		Matrix matrix = new Matrix();
		/* 使用Matrix.postScale設定維度 */
		matrix.postScale(scaleWidth, scaleHeight);

		/* 使用Matrix.postRotate方法旋轉Bitmap */
		matrix.setRotate(5 * ScaleAngle);

		/* 建立新的Bitmap物件 */
		resizedBitmap = Bitmap.createBitmap(mySourceBmp1, 0, 0, widthOrig,
				heightOrig, matrix, true);
		/**/
		myNewBitmapDrawable = new BitmapDrawable(resizedBitmap);
		setMyNewBitmapDrawable(myNewBitmapDrawable);
		setResult(Integer.toString(5 * ScaleAngle));

		mImageView1.setImageDrawable(getMyNewBitmapDrawable());
		mTextView1.setText(getResult());

		if (ScaleAngle == -5 || ScaleAngle == 5) {
			mTextView1.setText(strDone);
			Log.i("event", strDone);

		}
		
	}

	public int getScaleAngle() {
		return ScaleAngle;
	}

	public void setScaleAngle(int scaleAngle) {
		ScaleAngle = scaleAngle;
	}

	public BitmapDrawable getMyNewBitmapDrawable() {
		return myNewBitmapDrawable;
	}

	public void setMyNewBitmapDrawable(BitmapDrawable myNewBitmapDrawable) {
		this.myNewBitmapDrawable = myNewBitmapDrawable;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
