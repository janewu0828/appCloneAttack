package com.project.interfaces;

import com.project.module.ProjectConfig;
import irdc.EX05_04.EX05_04;
import irdc.EX05_04.R;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;


public class InterfaceTest implements MainInterface
{
  Context mContext = ProjectConfig.mContext;
  
  EditText mEditText01=EX05_04.mEditText01;
  EditText mEditText02=EX05_04.mEditText02;
  EditText mEditText03=EX05_04.mEditText03;
  EditText mEditText04=EX05_04.mEditText04;
  String[] strEmailReciver=EX05_04.strEmailReciver;
  String[] strEmailCc=EX05_04.strEmailCc;
  String strEmailSubject=EX05_04.strEmailSubject; 
  String strEmailBody=EX05_04.strEmailBody; 
  
  public void loadMethod()
  {
    /*透過Intent來發送郵件*/
    Intent mEmailIntent = new Intent(android.content.Intent.ACTION_SEND);  
    /*設定郵件格式為plain/text*/
    mEmailIntent.setType("plain/text");
    
    /*取得EditText01,02,03,04的值作為收件人地址,附件,主旨,內文*/
    strEmailReciver = new String[]{mEditText01.getText().toString()};
    strEmailCc = new String[]{mEditText02.getText().toString()};
    strEmailSubject = mEditText03.getText().toString();
    strEmailBody = mEditText04.getText().toString();
    
    /*將取得的字串放入mEmailIntent中*/
    mEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, strEmailReciver); 
    mEmailIntent.putExtra(android.content.Intent.EXTRA_CC, strEmailCc);
    mEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strEmailSubject);
    mEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strEmailBody);
    /*開啟Gmail 並將相關參數傳入*/
    mContext.startActivity(Intent.createChooser(mEmailIntent, mContext.getResources().getString(R.string.str_message))); 
  }
  

}
