package trustedappframework.subprojecttwo;

import trustedappframework.subprojecttwo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), ListActivity.class);
		startActivity(intent);
	}
}
