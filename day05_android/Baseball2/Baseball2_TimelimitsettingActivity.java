package com.example.myapplicationnumbaseball2;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TimelimitsettingActivity extends AppCompatActivity {

    private EditText timeInputView;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timelimitsetting);

        timeInputView = findViewById(R.id.timeInputView);
        startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeLimitStr = timeInputView.getText().toString();
                int timeLimit = timeLimitStr.isEmpty() ? 60 : Integer.parseInt(timeLimitStr); // 기본값 설정
                Intent intent = new Intent(TimelimitsettingActivity.this, MainActivity.class);
                intent.putExtra("difficulty", 4); // 시간 제한 모드
                intent.putExtra("timeLimit", timeLimit);
                startActivity(intent);
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}