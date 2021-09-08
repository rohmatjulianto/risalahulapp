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

import com.example.a1183008.Model.ModelUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {
    EditText nama, username, password;
    Button update;
    public String id_user, id_fire;
    public RadioButton selectButton, rbtnAdmin, rbtnGuru, rbtnUser, rbtnAdminKitab;
    private RadioGroup radioGroup;
    ProgressDialog dialog;
    public String tbs ="";
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        radioGroup = findViewById(R.id.radioGroup);
        rbtnAdmin = findViewById(R.id.rbtnAdmin);
        rbtnAdminKitab = findViewById(R.id.rbtnAdminKitab);
        rbtnGuru = findViewById(R.id.rbtnGuru);
        rbtnUser = findViewById(R.id.rbtnUser);
        nama = findViewById(R.id.edt_namalengkap);
        username = findViewById(R.id.edt_usenamedaftar);
        password = findViewById(R.id.edt_passworddaftar);
        update = findViewById(R.id.btnUpdate);

         id_fire = getIntent().getStringExtra("id_fire");
         id_user = getIntent().getStringExtra("id_user");
        String vnama = getIntent().getStringExtra("nama");
        String vusername = getIntent().getStringExtra("username");
        String vrole_id = getIntent().getStringExtra("role_id");
        String vpassword = getIntent().getStringExtra("password");

        nama.setText(vnama);
        username.setText(vusername);
        if (vrole_id.equals("1")){
            rbtnAdmin.setChecked(true);
        }else if (vrole_id.equals("2")){
            rbtnGuru.setChecked(true);
        }else if(vrole_id.equals("3")){
            rbtnUser.setChecked(true);
        }else if(vrole_id.equals("4")){
            rbtnAdminKitab.setChecked(true);
        }
        password.setText(vpassword);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama = nama.getText().toString();
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                selectButton = findViewById(radioGroup.getCheckedRadioButtonId());

                if(sNama.isEmpty()){
                    nama.setError("Nama tidak boleh kosong!");
                }else if(sUsername.isEmpty()){
                    username.setError("Username tidak boleh kosong!");
                }else if(sPassword.isEmpty()){
                    password.setError("Password tidak boleh kosong!");
                }else{
                    prosesUpdate();
                }
            }
        });


    }

    private void prosesUpdate() {
        String sId = nama.getText().toString();
        String sNama = nama.getText().toString();
        String sUsername = username.getText().toString();
        String sPassword = password.getText().toString();
        String role = selectButton.getText().toString();
        String id_role = null;

        dialog = new ProgressDialog(this);
        dialog.setMessage("Proses mendaftar...");
        dialog.setCancelable(false);
        dialog.show();


        if (role.equals("Admin")){
            id_role = "1";
            tbs = "admin";
        }else if(role.equals("Guru")){
            id_role = "2";
            tbs = "guru";
        }else if(role.equals("User")){
            id_role = "3";
            tbs = "pengguna";
        }else if(role.equals("Admin Kitab")) {
            id_role = "4";
            tbs = "adminkitab";
        }
//        Toast.makeText(this, "coba : " + id_fire + "Dan : " + tbs, Toast.LENGTH_SHORT).show();
//        dialog.cancel();
        Call<List<ModelUser>> register = Api.service().getUpdateUser(id_user, sNama, sUsername,sPassword, id_role);
        register.enqueue(new Callback<List<ModelUser>>() {
            @Override
            public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {

                //edit firebase
                database = FirebaseDatabase.getInstance().getReference("Users").child(tbs).child(id_fire);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", id_fire);
                hashMap.put("nama", sNama);
                hashMap.put("username", sUsername);
                hashMap.put("imageURL", "default");
                hashMap.put("status", "offline");
                hashMap.put("role", tbs);
                hashMap.put("bio", "");
                hashMap.put("search", sUsername.toLowerCase());
                database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                    }
                });

                Toast.makeText(EditUserActivity.this, "Berhasil Mengupdate Akun !", Toast.LENGTH_SHORT).show();
                DataUserActivity.da.TampilkanData("");
                finish();
                dialog.cancel();

            }
            @Override
            public void onFailure(Call<List<ModelUser>> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
}