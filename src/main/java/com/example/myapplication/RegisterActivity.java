package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Button backBtn, register, duplicatecheck;
    private EditText nameEdt, idEdt, pwEdt, yearEdt, monthEdt, dayEdt;
    private RadioGroup genderRG;
    private RadioButton male, female;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // DB 초기화
        dbHelper = new DBHelper(this);

        // View 초기화
        backBtn = findViewById(R.id.backBtn);
        register = findViewById(R.id.register);
        nameEdt = findViewById(R.id.nameEdt);
        idEdt = findViewById(R.id.idEdt);
        pwEdt = findViewById(R.id.pwEdt);
        yearEdt = findViewById(R.id.yearEdt);
        monthEdt = findViewById(R.id.monthEdt);
        dayEdt = findViewById(R.id.dayEdt);
        genderRG = findViewById(R.id.genderRG);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        convertActivity();  // 버튼 이벤트 설정
        setBirth();          // DatePicker 설정
    }


    // 회원 데이터 저장 메소드
    private void saveUserData() {
        String name = nameEdt.getText().toString().trim();
        String userId = idEdt.getText().toString().trim();
        String password = pwEdt.getText().toString().trim();
        String birth = formatBirthDate();
        String gender = getSelectedGender();

        // 필수 정보가 비어있는 경우 확인
        if (name.isEmpty() || userId.isEmpty() || password.isEmpty() || birth.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "모든 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 비밀번호 유효성 검사
        if (!isPasswordValid(password)) {
            Toast.makeText(this, "비밀번호는 최소 6자 이상으로 작성해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ID 중복 체크
        if (dbHelper.isIdDuplicate(userId)) {
            Toast.makeText(this, "이미 사용 중인 ID입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 데이터베이스에 유저 정보 저장
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("id", userId);
            values.put("name", name);
            values.put("gender", gender);
            values.put("birth", birth);
            values.put("password", password);

            long result = db.insert("user", null, values);

            if (result == -1) {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                finish();  // 회원가입 후 액티비티 종료
            }
        } finally {
            db.close();  // 항상 DB 닫기
        }
    }


    // 비밀번호 유효성 검사 메소드
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    // 성별 반환 메소드
    private String getSelectedGender() {
        int selectedId = genderRG.getCheckedRadioButtonId();
        if (selectedId == R.id.male) {
            return "male";
        } else if (selectedId == R.id.female) {
            return "female";
        } else {
            return "";  // 선택되지 않은 경우
        }
    }

    // 생일 선택 DatePicker 설정
    protected void setBirth() {
        View.OnClickListener datePickerListener = v -> {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            // DatePickerDialog 생성
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        yearEdt.setText(String.valueOf(year));
                        monthEdt.setText(String.format("%02d", month + 1));  // 월은 0부터 시작하므로 +1
                        dayEdt.setText(String.format("%02d", dayOfMonth));
                    },
                    currentYear, currentMonth, currentDay
            );

            datePickerDialog.show();  // 다이얼로그 표시
        };

        // EditText 클릭 시 DatePickerDialog 표시
        yearEdt.setOnClickListener(datePickerListener);
        monthEdt.setOnClickListener(datePickerListener);
        dayEdt.setOnClickListener(datePickerListener);

        // EditText 직접 입력 방지
        yearEdt.setFocusable(false);
        monthEdt.setFocusable(false);
        dayEdt.setFocusable(false);
    }

    // YYYY-MM-DD 형식으로 날짜 반환
    private String formatBirthDate() {
        String year = yearEdt.getText().toString();
        String month = monthEdt.getText().toString();
        String day = dayEdt.getText().toString();

        if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
            return "";  // 입력값이 비어있을 경우 빈 문자열 반환
        }

        return year + "-" + month + "-" + day;
    }

    // 액티비티 전환 메소드
    protected void convertActivity() {
        backBtn.setOnClickListener(v -> finish());  // 뒤로가기 버튼 클릭 시 액티비티 종료

        // 회원가입 버튼 클릭 시 데이터 저장
        register.setOnClickListener(v -> saveUserData());
    }
}
