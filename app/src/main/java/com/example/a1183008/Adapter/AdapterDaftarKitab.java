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
import com.example.a1183008.DaftarKitabActivity;
import com.example.a1183008.DetailKitabActivity;
import com.example.a1183008.EditKitabActivity;
import com.example.a1183008.Model.ModelDeleteUser;
import com.example.a1183008.Model.ModelKitab;
import com.example.a1183008.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDaftarKitab extends RecyclerView.Adapter<AdapterDaftarKitab.ListViewHolder> {
    private Context context;
    private List<ModelKitab> dataList;
    ProgressDialog dialog;

    public AdapterDaftarKitab(Context context, List<ModelKitab>dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_edit_kitab, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelKitab data = dataList.get(position);
        holder.tvNamaKitab.setText(data.getNama_kitab());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(holder.itemView.getContext(), DetailKitabActivity.class);
                pindah.putExtra("id_kitab", data.getId_kitab());
                pindah.putExtra("nama", data.getNama_kitab());
                pindah.putExtra("isi", data.getIsi_kitab());
                holder.itemView.getContext().startActivity(pindah);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(holder.itemView.getContext(), EditKitabActivity.class);
                pindah.putExtra("id_kitab", data.getId_kitab());
                pindah.putExtra("nama", data.getNama_kitab());
                pindah.putExtra("isi", data.getIsi_kitab());
                holder.itemView.getContext().startActivity(pindah);
            }
        });

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

                        Call<List<ModelDeleteUser>> cek = Api.service().getDeletKitab(data.getId_kitab());
                        cek.enqueue(new Callback<List<ModelDeleteUser>>() {
                            @Override
                            public void onResponse(Call<List<ModelDeleteUser>> call, Response<List<ModelDeleteUser>> response) {
                                String statuss = response.body().get(0).getStatus();
                                if (statuss.equals("gagal")){
                                    Toast.makeText(context, "Maaf tidak bisa dihapus !", Toast.LENGTH_SHORT).show();
                                }else if(statuss.equals("berhasil")){
                                    Toast.makeText(context, "Kitab berhasil dihapus !", Toast.LENGTH_SHORT).show();
                                }

                                DaftarKitabActivity.dk.TampilkanData("");
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
                }).setMessage("Apakah yakin mau menghapus Data ? " + data.getNama_kitab());
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKitab;
        ImageView edit, delete;
        CardView card;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKitab = itemView.findViewById(R.id.tvNamaKitab);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            card = itemView.findViewById(R.id.card);

        }
    }
}
