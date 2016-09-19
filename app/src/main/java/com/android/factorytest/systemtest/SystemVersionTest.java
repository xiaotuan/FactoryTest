package com.android.factorytest.systemtest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.Log;
import com.android.factorytest.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemVersionTest extends BaseActivity {

    private static final int REQUEST_PERMISSION_CODE = 119;

    private TextView mAndroidVersionTv;
    private TextView mKernalVersionTv;
    private TextView mSystemVersionTv;
    private TextView mDeviceSerialNumberTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.system_version_test_title);
        setContentView(R.layout.system_version_test);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String androidVersion = Build.VERSION.RELEASE;
        String kernalVersion = getKernalVersion();
        String systemVersion = Build.DISPLAY;
        String deviceSerialNumber = getDeviceSerialNumber();
    }

    private void initViews() {
        mAndroidVersionTv = (TextView) findViewById(R.id.android_version);
        mKernalVersionTv = (TextView) findViewById(R.id.kernal_version);
        mSystemVersionTv = (TextView) findViewById(R.id.system_version);
        mDeviceSerialNumberTv = (TextView) findViewById(R.id.device_serial_number);
    }

    private String getKernalVersion() {
        String version = null;
        BufferedReader reader = null;
        StringBuffer versionBuffer = new StringBuffer();

        try {
            FileInputStream fi = new FileInputStream("/proc/version");
            reader = new BufferedReader(new InputStreamReader(fi));
            String str = reader.readLine();

            while (str != null) {
                versionBuffer.append(str + "\n");
                str = reader.readLine();
            }
        } catch (Exception e) {
            versionBuffer = null;
            Log.d(this, "getKernalVersion=>error: ", e);
        }finally {
            if (versionBuffer != null) {
                version = versionBuffer.toString();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(this, "getKernalVersion=>kernal version: " + version);
        return version;
    }

    private String getDeviceSerialNumber() {
        String number = null;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PackageManager pm = getPackageManager();
        if (tm != null && pm != null) {
            if (PackageManager.PERMISSION_GRANTED !=
                    pm.checkPermission("android.permission.READ_PHONE_STATE", "packageName")) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},REQUEST_PERMISSION_CODE);
            } else {
                number = tm.getDeviceId();
            }
        }
        Log.d(this, "getDeviceSerialNumber=>number: " + number);
        return number;
    }
}
