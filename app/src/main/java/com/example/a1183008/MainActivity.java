package com.example.a1183008;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import com.example.a1183008.Model.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView hitungid , shalatid , kitab , tanyaid;
    public TextView nama;
    public ImageView button_logout;
    public ProgressDialog dialog;
    public SessionPreference sp;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ma = this;

        hitungid = (CardView) findViewById(R.id.hitung);
        shalatid = (CardView) findViewById(R.id.shalat);
        kitab = (CardView) findViewById(R.id.kitab);
        nama = (TextView) findViewById(R.id.nama);
        tanyaid = (CardView) findViewById(R.id.tanya);
        button_logout = (ImageView) findViewById(R.id.button_logout);
        tanyaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivityChat.class));
            }
        });

        dialog = new ProgressDialog(MainActivity.this);
        sp = new SessionPreference(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        hitungid.setOnClickListener(this);
        shalatid.setOnClickListener(this);
        kitab.setOnClickListener(this);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setLogin("false");
                sp.setRole("false");
                status("offline");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        prosesUser();

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
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent i;

        if (v.getId() == R.id.hitung) {
            i = new Intent(this, HitungHaidActivity.class);
            startActivity(i);
        }  else if (v.getId() == R.id.tanya) {
            i = new Intent(this, MainActivityChat.class);
            startActivity(i);
        }else if (v.getId() == R.id.shalat) {
            i = new Intent(this, HitungShalatActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.kitab){
            startActivity(new Intent(MainActivity.this, KitabActivity.class));
        }
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child("pengguna").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);


    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }



}