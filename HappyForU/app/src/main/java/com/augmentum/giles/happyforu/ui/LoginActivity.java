package com.augmentum.giles.happyforu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.augmentum.giles.happyforu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15-1-26.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button mButtonLogin;
    private TextView mTextViewInfo;
    private EditText mEditTextName, mEditTextBirthday;
    private int mClickCount = 0;
    private List<String> mNames = new ArrayList<String>();
    private List<String> mBirthday = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNames.add("楠");
        mNames.add("楠");
        mNames.add("楠");
        mNames.add("楠");
        mBirthday.add("12-15");
        mBirthday.add("08-30");
        mBirthday.add("01-19");
        mBirthday.add("03-10");
        mButtonLogin = (Button) findViewById(R.id.activity_login_button);
        mTextViewInfo = (TextView) findViewById(R.id.textView_info);
        mEditTextBirthday = (EditText)findViewById(R.id.editText_birthday);
        mEditTextName = (EditText)findViewById(R.id.editText_name);
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_login_button:
                mClickCount ++;
                if (mClickCount >3)
                {
                    mTextViewInfo.setText("好吧，你赢了，幸好我知道");
                    mEditTextName.setText("楠");
                    mEditTextBirthday.setText("12-15");
                    mTextViewInfo.setVisibility(View.VISIBLE);
                    mClickCount = 0;
                }
                else {
                    String name = mEditTextName.getText().toString();
                    String birthday = mEditTextBirthday.getText().toString();
                    if (name == null || name.isEmpty() || !mNames.contains(name)) {
                        Toast.makeText(LoginActivity.this, "笨～不会连自己名字都不知道了吧", Toast.LENGTH_SHORT).show();
                    } else if (birthday == null || birthday.isEmpty() || !mBirthday.contains(birthday)) {
                        Toast.makeText(LoginActivity.this, "生日？好好想想，再试一次", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            default:
                break;
        }
    }
}
