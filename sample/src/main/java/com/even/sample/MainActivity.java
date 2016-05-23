package com.even.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.even.numbermorphview.TimerView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TimerView numberMorphView;
    private TimerView numberMorphView1;
    private TimerView numberMorphView2;
    private TimerView numberMorphView3;
    private TimerView numberMorphView4;
    private TimerView numberMorphView5;

    private TimerView numberMorphView6;
    private TimerView numberMorphView7;
    private TimerView numberMorphView8;
    private TimerView numberMorphView9;
    private TimerView numberMorphView10;
    private TimerView numberMorphView11;


    private static final int TICK_WHAT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberMorphView = (TimerView) findViewById(R.id.numberMorphView);
        numberMorphView1 = (TimerView) findViewById(R.id.numberMorphView1);
        numberMorphView2 = (TimerView) findViewById(R.id.numberMorphView2);
        numberMorphView3 = (TimerView) findViewById(R.id.numberMorphView3);
        numberMorphView4 = (TimerView) findViewById(R.id.numberMorphView4);
        numberMorphView5 = (TimerView) findViewById(R.id.numberMorphView5);

        numberMorphView6 = (TimerView) findViewById(R.id.numberMorphView6);
        numberMorphView7 = (TimerView) findViewById(R.id.numberMorphView7);
        numberMorphView8 = (TimerView) findViewById(R.id.numberMorphView8);
        numberMorphView9 = (TimerView) findViewById(R.id.numberMorphView9);
        numberMorphView10 = (TimerView) findViewById(R.id.numberMorphView10);
        numberMorphView11 = (TimerView) findViewById(R.id.numberMorphView11);

        if (numberMorphView != null) {
            numberMorphView.interpolator = new TimerView.LinearInterpolator();
            numberMorphView.setPeriod(1000);
        }
        if (numberMorphView1 != null) {
            numberMorphView1.interpolator = new TimerView.OvershootInterpolator();
            numberMorphView1.setPeriod(1000);
        }
        if (numberMorphView2 != null) {
            numberMorphView2.interpolator = new TimerView.SpringInterpolator();
            numberMorphView2.setPeriod(1000);
        }
        if (numberMorphView3 != null) {
            numberMorphView3.interpolator = new TimerView.BounceInterpolator();
            numberMorphView3.setPeriod(1000);
        }
        if (numberMorphView4 != null) {
            numberMorphView4.interpolator = new TimerView.AnticipateOvershootInterpolator();
            numberMorphView4.setPeriod(1000);
        }
        if (numberMorphView5 != null) {
            numberMorphView5.interpolator = new TimerView.CubicHermiteInterpolator();
            numberMorphView5.setPeriod(1000);
        }

        if (numberMorphView6 != null) {
            numberMorphView6.interpolator = new TimerView.LinearInterpolator();
            numberMorphView6.setPeriod(1000);
        }
        if (numberMorphView7 != null) {
            numberMorphView7.interpolator = new TimerView.OvershootInterpolator();
            numberMorphView7.setPeriod(1000);
        }
        if (numberMorphView8 != null) {
            numberMorphView8.interpolator = new TimerView.SpringInterpolator();
            numberMorphView8.setPeriod(1000);
        }
        if (numberMorphView9 != null) {
            numberMorphView9.interpolator = new TimerView.BounceInterpolator();
            numberMorphView9.setPeriod(1000);
        }
        if (numberMorphView10 != null) {
            numberMorphView10.interpolator = new TimerView.AnticipateOvershootInterpolator();
            numberMorphView10.setPeriod(1000);
        }
        if (numberMorphView11 != null) {
            numberMorphView11.interpolator = new TimerView.CubicHermiteInterpolator();
            numberMorphView11.setPeriod(1000);
        }

        mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), 1000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            numberMorphView.setCurrentNum((hour / 10) % 24);
            numberMorphView1.setCurrentNum(hour % 10);

            numberMorphView2.setCurrentNum((minute / 10) % 6);
            numberMorphView3.setCurrentNum(minute % 10);

            numberMorphView4.setCurrentNum((second / 10) % 6);

            numberMorphView5.setCurrentNum(second % 10);

            numberMorphView6.setCurrentNum(second % 10);
            numberMorphView7.setCurrentNum(second % 10);
            numberMorphView8.setCurrentNum(second % 10);
            numberMorphView9.setCurrentNum(second % 10);
            numberMorphView10.setCurrentNum(second % 10);
            numberMorphView11.setCurrentNum(second % 10);

            sendMessageDelayed(Message.obtain(this, TICK_WHAT), 1000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }
}