package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FolderDetailActivity extends AppCompatActivity {
    private String folderName;
    private TextView folderTitle; // 폴더 이름 표시하는 TextView
    private RecyclerView photoRecyclerView;
    private PhotoAdapter photoAdapter;
    private List<PhotoData> photoList; // PhotoItem 객체 리스트
    private DBHelper databaseHelper; // DBHelper 추가
//    private static String userId = UserSession.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);

        folderName = getIntent().getStringExtra("folderName");
        photoList = new ArrayList<>();
        databaseHelper = new DBHelper(this); // DBHelper 초기화

        // 폴더 이름 표시하는 TextView 초기화
        folderTitle = findViewById(R.id.folderTitle);
        folderTitle.setText(folderName);

        // RecyclerView 설정
        photoRecyclerView = findViewById(R.id.photoRecyclerView);
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new PhotoAdapter(this, photoList);
        photoRecyclerView.setAdapter(photoAdapter);

        // 폴더 이름 수정 버튼 클릭 이벤트
        Button editFolderNameButton = findViewById(R.id.editFolderNameButton);
        editFolderNameButton.setOnClickListener(v -> showEditFolderNameDialog());

        // 뒤로가기 버튼 클릭 이벤트
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        loadPhotos(); // 사진 불러오기
    }

    // 폴더 이름 수정 다이얼로그 표시
    private void showEditFolderNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("폴더 이름 수정");

        final EditText input = new EditText(this);
        input.setText(folderName); // 현재 폴더 이름을 입력 필드에 표시
        builder.setView(input);

        builder.setPositiveButton("수정", (dialog, which) -> {
            String newFolderName = input.getText().toString();
            folderName = newFolderName; // 폴더 이름 업데이트
            folderTitle.setText(folderName); // 폴더 이름 업데이트

            // 수정된 이름을 반환하기 위해 Intent에 추가
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newFolderName", newFolderName);
            resultIntent.putExtra("position", getIntent().getIntExtra("position", -1)); // 위치도 추가
            setResult(RESULT_OK, resultIntent); // 결과를 설정
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void loadPhotos() {
        String userId = UserSession.getInstance().getUserId();
        Log.i("loadPhotos", folderName);

        // 사용자 ID와 폴더 이름이 null인지 확인
        if (userId == null || folderName == null) {
            Log.i("loadPhotos login", userId);
            Toast.makeText(this, "유효하지 않은 사용자 ID 또는 폴더 이름입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        photoList = databaseHelper.getImagesByUserAndFolder(userId, folderName);
        photoAdapter = new PhotoAdapter(this, photoList);
        photoRecyclerView.setAdapter(photoAdapter);
    }


}
