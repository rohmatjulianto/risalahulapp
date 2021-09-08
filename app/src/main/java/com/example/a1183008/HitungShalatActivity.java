package com.example.a1183008;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HitungShalatActivity extends AppCompatActivity {
    private RadioButton selectRadioKeluar, selectRadioBerhenti;
    private RadioGroup radioKeluarDarah, radioBerhentiDarah;
    public LinearLayout linearHasil;
    public TextView hasil, hasil2;
    private Button cekButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_shalat);

        cekButton = findViewById(R.id.btnCek);
        radioKeluarDarah = findViewById(R.id.radioKeluarDarah);
        radioBerhentiDarah = findViewById(R.id.radioBerhentiDarah);
        linearHasil = findViewById(R.id.linerHasil);
        hasil = findViewById(R.id.hasil);
        hasil2 = findViewById(R.id.hasil2);

        linearHasil.setVisibility(View.GONE);
        cekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekKondisi();
            }
        });

    }

    private void cekKondisi() {
        selectRadioKeluar = findViewById(radioKeluarDarah.getCheckedRadioButtonId());
        selectRadioBerhenti = findViewById(radioBerhentiDarah.getCheckedRadioButtonId());
        String hasilKeluar = selectRadioKeluar.getText().toString();
        String hasilBerhenti = selectRadioBerhenti.getText().toString();

        if (hasilKeluar.equals("Subuh")){
            hasil.setText("Kamu harus Mengqadha Shalat Subuh");
        }else if(hasilKeluar.equals("Dzuhur")){
            hasil.setText("Kamu harus Mengqadha Shalat Dzuhur");
        }else if(hasilKeluar.equals("Asar")){
            hasil.setText("Kamu harus Mengqadha Shalat Asar");
        }else if(hasilKeluar.equals("Maghrib")){
            hasil.setText("Kamu harus Mengqadha Shalat Maghrib");
        }else if(hasilKeluar.equals("Isya")){
            hasil.setText("Kamu harus Mengqadha Shalat Isyak");
        }

        if (hasilBerhenti.equals("Subuh")){
            hasil2.setText("Dan kamu harus Mengqadha Shalat Subuh");
        }else if(hasilBerhenti.equals("Dzuhur")){
            hasil2.setText("Dan kamu Harus Mengqadha Shalat Dzuhur");
        }else if(hasilBerhenti.equals("Asar")){
            hasil2.setText("Dan kamu harus Mengqadha Shalat Dzuhur dan Asar");
        }else if(hasilBerhenti.equals("Maghrib")){
            hasil2.setText("Dan kamu harus Mengqadha Shalat Maghrib");
        }else if(hasilBerhenti.equals("Isya")){
            hasil2.setText("Dan kamu harus Mengqadha Shalat Maghrib dan Isyak");
        }
        linearHasil.setVisibility(View.VISIBLE);
    }
}
