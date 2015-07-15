package com.tricks.readjsonfromurl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListviewActivity extends Activity {

	ListView listView;
	
	ArrayList<HashMap<String, String>> arrList;
	
	String server_uri="http://140.118.19.64:8081/sub_project2/php/login.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_list);
		
		listView = (ListView) findViewById(R.id.listview);
		arrList = new ArrayList<HashMap<String, String>>();
		
		
		String json_str = getJsonData();
		System.out.println("json_str= " + json_str);
		
		try{
        	JSONArray jArray = new JSONArray(json_str);
        	
        	for (int i = 0; i < jArray.length(); i++) {
        		JSONObject json = null;
        		json = jArray.getJSONObject(i);
        		
	        	HashMap<String, String> map1 = new HashMap<String, String>();
            	
            	// adding each child node to HashMap key => value
                map1.put("name", json.getString("name"));
                map1.put("ver", json.getString("ver"));
                map1.put("cmt", json.getString("cmt"));
                
                // adding HashList to ArrayList
                arrList.add(map1);
        	}
        	
        	
        } catch ( JSONException e) {
        	e.printStackTrace();            	
        }
		
		
		if(!arrList.isEmpty()){
//			ListAdapter adapter = new SimpleAdapter( this, arrList,
//	                R.layout.list_item, new String[] { "id", "name", "url" },
//	                new int[] { R.id.wid, R.id.name, R.id.url });
			ListAdapter adapter = new SimpleAdapter( this, arrList,
	                R.layout.list_item, new String[] { "name", "ver", "cmt" },
	                new int[] { R.id.wid, R.id.name, R.id.url });
	        
	        listView.setAdapter(adapter);
		}
	}
	
	
	private String getJsonData(){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
		
		String str = "";
		HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
		//        HttpPost myConnection = new HttpPost("http://demos.tricksofit.com/files/json2.php");
        HttpPost myConnection = new HttpPost(server_uri);
        
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("appId", "fc9c1707a25b6c0efb6c92df2f8c51a8d1a529726d317a04a62d9afa03efe0af"));
		pairs.add(new BasicNameValuePair("appId2", "1ee950b8e251b77104418721bd16e96312720bdc"));
		pairs.add(new BasicNameValuePair("UUID", "fb70ea33d5f40a81"));
		try {
			myConnection.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        try {
        	response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("-----------info-----------" + str);
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return str;
	}
}
