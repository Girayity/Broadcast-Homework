package com.example.abozyigit.homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDatabase extends SQLiteOpenHelper {

    private static String DB_NAME = "AppDB";
    private static int DB_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "CREATE TABLE MYTABLE( "
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Locked TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "Unlocked TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(command);
    }

    public void insert(boolean locked) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (locked) {
            contentValues.put("Unlocked", "null");
        } else {
            contentValues.put("Locked", "null");
        }
        sqLiteDatabase.insert("MYTABLE", null, contentValues);
    }

    public Map<String, List<Object>> verileriAl() {
        Map<String, List<Object>> map =new HashMap();
        List<String> times = new ArrayList<String>();
        List<String> times2 = new ArrayList<String>();
        List<Boolean> locked = new ArrayList<Boolean>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MYTABLE", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String lockedtime = cursor.getString(cursor.getColumnIndex("Locked"));
                String unlockedtime = cursor.getString(cursor.getColumnIndex("Unlocked"));
                if (!lockedtime.equals("null")) {
                    times.add(lockedtime + "+00");
                    times2.add(lockedtime + "+00" + " Ekran Kapatıldı.");
                    locked.add(true);
                } else {
                    times.add(unlockedtime + "+00");
                    times2.add(unlockedtime + "+00" + " Ekran Açıldı.");
                    locked.add(false);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        map.put("times", new ArrayList<Object>(times));
        map.put("locked", new ArrayList<Object>(locked));
        map.put("times2", new ArrayList<Object>(times2));
        return map;
    }

    public String QueryWord(String input) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("WORDS", new String[]{"DEFINITON"}, "WORD=?", new String[]{input}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "There is no such a word in dictionary";
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}