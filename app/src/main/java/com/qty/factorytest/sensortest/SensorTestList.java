package com.qty.factorytest.sensortest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseListActivity;

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
