package com.example.attendanceapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WorkingAttendance.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_SESSIONS = "sessions";
    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String KEY_SESSION_ID = "id";
    public static final String KEY_SESSION_TIMESTAMP = "timestamp";
    public static final String KEY_ATTENDANCE_ID = "id";
    public static final String KEY_ATTENDANCE_SESSION_ID_FK = "session_id";
    public static final String KEY_ATTENDANCE_STUDENT_EMAIL = "student_email";
    private static final String CREATE_TABLE_SESSIONS = "CREATE TABLE " + TABLE_SESSIONS + "(" + KEY_SESSION_ID + " INTEGER PRIMARY KEY," + KEY_SESSION_TIMESTAMP + " INTEGER)";
    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE " + TABLE_ATTENDANCE + "(" + KEY_ATTENDANCE_ID + " INTEGER PRIMARY KEY," + KEY_ATTENDANCE_SESSION_ID_FK + " INTEGER," + KEY_ATTENDANCE_STUDENT_EMAIL + " TEXT)";
    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }
    @Override public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE_SESSIONS); db.execSQL(CREATE_TABLE_ATTENDANCE); }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE); db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS); onCreate(db); }
}
