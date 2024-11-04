package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private ImageView resultImageView;
    private Button shootButton, saveButton, confirmButton, exitButton;
    private TextView descriptionEditText;
    private Spinner pathSpinner;
    private String photoPath;
    private File photoFile;
    private Classifier cls;
    private BottomNavigationView bottom_navigation;
    private DBHelper dbHelper;
    private String userId = UserSession.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultImageView = findViewById(R.id.resultImageView);
        shootButton = findViewById(R.id.retryButton);  // XML에 정의된 'retryButton'을 'shootButton'으로 사용
        saveButton = findViewById(R.id.saveButton);
        confirmButton = findViewById(R.id.confirmButton);
        exitButton = findViewById(R.id.exitButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        pathSpinner = findViewById(R.id.pathSpinner);

        // 처음에는 ImageView와 저장/분석 버튼을 숨김
//        resultImageView.setVisibility(View.GONE);
//        saveButton.setEnabled(false);
//        confirmButton.setEnabled(false);

        // '사진 촬영' 버튼 클릭 리스너
        shootButton.setOnClickListener(v -> openCamera());

        hideNavigationBar();

        cls = new Classifier(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        dbHelper = new DBHelper(this);

        // 경로 선택 Spinner 초기화
        String userId = UserSession.getInstance().getUserId();

        if (userId != null) {
            // 사용자 ID에 맞는 폴더 목록 가져오기
            List<String> folderNames = dbHelper.getAllFolders();

            // 스피너 어댑터 설정
            if (folderNames != null && !folderNames.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, folderNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pathSpinner.setAdapter(adapter);
            } else {
                Toast.makeText(this, "폴더가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "로그인된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
        }


        saveButton.setOnClickListener(v -> {
            try {
                Bitmap image = getBitmapFromImageView();
                String selectedFolderName = pathSpinner.getSelectedItem().toString();

                // 선택한 folder_name이 FOLDER 테이블에 존재하는지 확인
                String folderNameInDB = dbHelper.getFolderByName(selectedFolderName);

                if (folderNameInDB != null && image != null) {
                    // 이미지 Bitmap을 byte[]로 변환
                    byte[] imageBytes = getBitmapAsByteArray(image);

                    // IMAGE 테이블에 데이터 저장
                    dbHelper.insertImageData(folderNameInDB, imageBytes, userId);
                    Toast.makeText(this, "이미지가 데이터베이스에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "선택한 폴더가 존재하지 않거나 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SaveButton", "이미지 저장 중 오류 발생: " + e.getMessage());
                Toast.makeText(this, "이미지 저장 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });



        // 분석 버튼 클릭 리스너
        confirmButton.setOnClickListener(v -> {
            Bitmap image = getBitmapFromImageView();
            ByteBuffer inputBuffer = bitmapToByteBuffer(image);
            Pair<Integer, Float> res = cls.classify(inputBuffer);
            String output = cls.resultStr(res);
            descriptionEditText.setText(output + "이(가) 의심 됩니다.");
        });

        // 나가기 버튼 클릭 리스너
        exitButton.setOnClickListener(v -> {
            finishAffinity(); // 앱 종료
            System.exit(0);
        });


        // 바텀 네비게이션
        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setSelectedItemId(R.id.navigation_dashboard);

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

    // 카메라 열기 메서드
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.myapplication.fileprovider", photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            } else {
                Toast.makeText(this, "카메라를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "카메라 권한이 부여되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // 파일 생성 메서드
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 사진 촬영 후 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (photoFile != null && photoFile.exists()) {
                photoPath = photoFile.getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(photoPath);

                // 회전 처리 추가
                bitmap = rotateImageIfRequired(bitmap, photoPath);

                resultImageView.setVisibility(View.VISIBLE);  // 이미지뷰 표시
                resultImageView.setImageBitmap(bitmap);

                // 저장 및 분석 버튼 활성화
                saveButton.setEnabled(true);
                confirmButton.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "사진 촬영에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // EXIF 데이터를 기반으로 이미지 회전 처리
    private Bitmap rotateImageIfRequired(Bitmap img, String imagePath) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return img;
        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;  // 이미 정방향일 경우 회전하지 않음
        }
    }

    // 회전 각도에 따라 이미지를 회전하는 메서드
    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    // ImageView에서 Bitmap 추출
    private Bitmap getBitmapFromImageView() {
        resultImageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(resultImageView.getDrawingCache());
        resultImageView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    //Bitmap 이미지를 byte배열로 변환
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    // Bitmap을 ByteBuffer로 변환
    private ByteBuffer bitmapToByteBuffer(Bitmap bitmap) {
        int size = 150;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * size * size * 3);
        buffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[size * size];
        scaledBitmap.getPixels(pixels, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        for (int pixelValue : pixels) {
            buffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f);
            buffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);
            buffer.putFloat((pixelValue & 0xFF) / 255.0f);
        }
        return buffer;
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }


}
