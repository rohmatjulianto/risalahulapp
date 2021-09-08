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

public class EditKitabActivity extends AppCompatActivity {
    private EditText ed_namaKitab, edIsiKitab;
    private Button update;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kitab);

        ed_namaKitab = findViewById(R.id.ed_namaKitab);
        edIsiKitab = findViewById(R.id.edIsiKitab);
        update = findViewById(R.id.btnUpdate);

        String id_kitab = getIntent().getStringExtra("id_kitab");
        String namakitabs = getIntent().getStringExtra("nama");
        String isikitab = getIntent().getStringExtra("isi");

        ed_namaKitab.setText(namakitabs);
        edIsiKitab.setText(isikitab);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = ed_namaKitab.getText().toString();
                String isi = edIsiKitab.getText().toString();

                dialog = new ProgressDialog(EditKitabActivity.this);
                dialog.setMessage("Proses update...");
                dialog.setCancelable(false);
                dialog.show();

                Call<List<ModelKitab>> update = Api.service().getUpdateKitab(id_kitab, judul, isi);
                update.enqueue(new Callback<List<ModelKitab>>() {
                    @Override
                    public void onResponse(Call<List<ModelKitab>> call, Response<List<ModelKitab>> response) {

                        Toast.makeText(EditKitabActivity.this, "Berhasil Mengupdate Data !", Toast.LENGTH_SHORT).show();
                        DaftarKitabActivity.dk.TampilkanData("");
                        finish();
                        dialog.cancel();

                    }
                    @Override
                    public void onFailure(Call<List<ModelKitab>> call, Throwable t) {
                        Toast.makeText(EditKitabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });


            }
        });
    }
}