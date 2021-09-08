package com.example.a1183008;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1183008.Model.ModelLogin;
import com.example.a1183008.Model.ModelUser;
import com.example.a1183008.ui.ForgotActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login, daftar2;
    ProgressDialog dialog;
    FirebaseAuth auth;
    SessionPreference sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.edt_usernamelogin);
        password = (EditText) findViewById(R.id.edt_passwordlogin);
        login = (Button) findViewById(R.id.btn_lohinlogin);
        daftar2 = (Button) findViewById(R.id.btn_daftarlogin);
        dialog = new ProgressDialog(LoginActivity.this);

        auth = FirebaseAuth.getInstance();

        sp = new SessionPreference(this);
        if (!sp.getLogin().equals("false")) {
            if (sp.getRole().equals("1")) {
                startActivity(new Intent(LoginActivity.this, AdminAcivity.class));
                finish();
            } else if (sp.getRole().equals("2")) {
                startActivity(new Intent(LoginActivity.this, HalamanGuruActivity.class));
                finish();
            } else if (sp.getRole().equals("3")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else if (sp.getRole().equals("4")) {
                startActivity(new Intent(LoginActivity.this, AdminKitabActivity.class));
                finish();
            }

        }

        TextView tvLupa = findViewById(R.id.tv_lupa);
        tvLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });


        daftar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarintent = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(daftarintent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();

                CheckLogin(sUsername, sPassword);
            }
        });
    }

    public void CheckLogin(final String usernames, final String passwords) {
        if (usernames.isEmpty()) {
            username.setError("Username tidak boleh kosong!");
        } else if (passwords.isEmpty()) {
            password.setError("Password tidak boleh kosong!");
        } else {
            prosesLogin(usernames, passwords);
        }
    }

    private void prosesLogin(final String usernames, final String passwords) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Proses login...");
        dialog.setCancelable(false);
        dialog.show();
        String sEmail = usernames + "@email.com";

        Call<List<ModelLogin>> cekData = Api.service().getLogin(usernames, passwords);
        cekData.enqueue(new Callback<List<ModelLogin>>() {
            @Override
            public void onResponse(Call<List<ModelLogin>> call, Response<List<ModelLogin>> response) {
                String respon = response.body().get(0).getRole_id();
                if (respon.equals("tdkada")) {
                    Toast.makeText(LoginActivity.this, "Maaf akun belum terdaftar !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else if (respon.equals("pswdsalah")) {
                    Toast.makeText(LoginActivity.this, "Maaf password salah !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    if (respon.equals("1")) {
                        startActivity(new Intent(LoginActivity.this, AdminAcivity.class));
                        sp.setLogin(response.body().get(0).getId_user());
                        sp.setRole(response.body().get(0).getRole_id());
                        sp.setUsername(response.body().get(0).getUsername());
                        dialog.cancel();
                        finish();
                    } else if (respon.equals("2")) {
                        sp.setLogin(response.body().get(0).getId_user());
                        sp.setRole(response.body().get(0).getRole_id());
                        sp.setUsername(response.body().get(0).getUsername());

                        auth.signInWithEmailAndPassword(sEmail, passwords)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(LoginActivity.this, HalamanGuruActivity.class));
                                            dialog.cancel();
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    }
                                });
                    } else if (respon.equals("3")) {
                        sp.setLogin(response.body().get(0).getId_user());
                        sp.setRole(response.body().get(0).getRole_id());
                        sp.setUsername(response.body().get(0).getUsername());

                        auth.signInWithEmailAndPassword(sEmail, passwords)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            dialog.cancel();
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    }
                                });
                    } else if (respon.equals("4")) {
                        startActivity(new Intent(LoginActivity.this, AdminKitabActivity.class));
                        sp.setLogin(response.body().get(0).getId_user());
                        sp.setRole(response.body().get(0).getRole_id());
                        sp.setUsername(response.body().get(0).getUsername());
                        dialog.cancel();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelLogin>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }


}