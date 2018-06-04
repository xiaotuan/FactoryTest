package com.android.factorytest;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

public class TestReportActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_report);

        setTitle(R.string.test_report_title);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
