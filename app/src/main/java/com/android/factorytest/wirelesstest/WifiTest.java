package com.android.factorytest.wirelesstest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.R;

public class WifiTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_test);
    }
}
