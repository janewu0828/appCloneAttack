package com.project.module;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

import com.project.interfaces.Load;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SendPostRunnable implements Runnable
{
  Context appContext;

  public static String server_url = "http://140.118.19.64:8081/web2/";
  String fileName = ""; 
  String appid = null;
  String deviceid = null;
  String IMEI = null;

  boolean result = true;

  /**
   * @param result
   */
  public SendPostRunnable(String fileName, Context appContext)
  {
    super();
    this.fileName = fileName;
    this.appContext = appContext;
    this.appid = getAppId();
    this.deviceid = getDeviceId(appContext);
    this.IMEI = getIMEI(appContext);
  }

  private void sendPostDataToInternet()
  {
    CheckUser cu = new CheckUser(appid, deviceid, IMEI);
    setResult(cu.checkUser());
    Log.i("cu.checkUser()", "" + result);

    if (result)
    {
      // Asnyc Dowload
      String file_url = server_url + "download/" + fileName;
      
      new DownloadFileFromURL().execute(file_url);
    }
  }

  @Override
  public void run()
  {
    // TODO Auto-generated method stub
    sendPostDataToInternet();

  }

  public void setResult(boolean result)
  {
    // TODO Auto-generated method stub
    this.result = result;
  }

  public boolean getResult()
  {
    // TODO Auto-generated method stub
    return result;
  }

  // AsynTask
  class DownloadFileFromURL extends AsyncTask<String, String, String>
  {

    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute()
    {
      super.onPreExecute();
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url)
    {
      int count;
      try
      {
        URL url = new URL(f_url[0]);
        URLConnection conection = url.openConnection();
        conection.connect();

        // this will be useful so that you can show a tipical 0-100%
        // progress bar
        int lenghtOfFile = conection.getContentLength();

        // download the file
        InputStream input = new BufferedInputStream(url.openStream(),
            8192);

        File outputPath = new File(Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/project");

        if (!outputPath.exists())
        {
          outputPath.mkdir();
        }

        Log.e("e", outputPath.toString());

        // Output stream
        OutputStream output = new FileOutputStream(Environment
            .getExternalStorageDirectory().toString()
            + "/project/"
            + fileName);

        byte data[] = new byte[2048];

        long total = 0;

        while ((count = input.read(data)) != -1)
        {
          total += count;
          // publishing the progress....
          // After this onProgressUpdate will be called
          publishProgress("" + (int) ((total * 100) / lenghtOfFile));

          // writing data to file
          output.write(data, 0, count);
        }

        // flushing output
        output.flush();

        // closing streams
        output.close();
        input.close();

      } catch (Exception e)
      {
        Log.e("Error: ", e.getMessage());
      }

      return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress)
    {
      // setting progress percentage
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url)
    {
      // dynamic loading -----
      Load ld = new Load(fileName);
      ld.loadJar(appContext);
    }

  }

  private String getAppId()
  {
    String PACKAGE_NAME = appContext.getPackageName();
    Log.i("package name", PACKAGE_NAME);

    try
    {
      appid = AeSimpleSHA1.SHA1(PACKAGE_NAME);
    } catch (NoSuchAlgorithmException e)
    {
      // TODO 自動產生的 catch 區塊
      e.printStackTrace();
    } catch (UnsupportedEncodingException e)
    {
      // TODO 自動產生的 catch 區塊
      e.printStackTrace();
    }

    Log.i("appId", appid);
    Log.i("appId length", "" + appid.length());
//    File file = new File(".\\Guess A of hearts.apk");
//    String appid = null;
//    try
//    {
//      appid = Hash.sha1(file);
//    } catch (Exception e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    Log.i("AAA",appid);

    return appid;
  }

  private String getDeviceId(Context context2)
  {
    // TODO Auto-generated method stub
    DeviceUuidFactory DFactory = new DeviceUuidFactory(context2);
    String deviceId = DFactory.getDeviceUuid().toString();

    Log.i("deviceId", deviceId);
    Log.i("deviceId" + " length", "" + deviceId.length());

    return deviceId;
  }

  private String getIMEI(Context context2)
  {
    // TODO Auto-generated method stub
    TelephonyManager tM = (TelephonyManager) appContext
        .getSystemService(Context.TELEPHONY_SERVICE);
    String imei = tM.getDeviceId();
    
    Log.i("imei", imei);
    Log.i("imei" + " length", "" + imei.length());

    return imei;
  }

}
