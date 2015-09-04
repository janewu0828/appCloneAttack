package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPDAsyncTask.appSecurityEnhancer_url;

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

public class TracingLog {
	private static final String TAG = "TracingLog";

	private String uri = appSecurityEnhancer_url + "php/tracing_20150905.php";
	private HashMap<String, String> session;

	public TracingLog(HashMap<String, String> session) {
		super();
		this.session = session;
	}

	public boolean sendTracingLog(String loadFileName) {
		String UUID = SendPostRunnable.UUID;
		String[] personalKey = ACAPDAsyncTask.personalKey;
		String sess_id = session.get("s_sessionid");
		// System.out.println("sess_id= " + sess_id);

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(uri); // 建立HTTP Post連線

		// Post運作傳送變數用NameValuePair[]陣列儲存
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("sess_deviceid", UUID));
		pairs.add(new BasicNameValuePair("sess_load_file_name", loadFileName));
		pairs.add(new BasicNameValuePair("sess_personal_key", personalKey[0]));
		pairs.add(new BasicNameValuePair("sess_personal_key2", personalKey[1]));
		pairs.add(new BasicNameValuePair("sess_personal_key3", personalKey[2]));
		pairs.add(new BasicNameValuePair("sess_sessionid", sess_id));

		try {
			// 發出HTTP request
			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
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
					// 以下主要是對伺服器端傳回的資料進行解析
					JSONObject jsonObject = null;
					// flag為標記，是從伺服器端傳回的資料
					String flag = "";
					String personal_key_update_status = "";
					String[] new_personal_key = new String[personalKey.length];

					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
						personal_key_update_status = jsonObject
								.getString("personal_key_update_status");
						new_personal_key[0] = jsonObject
								.getString("new_personal_key");
						new_personal_key[1] = jsonObject
								.getString("new_personal_key2");
						new_personal_key[2] = jsonObject
								.getString("new_personal_key3");
					} catch (JSONException e) {
						e.printStackTrace();
						Log.e(TAG, "Error: " + e.getMessage());
					}
					// 根據伺服器端返回的標記，判斷伺服器端的結果
					if (flag.equals("notempty")) {
						// 為session傳遞的值，用於在session過程中記錄相關用戶訊息
						session.put("s_personal_key_update_status",
								personal_key_update_status);
						session.put("s_personal_key", new_personal_key[0]);
						session.put("s_personal_key2", new_personal_key[1]);
						session.put("s_personal_key3", new_personal_key[2]);

						return true;
					} else {
						return false;
					}
				} else {
					Log.e(TAG, "Error: " + "entity = null");
					return false;
				}

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
		return false;
	}

	public HashMap<String, String> getSession() {
		return session;
	}

}
