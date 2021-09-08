package com.example.a1183008;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a1183008.Adapter.AdapterDaftarKitab;
import com.example.a1183008.Adapter.AdapterRiwayat;
import com.example.a1183008.Model.ModelInputHaid;
import com.example.a1183008.Model.ModelKitab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatHaidActivity extends AppCompatActivity {
    RecyclerView listView;
    AdapterRiwayat adapterRiwayat;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;
    public SessionPreference sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_haid);
        listView =  findViewById(R.id.listdata);
        sp = new SessionPreference(this);
        TampilkanData();
    }

    private void TampilkanData() {
        linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sedang memuat data...");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<ModelInputHaid>> call1 = Api.service().getRiwayat(sp.getLogin());
        call1.enqueue(new Callback<List<ModelInputHaid>>() {
            @Override
            public void onResponse(Call<List<ModelInputHaid>> call1, Response<List<ModelInputHaid>> response) {
                List<ModelInputHaid> post = response.body();
                adapterRiwayat = new AdapterRiwayat(RiwayatHaidActivity.this, post);
                listView.setAdapter(adapterRiwayat);
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<List<ModelInputHaid>> call1, Throwable t) {
                Toast.makeText(RiwayatHaidActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
    }
}