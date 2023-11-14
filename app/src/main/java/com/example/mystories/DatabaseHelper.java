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
    public Story get(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Bitmap bitmap = null;
        String title = "111";
        String content = "111";
        if (cursor.moveToFirst()) {
            byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
            bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            content = cursor.getString(cursor.getColumnIndex(CONTENT));
        }
        cursor.close();
        return new Story(title,content, bitmap);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
