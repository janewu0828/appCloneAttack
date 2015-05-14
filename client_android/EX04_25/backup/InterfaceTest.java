package com.project.interfaces;

import com.project.module.ProjectConfig;

import irdc.ex04_25.EX04_25;
import irdc.ex04_25.R;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InterfaceTest implements MainInterface
{
  boolean mUserChoice = EX04_25.mUserChoice;
  int intTimes = EX04_25.intTimes;
  
  int mRid01 = EX04_25.mRadio1.getId();
  int mRid02 = EX04_25.mRadio2.getId();
  int mRid03 = EX04_25.mRadio3.getId();
  
  int mMyChoice = EX04_25.mMyChoice;
  RadioGroup mRadioGroup1 = EX04_25.mRadioGroup1;
  Context mContext = ProjectConfig.mContext;
  View v = EX04_25.myview;
  TextView mTextView1=EX04_25.mTextView1;
  
  String strGuessCorrect=mContext.getResources().getString(R.string.str_guess_correct);
  String strGuessError=mContext.getResources().getString(R.string.str_guess_error);
  
  public String sayHello()
  {
    return "hello world";
  }
  
  public void loadMethod()
  {
    if (mUserChoice)
    {
      mTextView1.setText(strGuessCorrect);
      mUserChoice = false;
      intTimes = 0;
      
      int[] aryChoose = { mRid01, mRid02, mRid03 };
      int intRandom = (int) (Math.random() * 3);
      mMyChoice = aryChoose[intRandom];
      mRadioGroup1.clearCheck();
      
      Log.i("event", strGuessCorrect);
    }
    else
    {
      intTimes++;
      mTextView1.setText(strGuessError
          + "(" + Integer.toString(intTimes) + ")");
      Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
      v.startAnimation(shake);
      
      Log.i("event", "guess error");
    }
    
    EX04_25.mUserChoice = ismUserChoice();
    EX04_25.intTimes = getIntTimes();
    EX04_25.mMyChoice = getmMyChoice();
  }
  
  public boolean ismUserChoice()
  {
    return mUserChoice;
  }
  
  public void setmUserChoice(boolean mUserChoice)
  {
    this.mUserChoice = mUserChoice;
  }
  
  public int getIntTimes()
  {
    return intTimes;
  }
  
  public void setIntTimes(int intTimes)
  {
    this.intTimes = intTimes;
  }
  
  public int getmMyChoice()
  {
    return mMyChoice;
  }
  
  public void setmMyChoice(int mMyChoice)
  {
    this.mMyChoice = mMyChoice;
  }
  
  public RadioGroup getmRadioGroup1()
  {
    return mRadioGroup1;
  }
  
  public void setmRadioGroup1(RadioGroup mRadioGroup1)
  {
    this.mRadioGroup1 = mRadioGroup1;
  }
  
  public Context getmContext()
  {
    return mContext;
  }
  
  public void setmContext(Context mContext)
  {
    this.mContext = mContext;
  }
  
  public View getV()
  {
    return v;
  }
  
  public void setV(View v)
  {
    this.v = v;
  }
  
}
