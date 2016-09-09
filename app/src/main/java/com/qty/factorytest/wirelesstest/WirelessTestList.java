package com.qty.factorytest.wirelesstest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseListActivity;

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
