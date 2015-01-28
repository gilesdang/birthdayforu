package com.augmentum.giles.happyforu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.augmentum.giles.happyforu.CommonView.Image3DSwitchView;
import com.augmentum.giles.happyforu.R;

/**
 * Created by user on 15-1-28.
 */
public class PhotoActivity extends Activity{
    private TextView mTextViewInfo;
    private Button mButtonContinue;
    private static final int MSG_NEXT_PHOTO = 520;
    private Image3DSwitchView mImageSwitchViewPhoto;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_NEXT_PHOTO)
            {
                mImageSwitchViewPhoto.scrollToNext();
            }
        }
    };

    Thread mNextThread = new Thread(){
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    Thread.sleep(5000);
                    Message msg = new Message();
                    msg.what = MSG_NEXT_PHOTO;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mTextViewInfo = (TextView)findViewById(R.id.activity_photo_text_view_info);
        mImageSwitchViewPhoto = (Image3DSwitchView)findViewById(R.id.image_switch_view);
        mButtonContinue = (Button)findViewById(R.id.activity_photo_button_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        mImageSwitchViewPhoto.setOnImageSwitchListener(new Image3DSwitchView.OnImageSwitchListener() {
            @Override
            public void onImageSwitch(int currentImage) {
                mTextViewInfo.setText(currentImage + ".................................");
                if (currentImage == mImageSwitchViewPhoto.getChildCount()-1)
                {
                    mButtonContinue.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNextThread.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            mNextThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
