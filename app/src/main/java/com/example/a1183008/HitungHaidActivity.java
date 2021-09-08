package com.example.a1183008;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1183008.Model.ModelInputHaid;
import com.example.a1183008.Model.ModelKitab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitungHaidActivity extends AppCompatActivity {
    public LinearLayout linearHasil;
    public TextView hasil, judulTglAkhir, judulTglAwal;
    public Button btnHitung;
    public RelativeLayout riwayatHaid;
    public EditText ed_tglawal, ed_tglakhir;
    public RadioButton rbtntiya, rbtntidak;
    private RadioGroup radioGroup;
    private String tgAwal, tgAkhir, tglAwals;
    public int awalTgl,akhirTgl, awal;

    public SessionPreference sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_haid);

        ed_tglawal = findViewById(R.id.ed_tglawal);
        ed_tglakhir = findViewById(R.id.ed_tglakhir);
        hasil = findViewById(R.id.hasil);
        linearHasil = findViewById(R.id.linerHasil);
        btnHitung = findViewById(R.id.btnHitung);
        rbtntiya = findViewById(R.id.rbtniya);
        rbtntidak = findViewById(R.id.rbtntidak);
        radioGroup = findViewById(R.id.radioGroup);
        judulTglAwal = findViewById(R.id.judulTglAwal);
        judulTglAkhir = findViewById(R.id.judulTglAkhir);
        riwayatHaid = findViewById(R.id.riwayatHaid);

        linearHasil.setVisibility(View.GONE);
        riwayatHaid.setVisibility(View.GONE);
        btnHitung.setVisibility(View.GONE);
        sp = new SessionPreference(this);


        //inisialisasi tanggal
        ed_tglawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "data");
                datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String tahun = ""+datePicker.getYear();
                        String bulan = ""+(datePicker.getMonth()+1);
                        String hari = ""+datePicker.getDayOfMonth();
                        tgAwal = hari+"/"+bulan+"/"+tahun;
                        awalTgl = (Integer.parseInt(hari)) + (Integer.parseInt(bulan)*31) + (Integer.parseInt(tahun)*360);
                        sp.setAwal(awalTgl);
                        ed_tglawal.setText(tgAwal);
                    }
                });
            }
        });

        riwayatHaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HitungHaidActivity.this, RiwayatHaidActivity.class));
            }
        });

        ed_tglakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "data");
                datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String tahun = ""+datePicker.getYear();
                        String bulan = ""+(datePicker.getMonth()+1);
                        String hari = ""+datePicker.getDayOfMonth();
                         tgAkhir = hari+"/"+bulan+"/"+tahun;
                         akhirTgl = (Integer.parseInt(hari)) + (Integer.parseInt(bulan)*31) + (Integer.parseInt(tahun)*360);
                        sp.setAkhir(akhirTgl);

                        ed_tglakhir.setText(tgAkhir);
                    }
                });
            }
        });


        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sp.getTglAwalo().equals("null")){
                    tglAwals = sp.getTglAwalo();
                }else{
                    tglAwals = ed_tglawal.getText().toString();
                }

                String tglAkhirs = ed_tglakhir.getText().toString();


                if (tglAwals.isEmpty()){
                    ed_tglawal.setError("Masukan Tanggal Keluar Darah");
                }else if (tglAkhirs.isEmpty()){
                    ed_tglakhir.setError("Masukan Tanggal Berhenti Darah");
                }else {
                    if (!sp.getTglAwalo().equals("null")){
                        awal= Integer.parseInt(sp.getTglAwalo());
                    }else{
                        awal = sp.getAwal();
                    }

                    int akhir = sp.getAkhir();
                    if (awal > akhir){
                        Toast.makeText(HitungHaidActivity.this, "Masukan tanggal dengan benar", Toast.LENGTH_SHORT).show();
                    }else {
                        sp.setTglAwalo("null");
                        int penghitungan = akhir - awal;

                        if (penghitungan < 15) {
                            hasil.setText("Status kamu saat ini adalah Haid");
                        } else if (penghitungan > 15) {
                            hasil.setText("Status kamu saat ini adalah Istihadhah");
                        }
                        linearHasil.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtniya:
                        judulTglAwal.setText("Masukan Kembali Tanggal Keluar");
                        judulTglAkhir.setText("Masukan Kembali Tanggal Berakhir Darah");
                        sp.setAwalTgl(ed_tglawal.getText().toString());
                        sp.setAkhirTgl(ed_tglakhir.getText().toString());
                        sp.setTglAwalo(String.valueOf(awalTgl));

                        ed_tglawal.setText("");
                        ed_tglakhir.setText("");
                        rbtntiya.setChecked(false);

                        btnHitung.setVisibility(View.GONE);
                        riwayatHaid.setVisibility(View.VISIBLE);

                        //tambah input
                        Call<List<ModelInputHaid>> tambah = Api.service().getInputHaid(sp.getLogin(),
                                sp.getAwalTgl(), sp.getAkhirTgl(), "1");
                        tambah.enqueue(new Callback<List<ModelInputHaid>>() {
                            @Override
                            public void onResponse(Call<List<ModelInputHaid>> call, Response<List<ModelInputHaid>> response) {

                            }
                            @Override
                            public void onFailure(Call<List<ModelInputHaid>> call, Throwable t) {
                            }
                        });


                        hasil.setText("Silahkan Masukan kembali tanggal keluar dan berakhir darah !");
                        Toast.makeText(HitungHaidActivity.this, "Masukan kembali tanggal keluar dan berhenti darah", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbtntidak:
                        btnHitung.setVisibility(View.VISIBLE);
                        riwayatHaid.setVisibility(View.GONE);

                        judulTglAwal.setText("Masukan Kembali Tanggal Keluar");
                        judulTglAkhir.setText("Masukan Kembali Tanggal Berakhir Darah");
                        //tambah input
                        Call<List<ModelInputHaid>> tambah1 = Api.service().getInputHaid(sp.getLogin(),
                                sp.getAwalTgl(), sp.getAkhirTgl(), "2");
                        tambah1.enqueue(new Callback<List<ModelInputHaid>>() {
                            @Override
                            public void onResponse(Call<List<ModelInputHaid>> call, Response<List<ModelInputHaid>> response) {

                            }
                            @Override
                            public void onFailure(Call<List<ModelInputHaid>> call, Throwable t) {
                            }
                        });


                        sp.setAwalTgl("0");
                        Toast.makeText(HitungHaidActivity.this, "Silahkan klik Cek Status", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        if(!sp.getAwalTgl().equals("0")){
            judulTglAwal.setText("Masukan Kembali Tanggal Keluar Darah");
            judulTglAkhir.setText("Masukan Kembali Tanggal Berhenti Darah");
//            ed_tglawal.setText(sp.getAwalTgl());
//            ed_tglakhir.setText(sp.getAkhirTgl());
            btnHitung.setVisibility(View.GONE);
            riwayatHaid.setVisibility(View.VISIBLE);
        }

    }
}