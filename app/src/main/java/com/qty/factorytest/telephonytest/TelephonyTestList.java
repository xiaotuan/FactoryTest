package com.qty.factorytest.telephonytest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseListActivity;

public class TelephonyTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateTelephonyTestList();
        setTestList(mApplication.getTelephonyTestList());
    }
}
