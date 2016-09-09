package com.qty.factorytest.systemtest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseActivity;
import com.qty.factorytest.R;

public class SystemVersionTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.system_version_test);
    }
}
