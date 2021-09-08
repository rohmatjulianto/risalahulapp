package com.example.a1183008;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.a1183008.Model.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAcivity extends AppCompatActivity {
    CardView dataUser, kitab;
    TextView nama;
    ImageView button_logout;
    public ProgressDialog dialog;
    public SessionPreference sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hal_admin);

        kitab = (CardView) findViewById(R.id.kitab);
        dataUser = (CardView) findViewById(R.id.dataUser);
        nama = (TextView) findViewById(R.id.nama);
        button_logout = (ImageView) findViewById(R.id.button_logout);
        sp = new SessionPreference(this);
        dialog = new ProgressDialog(AdminAcivity.this);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setLogin("false");
                sp.setRole("false");
                startActivity(new Intent(AdminAcivity.this, LoginActivity.class));
                finish();
            }
        });
        prosesUser();

        dataUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAcivity.this, DataUserActivity.class));
            }
        });

        kitab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAcivity.this, KitabActivity.class));
            }
        });
    }

    private void prosesUser() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Proses login...");
        dialog.setCancelable(false);
        dialog.show();
        String id_user = sp.getLogin();
        Call<List<ModelUser>> cekData = Api.service().ResponUser(id_user);
        cekData.enqueue(new Callback<List<ModelUser>>() {
            @Override
            public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {
                String tvnama = response.body().get(0).getNama();
                nama.setText("Hai " + tvnama + " !");
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<ModelUser>> call, Throwable t) {
                Toast.makeText(AdminAcivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }

}
