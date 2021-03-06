package com.augmentum.giles.happyforu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.augmentum.giles.happyforu.CommonView.FlyTextView;
import com.augmentum.giles.happyforu.CommonView.RoundImageView;
import com.augmentum.giles.happyforu.R;

/**
 * Created by user on 15-1-28.
 */
public class ProfileActivity extends Activity{

    private RoundImageView mImageViewPhoto;
    private FlyTextView mTextInfo;
    private Button mButtonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImageViewPhoto = (RoundImageView) findViewById(R.id.activity_profile_image_photo);
        mTextInfo = (FlyTextView)findViewById(R.id.activity_profile_text_view_info);
        mButtonContinue = (Button)findViewById(R.id.activity_profile_button_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,PhotoActivity.class);
                startActivity(intent);
            }
        });
        mImageViewPhoto.setCircle();
        mTextInfo.setTexts("年轻的我们，在成长的过程中，少不了磕磕绊绊，雨雨风风。可是人生路上，总会有盏心灵的明灯照耀我们，一步步前进。");
        mTextInfo.startAnimation();
    }
}
