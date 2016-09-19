package com.android.factorytest.sensortest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.R;

public class GravitySensorTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gravity_sensor_test);
    }
}
