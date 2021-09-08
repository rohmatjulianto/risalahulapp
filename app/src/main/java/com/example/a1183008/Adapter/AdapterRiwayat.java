package com.example.a1183008.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.DetailKitabActivity;
import com.example.a1183008.Model.ModelInputHaid;
import com.example.a1183008.Model.ModelKitab;
import com.example.a1183008.R;

import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ListViewHolder> {
    private Context context;
    private List<ModelInputHaid> dataList;
    ProgressDialog dialog;

    public AdapterRiwayat(Context context, List<ModelInputHaid>dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_haid, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelInputHaid data = dataList.get(position);
        holder.tvTglAwal.setText("Tanggal Awal : " + data.getTgl_awal());
        holder.tvTglAkhir.setText("Tanggal Akhir : " + data.getTgl_akhir());
        holder.tglInput.setText("Dicatata : " + data.getTgl_input());
        String status = data.getStatus();
        switch (status){
            case "1" :
                holder.badgeName.setText("Masih Keluar");
                break;
            case "2" :
                holder.badgeName.setText("Sudah Berhenti");
                holder.badges.setBackgroundColor(ContextCompat.getColor(context, R.color.hijau));
                holder.tvTglAwal.setTextColor(ContextCompat.getColor(context, R.color.abu));
                holder.tvTglAkhir.setTextColor(ContextCompat.getColor(context, R.color.abu));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView badgeName, tvTglAwal, tvTglAkhir, tglInput;
        CardView card;
        LinearLayout badges;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeName = itemView.findViewById(R.id.badgeName);
            tvTglAwal = itemView.findViewById(R.id.tvTglAwal);
            tvTglAkhir = itemView.findViewById(R.id.tvTglAkhir);
            card = itemView.findViewById(R.id.card);
            badges = itemView.findViewById(R.id.badges);
            tglInput = itemView.findViewById(R.id.tvBuat);

        }
    }
}
