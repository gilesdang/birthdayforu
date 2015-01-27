package com.augmentum.giles.happyforu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        mBirthday.add("12-15");
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
                if (mClickCount >5)
                {
                    mTextViewInfo.setText("好吧，你赢了，幸好我知道");
                    mEditTextName.setText("楠");
                    mEditTextBirthday.setText("12-15");
                    mTextViewInfo.setVisibility(View.VISIBLE);
                }
                String name = mEditTextName.getText().toString();
                String birthday = mEditTextBirthday.getText().toString();
                if (name == null || name.isEmpty() || !mNames.contains(name))
                {
                    mTextViewInfo.setVisibility(View.VISIBLE);
                }
                else if (birthday == null || birthday.isEmpty() || !mBirthday.contains(birthday))
                {
                    mTextViewInfo.setVisibility(View.VISIBLE);
                }
                else
                {
                    //TODO go to next activity
                }
                break;
            default:
                break;
        }
    }
}
