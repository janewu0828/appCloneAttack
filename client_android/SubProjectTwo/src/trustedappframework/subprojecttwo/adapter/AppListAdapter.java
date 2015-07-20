package trustedappframework.subprojecttwo.adapter;

import java.util.List;

import trustedappframework.subprojecttwo.entity.Apps;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.trustedapp.subprojecttwo.R;

public class AppListAdapter extends ArrayAdapter<Apps> {

	private ListView listView = null;

	public AppListAdapter(Activity activity, List<Apps> imgsAndTexts,
			ListView listView) {
		super(activity, 0, imgsAndTexts);
		this.listView = listView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();

		View view = convertView;
		ViewAppList viewAppList;

		if (view == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.app_item, null);
			viewAppList = new ViewAppList(view);
			view.setTag(viewAppList);
		} else {
			viewAppList = (ViewAppList) view.getTag();
		}

		Apps data = getItem(position);

		viewAppList.getIvImage().setImageDrawable(data.getApp_icon());
		viewAppList.getTvAppname().setText(data.getApp_name());
		return view;
	}
}