package com.augmentum.giles.happyforu.ui;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;

import com.augmentum.giles.happyforu.CommonView.FlyTextView;
import com.augmentum.giles.happyforu.CommonView.GifImageView;
import com.augmentum.giles.happyforu.R;
import com.augmentum.giles.happyforu.com.fireview.MyView;

/**
 * Created by user on 15-1-28.
 */
public class BirthDayActivity extends Activity implements SensorEventListener {
    private MyView mFireView;
    private FlyTextView mTextInfo;
    private GifImageView mImageGift;
    private ImageView mImageViewEnd;
    private SensorManager mSensorManager = null;
    private final int MSG_INFO = 1314;
    private final int MSG_BEGIN = 520;
    private boolean mIsSuccess = false;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_INFO && !mIsSuccess)
            {
                Toast.makeText(BirthDayActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == MSG_BEGIN)
            {
                mTextInfo.setTextColor(getResources().getColor(R.color.common_yellow));
                mTextInfo.setTexts("Surprise....");
                mTextInfo.startAnimation();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mHandler.sendEmptyMessageDelayed(MSG_BEGIN, 3000l);
        mHandler.sendEmptyMessageDelayed(MSG_INFO, 8000l);
        mFireView = (MyView)findViewById(R.id.activity_birthday_fire_view);
        mImageGift = (GifImageView)findViewById(R.id.activity_birthday_image);
        mTextInfo = (FlyTextView)findViewById(R.id.activity_birthday_info);
        mImageViewEnd = (ImageView)findViewById(R.id.activity_birthday_image2);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(8000);
        animationSet.addAnimation(animation);
        mImageGift.startAnimation(animationSet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener((android.hardware.SensorEventListener) this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17))
            {
                mIsSuccess = true;
                mTextInfo.setTextColor(getResources().getColor(R.color.common_yellow));
                mTextInfo.setTexts("Happy....");
                mTextInfo.startAnimation();
                mImageGift.setVisibility(View.INVISIBLE);
                mImageViewEnd.setVisibility(View.VISIBLE);
                AnimationSet animationSet = new AnimationSet(true);
                AlphaAnimation animation = new AlphaAnimation(1, 0);
                animation.setDuration(5000);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mImageViewEnd.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animationSet.addAnimation(animation);
                mImageViewEnd.startAnimation(animationSet);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFireView!=null && mFireView.isRunning())
        {
            mFireView.setRunning(false);
        }
    }
}
