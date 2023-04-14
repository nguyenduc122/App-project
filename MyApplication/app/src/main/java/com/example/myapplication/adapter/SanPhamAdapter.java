package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.detailSP;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.iteamHolder> {
    Context context;
    ArrayList<SanPham> arraySP;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySP) {
        this.context = context;
        this.arraySP = arraySP;
    }

    @NonNull
    @Override
    public iteamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spmoi,null);
        iteamHolder itHolder = new iteamHolder(v);
        return itHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull iteamHolder holder, int position) {
        SanPham sanPham = arraySP.get(position);
        holder.tvTenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,####,###");
        holder.tvGiaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) +"Đ");
        Picasso.get().load(sanPham.getImgSP())
                .placeholder(R.drawable.ic_downloading)
                .into(holder.imgSanPham);

    }

    @Override
    public int getItemCount() {
        return arraySP.size();
    }

    public class iteamHolder extends RecyclerView.ViewHolder{
        public ImageView imgSanPham;
        public TextView tvTenSP, tvGiaSP;

        public iteamHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, detailSP.class);
                    intent.putExtra("infoSP", arraySP.get(getPosition()));
                    CheckConnection.showToast(context, arraySP.get(getPosition()).getTenSP());
                    context.startActivity(intent);
                }
            });

        }
    }
}
