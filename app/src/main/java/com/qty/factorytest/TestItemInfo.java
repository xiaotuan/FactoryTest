package com.qty.factorytest;

import android.content.ComponentName;
import android.content.Intent;

public class TestItemInfo {
	
	private String mTitle;
	private String mClassName;
	private State mState;
	
	public TestItemInfo(String title, String className, State state) {
		mTitle = title;
		mClassName = className;
		mState = state;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getClassName() {
		return mClassName;
	}
	
	public State getState() {
		return mState;
	}
	
	public Intent getIntent() {
		Intent intent = new Intent();
		ComponentName name = new ComponentName("com.android.factorytestingtool", mClassName);
		intent.setComponent(name);
		return intent;
	}

	public enum State {
		UNKNOW, PASS, FAIL
	}
}
