package com.android.factorytest.telephonytest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.R;

public class SimCardTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_card_test);
    }
}
