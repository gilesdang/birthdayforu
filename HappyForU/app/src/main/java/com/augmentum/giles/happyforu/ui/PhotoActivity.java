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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15-1-28.
 */
public class PhotoActivity extends Activity{
    private TextView mTextViewInfo;
    private Button mButtonContinue;
    private static final int MSG_NEXT_PHOTO = 520;
    private Image3DSwitchView mImageSwitchViewPhoto;
    private List<String> mInfos = new ArrayList<String>();
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
        mInfos.add("人生是一条单行线，爸爸妈妈赋予了生命，就没有自己选择的权力。也许是因为从这一开始，就注定生命没有选择，所以太多抱怨，太多情绪，在成长中蔓延。");
        mInfos.add("年轻的我们，在成长的过程中，少不了磕磕绊绊，雨雨风风。可是人生路上，总会有盏心灵的明灯照耀我们，一步步前进。");
        mInfos.add("在人生的长河中，成长常常伴随着你，成长成了你永久的纪念和喜悦。");
        mInfos.add("成长在续集中，如我亲手在编织一个摇篮，总在编织，却依然不知道，究竟哪一天，要多大，这个摇篮才可以装下我要装的所有东西，还在成长，还会要关乎太多的东西，愿成长，一路走好。");
        mInfos.add("人生如行路，一路艰辛，一路风景。");
        mInfos.add("未完待续");
        mInfos.add("未完待续");
        mTextViewInfo = (TextView)findViewById(R.id.activity_photo_text_view_info);
        mTextViewInfo.setText(mInfos.get(0));
        mImageSwitchViewPhoto = (Image3DSwitchView)findViewById(R.id.image_switch_view);
        mButtonContinue = (Button)findViewById(R.id.activity_photo_button_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoActivity.this, BirthDayActivity.class);
                startActivity(intent);
            }
        });
        mImageSwitchViewPhoto.setOnImageSwitchListener(new Image3DSwitchView.OnImageSwitchListener() {
            @Override
            public void onImageSwitch(int currentImage) {
                if (currentImage < mInfos.size())
                {
                    mTextViewInfo.setText(mInfos.get(currentImage));
                }

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
