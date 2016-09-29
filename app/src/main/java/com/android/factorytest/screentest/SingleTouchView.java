package com.android.factorytest.screentest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.factorytest.Log;

import java.util.ArrayList;

/**
 * Created by user on 16-9-27.
 */

public class SingleTouchView extends View {

    private Paint mNormalPaint;
    private Paint mPassPaint;
    private Paint mLinePaint;
    private ArrayList<TestRect> mRects;
    private ArrayList<ArrayList<PT>> mLines;
    private ArrayList<PT> mCurrentLine;
    private CallBack mCallBack;

    private int mRectWidth;
    private int mLineWidth;
    private float mLastPointX;
    private float mLastPointY;
    private float mPointX;
    private float mPointY;
    private float mRecordStep = 4;
    private int mNormalColor;
    private int mPassColor;
    private int mLineColoe;
    private boolean mTestPass;

    public SingleTouchView(Context context) {
        this(context, null);
    }

    public SingleTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SingleTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRectWidth = 60;
        mLineWidth = 4;
        mTestPass = false;
        mNormalColor = 0xffff0000;
        mPassColor = 0xff00ff00;
        mLineColoe = 0xff000000;
        mNormalPaint = new Paint();
        mNormalPaint.setColor(mNormalColor);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mPassPaint = new Paint();
        mPassPaint.setColor(mPassColor);
        mPassPaint.setAntiAlias(true);
        mPassPaint.setStyle(Paint.Style.STROKE);
        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColoe);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mRects = new ArrayList<TestRect>();
        mLines = new ArrayList<ArrayList<PT>>();
        mCurrentLine = new ArrayList<PT>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            createRect();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TestRect rect = null;
        Paint paint = null;
        for (int i = 0; i < mRects.size(); i++) {
            rect = mRects.get(i);
            if (rect.isPass) {
                paint = mPassPaint;
            } else {
                paint = mNormalPaint;
            }
            canvas.drawRect(rect.left,rect.top, rect.right, rect.bottom, paint);
        }

        float lastX = 0;
        float lastY = 0;
        PT pt;
        ArrayList<PT> line;
        for (int i = 0; i < mLines.size(); i++) {
            line = mLines.get(i);
            for (int j = 0; j < line.size(); j++) {
                pt = line.get(j);
                if (line.size() == 1) {
                    canvas.drawPoint(pt.mX, pt.mY, mLinePaint);
                } else {
                    if (j > 0) {
                        canvas.drawLine(lastX, lastY, pt.mX, pt.mY, mLinePaint);
                    }
                    lastX = pt.mX;
                    lastY = pt.mY;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mTestPass) {
                    mLastPointX = mPointX = event.getX();
                    mLastPointY = mPointY = event.getY();
                    mCurrentLine = new ArrayList<PT>();
                    mCurrentLine.add(new PT(mPointX, mPointY));
                    mLines.add(mCurrentLine);
                    testRectPass(mPointX, mPointY);
                    isPass();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mTestPass) {
                    mPointX = event.getX();
                    mPointY = event.getY();
                    if (Math.abs(mPointX - mLastPointX) >= mRecordStep
                            || Math.abs(mPointY - mLastPointY) >= mRecordStep) {
                        mCurrentLine.add(new PT(mPointX, mPointY));
                        mLastPointX = mPointX;
                        mLastPointY = mPointY;
                    }
                    testRectPass(mPointX, mPointY);
                    isPass();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                invalidate();
                break;
        }
        return true;
    }

    private void testRectPass(float x, float y) {
        for (TestRect tr : mRects) {
            if (!tr.isPass) {
                if (tr.contain(x, y)) {
                    tr.isPass = true;
                }
            }
        }
    }

    private void isPass() {
        int passCount = 0;
        for (TestRect tr : mRects) {
            if (tr.isPass) {
                passCount++;
            } else {
                break;
            }
        }

        if (passCount == mRects.size()) {
            mTestPass = true;
            if (mCallBack != null) {
                mCallBack.onTestCompleted();
            }
        }
    }

    private void createRect() {
        mRects = new ArrayList<TestRect>();
        int width = getWidth();
        int height = getHeight();
        Log.d(this, "createRect=>width: " + width + " height: " + height);

        for (int i = 0; i < width; i = i + mRectWidth + 2) {
            Log.d(this, "createRect=>i: " + i);
            TestRect topRect = new TestRect();
            topRect.top = 1;
            topRect.left = i - 1;
            topRect.right = i + mRectWidth - 1;
            topRect.bottom = mRectWidth - 1;
            mRects.add(topRect);

            TestRect bottomRect = new TestRect();
            bottomRect.top = height - mRectWidth - 1;
            bottomRect.left = i - 1;
            bottomRect.right = i + mRectWidth - 1;
            bottomRect.bottom = height - 1;
            mRects.add(bottomRect);
        }

        for (int i = mRectWidth; i < height - mRectWidth; i = i + mRectWidth + 2) {
            TestRect leftRect = new TestRect();
            leftRect.top = i - 1;
            leftRect.left = 1;
            leftRect.right = mRectWidth -1;
            leftRect.bottom = i + mRectWidth - 1;
            mRects.add(leftRect);

            TestRect rightRect = new TestRect();
            rightRect.top = i - 1;
            rightRect.left = width - mRectWidth - 1;
            rightRect.right = width - 1;
            rightRect.bottom = i + mRectWidth - 1;
            mRects.add(rightRect);
        }

        int centerX = width / 2;
        int centery = height / 2;
        TestRect center = new TestRect();
        center.top = centery - mRectWidth / 2;
        center.left = centerX - mRectWidth / 2;
        center.right = centerX + mRectWidth / 2;
        center.bottom = centery + mRectWidth / 2;
        mRects.add(center);

        for (int i = centery - mRectWidth / 2, j = centerX; i > mRectWidth; i -= mRectWidth, j -= mRectWidth / 2) {
            TestRect leftUp = new TestRect();
            leftUp.top = i - mRectWidth;
            leftUp.left = j - mRectWidth;
            leftUp.right = j;
            leftUp.bottom = i;
            mRects.add(leftUp);
        }

        for (int i = centery - mRectWidth / 2 - mRectWidth, j = centerX; i > 0; i -= mRectWidth, j += mRectWidth / 2) {
            TestRect rightUp = new TestRect();
            rightUp.top = i;
            rightUp.left = j;
            rightUp.right = j + mRectWidth;
            rightUp.bottom = i + mRectWidth;
            mRects.add(rightUp);
        }

        for (int i = centery + mRectWidth / 2, j = centerX; i < height - mRectWidth; i += mRectWidth, j -= mRectWidth / 2) {
            TestRect leftDown = new TestRect();
            leftDown.top = i;
            leftDown.left = j - mRectWidth;
            leftDown.right = j;
            leftDown.bottom = i + mRectWidth;
            mRects.add(leftDown);
        }

        for (int i = centery + mRectWidth / 2, j = centerX; i < height - mRectWidth; i += mRectWidth, j += mRectWidth / 2) {
            TestRect rightDown = new TestRect();
            rightDown.top = i;
            rightDown.left = j;
            rightDown.right = j + mRectWidth;
            rightDown.bottom = i + mRectWidth;
            mRects.add(rightDown);
            Log.d(this, "createRect(rigthDown)=>i: " + i + " j: " + j);
        }

        if (mTestPass) {
            for (TestRect tr : mRects) {
                tr.isPass = true;
            }
        }
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onTestCompleted();
    }

    class TestRect {
        float top;
        float left;
        float right;
        float bottom;
        boolean isPass;

        public boolean contain(float x, float y) {
            if (x >= left && x <= right && y >= top && y <= bottom) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "[" + top + ", " + left + ", " + right + ", " + bottom + ", " + isPass + "]";
        }
    }

    public class PT {
        public float mX;
        public float mY;

        public PT(float x, float y) {
            this.mX = x;
            this.mY = y;
        }
    };
}
