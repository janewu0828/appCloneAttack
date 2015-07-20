package trustedappframework.subprojecttwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 3000; // delay 3 sec.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashActivity.this, ListActivity.class); // 主要檔案名稱
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);

	}

}
