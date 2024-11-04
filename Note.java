package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Note extends AppCompatActivity {
    private RecyclerView folderRecyclerView;
    private FolderAdapter folderAdapter;
    private List<String> folderList;
    private DBHelper databaseHelper;
    private static final int REQUEST_CODE_EDIT_FOLDER = 1;
    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        hideNavigationBar();

        folderRecyclerView = findViewById(R.id.folderRecyclerView);
        Button addFolderButton = findViewById(R.id.addFolderButton);

        databaseHelper = new DBHelper(this);

        // 폴더 목록 초기화: 데이터베이스에서 폴더 목록을 가져오거나 빈 리스트로 초기화
        folderList = databaseHelper.getAllFolders();
        if (folderList == null) {
            folderList = new ArrayList<>(); // null 방지를 위해 빈 리스트로 초기화
        }

        // 리사이클러뷰 설정
        folderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        folderAdapter = new FolderAdapter(folderList);
        folderRecyclerView.setAdapter(folderAdapter);

        // 새 폴더 추가 버튼 클릭 이벤트
        addFolderButton.setOnClickListener(v -> {
            addNewFolder();
        });

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.navigation_notifications);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_dashboard) {
                startActivity(new Intent(this, ResultActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    // 새 폴더 추가
    private void addNewFolder() {
        String userId = UserSession.getInstance().getUserId();

        String newFolderName = "폴더 " + (folderList.size() + 1);
        folderList.add(newFolderName);
        folderAdapter.notifyItemInserted(folderList.size() - 1);

        // 현재 날짜를 가져오는 코드
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // 데이터베이스에 폴더 추가
        databaseHelper.insertFolder(newFolderName, userId); // userId와 함께 데이터베이스에 저장
        Toast.makeText(this, newFolderName + "이 추가되었습니다.", Toast.LENGTH_SHORT).show();
        Log.i("insert", newFolderName + userId);
    }

    // 폴더 목록을 관리하는 어댑터 클래스
    private class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
        private final List<String> folders;

        public FolderAdapter(List<String> folders) {
            this.folders = folders;
        }

        @Override
        public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new FolderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FolderViewHolder holder, int position) {
            String folderName = folders.get(position);
            holder.folderNameTextView.setText(folderName);

            // 폴더 클릭 시 상세 화면으로 이동
            holder.itemView.setOnClickListener(v -> {
                openFolder(folderName, position);
            });
        }

        @Override
        public int getItemCount() {
            return folders != null ? folders.size() : 0;
        }

        class FolderViewHolder extends RecyclerView.ViewHolder {
            TextView folderNameTextView;

            public FolderViewHolder(View itemView) {
                super(itemView);
                folderNameTextView = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    // 폴더 클릭 시 해당 폴더로 이동
    private void openFolder(String folderName, int position) {
        Intent intent = new Intent(Note.this, FolderDetailActivity.class);
        intent.putExtra("folderName", folderName);
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT_FOLDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_FOLDER && resultCode == RESULT_OK && data != null) {
            String newFolderName = data.getStringExtra("newFolderName");
            int position = data.getIntExtra("position", -1);
            if (position != -1 && newFolderName != null) {
                folderList.set(position, newFolderName);
                folderAdapter.notifyItemChanged(position);
            }
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }



}
