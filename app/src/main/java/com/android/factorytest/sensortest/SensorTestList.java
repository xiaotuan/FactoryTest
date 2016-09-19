package com.android.factorytest.sensortest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseListActivity;

public class SensorTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateSensorTestList();
        setTestList(mApplication.getSensorTestList());
    }
}
