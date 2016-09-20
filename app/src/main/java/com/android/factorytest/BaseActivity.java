package com.android.factorytest;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    protected static final int MSG_FAIL = 0;
    protected static final int MSG_PASS = 1;

    protected Button mPassBtn;
    protected Button mFailBtn;

    protected FactoryTestApplication mApplication;

    protected boolean mIsAutoTest;
    protected int mAutoTestDelayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);
        //getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
        if (mHandler.hasMessages(MSG_PASS)) {
            mHandler.removeMessages(MSG_PASS);
        }
        if (mHandler.hasMessages(MSG_FAIL)) {
            mHandler.removeMessages(MSG_FAIL);
        }
        String className = this.getClass().getName();
        int index = mApplication.findTestIndex(className);
        Log.d(this, "onClick=>pass: " + mPassBtn.getId() + " fail: " + mFailBtn.getId() + " id: " + v.getId());
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
        mAutoTestDelayTime = getResources().getInteger(R.integer.auto_test_delay_time);
        String className = getIntent().getStringExtra(Utils.EXTRA_PARENT);
        Log.d(this, "initValues=>auto test: " + mIsAutoTest + " parent: " + className);
    }

    private void initViews() {
        mPassBtn = (Button) findViewById(R.id.pass);
        mFailBtn = (Button) findViewById(R.id.fail);

        mPassBtn.setOnClickListener(this);
        mFailBtn.setOnClickListener(this);
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("BaseActivity", "handleMessage=>what: " + msg.what);
            switch (msg.what) {
                case MSG_PASS:
                    onClick(mPassBtn);
                    break;

                case MSG_FAIL:
                    onClick(mFailBtn);
                    break;
            }
        }
    };

}
