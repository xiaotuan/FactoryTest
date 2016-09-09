package com.qty.factorytest.telephonytest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseActivity;
import com.qty.factorytest.R;

public class LoopbackTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loopback_test);
    }
}
