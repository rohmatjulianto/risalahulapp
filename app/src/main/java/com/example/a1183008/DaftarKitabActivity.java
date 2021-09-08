package com.example.a1183008;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.Adapter.AdapterDaftarKitab;
import com.example.a1183008.Model.ModelKitab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKitabActivity extends AppCompatActivity {
    RecyclerView listView;
    AdapterDaftarKitab adapterDaftarKitab;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;
    EditText cari;
    ImageView btn_cari;
    RelativeLayout tambahKitab;
    public static DaftarKitabActivity dk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kitab);
        listView =  findViewById(R.id.listdata);
        tambahKitab =  findViewById(R.id.tambahKitab);
        btn_cari =  findViewById(R.id.btn_cari);
        cari = findViewById(R.id.cari);

        tambahKitab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaftarKitabActivity.this, TambahKitabActivity.class));
            }
        });

        dk = this;

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilkanData(cari.getText().toString());
            }
        });

        TampilkanData("");
    }

    public void TampilkanData(String tanya) {
        linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sedang memuat data...");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<ModelKitab>> call1 = Api.service().getData(tanya);
        call1.enqueue(new Callback<List<ModelKitab>>() {
            @Override
            public void onResponse(Call<List<ModelKitab>> call1, Response<List<ModelKitab>> response) {
                List<ModelKitab> post = response.body();
                adapterDaftarKitab = new AdapterDaftarKitab(DaftarKitabActivity.this, post);
                listView.setAdapter(adapterDaftarKitab);
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<List<ModelKitab>> call1, Throwable t) {
                Toast.makeText(DaftarKitabActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
    }
}