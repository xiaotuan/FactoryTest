package com.android.factorytest.screentest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseListActivity;
import com.android.factorytest.R;

public class ScreenTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.screen_test_title);
        mApplication.updateScreenTestList();
        setTestList(mApplication.getScreenTestList());
    }
}
