package com.example.myapplicationnumbaseball2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HintActivity extends AppCompatActivity {

    private TextView resultView;
    private EditText positionInputView;
    private EditText numberInputView;
    private Button confirmButton;
    private int[] computerNumber;
    private int numberLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hint);

        resultView = findViewById(R.id.resultView);
        positionInputView = findViewById(R.id.positionInputView);
        numberInputView = findViewById(R.id.numberInputView);
        confirmButton = findViewById(R.id.confirmButton);

        // main에서 전달받은 숫자와 숫자길이 받아오기
        computerNumber = getIntent().getIntArrayExtra("computerNumber");
        numberLength = getIntent().getIntExtra("numberLength", 4);

        // 확인 버튼
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provideHint();
            }
        });
    }

    private void provideHint() {
        String positionInput = positionInputView.getText().toString();
        String numberInput = numberInputView.getText().toString();

        if (positionInput.isEmpty() || numberInput.isEmpty()) {
            resultView.setText("자리와 숫자를 입력하세요.");
            return;
        }

        int selectedPosition;
        int hintNumber;

        try {
            selectedPosition = Integer.parseInt(positionInput) -1;
            hintNumber = Integer.parseInt(numberInput);
        } catch (NumberFormatException e) {
            resultView.setText("자리와 숫자는 숫자만 입력할 수 있습니다.");
            return;
        }

        if (selectedPosition < 0 || selectedPosition >= numberLength) {
            resultView.setText("유효한 자리를 입력하세요.");
            return;
        }

        if (hintNumber < 0 || hintNumber > 9) {
            resultView.setText("0 ~ 9 까지의 숫자를 입력하세요.");
            return;
        }

        String hintMessage;
        if (computerNumber[selectedPosition] == hintNumber) {
            hintMessage = "힌트: " + (selectedPosition + 1) + "번째 자리의 숫자는 Strike입니다!";
        } else if (isBall(hintNumber)) {
            hintMessage = "힌트: " + (selectedPosition + 1) + "번째 자리의 숫자는 Ball입니다!";
        } else {
            hintMessage = "힌트: " + "선택한 숫자는 답에 포함되어 있지 않습니다";
        }

        resultView.setText(hintMessage);

        // 5초 후 메인 화면으로 돌아가기
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish(); // 현재 HintActivity를 종료하고 MainActivity로 돌아감
            }
        }, 5000);
    }

    private boolean isBall(int num) {
        for (int i = 0; i < numberLength; i++) {
            if (num == computerNumber[i]) {
                return true;
            }
        }
        return false;
    }
}