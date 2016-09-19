package com.android.factorytest.sensortest;

import android.os.Bundle;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.R;

public class AmbientLightTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ambient_light_test);
    }
}
