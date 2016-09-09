package com.qty.factorytest.systemtest;

import android.app.Activity;
import android.os.Bundle;

import com.qty.factorytest.BaseListActivity;

public class SystemTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateSystemTestList();
        setTestList(mApplication.getSystemTestList());
    }
}
