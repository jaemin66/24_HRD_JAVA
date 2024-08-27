package com.example.myapplicationdeb;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        int result = 0;
        int a = 2;
        int b = 3;
        int c = 5;
        result = a << 2;     // 결과 8  a에 저장된 값을 좌측으로 2비트 이통시키라는 의미
        result += b;         // 결과 = 8 + 3 = 11
        result = (result + c) >> 1;    // 결과   =  8
        result = add(result, 3);    // 결과
        textView.setText(String.valueOf(result));     // 결과 = 11

    }
    int add(int a, int b){
        int sum = 0;
        sum = a;          // 결과 = 8,   sum = a = result = 8
        sum += b;         // 결괴 = 11   ,  sum = 8 + 3 = 11
        return sum;
    }
}