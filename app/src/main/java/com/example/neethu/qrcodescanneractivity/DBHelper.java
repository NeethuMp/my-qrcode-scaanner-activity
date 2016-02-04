package com.example.neethu.qrcodescanneractivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neethu on 2/2/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QrScanHistory";
    public static final String TABLE_NAME = "ScanHistoryData";
    public static final String SCAN_URL = "qr_data";
    public static final String SCAN_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SCAN_URL + " TEXT,"
                + SCAN_DATE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(HistoryData historyData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Log.e("DB", "SAving: " + historyData);

        contentValues.put(SCAN_URL, historyData.getUrl());
        contentValues.put(SCAN_DATE, historyData.getDate());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {
        String selectQuery="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
