package com.project.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

public interface MainInterface
{
  
  public String sayHello();
  
  public void loadMethod();
  
  public boolean ismUserChoice();
  
  public void setmUserChoice(boolean mUserChoice);
  
  public int getIntTimes();
  
  public void setIntTimes(int intTimes);
  
  public int getmMyChoice();
  
  public void setmMyChoice(int mMyChoice);
  
  public RadioGroup getmRadioGroup1();
  
  public void setmRadioGroup1(RadioGroup mRadioGroup1);
  
  public Context getmContext();
  
  public void setmContext(Context mContext);
  
  public View getV();
  
  public void setV(View v);
  
}
