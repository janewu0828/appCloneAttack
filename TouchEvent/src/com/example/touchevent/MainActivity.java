package com.example.touchevent;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private float touchX;
	private float touchY;
	private int tvWidth = LayoutParams.WRAP_CONTENT;
	private int tvHeight = LayoutParams.WRAP_CONTENT;
	private TextView tv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new Fragment()).commit();
		}

		// 取得 TextView 物件
		tv = (TextView) findViewById(R.id.textWidget);
		tv.setTextColor(0xFF000000);
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

	@Override
	// 利用 MotionEvent 處理觸控程序
	public boolean onTouchEvent(MotionEvent event) {

		touchX = event.getX(); // 觸控的 X 軸位置
		touchY = event.getY() - 50; // 觸控的 Y 軸位置

		// 判斷觸控動作
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN: // 按下

			// 設定 TextView 內容, 大小, 位置
			tv.setText("X: " + touchX + ", Y: " + touchY + ", 按下");
			tv.setLayoutParams(new AbsoluteLayout.LayoutParams(tvWidth,
					tvHeight, (int) touchX, (int) touchY));
			break;

		case MotionEvent.ACTION_MOVE: // 拖曳移動

			// 設定 TextView 內容, 大小, 位置
			tv.setText("X: " + touchX + ", Y: " + touchY + ", 拖曳移動");
			tv.setLayoutParams(new AbsoluteLayout.LayoutParams(tvWidth,
					tvHeight, (int) touchX, (int) touchY));
			break;

		case MotionEvent.ACTION_UP: // 放開

			// 設定 TextView 內容
			tv.setText("X: " + touchX + ", Y: " + touchY + ", 放開");
			break;
		}

		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}
