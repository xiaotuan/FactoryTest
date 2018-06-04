package com.android.factorytest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.factorytest.keytest.KeyTest;

/**
 * 测试项的基类
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    protected static final int MSG_FAIL = 0;
    protected static final int MSG_PASS = 1;

    protected Button mPassBtn;
    protected Button mFailBtn;
    protected View mBottomButtonContainer;

    protected FactoryTestApplication mApplication;

    // 是否是自动测试模式
    protected boolean mIsAutoTest;
    // 自动测试时，自动测试下一个测试项的时间
    protected int mAutoTestDelayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 允许应用获取Home键事件
        getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // 全屏显示
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
            // 取消自动测试，结束测试
            if (mIsAutoTest) {
                mApplication.resetAutoTest();
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this instanceof KeyTest) {
            return super.dispatchKeyEvent(event);
        }
        // 截断Home键和Back键事件
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                Toast.makeText(this, R.string.disabled_key_tip, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        // 移除MSG_PASS和MSG_FAIL消息
        if (mHandler.hasMessages(MSG_PASS)) {
            mHandler.removeMessages(MSG_PASS);
        }
        if (mHandler.hasMessages(MSG_FAIL)) {
            mHandler.removeMessages(MSG_FAIL);
        }
        String className = this.getClass().getName();
        // 获取测试项下标
        int index = mApplication.findTestIndex(className);
        Log.d(this, "onClick=>pass: " + mPassBtn.getId() + " fail: " + mFailBtn.getId() + " id: " + v.getId());
        // 设置测试结果
        if (index != -1) {
            switch (v.getId()) {
                case R.id.pass:
                    mApplication.setTestState(index, className, TestItemInfo.State.PASS);
                    break;

                case R.id.fail:
                    mApplication.setTestState(index, className, TestItemInfo.State.FAIL);
                    break;
            }
        } else {
            Log.e(this, "onClick=>class: " + className + " is not find index. index: " + index);
            Toast.makeText(this, R.string.not_found_test_index, Toast.LENGTH_SHORT).show();
        }
        // 如果是自动测试模式，则启动下一个测试
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
        mBottomButtonContainer = findViewById(R.id.bottom_button_container);

        mPassBtn.setOnClickListener(this);
        mFailBtn.setOnClickListener(this);
    }

    /**
     * 用于自动测试，自动结束测试
     */
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
