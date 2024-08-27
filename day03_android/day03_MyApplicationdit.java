    package com.example.myapplicationedit;

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

        private TextView textView;
        private EditText editText;
        private Button button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            textView = (TextView) findViewById(R.id.textView);
            editText = findViewById(R.id.editText);
            button = findViewById(R.id.button);
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            EditText editText2 = findViewById(R.id.editText2);
            Button button2 = findViewById(R.id.button2);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = editText.getText().toString();    // edit의 입력을 가져와 문자로 변환에 str에 저장
                    editText.setText("");   // edittext에 적은 걸 세팅한다
                    textView2.setText(str);    // textview에 str객체에 저장된 문자를 출력한다.
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = editText2.getText().toString();
                    editText2.setText("");
                    textView.setText(str);
                }
            });

        }
    }