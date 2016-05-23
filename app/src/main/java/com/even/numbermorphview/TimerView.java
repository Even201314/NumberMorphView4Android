package com.even.numbermorphview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

/**
 * TimerView
 * Created by Even on 20/5/16.
 */
public class TimerView extends NumberMorphView {

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean mRunning;
    private boolean mVisible;
    private boolean mStarted;
    private static final int TICK_WHAT = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mRunning) {
                if (isFinished) {
                    stop();
                    isFinished = false;
                } else {
                    updateNumber();
                    sendMessageDelayed(Message.obtain(this, TICK_WHAT), 0);
                }
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }

    private void updateRunning() {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                updateNumber();
                mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), 0);
            } else {
                mHandler.removeMessages(TICK_WHAT);
            }
            mRunning = running;
        }
    }

    @Override
    public void setPeriod(double period) {
        super.setPeriod(1000.0 / period);
    }

    @SuppressWarnings("unused")
    public void start() {
        mStarted = true;
        updateRunning();
    }

    @SuppressWarnings("unused")
    public void stop() {
        mStarted = false;
        updateRunning();
    }

    public void setCurrentNum(int currentNum) {
        setCurrentIndex(currentNum);
        start();
    }

    @SuppressWarnings("unused")
    public int getCurrentNum() {
        return getCurrentIndex();
    }
}
