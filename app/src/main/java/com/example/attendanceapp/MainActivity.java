package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The clickable elements are LinearLayouts inside CardViews
        View btnTeacher = findViewById(R.id.btnTeacher);
        View btnStudent = findViewById(R.id.btnStudent);

        btnTeacher.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TeacherActivity.class)));

        btnStudent.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, StudentActivity.class)));
    }
}