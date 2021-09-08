package com.example.a1183008;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailKitabActivity extends AppCompatActivity {
    TextView namaKitab, isiKitab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kitab);

        namaKitab = findViewById(R.id.namakitab);
        isiKitab = findViewById(R.id.isiKitab);

        String namaKitabs = getIntent().getStringExtra("nama");
        String isiKitabs = getIntent().getStringExtra("isi");

        namaKitab.setText(namaKitabs);
        isiKitab.setText(isiKitabs);
    }
}