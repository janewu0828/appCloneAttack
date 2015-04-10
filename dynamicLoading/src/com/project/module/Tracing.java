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

import android.util.Log;

public class Tracing {
	private static final String TAG = "Tracing";

	private String uri = appSecurityEnhancer_url + "php/tracing.php";
	private String personalKey = null;
	private String loadFileName = null;

	private HashMap<String, String> session;

	public Tracing(String loadFileName, String personalKey,
			HashMap<String, String> session) {
		super();
		this.loadFileName = loadFileName;
		this.personalKey = personalKey;
		this.session = session;
	}

	public boolean tracingLog() {
		String sess_app_id = session.get("s_app_id");
		String sess_deviceid = session.get("s_deviceid");
		String sess_id = session.get("s_sessionid");

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(uri);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("sess_app_id", sess_app_id));
		pairs.add(new BasicNameValuePair("sess_deviceid", sess_deviceid));
		pairs.add(new BasicNameValuePair("sess_load_file_name", loadFileName));
		pairs.add(new BasicNameValuePair("sess_personal_key", personalKey));
		pairs.add(new BasicNameValuePair("sess_sessionid", sess_id));

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
					String app_id = "";
					String deviceid = "";
					String load_file_name = "";
					String personal_key = "";
					String sessionid = "";

					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
						app_id = jsonObject.getString("app_id");
						deviceid = jsonObject.getString("deviceid");
						load_file_name = jsonObject.getString("load_file_name");
						personal_key = jsonObject.getString("personal_key");
						sessionid = jsonObject.getString("sessionid");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(TAG, "Error: " + e.getMessage());
					}
					// 根据服务器端返回的标记,判断服务端端验证是否成功
					if (flag.equals("success")) {
						// 为session传递相的值,用于在session过程中记录相关用户信息
						session.put("info_app_id", app_id);
						session.put("info_deviceid", deviceid);
						session.put("info_load_file_name", load_file_name);
						session.put("info_personal_key", personal_key);
						session.put("info_sessionid", sessionid);
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
			Log.e(TAG, "Error: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
		return false;
	}

}
