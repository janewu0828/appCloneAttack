package com.subprojecttwo.entity;

import android.graphics.drawable.Drawable;

public class Apps {
	public Drawable app_icon = null;
	public String app_name = null;

	public Apps(Drawable app_icon, String app_name) {
		this.app_icon = app_icon;
		this.app_name = app_name;
	}

	public Apps() {
		// TODO 自動產生的建構子 Stub
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
}
