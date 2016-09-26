package com.android.factorytest;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * （１）在应用创建时，初始化所有测试项。
 * （２）
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
        // 语言改变时，更新各测试项的测试标题名称
        updateValues();
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 更新所有测试项信息。
     * 首先更新主界面测试列表信息。
     * 其次更新所有测试项信息。
     * 最后更新自动测试项列表信息，因为该信息是从各测试项中直接获取的。
     */
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

    /**
     * 更新主界面测试列表信息。
     */
    public void updateTestList() {
        mTestList.clear();
        ArrayList<FactoryDatabase.ItemState> states = mFactoryDatabase.getAllTestState();
        String[] titles = mResources.getStringArray(R.array.test_item_title);
        String[] classes = mResources.getStringArray(R.array.test_item_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], getTestState(i, states));
                mTestList.add(info);
            }
        }
    }

    /**
     * 更新系统测试组测试列表信息。
     */
    public void updateSystemTestList() {
        mSystemTestList.clear();
        String[] titles = mResources.getStringArray(R.array.system_test_title);
        String[] classes = mResources.getStringArray(R.array.system_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mSystemTestList.add(info);
            }
        }
    }

    /**
     * 更新屏幕测试组测试列表信息。
     */
    public void updateScreenTestList() {
        mScreenTestList.clear();
        String[] titles = mResources.getStringArray(R.array.screen_test_title);
        String[] classes = mResources.getStringArray(R.array.screen_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mScreenTestList.add(info);
            }
        }
    }

    /**
     * 更新铃声测试组测试列表信息。
     */
    public void updateRingTestList() {
        mRingTestList.clear();
        String[] titles = mResources.getStringArray(R.array.ring_test_title);
        String[] classes = mResources.getStringArray(R.array.ring_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mRingTestList.add(info);
            }
        }
    }

    /**
     * 更新电话测试组测试列表信息。
     */
    public void updateTelephonyTestList() {
        mTelephonyTestList.clear();
        String[] titles = mResources.getStringArray(R.array.telephony_test_title);
        String[] classes = mResources.getStringArray(R.array.telephony_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mTelephonyTestList.add(info);
            }
        }
    }

    /**
     * 更新附件测试组测试列表信息。
     */
    public void updateAnnexTestList() {
        mAnnexTestList.clear();
        String[] titles = mResources.getStringArray(R.array.annex_test_title);
        String[] classes = mResources.getStringArray(R.array.annex_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mAnnexTestList.add(info);
            }
        }
    }

    /**
     * 更新无线测试组测试列表信息。
     */
    public void updateWirelessTestList() {
        mWirelessTestList.clear();
        String[] titles = mResources.getStringArray(R.array.wireless_test_title);
        String[] classes = mResources.getStringArray(R.array.wireless_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mWirelessTestList.add(info);
            }
        }
    }

    /**
     * 更新传感器测试组测试列表信息。
     */
    public void updateSensorTestList() {
        mSensorTestList.clear();
        String[] titles = mResources.getStringArray(R.array.sensor_test_title);
        String[] classes = mResources.getStringArray(R.array.sensor_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mSensorTestList.add(info);
            }
        }
    }

    /**
     * 更新相机测试组测试列表信息。
     */
    public void updateCameraTestList() {
        mCameraTestList.clear();
        String[] titles = mResources.getStringArray(R.array.camera_test_title);
        String[] classes = mResources.getStringArray(R.array.camera_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], mFactoryDatabase.getTestState(classes[i]));
                mCameraTestList.add(info);
            }
        }
    }

    /**
     * 更新按键测试组测试列表信息。
     */
    public void updateKeyTestList() {
        mKeyTestList.clear();
        String[] titles = mResources.getStringArray(R.array.key_test_title);
        String[] classes = mResources.getStringArray(R.array.key_test_class);
        int length = (titles.length <= classes.length ? titles.length : classes.length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                TestItemInfo info = new TestItemInfo(titles[i], getPackageName(), classes[i], TestItemInfo.State.UNKNOWN);
                mKeyTestList.add(info);
            }
        }
    }

    /**
     * 更新自动测试列表信息。
     */
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

    /**
     * 获取测试组的测试状态信息。
     * @param index 测试组下标，从０开始
     * @param states　所有测试项的测试状态数组
     * @return  如果测试组中的所有测试项都测试通过，则返回State.PASS；如果测试组中有一项或多项测试不通过，则返回State.FAIL；否则返回State.UNKNOWN。
     */
    public TestItemInfo.State getTestState(int index, ArrayList<FactoryDatabase.ItemState> states) {
        TestItemInfo.State state = TestItemInfo.State.UNKNOWN;
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

    /**
     * 获取测试项的测试状态
     * @param className 测试项的类名
     * @return 如果测试通过，返回State.PASS; 如果测试失败，则返回State.FAIL；否则返回State.UNKNOWN。
     */
    public TestItemInfo.State getTestState(String className) {
        return mFactoryDatabase.getTestState(className);
    }

    /**
     * 设置测试项的测试状态。
     * @param index 测试项所在的下标
     * @param className　测试项的类名
     * @param state 测试结果
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setTestState(int index, String className, TestItemInfo.State state) {
        long result = mFactoryDatabase.setTestState(index, className, state);
        return result != -1;
    }

    /**
     * 查找测试项的下标
     * @param className　测试项的类名
     * @return 如果找到测试项，这返回该测试项的下标；否则返回-1
     */
    public int findTestIndex(String className) {
        int index = -1;
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

    /**
     * 重置自动测试下标
     */
    public void resetAutoTest() {
        mTestPosition = 0;
    }

    /**
     * 删除所有测试数据
     */
    public void clearAllData() {
        mFactoryDatabase.clearAllData();
    }

    /**
     * 启动自动测试。
     */
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

    /**
     * 自动测试中，测试下一个测试项。
     */
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
