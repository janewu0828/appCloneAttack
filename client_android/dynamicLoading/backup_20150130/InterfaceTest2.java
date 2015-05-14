package trustedappframework.subprojecttwo.interfaces;

import static trustedappframework.subprojecttwo.module.ProjectConfig.mContext;
import irdc.ex04_16.EX04_16;
import irdc.ex04_16.R;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class InterfaceTest2 implements MainInterface {
	TextView mText = EX04_16.mText;

	ImageView mImageView01 = EX04_16.mImageView01;
	ImageView mImageView02 = EX04_16.mImageView02;
	ImageView mImageView03 = EX04_16.mImageView03;

	Drawable p04 = mContext.getResources().getDrawable(R.drawable.p04);
	String str_title = mContext.getResources().getString(R.string.str_title);

	public String sayHello() {
		return "hello world";
	}

	@SuppressWarnings("deprecation")
	public void loadMethod() {
		mText.setText(str_title);

		mImageView01.setImageDrawable(p04);
		mImageView02.setImageDrawable(p04);
		mImageView03.setImageDrawable(p04);
		mImageView01.setAlpha(255);
		mImageView02.setAlpha(255);
		mImageView03.setAlpha(255);
		
		EX04_16.randon();
		EX04_16.choiceStatus = 0;
	}

}
