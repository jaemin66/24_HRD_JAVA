package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

        EditText inputId;
        EditText inputPw;

        Button loginBtn;
        Button registerBtn;

        DBHelper databaseHelper;

        String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        loginBtn  = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPw);

        databaseHelper = new DBHelper(this);

        convertActivity();
    }


    // 액티비티 전환 메소드
    protected void convertActivity() {
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        String username = inputId.getText().toString().trim();
        String password = inputPw.getText().toString().trim();

        if (databaseHelper.checkUser(username, password)) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
            UserSession.getInstance().setUserId(username);
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("userId", username); // 로그인한 사용자 ID를 Intent에 추가
            Log.i("login id" , UserSession.getInstance().getUserId());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }



}