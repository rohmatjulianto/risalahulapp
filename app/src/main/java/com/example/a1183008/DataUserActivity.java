package com.example.a1183008;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.Adapter.AdapterDataUser;
import com.example.a1183008.Model.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataUserActivity extends AppCompatActivity {
    RecyclerView listView;
    AdapterDataUser adapterDataUser;
    EditText cari;
    ImageView btn_cari;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;
    TextView judul;
    RelativeLayout tambahUser;
    public static DataUserActivity da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
        listView =  findViewById(R.id.listdata);
        tambahUser =  findViewById(R.id.tambahUser);
        btn_cari =  findViewById(R.id.btn_cari);
        cari = findViewById(R.id.cari);
        judul =  findViewById(R.id.judul);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilkanData(cari.getText().toString());
            }
        });

        da = this;
        TampilkanData("");

        tambahUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataUserActivity.this, TambahUserActivity.class));
            }
        });
    }

    public void TampilkanData(String tanya) {
        linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sedang memuat data...");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<ModelUser>> call1 = Api.service().getUserData(tanya);
        call1.enqueue(new Callback<List<ModelUser>>() {
            @Override
            public void onResponse(Call<List<ModelUser>> call1, Response<List<ModelUser>> response) {
                List<ModelUser> post = response.body();
                adapterDataUser = new AdapterDataUser(DataUserActivity.this, post);
                listView.setAdapter(adapterDataUser);
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<List<ModelUser>> call1, Throwable t) {
                Toast.makeText(DataUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }

        });
    }
}