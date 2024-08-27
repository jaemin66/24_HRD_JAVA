package com.example.myapplicationactmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MSG = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                String msg = editText.getText().toString();
                editText.setText("");   // editText의 내용을 지운다.
                intent.putExtra(TAG_MSG, msg);   // 데이터 전달 (msg에 들어있는 값을 TAG_MSG를 통해 전달)
                                                 // 즉 서브 액티비티로 데이터를 전달
                startActivity(intent);
            }
        });

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();     // 전달된 데이터를 수신한다(받는다).
        String msg = intent.getStringExtra(MainActivity.TAG_MSG);    // 데이터 추출
        textView.setText(msg);

    }
}