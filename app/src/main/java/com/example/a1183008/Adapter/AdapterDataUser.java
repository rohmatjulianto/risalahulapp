package com.example.a1183008.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.Api;
import com.example.a1183008.DataUserActivity;
import com.example.a1183008.EditUserActivity;
import com.example.a1183008.Model.ModelDeleteUser;
import com.example.a1183008.Model.ModelUser;
import com.example.a1183008.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataUser extends RecyclerView.Adapter<AdapterDataUser.ListViewHolder> {
    private Context context;
    private List<ModelUser> dataList;
    ProgressDialog dialog;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public AdapterDataUser(Context context, List<ModelUser>dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_user, viewGroup, false);
        return new AdapterDataUser.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelUser data = dataList.get(position);
        holder.tvNamaUser.setText(data.getNama());
        String roleId = data.getRole_id();

        if (roleId.equals("1")){
            holder.tvRoleUser.setText("- Admin");
        }else if (roleId.equals("2")){
            holder.tvRoleUser.setText("- Guru");
        }else if(roleId.equals("3")){
            holder.tvRoleUser.setText("- User");
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog = new ProgressDialog(context);
                        dialog.setMessage("Sedang menghapus...");
                        dialog.setCancelable(false);
                        dialog.show();

                        Call<List<ModelDeleteUser>> cek = Api.service().getDelete(data.getId_user());
                        cek.enqueue(new Callback<List<ModelDeleteUser>>() {
                            @Override
                            public void onResponse(Call<List<ModelDeleteUser>> call, Response<List<ModelDeleteUser>> response) {
                                String statuss = response.body().get(0).getStatus();

                                if (statuss.equals("gagaladmin")){
                                    Toast.makeText(context, "Maaf admin utama tidak bisa dihapus !", Toast.LENGTH_SHORT).show();
                                }else if(statuss.equals("berhasil")){
                                    Toast.makeText(context, "User berhasil dihapus !", Toast.LENGTH_SHORT).show();
                                }else if(statuss.equals("gagal")){
                                    Toast.makeText(context, "Gagal Menghapus !", Toast.LENGTH_SHORT).show();
                                }
                                //hapus di firebase
                                String role = "null";
                                if(data.getRole_id().equals("2")){
                                    role = "guru";
                                    database.child("Users").child("guru").child(data.getId_firebase())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }else if(data.getRole_id().equals("3")){
                                    database.child("Users").child("pengguna").child(data.getId_firebase())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }else if(data.getRole_id().equals("4")){
                                    database.child("Users").child("adminkitab").child(data.getId_firebase())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }
                                DataUserActivity.da.TampilkanData("");
                                dialog.cancel();
                            }
                            @Override
                            public void onFailure(Call<List<ModelDeleteUser>> call, Throwable t) {
                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah yakin mau menghapus Akun ? " + data.getNama());
                builder.show();

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(holder.itemView.getContext(), EditUserActivity.class);
                pindah.putExtra("id_fire", data.getId_firebase());
                pindah.putExtra("id_user", data.getId_user());
                pindah.putExtra("nama", data.getNama());
                pindah.putExtra("username", data.getUsername());
                pindah.putExtra("role_id", data.getRole_id());
                pindah.putExtra("password", data.getPassword());
                holder.itemView.getContext().startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaUser, tvRoleUser;
        CardView card;
        ImageView edit, delete;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaUser = itemView.findViewById(R.id.tvNamaUser);
            tvRoleUser = itemView.findViewById(R.id.tvRoleUser);
            card = itemView.findViewById(R.id.card);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
