package com.example.myapplicationnumbaseball2;

import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity {

    private Button startButton;
    private Button rules_Button;
    private RadioGroup difficultyGroup;
    private RadioButton easyModeRadioButton, normalModeRadioButton, hardModeRadioButton, timeLimitModeRadioButton;

    private ActivityResultLauncher<Intent> timeLimitActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        startButton = findViewById(R.id.startButton);
        rules_Button = findViewById(R.id.rules_Button);
        difficultyGroup = findViewById(R.id.difficultyGroup);
        easyModeRadioButton = findViewById(R.id.easyRadioButton);
        normalModeRadioButton = findViewById(R.id.normalRadioButton);
        hardModeRadioButton = findViewById(R.id.hardRadioButton);
        timeLimitModeRadioButton = findViewById(R.id.timeLimitModeRadioButton);


        // 게임 시작 버튼 설정
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedDifficulty = getSelectedDifficulty();

                if (selectedDifficulty == 4) {     // 시간 제한 모드 실행 시
                    Intent intent = new Intent(StartActivity.this, TimelimitsettingActivity.class);
                    startActivity(intent);
                }else {   // 다른 난이도 선택시
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", selectedDifficulty);
                    startActivity(intent);
                }
                finish();
            }
        });

        rules_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private int getSelectedDifficulty() {
        int selectedId = difficultyGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.easyRadioButton) {
            return 1;
        } else if (selectedId == R.id.normalRadioButton) {
            return 2;
        } else if (selectedId == R.id.hardRadioButton) {
            return 3;
        } else if (selectedId == R.id.timeLimitModeRadioButton) {
            return 4;
        } else {
            return 2;
        }
    }
}