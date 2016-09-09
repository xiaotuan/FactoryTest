package com.qty.factorytest;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 16-9-6.
 */
public class FactoryTestApplication extends Application {

    private Resources mResources;
    private FactoryDatabase mFactoryDatabase;

    private ArrayList<TestItemInfo> mTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mAutoTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mSystemTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mScreenTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mRingTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mTelephonyTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mAnnexTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mWirelessTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mSensorTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mCameraTestList = new ArrayList<TestItemInfo>();
    private ArrayList<TestItemInfo> mKeyTestList = new ArrayList<TestItemInfo>();

    private int mTestPosition = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        mResources = getResources();
        mFactoryDatabase = FactoryDatabase.getInstance(this);
        updateValues();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        updateValues();
        super.onConfigurationChanged(newConfig);
    }

    public void updateValues() {
        updateTestList();
        updateSystemTestList();
        updateScreenTestList();
        updateRingTestList();
        updateTelephonyTestList();
        updateAnnexTestList();
        updateWirelessTestList();
        updateSensorTestList();
        updateCameraTestList();
        updateKeyTestList();
        updateAutoTestList();
    }

    public void updateTestList() {
        mTestList.clear();
        ArrayList<FactoryDatabase.ItemState> states = mFactoryDatabase.getAllTestState();
        String[] titles = mResources.getStringArray(R.array.test_item_title);
        String[] classes = mResources.getStringArray(R.array.test_item_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], getTestState(i, states));
                mTestList.add(info);
            }
        }
    }

    public void updateSystemTestList() {
        mSystemTestList.clear();
        String[] titles = mResources.getStringArray(R.array.system_test_title);
        String[] classes = mResources.getStringArray(R.array.system_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mSystemTestList.add(info);
            }
        }
    }

    public void updateScreenTestList() {
        mScreenTestList.clear();
        String[] titles = mResources.getStringArray(R.array.screen_test_title);
        String[] classes = mResources.getStringArray(R.array.screen_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mScreenTestList.add(info);
            }
        }
    }

    public void updateRingTestList() {
        mRingTestList.clear();
        String[] titles = mResources.getStringArray(R.array.ring_test_title);
        String[] classes = mResources.getStringArray(R.array.ring_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mRingTestList.add(info);
            }
        }
    }

    public void updateTelephonyTestList() {
        mTelephonyTestList.clear();
        String[] titles = mResources.getStringArray(R.array.telephony_test_title);
        String[] classes = mResources.getStringArray(R.array.telephony_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mTelephonyTestList.add(info);
            }
        }
    }

    public void updateAnnexTestList() {
        mAnnexTestList.clear();
        String[] titles = mResources.getStringArray(R.array.annex_test_title);
        String[] classes = mResources.getStringArray(R.array.annex_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mAnnexTestList.add(info);
            }
        }
    }

    public void updateWirelessTestList() {
        mWirelessTestList.clear();
        String[] titles = mResources.getStringArray(R.array.wireless_test_title);
        String[] classes = mResources.getStringArray(R.array.wireless_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mWirelessTestList.add(info);
            }
        }
    }

    public void updateSensorTestList() {
        mSensorTestList.clear();
        String[] titles = mResources.getStringArray(R.array.sensor_test_title);
        String[] classes = mResources.getStringArray(R.array.sensor_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mSensorTestList.add(info);
            }
        }
    }

    public void updateCameraTestList() {
        mCameraTestList.clear();
        String[] titles = mResources.getStringArray(R.array.camera_test_title);
        String[] classes = mResources.getStringArray(R.array.camera_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], mFactoryDatabase.getTestState(classes[i]));
                mCameraTestList.add(info);
            }
        }
    }

    public void updateKeyTestList() {
        mKeyTestList.clear();
        String[] titles = mResources.getStringArray(R.array.key_test_title);
        String[] classes = mResources.getStringArray(R.array.key_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], classes[i], TestItemInfo.State.UNKNOW);
                mKeyTestList.add(info);
            }
        }
    }

    public void updateAutoTestList() {
        mAutoTestList.clear();
        if (mSystemTestList.size() > 0) {
            for (int i = 0; i < mSystemTestList.size(); i++) {
                mAutoTestList.add(mSystemTestList.get(i));
            }
        }

        if (mScreenTestList.size() > 0) {
            for (int i = 0; i < mScreenTestList.size(); i++) {
                mAutoTestList.add(mScreenTestList.get(i));
            }
        }

        if (mRingTestList.size() > 0) {
            for (int i = 0; i < mRingTestList.size(); i++) {
                mAutoTestList.add(mRingTestList.get(i));
            }
        }

        if (mTelephonyTestList.size() > 0) {
            for (int i = 0; i < mTelephonyTestList.size(); i++) {
                mAutoTestList.add(mTelephonyTestList.get(i));
            }
        }

        if (mAnnexTestList.size() > 0) {
            for (int i = 0; i < mAnnexTestList.size(); i++) {
                mAutoTestList.add(mAnnexTestList.get(i));
            }
        }

        if (mWirelessTestList.size() > 0) {
            for (int i = 0; i < mWirelessTestList.size(); i++) {
                mAutoTestList.add(mWirelessTestList.get(i));
            }
        }

        if (mSensorTestList.size() > 0) {
            for (int i = 0; i < mSensorTestList.size(); i++) {
                mAutoTestList.add(mSensorTestList.get(i));
            }
        }

        if (mCameraTestList.size() > 0) {
            for (int i = 0; i < mCameraTestList.size(); i++) {
                mAutoTestList.add(mCameraTestList.get(i));
            }
        }

        if (mKeyTestList.size() > 0) {
            for (int i = 0; i < mKeyTestList.size(); i++) {
                mAutoTestList.add(mKeyTestList.get(i));
            }
        }
        Log.d(this, "updateAutoTestList=>size: " + mAutoTestList.size());
    }

    public TestItemInfo.State getTestState(int index, ArrayList<FactoryDatabase.ItemState> states) {
        TestItemInfo.State state = TestItemInfo.State.UNKNOW;
        String[] idNames = mResources.getStringArray(R.array.test_item_state_list);
        if (index < idNames.length) {
            int id = mResources.getIdentifier(idNames[index], "array", getPackageName());
            String[] testClasses = mResources.getStringArray(id);
            int passCount = 0;
            FactoryDatabase.ItemState item = null;
            if (testClasses.length > 0) {
                for (int i = 0; i < testClasses.length; i++) {
                    for (int j = 0; j < states.size(); j++) {
                        item = states.get(j);
                        if (item.mClassName.equals(testClasses[i]))
                            if (item.mState == TestItemInfo.State.FAIL) {
                                state = TestItemInfo.State.FAIL;
                                break;
                            } else if (item.mState == TestItemInfo.State.PASS) {
                                passCount++;
                                break;
                            }
                    }
                }
                if (passCount == testClasses.length) {
                    state = TestItemInfo.State.PASS;
                }
            }
        }
        return state;
    }

    public ArrayList<TestItemInfo> getTestList() {
        return mTestList;
    }

    public ArrayList<TestItemInfo> getSystemTestList() {
        return mSystemTestList;
    }

    public ArrayList<TestItemInfo> getScreenTestList() {
        return mScreenTestList;
    }

    public ArrayList<TestItemInfo> getRingTestList() {
        return mRingTestList;
    }

    public ArrayList<TestItemInfo> getTelephonyTestList() {
        return mTelephonyTestList;
    }

    public ArrayList<TestItemInfo> getAnnexTestList() {
        return mAnnexTestList;
    }

    public ArrayList<TestItemInfo> getWirelessTestList() {
        return mWirelessTestList;
    }

    public ArrayList<TestItemInfo> getSensorTestList() {
        return mSensorTestList;
    }

    public ArrayList<TestItemInfo> getCameraTestList() {
        return mCameraTestList;
    }

    public ArrayList<TestItemInfo> getKeyTestList() {
        return mKeyTestList;
    }
    public ArrayList<TestItemInfo> getAutoTestList() {
        return mAutoTestList;
    }

    public TestItemInfo.State getTestState(String className) {
        return mFactoryDatabase.getTestState(className);
    }

    public boolean setTestState(int index, String className, TestItemInfo.State state) {
        long result = mFactoryDatabase.setTestState(index, className, state);
        return result != -1;
    }

    public int findTestIndex(String className) {
        int index = 1;
        if (mAutoTestList.size() <= 0){
            updateAutoTestList();
        }
        TestItemInfo info = null;
        for (int i = 0; i < mAutoTestList.size(); i++) {
            info = mAutoTestList.get(i);
            if (info.getClassName().equals(className)) {
                index = i + 1;
                break;
            }
        }
        return index;
    }

    public void resetAutoTest() {
        mTestPosition = 0;
        mFactoryDatabase.clearAllData();
    }

    public void startAutoTest() {
        if (mAutoTestList.size() > 0) {
            TestItemInfo info = mAutoTestList.get(mTestPosition);
            Intent intent = info.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Utils.EXTRA_AUTO_TEST, true);
            intent.putExtra(Utils.EXTRA_PARENT, this.getClass().getSimpleName());
            Log.d(this, "startAutoTest=>intent: " + intent);
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.d(this, "startAutoTest=>error: ", e);
                Toast.makeText(this, R.string.not_found_test, Toast.LENGTH_SHORT).show();
            }
            mTestPosition++;
        }
    }

    public void startNextTest() {
        Log.d(this, "startAutoTest=>size: " + mAutoTestList.size() + " testPosition: " + mTestPosition);
        if (mAutoTestList.size() > 0) {
            if (mTestPosition < mAutoTestList.size()){
                TestItemInfo info = mAutoTestList.get(mTestPosition);
                Intent intent = info.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Utils.EXTRA_AUTO_TEST, true);
                intent.putExtra(Utils.EXTRA_PARENT, this.getClass().getSimpleName());
                Log.d(this, "startAutoTest=>intent: " + intent);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d(this, "startAutoTest=>error: ", e);
                    Toast.makeText(this, R.string.not_found_test, Toast.LENGTH_SHORT).show();
                }
                mTestPosition++;
            } else {
                Intent intent = new Intent(this, TestReportActivity.class);
                startActivity(intent);
            }
        }
    }
}
