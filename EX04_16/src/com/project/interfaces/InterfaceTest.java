package com.project.interfaces;

import com.project.module.ProjectConfig;

import irdc.ex04_16.EX04_16;
import irdc.ex04_16.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class InterfaceTest implements MainInterface
{
  Context mContext = ProjectConfig.mContext;

  Drawable d01 = EX04_16.d01;
  Drawable d02 = EX04_16.d02;
  Drawable d03 = EX04_16.d03;

  ImageView mImageView01 = EX04_16.mImageView01;
  ImageView mImageView02 = EX04_16.mImageView02;
  ImageView mImageView03 = EX04_16.mImageView03;

  TextView mText = EX04_16.mText;

  boolean mI01 = EX04_16.mI01;
  boolean mI02 = EX04_16.mI02;
  boolean mI03 = EX04_16.mI03;

  int choiceStatus = EX04_16.choiceStatus;
  int s1 = EX04_16.mys1;
  int ans = EX04_16.ans;
  String result = "";

  String guessResultTrue = mContext.getResources().getString(
      R.string.guess_result_true);
  String guessResultFalse = mContext.getResources().getString(
      R.string.guess_result_false);

  public String sayHello()
  {
    return "hello world";
  }

  @SuppressWarnings("deprecation")
  public String loadMethod()
  {
    /* 三張牌同時翻面，並將未選擇的兩張牌變透明 */
    mImageView01.setImageDrawable(d01);
    mImageView02.setImageDrawable(d02);
    mImageView03.setImageDrawable(d03);

    int alpha = 100;
    if (mI01)
      mImageView01.setAlpha(alpha);
    if (mI02)
      mImageView02.setAlpha(alpha);
    if (mI03)
      mImageView03.setAlpha(alpha);

    /* 依有沒有猜對來決定TextView要顯示的訊息 */
    if (s1 == ans)
    {
      setResult(guessResultTrue);
    } else
    {
      setResult(guessResultFalse);
    }
    setChoiceStatus(1);

    mText.setText(result);
    Log.i("result", "" + result);

    choiceStatus = getChoiceStatus();

    return result;
  }

  public int getS1()
  {
    return s1;
  }

  public void setS1(int s1)
  {
    this.s1 = s1;
  }

  public int getAns()
  {
    return ans;
  }

  public void setAns(int ans)
  {
    this.ans = ans;
  }

  public int getChoiceStatus()
  {
    return choiceStatus;
  }

  public void setChoiceStatus(int choiceStatus)
  {
    this.choiceStatus = choiceStatus;
  }

  public Drawable getD01()
  {
    return d01;
  }

  public void setD01(Drawable d01)
  {
    this.d01 = d01;
  }

  public Drawable getD02()
  {
    return d02;
  }

  public void setD02(Drawable d02)
  {
    this.d02 = d02;
  }

  public Drawable getD03()
  {
    return d03;
  }

  public void setD03(Drawable d03)
  {
    this.d03 = d03;
  }

  public String getResult()
  {
    return result;
  }

  public void setResult(String result)
  {
    this.result = result;
  }

  public Context getmContext()
  {
    return mContext;
  }

  public void setmContext(Context mContext)
  {
    this.mContext = mContext;
  }

}
