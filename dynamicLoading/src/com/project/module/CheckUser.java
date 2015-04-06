package com.project.module;

import static com.project.module.SendPostRunnable.appSecurityEnhancer_url;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckUser {
	public static final int progress_bar_type = 0;

	private String uri = appSecurityEnhancer_url + "php/app.php";
	private String appid = null;
	private String deviceid = null;
	private String IMEI = null;

	// 主要是记录用户会话过程中的一些用户的基本信息
	private HashMap<String, String> session = new HashMap<String, String>();

	/**
	 * @param appid
	 * @param deviceid
	 */
	public CheckUser(String appid, String deviceid, String IMEI) {
		super();
		this.appid = appid;
		this.deviceid = deviceid;
		this.IMEI = IMEI;
	}

	public boolean checkUser() {

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(uri);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("appId", appid));
		pairs.add(new BasicNameValuePair("deviceId", deviceid));
		pairs.add(new BasicNameValuePair("IMEI", IMEI));

		try {
			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			HttpResponse response = mHttpClient.execute(mPost);
			int res = response.getStatusLine().getStatusCode();

			if (res == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					String info = EntityUtils.toString(entity);
					System.out.println("-----------info-----------" + info);
					// 以下主要是对服务器端返回的数据进行解析
					JSONObject jsonObject = null;
					// flag为登录成功与否的标记,从服务器端返回的数据
					String flag = "";
					String appId = "";
					String deviceId = "";
					// String IMEI="";
					String sessionid = "";
					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
						appId = jsonObject.getString("appId");
						deviceId = jsonObject.getString("deviceId");
						// IMEI = jsonObject.getString("IMEI");
						sessionid = jsonObject.getString("sessionid");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 根据服务器端返回的标记,判断服务端端验证是否成功
					if (flag.equals("success")) {
						// 为session传递相的值,用于在session过程中记录相关用户信息
						session.put("s_userid", appId);
						session.put("s_username", deviceId);
						session.put("s_sessionid", sessionid);

						return true;
					} else {

						return false;
					}
				} else {

					return false;
				}

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
