package com.android.factorytest.ringtest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseListActivity;

public class RingTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateRingTestList();
        setTestList(mApplication.getRingTestList());
    }
}
