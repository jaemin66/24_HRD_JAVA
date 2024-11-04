package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Project.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public String userId = UserSession.getInstance().getUserId();

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // USER 테이블 생성
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS USER ( " +
                "id TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "name TEXT, " +
                "gender TEXT, " +
                "birth TEXT);");

        // BABY 테이블 생성
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS BABY ( " +
                "baby_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "parent_id TEXT, " +
                "order_number TEXT, " +
                "baby_name TEXT, " +
                "birth_date TEXT, " +
                "blood_type TEXT, " +
                "gender TEXT, " +
                "detail TEXT, " +
                "FOREIGN KEY(parent_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE);");

        // FOLDER 테이블 생성
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS FOLDER ( " +
                "number INTEGER PRIMARY KEY AUTOINCREMENT, " +  // 고유 ID
                "folder_name TEXT, " +                   // 중복을 허용하지 않고 고유하도록 설정
                "id TEXT, " +
                "FOREIGN KEY(id) REFERENCES USER(id) ON DELETE CASCADE ON UPDATE CASCADE);");

    // IMAGE 테이블 생성
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS IMAGE ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "folder_name TEXT, " +                     // 독립적인 폴더 이름
                "user_id TEXT, " +                         // USER 테이블의 id를 참조하는 열 추가
                "blob_image BLOB, " +
                "date TEXT DEFAULT (datetime('now')), " +
                "FOREIGN KEY(user_id) REFERENCES USER(id) ON DELETE CASCADE ON UPDATE CASCADE);");

    }


        // 외래키 활성화
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");  // 외래키 활성화
    }



    // 로그인 시 user의 정보와 일치 확인 메소드
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"id"},
                "id=? AND password=?",
                new String[]{username, password}, null, null, null);

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }


    // ID 중복 체크 메서드
    public boolean isIdDuplicate(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM user WHERE id = ?",
                new String[]{id}
        );

        boolean exists = cursor.moveToFirst();  // 해당 ID가 있는지 확인
        cursor.close();
        return exists;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BABY");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FOLDER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS IMAGE");
        onCreate(sqLiteDatabase);
    }

    // 폴더 명 DB 저장 메서드
    public void insertFolder(String name, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues 사용하여 데이터를 삽입
        ContentValues values = new ContentValues();
        values.put("folder_name", name);
        values.put("id", userId); // userId 값 추가

        // 데이터베이스에 삽입
        db.insert("FOLDER", null, values);
        db.close();
    }


    // 모든 폴더 목록 가져오기 메서드
    public List<String> getAllFolders() {
        List<String> folderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // folders 테이블에서 폴더 이름 가져오기
            cursor = db.rawQuery("SELECT folder_name FROM FOLDER WHERE id = ?", new String[]{userId});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String folderName = cursor.getString(cursor.getColumnIndexOrThrow("folder_name"));
                    folderList.add(folderName);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return folderList;
    }

    public void insertImageData(String folderName, byte[] imageBytes, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("folder_name", folderName);// FOLDER 테이블의 folder_name 참조
        values.put("blob_image", imageBytes);   // 이미지 데이터 BLOB 형식
        values.put("user_id", userId);

        try {
            long result = db.insert("IMAGE", null, values);
            if (result == -1) {
                Log.e("DBHelper", "데이터 삽입 실패 - folder_name 참조 확인 필요");
            } else {
                Log.d("DBHelper", "데이터 삽입 성공: ID = " + result);
            }
        } catch (Exception e) {
            Log.e("DBHelper", "데이터베이스 삽입 오류 발생: " + e.getMessage());
        } finally {
            db.close();
        }

    }

    public String getFolderByName(String folderName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String folderNameResult = null;
        Cursor cursor = db.rawQuery("SELECT folder_name FROM FOLDER WHERE folder_name = ?", new String[]{folderName});
        if (cursor.moveToFirst()) {
            folderNameResult = cursor.getString(cursor.getColumnIndexOrThrow("folder_name"));
        }
        cursor.close();
        db.close();
        return folderNameResult;
    }

    public List<PhotoData> getImagesByUserAndFolder(String userId, String folderName) {
        List<PhotoData> photos = new ArrayList<>();

        // userId와 folderName이 null인지 확인
        if (userId == null || folderName == null) {
            Log.e("DBHelper", "userId 또는 folderName이 null입니다.");
            return photos;  // 빈 목록 반환
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT blob_image, date FROM IMAGE WHERE user_id = ? AND folder_name = ?",
                    new String[]{userId, folderName});

            while (cursor.moveToNext()) {
                byte[] imageBlob = cursor.getBlob(cursor.getColumnIndexOrThrow("blob_image"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                photos.add(new PhotoData(imageBlob, date));
            }
        } catch (Exception e) {
            Log.e("DBHelper", "데이터베이스에서 데이터를 가져오는 중 오류 발생", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return photos;
    }





}
