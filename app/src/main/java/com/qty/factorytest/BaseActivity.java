package com.qty.factorytest;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    protected Button mPassBtn;
    protected Button mFailBtn;
    protected Handler mHandler;

    protected FactoryTestApplication mApplication;


    protected boolean mIsAutoTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);
        getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);

        initActionBar();
        initValues();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mApplication.resetAutoTest();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            Toast.makeText(this, R.string.disabled_key_tip, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        String className = this.getClass().getName();
        int index = mApplication.findTestIndex(className);
        switch (v.getId()) {
            case R.id.pass:
                mApplication.setTestState(index, className, TestItemInfo.State.PASS);
                break;

            case R.id.fail:
                mApplication.setTestState(index, className, TestItemInfo.State.FAIL);
                break;
        }
        if (mIsAutoTest) {
            mApplication.startNextTest();
        }
        finish();
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initValues() {
        mApplication = (FactoryTestApplication) getApplication();
        mIsAutoTest = getIntent().getBooleanExtra(Utils.EXTRA_AUTO_TEST, false);
        String className = getIntent().getStringExtra(Utils.EXTRA_PARENT);
        mHandler = new Handler();
        Log.d(this, "initValues=>auto test: " + mIsAutoTest + " parent: " + className);
    }

    private void initViews() {
        mPassBtn = (Button) findViewById(R.id.pass);
        mFailBtn = (Button) findViewById(R.id.fail);

        mPassBtn.setOnClickListener(this);
        mFailBtn.setOnClickListener(this);
    }


}
