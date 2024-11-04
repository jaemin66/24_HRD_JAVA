package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addProfile;
    private DBHelper dbHelper;
    private TextView userName;
    private BottomNavigationView bottom_navigation;

    private SQLiteDatabase db;

    private RecyclerView babyProfileRecycler;
    private BabyAdapter babyAdapter;
    private List<Baby> babyList;

    private String userId = UserSession.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // DB 초기화
        dbHelper = new DBHelper(this);

        userName = findViewById(R.id.userName);

        // 어댑터 초기화
        babyList = new ArrayList<>();
        babyAdapter = new BabyAdapter(babyList);

        // 리사이클러뷰 초기화
        babyProfileRecycler = findViewById(R.id.babyProfile);
        babyProfileRecycler.setAdapter(babyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL); // 세로 방향으로 설정
        babyProfileRecycler.setLayoutManager(layoutManager);

        // DB에서 아기 프로필 가져오기
        loadBabyProfilesFromDB();

        // 사용자 이름 표시
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String name = getUserNameById(db, userId);
        db.close();

        if (name != null) {
            userName.setText(name);
            userName.setTextSize(40);
        } else {
            userName.setText("사용자 없음");
        }


        // 프로필 추가
        addProfile = findViewById(R.id.addProfile);
        addProfile.setOnClickListener(View -> {
            showBabyProfileDialog();
        });

        babyAdapter.setOnItemClickListener(baby -> showBabyProfileDialog(baby));


        hideNavigationBar();


        // 바텀 네비게이션
        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setSelectedItemId(R.id.navigation_home);

        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_dashboard){
                startActivity(new Intent(this, ResultActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_notifications) {
                startActivity(new Intent(this, Note.class));
                finish();
                return true;
            }
            return false;
        });
    }


    // DB에서 아기 프로필 정보를 가져오는 메서드
    private void loadBabyProfilesFromDB() {
        SQLiteDatabase db = null;
        Cursor cursor = null; // 커서 초기화
        try {
            db = dbHelper.getReadableDatabase(); // 데이터베이스 인스턴스 가져오기

            if (userId == null) {
                Toast.makeText(this, "부모 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return; // userId가 null이면 메소드 종료
            }

            Log.d("MainActivity", "Parent ID: " + userId); // 로그 추가

            cursor = db.rawQuery("SELECT * FROM BABY WHERE parent_id = ?", new String[]{userId});
            babyList.clear(); // 새로 데이터를 로드하기 전에 리스트 초기화

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String babyNum = cursor.getString(cursor.getColumnIndexOrThrow("order_number"));
                    String babyName = cursor.getString(cursor.getColumnIndexOrThrow("baby_name"));
                    String babyBirth = cursor.getString(cursor.getColumnIndexOrThrow("birth_date"));
                    String babyBlood = cursor.getString(cursor.getColumnIndexOrThrow("blood_type"));
                    String babyGender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
                    String babyDetail = cursor.getString(cursor.getColumnIndexOrThrow("detail"));

                    // Baby 객체 생성 후 리스트에 추가
                    Baby baby = new Baby(babyNum, babyName, babyBirth, babyBlood, babyGender, babyDetail);
                    babyList.add(baby);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error loading baby profiles: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close(); // 커서 닫기
            }
            if (db != null) {
                db.close(); // 데이터베이스 닫기
            }
        }

        // RecyclerView 업데이트
        babyAdapter.notifyDataSetChanged();
    }



    // 프로필 작성 및 DB Insert
    private void showBabyProfileDialog() {
        String parentName = userName.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_baby_profile, null);

        // 날짜 EditText 설정
        EditText babyYearEditText = dialogView.findViewById(R.id.inputBabyYear);
        EditText babyMonthEditText = dialogView.findViewById(R.id.inputBabyMonth);
        EditText babyDayEditText = dialogView.findViewById(R.id.inputBabyDay);

        // EditText 클릭 시 DatePickerDialog 표시
        babyYearEditText.setOnClickListener(v -> showDatePickerDialog(babyYearEditText, babyMonthEditText, babyDayEditText));
        babyMonthEditText.setOnClickListener(v -> showDatePickerDialog(babyYearEditText, babyMonthEditText, babyDayEditText));
        babyDayEditText.setOnClickListener(v -> showDatePickerDialog(babyYearEditText, babyMonthEditText, babyDayEditText));

        builder.setView(dialogView)
                .setTitle("아기 프로필 입력")
                .setPositiveButton("확인", null) // null로 설정하여 클릭 시 아무 동작도 하지 않게 함
                .setNegativeButton("취소", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                // 입력값 처리 로직
                String babyOrder = ((Spinner) dialogView.findViewById(R.id.inputBabyOrder)).getSelectedItem().toString();
                String babyName = ((EditText) dialogView.findViewById(R.id.inputBabyName)).getText().toString();
                String babyYear = babyYearEditText.getText().toString();
                String babyMonth = babyMonthEditText.getText().toString();
                String babyDay = babyDayEditText.getText().toString();
                String bloodType = ((Spinner) dialogView.findViewById(R.id.inputBabyBlood)).getSelectedItem().toString();
                String babyGender = ((RadioButton) dialogView.findViewById(R.id.babyMale)).isChecked() ? "male" : "female";
                String medicalHistory = ((EditText) dialogView.findViewById(R.id.inputBabyDetail)).getText().toString();

                // 빈 칸 체크
                if (babyName.isEmpty() || babyYear.isEmpty() || babyMonth.isEmpty() || babyDay.isEmpty() || medicalHistory.isEmpty()) {
                    // 빈 칸이 있을 경우 알림 메시지 표시
                    Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 생년월일 문자열로 변환 (예: "YYYY-MM-DD")
                    String birthDate = String.format("%s-%s-%s", babyYear, babyMonth, babyDay);

                    // 부모 ID 가져오기
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // 아기 프로필 삽입
                    insertBabyProfile(userId, babyOrder, babyName, birthDate, bloodType, babyGender, medicalHistory);

                    // 새로 추가된 아기 객체를 리스트에 추가하고 어댑터 갱신
                    Baby newBaby = new Baby(babyOrder, babyName, birthDate, bloodType, babyGender, medicalHistory);
                    babyList.add(newBaby);
                    babyAdapter.notifyItemInserted(babyList.size() - 1); // 새 아이템만 갱신
                    Toast.makeText(this, "프로필이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    // 대화상자 닫기
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }


    // 다이얼로그를 표시하는 메서드
    private void showBabyProfileDialog(Baby baby) {
        // 다이얼로그에 사용할 레이아웃 인플레이트
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_baby_profile_info, null);

        // 다이얼로그의 View와 데이터 연결
        TextView babyNumTextView = dialogView.findViewById(R.id.babyOrderInfo);
        TextView babyNameTextView = dialogView.findViewById(R.id.babyNameInfo);
        TextView babyBirthTextView = dialogView.findViewById(R.id.babyBirthInfo);
        TextView babyBloodTextView = dialogView.findViewById(R.id.babyBloodInfo);
        TextView babyGenderTextView = dialogView.findViewById(R.id.babyGenderInfo);
        TextView babyDetailTextView = dialogView.findViewById(R.id.babyDetailInfo);

        // 아기 정보 설정
        babyNumTextView.setText(baby.getBabyNum());
        babyNameTextView.setText(baby.getBabyName());
        babyBirthTextView.setText(baby.getBabyBirth());
        babyBloodTextView.setText(baby.getBabyBlood());
        babyGenderTextView.setText(baby.getBabyGender());
        babyDetailTextView.setText(baby.getBabyDetail());

        // 다이얼로그 생성 및 표시
        builder.setView(dialogView)
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }



    // 데이터피커 적용
    private void showDatePickerDialog(EditText yearEditText, EditText monthEditText, EditText dayEditText) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    yearEditText.setText(String.valueOf(year));
                    monthEditText.setText(String.format("%02d", month + 1));
                    dayEditText.setText(String.format("%02d", dayOfMonth));
                },
                currentYear, currentMonth, currentDay
        );
        datePickerDialog.show();
    }



    // Users 테이블에서 이름(name)을 가져오는 메소드
    private String getUserNameById(SQLiteDatabase sqLiteDatabase, String userId) {
        Cursor cursor = null; // 커서 초기화
        try {
            cursor = sqLiteDatabase.query(
                    "USER",                // 테이블 이름
                    new String[]{"name"},   // 가져올 열 이름
                    "id = ?",               // 조건: ID로 검색
                    new String[]{userId},   // 검색할 ID
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                Log.d("MainActivity", "Retrieved User Name: " + userName); // 로그 추가
                return userName;  // 사용자 이름 반환
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error retrieving user name: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close(); // 커서 닫기
            }
        }

        Log.d("MainActivity", "No User Name found for ID: " + userId); // 로그 추가
        return null;  // 이름이 없을 경우 null 반환
    }




    // 아기 프로필을 데이터베이스에 삽입하는 메소드
    private void insertBabyProfile(String parentId, String orderNumber, String babyName, String birthDate, String bloodType, String gender, String detail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO BABY (parent_id, order_number, baby_name, birth_date, blood_type, gender, detail) VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{parentId, orderNumber, babyName, birthDate, bloodType, gender, detail}); // SQL 실행
        db.close();
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

}