package com.example.encryptapk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = "encrypt apk";
	private static String seed = "guess"; // 种子

	private TextView text_state;
	private EditText edit_seed;
	private Button btn_Encryption;

	private File sdCard = Environment.getExternalStorageDirectory();
	// 欲加密檔案的路径，在res\raw\encryptapk.apk找到文件，再放到外部存储的根目录下。用於测试
	private File oldFile = new File(sdCard, "encryptapk.apk");

	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	boolean isSuccess = false;

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String str = edit_seed.getText().toString();

			if (str == null || "".equals(str)) {
				text_state.setText("seed is null !!!");
				Log.e(TAG, "str is null" + str);
			} else {
				// 加密保存
				
				seed=str;
				isSuccess = true;
				
				try {
					fis = new FileInputStream(oldFile);
					byte[] oldByte = new byte[(int) oldFile.length()];
					fis.read(oldByte); // 读取

					Log.e(TAG, "seed= " + seed);
					byte[] newByte = AESUtils.encryptVoice(seed, oldByte);
					// 加密
					fos = new FileOutputStream(oldFile);
					fos.write(newByte);

				} catch (FileNotFoundException e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, String.valueOf(e));
				} catch (IOException e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, String.valueOf(e));
				} catch (Exception e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, String.valueOf(e));
				} finally {
					try {
						fis.close();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
						Log.e(TAG, String.valueOf(e));
					}
				}

				if (isSuccess) {
					text_state.setText("加密成功");
					Log.i(TAG, "seed=" + edit_seed.toString() + "加密成功");
				} else {
					text_state.setText("加密失敗");
				}
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text_state = (TextView) findViewById(R.id.text_state);
		edit_seed = (EditText) findViewById(R.id.edit_seed);

		btn_Encryption = (Button) findViewById(R.id.btn_encryption);
		btn_Encryption.setOnClickListener(new MyClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
