package trustedappframework.subprojecttwo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import trustedappframework.subprojecttwo.R;

class ViewAppList {
	View baseView;
	ImageView imageView;
	TextView appName;

	public ViewAppList(View baseView) {
		this.baseView = baseView;
	}

	public ImageView getIvImage() {
		if (imageView == null) {
			imageView = (ImageView) baseView.findViewById(R.id.app_icon);
		}
		return imageView;
	}

	public TextView getTvAppname() {
		if (appName == null) {
			appName = (TextView) baseView.findViewById(R.id.app_name);
		}
		return appName;
	}
}
