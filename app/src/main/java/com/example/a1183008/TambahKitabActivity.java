package com.example.a1183008;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1183008.Model.ModelKitab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKitabActivity extends AppCompatActivity {
    private EditText ed_namaKitab, edIsiKitab;
    private Button tambah;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kitab);

        ed_namaKitab = findViewById(R.id.ed_namaKitab);
        edIsiKitab = findViewById(R.id.edIsiKitab);
        tambah = findViewById(R.id.btntambah);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = ed_namaKitab.getText().toString();
                String isi = edIsiKitab.getText().toString();

                if (judul.isEmpty()){
                    ed_namaKitab.setError("Judul tidak boleh kosong !");
                }else if(isi.isEmpty()){
                    edIsiKitab.setError("Isi tidak boleh kosong !");
                }else{
                    dialog = new ProgressDialog(TambahKitabActivity.this);
                    dialog.setMessage("Proses update...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Call<List<ModelKitab>> tambah = Api.service().getTambahKitab(judul, isi);
                    tambah.enqueue(new Callback<List<ModelKitab>>() {
                        @Override
                        public void onResponse(Call<List<ModelKitab>> call, Response<List<ModelKitab>> response) {

                            Toast.makeText(TambahKitabActivity.this, "Berhasil Menambahkan Data !", Toast.LENGTH_SHORT).show();
                            DaftarKitabActivity.dk.TampilkanData("");
                            finish();
                            dialog.cancel();
                        }
                        @Override
                        public void onFailure(Call<List<ModelKitab>> call, Throwable t) {
                            Toast.makeText(TambahKitabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                }
            }
        });
    }
}