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
		String sess_deviceid = session.get("s_deviceid");
		String sess_id = session.get("s_sessionid");

		// System.out.println("sess_deviceid= " + sess_deviceid);
		// System.out.println("sess_id= " + sess_id);

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(uri); // 建立HTTP Post連線

		// Post運作傳送變數用NameValuePair[]陣列儲存
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("sess_deviceid", sess_deviceid));
		pairs.add(new BasicNameValuePair("sess_load_file_name", loadFileName));
		pairs.add(new BasicNameValuePair("sess_personal_key", personalKey));
		pairs.add(new BasicNameValuePair("sess_sessionid", sess_id));

		try {
			// 發出HTTP request
			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 取得HTTP response
			HttpResponse response = mHttpClient.execute(mPost);
			int res = response.getStatusLine().getStatusCode();

			// 若狀態碼為200 ok
			if (res == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					// 取出response字串
					String info = EntityUtils.toString(entity);
					System.out.println("-----------info-----------" + info);
					// 以下主要是对服务器端返回的数据进行解析
					JSONObject jsonObject = null;
					// flag为登录成功与否的标记,从服务器端返回的数据
					String flag = "";

					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(TAG, "Error: " + e.getMessage());
					}
					// 根据服务器端返回的标记,判断服务端端验证是否成功
					if (flag.equals("notempty")) {

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
