package trustedappframework.subprojecttwo.entity;

import android.graphics.drawable.Drawable;

public class Apps {
	private Drawable app_icon;
	private String app_name;
	private String pkg_name;

	public Apps(Drawable app_icon, String app_name,String pkg_name) {
		this.app_icon = app_icon;
		this.app_name = app_name;
		this.pkg_name = pkg_name;		
	}

	public Drawable getApp_icon() {
		return app_icon;
	}

	public void setApp_icon(Drawable app_icon) {
		this.app_icon = app_icon;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	
	public String getPkg_name() {
		return pkg_name;
	}
}
