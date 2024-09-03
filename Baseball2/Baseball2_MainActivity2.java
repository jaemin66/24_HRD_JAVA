package com.example.myapplicationnumbaseball2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private TextView recordsView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recordsView = findViewById(R.id.recordsView);
        backButton = findViewById(R.id.backButton);

        // 전달된 기록을 받아오기
        ArrayList<String> records = getIntent().getStringArrayListExtra("records");

        if (records != null) {
            StringBuilder displayText = new StringBuilder();
            for (String record : records) {
                displayText.append(record).append("\n");
            }
            recordsView.setText(displayText.toString());
        }// ------------------- 기록받아 저장하기

        // 이전화면으로 돌아가는 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

}
