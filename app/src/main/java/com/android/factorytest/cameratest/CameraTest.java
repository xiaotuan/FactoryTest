package com.android.factorytest.cameratest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.R;

public class CameraTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_test);
    }
}
