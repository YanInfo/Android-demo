package com.iauto.boradcasttest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iauto.boradcasttest.tools.BaseActivity;

import static android.content.ContentValues.TAG;

/**
 * @author zhangyan
 */
public class LoginActivity extends BaseActivity {

    private EditText user;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        //登录按钮监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText_user = user.getText().toString();
                String inputText_password = password.getText().toString();

                //判断用户名密码是否匹配
                if (inputText_user.equals("admin") && inputText_password.equals("123456")) {


                    Log.d(TAG, "哎呦,竟然蒙对了!!");
                    //登录成功，跳转到成功页面
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "呵呵,密码错误,请重试..",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
