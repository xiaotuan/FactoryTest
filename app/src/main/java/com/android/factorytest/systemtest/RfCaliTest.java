package com.android.factorytest.systemtest;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.Log;
import com.android.factorytest.R;

public class RfCaliTest extends BaseActivity {

    private TextView mSNTv;
    private TextView mCalibrationTv;
    private TextView mComprehensiveTestTv;

    private State mCalibrationState;
    private State mComprehensiveTestState;
    private int mDefaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.rf_cali_test_title);
        setContentView(R.layout.rf_cali_test);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sn = SystemProperties.get("gsm.serial", null);
        mCalibrationState = State.UNKNOWN;
        mComprehensiveTestState = State.UNKNOWN;
        if (!TextUtils.isEmpty(sn)) {
            if (sn.length() >= 63) {
                String calibration = sn.substring(60, 62);
                char comprehensiveTest = sn.charAt(62);
                Log.d(this, "onResume=>calibration: " + calibration + " comprehensiveTest: " + comprehensiveTest);
                if ("10".equals(calibration)) {
                    mCalibrationState = State.PASS;
                } else if ("01".equals(calibration)) {
                    mCalibrationState = State.FAIL;
                }
                if ('P' == comprehensiveTest) {
                    mComprehensiveTestState = State.PASS;
                } else if ('F' == comprehensiveTest) {
                    mComprehensiveTestState = State.FAIL;
                }
            } else {
                mCalibrationState = State.FAIL;
                mComprehensiveTestState = State.FAIL;
            }
        }
        Log.d(this, "onResume=>sn: " + sn + " calibration state: " + mCalibrationState + " comprehensive test state: " + mComprehensiveTestState);
        if (TextUtils.isEmpty(sn)) {
            mSNTv.setTextColor(getResources().getColor(R.color.orange, getTheme()));
            mSNTv.setText(R.string.unknown);
        } else {
            mSNTv.setTextColor(mDefaultTextColor);
            mSNTv.setText(sn);
        }
        switch (mCalibrationState) {
            case UNKNOWN:
                mCalibrationTv.setTextColor(getResources().getColor(R.color.orange, getTheme()));
                mCalibrationTv.setText(R.string.unknown);
                break;

            case FAIL:
                mCalibrationTv.setTextColor(getResources().getColor(R.color.red, getTheme()));
                mCalibrationTv.setText(R.string.fail);
                break;

            case PASS:
                mCalibrationTv.setTextColor(getResources().getColor(R.color.green, getTheme()));
                mCalibrationTv.setText(R.string.pass);
                break;
        }

        switch (mComprehensiveTestState) {
            case UNKNOWN:
                mComprehensiveTestTv.setTextColor(getResources().getColor(R.color.orange, getTheme()));
                mComprehensiveTestTv.setText(R.string.unknown);
                break;

            case FAIL:
                mComprehensiveTestTv.setTextColor(getResources().getColor(R.color.red));
                mComprehensiveTestTv.setText(R.string.fail);
                break;

            case PASS:
                mComprehensiveTestTv.setTextColor(getResources().getColor(R.color.green));
                mComprehensiveTestTv.setText(R.string.pass);
                break;
        }

        if (mIsAutoTest) {
            if (!TextUtils.isEmpty(sn) && mCalibrationState == State.PASS && mComprehensiveTestState == State.PASS) {
                mHandler.sendEmptyMessageDelayed(MSG_PASS, mAutoTestDelayTime);
            } else {
                mHandler.sendEmptyMessageDelayed(MSG_FAIL, mAutoTestDelayTime);
            }
        }
    }

    private void initViews() {
        mSNTv = (TextView) findViewById(R.id.sn);
        mCalibrationTv = (TextView) findViewById(R.id.calibration);
        mComprehensiveTestTv = (TextView) findViewById(R.id.comprehensive_test);
        mDefaultTextColor = mSNTv.getCurrentTextColor();
    }

    enum State {
        UNKNOWN, FAIL, PASS
    }

}
