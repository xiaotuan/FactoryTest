package com.android.factorytest.annextest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.factorytest.BaseListActivity;
import com.android.factorytest.R;
import com.android.factorytest.TestItemInfo;

import java.util.ArrayList;

public class AnnexTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateAnnexTestList();
        setTestList(mApplication.getAnnexTestList());
    }
}
