package com.android.factorytest.wirelesstest;

import android.app.Activity;
import android.os.Bundle;

import com.android.factorytest.BaseListActivity;

public class WirelessTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateWirelessTestList();
        setTestList(mApplication.getWirelessTestList());
    }
}
