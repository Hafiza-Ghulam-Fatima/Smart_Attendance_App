package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    Button btnGenerateQr, btnRefreshList;
    ImageView imgQrCode;
    RecyclerView rvAttendees;
    TextView tvSessionId, tvSessionCount, tvPresentCount;

    DatabaseHelper dbHelper;
    AttendanceAdapter adapter;
    List<String> attendeeList;
    long currentSessionId = -1;

    private static final String PREFS_NAME = "AttendancePrefs";
    private static final String KEY_LAST_SESSION_ID = "lastSessionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // Bind views
        btnGenerateQr   = findViewById(R.id.btnGenerateQr);
        btnRefreshList  = findViewById(R.id.btnRefreshList);
        imgQrCode       = findViewById(R.id.imgQrCode);
        rvAttendees     = findViewById(R.id.rvAttendees);
        tvSessionId     = findViewById(R.id.tvSessionId);
        tvSessionCount  = findViewById(R.id.tvSessionCount);
        tvPresentCount  = findViewById(R.id.tvPresentCount);

        dbHelper     = new DatabaseHelper(this);
        attendeeList = new ArrayList<>();
        adapter      = new AttendanceAdapter(attendeeList);

        rvAttendees.setLayoutManager(new LinearLayoutManager(this));
        rvAttendees.setAdapter(adapter);

        loadLastSession();
        updateSessionCountStat();

        btnGenerateQr.setOnClickListener(v -> createNewSession());
        btnRefreshList.setOnClickListener(v -> refreshAttendeeList());

        if (currentSessionId != -1) {
            tvSessionId.setText(String.valueOf(currentSessionId));
            generateQrCode(String.valueOf(currentSessionId));
            refreshAttendeeList();
        }
    }

    // ── Persistence ──────────────────────────────────────────────────────

    private void loadLastSession() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currentSessionId = prefs.getLong(KEY_LAST_SESSION_ID, -1);
    }

    private void saveCurrentSession(long sessionId) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putLong(KEY_LAST_SESSION_ID, sessionId).apply();
    }

    // ── Session creation ─────────────────────────────────────────────────

    private void createNewSession() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_SESSION_TIMESTAMP, System.currentTimeMillis());
        long newSessionId = db.insert(DatabaseHelper.TABLE_SESSIONS, null, values);
        db.close();

        if (newSessionId != -1) {
            currentSessionId = newSessionId;
            saveCurrentSession(currentSessionId);
            tvSessionId.setText(String.valueOf(currentSessionId));
            generateQrCode(String.valueOf(currentSessionId));
            updateSessionCountStat();
            refreshAttendeeList();
            Toast.makeText(this, "New session started! Share the QR code.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to create session.", Toast.LENGTH_SHORT).show();
        }
    }

    // ── QR generation ────────────────────────────────────────────────────

    private void generateQrCode(String sessionId) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(sessionId, BarcodeFormat.QR_CODE, 400, 400);
            int width  = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQrCode.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    // ── Attendee list ────────────────────────────────────────────────────

    private void refreshAttendeeList() {
        if (currentSessionId == -1) {
            attendeeList.clear();
            adapter.notifyDataSetChanged();
            tvPresentCount.setText("0");
            return;
        }

        attendeeList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ATTENDANCE,
                new String[]{DatabaseHelper.KEY_ATTENDANCE_STUDENT_EMAIL},
                DatabaseHelper.KEY_ATTENDANCE_SESSION_ID_FK + " = ?",
                new String[]{String.valueOf(currentSessionId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                attendeeList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
        tvPresentCount.setText(String.valueOf(attendeeList.size()));
    }

    // ── Stats ────────────────────────────────────────────────────────────

    private void updateSessionCountStat() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SESSIONS, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        db.close();
        tvSessionCount.setText(String.valueOf(count));
    }
}