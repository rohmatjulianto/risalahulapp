package com.example.a1183008;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1183008.Model.ModelAddUserChat;
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

public class TambahUserActivity extends AppCompatActivity {

    EditText nama, username, password, conpassword;
    Button daftar;
    String userid;
    public RadioButton selectButton;
    private RadioGroup radioGroup;
    ProgressDialog dialog;
    DatabaseReference reference;
    FirebaseAuth auth;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        radioGroup = findViewById(R.id.radioGroup);
        nama = findViewById(R.id.edt_namalengkap);
        username = findViewById(R.id.edt_usenamedaftar);
        password = findViewById(R.id.edt_passworddaftar);
        conpassword = findViewById(R.id.edt_conpassworddaftar);
        daftar = findViewById(R.id.btn_daftardaftar);
        auth = FirebaseAuth.getInstance();


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama = nama.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sConPassword = conpassword.getText().toString();
                selectButton = findViewById(radioGroup.getCheckedRadioButtonId());

                if(sNama.isEmpty()){
                    nama.setError("Nama tidak boleh kosong!");
                }else if(sUsername.isEmpty()){
                    username.setError("Username tidak boleh kosong!");
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
                        Toast.makeText(TambahUserActivity.this, "Password tidak sama!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void prosesDaftar() {
        String sNama = nama.getText().toString();
        String sUsername = username.getText().toString();
        String sPassword = password.getText().toString();
        String role = selectButton.getText().toString();
        String sEmail = sUsername+"@email.com";
        String id_role = null;

        if (role.equals("Admin")){
            id_role = "1";
        }else if(role.equals("Guru")){
            id_role = "2";
        }else if(role.equals("User")){
            id_role = "3";
        }else if(role.equals("Admin Kitab")){
            id_role = "4";
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Proses mendaftar...");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<ModelUser>> register = Api.service().getAddUser(sNama, sUsername,sPassword, id_role);
        String finalId_role = id_role;
        register.enqueue(new Callback<List<ModelUser>>() {
            @Override
            public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {
                if(response.body().get(0).getStatus().equals("akunudahada")){
                    Toast.makeText(TambahUserActivity.this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }else{

                    //daftar difirebase
                    auth.createUserWithEmailAndPassword(sEmail, sPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String tb = "null";
                                    if (role.equals("Admin")){
                                        tb = "admin";
                                    }else if(role.equals("Guru")){
                                        tb = "guru";
                                    }else if(role.equals("User")){
                                        tb = "pengguna";
                                    }else if(role.equals("Admin Kitab")){
                                        tb = "adminkitab";
                                    }
                                    if (task.isSuccessful()){
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        assert firebaseUser != null;
                                        userid = firebaseUser.getUid();


                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(tb).child(userid);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("nama", sNama);
                                        hashMap.put("username", sUsername);
                                        hashMap.put("imageURL", "default");
                                        hashMap.put("status", "offline");
                                        hashMap.put("role", tb);
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
                                        //update id
                                        Call<List<ModelUser>> upgrae = Api.service().getUpdateId(sUsername, userid);
                                        upgrae.enqueue(new Callback<List<ModelUser>>() {
                                            @Override
                                            public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {
                                                FirebaseAuth.getInstance().signOut();
                                                Toast.makeText(TambahUserActivity.this, "Berhasil Mendaftarkan Akun !", Toast.LENGTH_SHORT).show();
                                                DataUserActivity.da.TampilkanData("");

                                                finish();
                                                dialog.cancel();
                                            }
                                            @Override
                                            public void onFailure(Call<List<ModelUser>> call, Throwable t) {
                                                Toast.makeText(TambahUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {

                                    }
                                }
                            });
                }

            }
            @Override
            public void onFailure(Call<List<ModelUser>> call, Throwable t) {
                Toast.makeText(TambahUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
}