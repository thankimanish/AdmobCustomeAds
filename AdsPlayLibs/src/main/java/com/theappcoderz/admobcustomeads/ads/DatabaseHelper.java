package com.theappcoderz.admobcustomeads.ads;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FreeCoin";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RECORD = "DeletedRecord";
    private static final String ID_M = "id";
    private static final String C1_M = "urlid";
    private static final String C2_M = "title";
    private static final String C3_M = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_RECORD + "("
                + ID_M + " INTEGER PRIMARY KEY,"
                + C1_M + " TEXT,"
                + C2_M + " TEXT,"
                + C3_M + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_RECORD + "("
                + ID_M + " INTEGER PRIMARY KEY,"
                + C1_M + " TEXT,"
                + C2_M + " TEXT,"
                + C3_M + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(db);
    }

    public void insertRecordDelete(Link message) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_RECORD + " WHERE urlid='" + message.id + "'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        try {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (db) {
                if (cursor.getCount() <= 0) {
                    ContentValues values = new ContentValues();
                    values.put(C1_M, message.id);
                    values.put(C2_M, message.title);
                    values.put(C3_M, message.url);
                    db.insert(TABLE_RECORD, null, values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }

    }


    public boolean isDeleted(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isDeleted;
        try {
            String query = "SELECT  * FROM " + TABLE_RECORD + " WHERE urlid='" + id + "'";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            isDeleted = cursor.getCount() > 0;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return isDeleted;
    }


    private int getVersionId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT version_id FROM dbVersion";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int v = cursor.getInt(0);
        db.close();
        return v;
    }

    public boolean checked(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RECORD + " WHERE vid='" + id + "'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        boolean isAvalable;
        try {
            isAvalable = cursor.getCount() > 0;
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return isAvalable;
    }
}
