package com.example.a1183008.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.DetailKitabActivity;
import com.example.a1183008.Model.ModelKitab;
import com.example.a1183008.R;

import java.util.List;

public class AdapterKitab extends RecyclerView.Adapter<AdapterKitab.ListViewHolder> {
    private Context context;
    private List<ModelKitab> dataList;
    ProgressDialog dialog;

    public AdapterKitab(Context context, List<ModelKitab>dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
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
                pindah.putExtra("nama", data.getNama_kitab());
                pindah.putExtra("isi", data.getIsi_kitab());
                holder.itemView.getContext().startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKitab;
        CardView card;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKitab = itemView.findViewById(R.id.tvNamaKitab);
            card = itemView.findViewById(R.id.card);

        }
    }
}
