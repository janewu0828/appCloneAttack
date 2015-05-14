package com.project.interfaces;

import android.content.Context;

public interface MainInterface
{

  public String sayHello();
  
  public void loadMethod();
  
  public void setmContext(Context mContext);
  public void setIntLevel(int intLevel);
  public void setIntScale(int intScale);
  
  public Context getmContext();
  public int getIntLevel();
  public int getIntScale();

}
