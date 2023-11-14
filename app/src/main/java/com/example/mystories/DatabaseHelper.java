package com.example.mystories;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_db";
    private static final int DATABASE_VERSION = 1;
    public  static String TABLE_NAME = "stories";
    public  static String COLUMN_ID = "id";
    public  static String COLUMN_IMAGE = "image";
    public  static String TITLE = "title";
    public  static String CONTENT = "content";
    public  static String CREATE_AT = "created_at";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_IMAGE + " BLOB, " +
            TITLE + " TEXT, " +
            CONTENT + " TEXT, " +
            CREATE_AT + " DATETIME DEFAULT (datetime('now', 'localtime'))" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void save(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Bitmap bitmap = story.getImg();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        values.put(COLUMN_IMAGE, byteArray);
        values.put(TITLE, story.getTitle());
        values.put(CONTENT, story.getContent());
        db.insert(TABLE_NAME, null, values);
        db.close();

    }


    @SuppressLint("Range")
    public List<Story> getAllStories() {
        List<Story> storyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + CREATE_AT + " DESC ", null);

        if (cursor.moveToFirst()) {
            do {
                byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                String title = cursor.getString(cursor.getColumnIndex(TITLE));
                String content = cursor.getString(cursor.getColumnIndex(CONTENT));
                String created_at = cursor.getString(cursor.getColumnIndex(CREATE_AT));
                Story story = new Story(title, content, bitmap, created_at);
                storyList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storyList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
