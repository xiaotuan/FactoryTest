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
 * Created by user on 16-9-29.
 */

public class MultiTouchView extends View {

    private Paint mCirclePaint;
    private Paint mTextPaint;
    private CallBack mCallBack;
    private ArrayList<Point> mPoints;

    private int mRadius;
    private int mWidth;
    private int mTextSize;
    private int mTextPaddingBottom;
    private int mMaxPointCount;
    private int mCircleColor;
    private int mTextColor;
    private boolean mPass;

    public MultiTouchView(Context context) {
        this(context, null);
    }

    public MultiTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MultiTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = 60;
        mWidth = 8;
        mTextSize = 24;
        mTextPaddingBottom = 8;
        mMaxPointCount = 0;
        mCircleColor = 0xff00ff00;
        mTextColor = 0xff0000ff;
        mPass = false;
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(mWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mPoints = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Point p : mPoints) {
            drawCircle(canvas, p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        Log.d(this, "onTouchEvent=>count: " + MotionEvent.actionToString(action));
        int count = event.getPointerCount();
        if (mMaxPointCount < count) {
            mMaxPointCount = count;
        }
        Log.d(this, "onTouchEvent=>count: " + MotionEvent.actionToString(action) + " maxCount: " + mMaxPointCount);
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_POINTER_UP) {
            if (mMaxPointCount >= 2) {
                mPass = true;
                if (mCallBack != null) {
                    mCallBack.onTestCompleted();
                }
            }
        } else {
            updatePoints(event);
        }
        return true;
    }

    private void drawCircle(Canvas canvas, Point point) {
        canvas.drawCircle(point.x, point.y, mRadius, mCirclePaint);
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float height = fm.descent - fm.ascent;
        String text = "X: " + point.x + " Y: " + point.y;
        float width = mTextPaint.measureText(text);
        canvas.drawText(text, point.x - width / 2, point.y - mTextPaddingBottom - height - mRadius, mTextPaint);
    }

    private void updatePoints(MotionEvent ev) {
        int count = ev.getPointerCount();
        Log.d(this, "updatePoints=>count: " + ev.getPointerCount());
        mPoints = new ArrayList<Point>();
        for (int i = 0; i < count; i++) {
            mPoints.add(new Point(ev.getX(i), ev.getY(i)));
        }
        postInvalidate();
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onTestCompleted();
    }

    class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
