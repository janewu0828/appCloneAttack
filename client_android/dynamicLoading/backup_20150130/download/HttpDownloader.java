package com.project.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpDownloader {

  private URL url = null;
  
  String urlStr = null;
  String path = null;
  String fileName = null;  
  
  public HttpDownloader(String fileName) {
    // TODO Auto-generated constructor stub
    path = "project"+ File.separator;
    this.fileName = fileName;
    urlStr = "http://140.118.19.15/web2/download/" + fileName;
  } 

  /**
   * @param urlStr
   * @param path
   * @param fileName
   * @return 0:檔下載出錯 1:檔下載成功 2:檔已經存在
   */
  public int downFile() {

    InputStream inputStream = null;
    try {
      FileUtils fileUtils = new FileUtils();

      if (fileUtils.isFileExist(path + fileName)) {
        return 2;
      } else {
        inputStream = getInputStreamFromURL(urlStr);
        File resultFile = fileUtils.write2SDFromInput(path, fileName,
            inputStream);
        if (resultFile == null) {
          return 0;
        }
      }

      try {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
      Log.i("Exception of download", "");

      return 0;
    } finally {
      //會發生例外錯誤：NullPointerException
//      try {
//        inputStream.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
    }
    return 1;

  }

  /**
   * 根據URL得到輸入流
   * 
   * @param urlStr
   * @return
   */
  public InputStream getInputStreamFromURL(String urlStr) {

    HttpURLConnection urlConn = null;
    InputStream inputStream = null;

    try {
      url = new URL(urlStr);
      urlConn = (HttpURLConnection) url.openConnection();
      inputStream = urlConn.getInputStream();

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return inputStream;
  }

}