package com.example.a1183008;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1183008.Model.ModelAddUserChat;
import com.example.a1183008.Model.ModelRegister;
import com.example.a1183008.Model.ModelUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {

    EditText nama, username, password, conpassword, email;
    Button login, daftar2;
    String userid;
    DatabaseReference reference;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        nama = (EditText) findViewById(R.id.edt_namalengkap);
        email = (EditText) findViewById(R.id.edt_email);
        username = (EditText) findViewById(R.id.edt_usenamedaftar);
        password = (EditText) findViewById(R.id.edt_passworddaftar);
        conpassword = (EditText) findViewById(R.id.edt_conpassworddaftar);
        login = (Button) findViewById(R.id.btn_daftarlogin);
        daftar2 = (Button) findViewById(R.id.btn_daftardaftar);
        dialog = new ProgressDialog(DaftarActivity.this);
        auth = FirebaseAuth.getInstance();

        daftar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sNama = nama.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sConPassword = conpassword.getText().toString();
                String sEmail = email.getText().toString();

                if(sNama.isEmpty()){
                    nama.setError("Nama tidak boleh kosong!");
                }else if(sUsername.isEmpty()){
                    username.setError("Username tidak boleh kosong!");
                }else if(sEmail.isEmpty()){
                    email.setError("Email tidak boleh kosong!");
                }else if(sPassword.isEmpty()){
                    password.setError("Password tidak boleh kosong!");
                }else if(sConPassword.isEmpty()){
                    conpassword.setError("Re-Password tidak boleh kosong!");
                }else if(sPassword.length() < 6){
                    password.setError("Password Minimal 6 kata !");
                }else{
                    if(sPassword.equals(sConPassword)){
                        prosesDaftar();
                    }else{
                        Toast.makeText(DaftarActivity.this, "Password tidak sama!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void prosesDaftar() {
        String sNama = nama.getText().toString();
        String sUsername = username.getText().toString();
        String sPassword = password.getText().toString();
        String sEmail = email.getText().toString();
        String emailFire = sUsername + "@gmail.com";

        dialog = new ProgressDialog(this);
        dialog.setMessage("Proses mendaftar...");
        dialog.setCancelable(false);
        dialog.show();


        Call<List<ModelRegister>> register = Api.service().TambahRespon(sNama, sUsername,sPassword, sEmail);
        register.enqueue(new Callback<List<ModelRegister>>() {
            @Override
            public void onResponse(Call<List<ModelRegister>> call, Response<List<ModelRegister>> response) {
                if(response.body().get(0).getStatus().equals("akunudahada")){
                    Toast.makeText(DaftarActivity.this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }else {
                    auth.createUserWithEmailAndPassword(emailFire, sPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        assert firebaseUser != null;
                                        userid = firebaseUser.getUid();
                                        //update id
                                        Call<List<ModelUser>> upgrae = Api.service().getUpdateId(sUsername, userid);
                                        upgrae.enqueue(new Callback<List<ModelUser>>() {
                                            @Override
                                            public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {
                                            }
                                            @Override
                                            public void onFailure(Call<List<ModelUser>> call, Throwable t) {
                                                Toast.makeText(DaftarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child("pengguna").child(userid);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("nama", sNama);
                                        hashMap.put("username", sUsername);
                                        hashMap.put("imageURL", "default");
                                        hashMap.put("status", "offline");
                                        hashMap.put("role", "pengguna");
                                        hashMap.put("bio", "");
                                        hashMap.put("search", sUsername.toLowerCase());
                                        if(dialog!=null){
                                            dialog.dismiss();
                                        }
                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                }
                                            }
                                        });
                                    } else {

                                    }
                                }
                            });


                    Toast.makeText(DaftarActivity.this, "Berhasil Mendaftar Akun !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                    finish();
                }
            }
            @Override
            public void onFailure(Call<List<ModelRegister>> call, Throwable t) {
                dialog.cancel();
            }
        });
    }


}