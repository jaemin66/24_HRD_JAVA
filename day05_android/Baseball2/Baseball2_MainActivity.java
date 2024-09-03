package com.example.myapplicationnumbaseball2;

import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> records = new ArrayList<>();
    private TextView resultView;
    private TextView timerView;
    private EditText inputView;
    private Button button;
    private Button recordButton;  // 기록 보기 버튼
    private Button giveUpButton;  // 포기 버튼
    private Button hintButton;    // 힌트 버튼
    private int[] computerNumber = new int[4];
    private int[] userNumber = new int[4];
    private int strikeCount = 0;
    private int ballCount = 0;
    private int numberLength;   // 숫자의 자리 수
    private int timeLimit;    // 타이머 시간제한 (초 단위)
    private CountDownTimer countDownTimer;
    private int hintLimit;    // 힌트 사용 횟수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.resultView);  // 'rssultView'를 'resultView'로 수정
        inputView = findViewById(R.id.inputView);
        button = findViewById(R.id.button);
        recordButton = findViewById(R.id.recordButton);  // 기록 보기 버튼
        giveUpButton = findViewById(R.id.giveUpButton);
        hintButton = findViewById(R.id.hintButton);
        timerView = findViewById(R.id.timerView);     // 타이머 표시 textview


        int difficulty = getIntent().getIntExtra("difficulty", 2);

        switch (difficulty) {
            case 1:
                numberLength = 3;   // 쉬움 난이도 3자리
                hintLimit = 4;
                break;
            case 2:
                numberLength = 4;   // 보통 난이도 4자리
                hintLimit = 3;
                break;
            case 3:
                numberLength = 5;   // 어려움 난이도 5자리
                hintLimit = 2;
                break;
            case 4:   // 시간 제한 모드일 경우
                numberLength = 4;
                timeLimit = getIntent().getIntExtra("timeLimit", 60); // 타이머 설정 시간 가져오기
                startTimer(timeLimit);
                hintLimit = 2;
                break;
            default:
                numberLength = 4;
                hintLimit = 3;
        }

        // 자릿 수에 따른 배열 크기 설정
        computerNumber = new int[numberLength];
        userNumber = new int[numberLength];

        // 컴퓨터 숫자 생성
        generateComputerNumber(difficulty);


        // 게임 버튼 클릭 리스너 설정
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputView.getText().toString();
                if (input.length() == numberLength) {
                    inputView.setText("");
                    checkNumber(input);

                    // 입력한 숫자와 결과를 기록에 추가
                    String record = "Input: " + input + " - Strike: " + strikeCount + ", Ball: " + ballCount;
                    records.add(record);
                } else {
                    resultView.setText(numberLength + "자리 숫자를 입력하세요.");
                }
            }
        });

        // 기록 보기 버튼 클릭 리스너 설정
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putStringArrayListExtra("records", records);
                startActivity(intent);
            }
        });

        // 포기 버튼 설정
        giveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCorrectAnswerAndFinish();
            }
        });

        // 힌트 버튼 설정
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hintLimit > 0) {
                    Intent intent = new Intent(MainActivity.this, HintActivity.class);
                    intent.putExtra("computerNumber", computerNumber);
                    intent.putExtra("numberLength", numberLength);
                    startActivity(intent);

                    // 힌트 사용 횟수 감소
                    hintLimit--;
                    if (hintLimit == 0) {
                        hintButton.setEnabled(false);
                        resultView.setText("힌트를 모두 소진했습니다.");
                    }
                } else {
                    resultView.setText("힌트를 모두 소진하셨습니다.");
                }
            }
        });
    }


    private void generateComputerNumber(int difficulty) {
        Random random = new Random();

        // 쉬움과 보통 난이도에서는 중복 없는 숫자 생성
        if (difficulty == 1 || difficulty == 2) {
            for (int i = 0; i < numberLength; i++) {
                computerNumber[i] = random.nextInt(10);
                for (int j = 0; j < i; j++) {
                    while (computerNumber[i] == computerNumber[j]) {
                        computerNumber[i] = random.nextInt(10);
                    }
                }
            }
        } else if (difficulty == 3) {      // 어려움 난이도에선 중복 숫자 최대 2개 허용
            int[] frequency = new int[10];   // 각 수의 빈도 체크

            for (int i = 0; i < numberLength; i++) {
                int num;
                boolean isValid;

                do {
                    num = random.nextInt(10);
                    isValid = true;

                    // 중복이 존재시 다른 중복은 나오지 않게 하는 코드
                    if (frequency[num] >= 2) {
                        isValid = false;   // 2번 나온 숫자는 더 이상 추가 없음
                    }

                } while (!isValid);

                computerNumber[i] = num;
                frequency[num]++;
            }
        }else if (difficulty == 4) {    // 시간 제한 모드
            // 특별한 제약 없이 기본 규칙 사용
            for (int i = 0; i < numberLength; i++) {
                computerNumber[i] = random.nextInt(10);
                for (int j = 0; j < i; j++) {
                    while (computerNumber[i] == computerNumber[j]) {
                        computerNumber[i] = random.nextInt(10);
                    }
                }
            }
        }
    }

    private void checkNumber(String input) {
        for (int i = 0; i < numberLength; i++) {
            userNumber[i] = Integer.parseInt(input.substring(i, i + 1));
        }
        strikeCount = 0;
        ballCount = 0;

        for (int i = 0; i < numberLength; i++) {
            if (userNumber[i] == computerNumber[i]) {
                strikeCount++;
            }
        }
        for (int i = 0; i < numberLength; i++) {
            for (int j = 0; j < numberLength; j++) {
                if (userNumber[i] == computerNumber[j] && userNumber[i] != computerNumber[i]) {
                    ballCount++;
                }
            }
        }
        resultView.setText("Strike: " + strikeCount + ", Ball: " + ballCount);


        // 게임 종료 조건 확인
        if (strikeCount == numberLength) {
            resultView.setText("축하합니다! 숫자를 모두 맞추셨습니다.");
            inputView.setEnabled(false);
            button.setEnabled(false);
            recordButton.setEnabled(false);
            giveUpButton.setEnabled(false);

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

        }

    }
    private void startTimer(int timeLimit) {

        timerView.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeLimit * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText("남은 시간 : " + millisUntilFinished / 1000 + "초");
            }

            @Override
            public void onFinish() {
                timerView.setText("시간 초과!!");
                resultView.setText("시간 초과!! 정답은 : " + getComputerNumberAsString());
                inputView.setEnabled(false);
                button.setEnabled(false);
                recordButton.setEnabled(false);
                giveUpButton.setEnabled(false);

                // 5초 후 시작 화면으로 이동
                button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 5000);   // 5초 지연
            }
        }.start();
    }

    private void showCorrectAnswerAndFinish() {
        StringBuilder correctAnswer = new StringBuilder();
        for (int i = 0; i < numberLength; i++) {
            correctAnswer.append(computerNumber[i]);
        }
        resultView.setText("정답은 : " + correctAnswer.toString());

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 10초 후 시작 화면으로 이동
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, 10000); // 10초 지연
    }

    private String getComputerNumberAsString() {
        StringBuilder builder = new StringBuilder();
        for (int number : computerNumber) {
            builder.append(number);
        }
        return builder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
