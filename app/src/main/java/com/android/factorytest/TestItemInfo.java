package com.android.factorytest;

import android.content.ComponentName;
import android.content.Intent;

public class TestItemInfo {
	
	private String mTitle;
	private String mPackageName;
	private String mClassName;
	private State mState;
	
	public TestItemInfo(String title, String packageName, String className, State state) {
		mTitle = title;
		mPackageName = packageName;
		mClassName = className;
		mState = state;
	}
	
	public String getTitle() {
		return mTitle;
	}

	public String getPackageName() { return mPackageName; }
	
	public String getClassName() {
		return mClassName;
	}
	
	public State getState() {
		return mState;
	}
	
	public Intent getIntent() {
		Intent intent = new Intent();
		ComponentName name = new ComponentName(mPackageName, mClassName);
		intent.setComponent(name);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		return intent;
	}

	public enum State {
		UNKNOW, PASS, FAIL
	}
}
