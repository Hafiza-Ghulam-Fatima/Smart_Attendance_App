package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button btnMarkAttendance = findViewById(R.id.btnMarkAttendance);

        btnMarkAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, ScannerActivity.class);
            startActivity(intent);
        });
    }
}