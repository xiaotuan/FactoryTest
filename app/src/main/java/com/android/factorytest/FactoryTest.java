package com.android.factorytest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FactoryTest extends Activity {

    private TextView mAutoTestTv;
    private GridLayout mItemTestContainer;

    private Resources mResources;
    private FactoryTestApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_test);

        initValues();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.updateValues();
        updateItemContainer();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.factory_testing_tool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    public View.OnClickListener mTestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof TestItemInfo) {
                TestItemInfo info = (TestItemInfo) v.getTag();
                Log.d(FactoryTest.this, "onClick(test)=>class: " + info.getClassName());
                Intent intent = info.getIntent();
                intent.putExtra(Utils.EXTRA_AUTO_TEST, false);
                intent.putExtra(Utils.EXTRA_PARENT, FactoryTest.this.getClass().getSimpleName());
                if (intent != null) {
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e(this, "(mTestClickListener)onClick=>start activity fail. intent: " + intent + " error: ", e);
                        Toast.makeText(FactoryTest.this, R.string.not_found_test, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    public View.OnClickListener mAutoTestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mApplication.resetAutoTest();
            mApplication.clearAllData();
            mApplication.startAutoTest();
        }
    };

    private void initValues() {
        mResources = getResources();
        mApplication = (FactoryTestApplication) getApplication();
    }

    private void initViews() {
        mAutoTestTv = (TextView) findViewById(R.id.auto_test);
        mItemTestContainer = (GridLayout) findViewById(R.id.test_item_container);

        mAutoTestTv.setOnClickListener(mAutoTestClickListener);
    }

    private TestItemInfo.State getTestState(String test) {
        return TestItemInfo.State.UNKNOW;
    }

    private void updateItemContainer() {
        mItemTestContainer.removeAllViews();
        ArrayList<TestItemInfo> testList = mApplication.getTestList();
        int padding = mResources.getDimensionPixelSize(R.dimen.activity_vertical_margin);
        int height = mResources.getDimensionPixelSize(R.dimen.item_height);
        Drawable leftBackground = mResources.getDrawable(R.drawable.ic_left_item_background);
        Drawable rightBackground = mResources.getDrawable(R.drawable.ic_right_item_background);
        int unknown = mResources.getColor(R.color.white);
        int fail = mResources.getColor(R.color.red);
        int pass = mResources.getColor(R.color.green);
        int maxWidth = mResources.getDisplayMetrics().widthPixels - (2 * padding);
        Log.d(this, "updateItemContainer=>size: " + testList.size() + ", maxWidth: " + maxWidth);
        if (testList.size() > 0) {
            for (int i = 0; i < testList.size(); i++) {
                TestItemInfo info = testList.get(i);
                TextView itemTest = new TextView(this);
                itemTest.setText(info.getTitle());
                itemTest.setTypeface(itemTest.getTypeface(), Typeface.BOLD);
                itemTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, mResources.getDimension(R.dimen.item_test_text_size));
                itemTest.setGravity(Gravity.CENTER);
                if (info.getState() == TestItemInfo.State.UNKNOW) {
                    itemTest.setTextColor(unknown);
                } else if (info.getState() == TestItemInfo.State.FAIL) {
                    itemTest.setTextColor(fail);
                } else if (info.getState() == TestItemInfo.State.PASS) {
                    itemTest.setTextColor(pass);
                }
                if (((i + 1) % 2) != 0) {
                    itemTest.setBackground(leftBackground);
                } else {
                    itemTest.setBackground(rightBackground);
                }
                itemTest.setTag(info);
                itemTest.setOnClickListener(mTestClickListener);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setGravity(Gravity.CENTER);
                params.width = maxWidth / 2;
                params.height = height;
                mItemTestContainer.addView(itemTest, i, params);
            }
        }
    }
}
