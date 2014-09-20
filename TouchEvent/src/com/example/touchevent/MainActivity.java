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

		// ���o TextView ����
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
	// �Q�� MotionEvent �B�zĲ���{��
	public boolean onTouchEvent(MotionEvent event) {

		touchX = event.getX(); // Ĳ���� X �b��m
		touchY = event.getY() - 50; // Ĳ���� Y �b��m

		// �P�_Ĳ���ʧ@
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN: // ���U

			// �]�w TextView ���e, �j�p, ��m
			tv.setText("X: " + touchX + ", Y: " + touchY + ", ���U");
			tv.setLayoutParams(new AbsoluteLayout.LayoutParams(tvWidth,
					tvHeight, (int) touchX, (int) touchY));
			break;

		case MotionEvent.ACTION_MOVE: // �즲����

			// �]�w TextView ���e, �j�p, ��m
			tv.setText("X: " + touchX + ", Y: " + touchY + ", �즲����");
			tv.setLayoutParams(new AbsoluteLayout.LayoutParams(tvWidth,
					tvHeight, (int) touchX, (int) touchY));
			break;

		case MotionEvent.ACTION_UP: // ��}

			// �]�w TextView ���e
			tv.setText("X: " + touchX + ", Y: " + touchY + ", ��}");
			break;
		}

		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}
