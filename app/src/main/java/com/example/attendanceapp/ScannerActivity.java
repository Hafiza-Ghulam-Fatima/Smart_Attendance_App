package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        dbHelper = new DatabaseHelper(this);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> markAttendance(result.getText()));
            }
        });
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCodeScanner.startPreview();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCodeScanner.startPreview();
            } else {
                Toast.makeText(this,
                        "Camera permission is required to scan codes.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void markAttendance(String scannedText) {
        try {
            long sessionId = Long.parseLong(scannedText);
            String studentEmail = "student" + (int) (Math.random() * 1000) + "@example.com";

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_ATTENDANCE_SESSION_ID_FK, sessionId);
            values.put(DatabaseHelper.KEY_ATTENDANCE_STUDENT_EMAIL, studentEmail);
            long newRowId = db.insert(DatabaseHelper.TABLE_ATTENDANCE, null, values);
            db.close();

            if (newRowId != -1) {
                showResultDialog("✅ Attendance Recorded!",
                        "Your email: " + studentEmail + "\n\nYour attendance has been saved successfully.");
            } else {
                showResultDialog("❌ Database Error",
                        "Could not save attendance. Please try again.");
            }
        } catch (Exception e) {
            showResultDialog("❌ Invalid QR Code",
                    "This QR code is not recognized. Please scan your teacher's code.");
            e.printStackTrace();
        }
    }

    private void showResultDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> mCodeScanner.startPreview())
                .setOnCancelListener(dialog -> mCodeScanner.startPreview())
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}