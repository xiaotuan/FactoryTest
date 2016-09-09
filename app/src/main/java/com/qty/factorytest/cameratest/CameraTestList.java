package com.qty.factorytest.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.qty.factorytest.BaseListActivity;
import com.qty.factorytest.R;
import com.qty.factorytest.TestItemInfo;

import java.util.ArrayList;

public class CameraTestList extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateCameraTestList();
        setTestList(mApplication.getCameraTestList());
    }
}
