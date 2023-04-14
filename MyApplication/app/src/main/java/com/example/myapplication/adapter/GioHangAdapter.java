package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.DatHang;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView tvTenGioHang, tvgiaGioHang;
        public ImageView imgGioHang;
        public Button btnMinus, btnValues, btnPlus;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_giohang, null);
            viewHolder.tvTenGioHang = convertView.findViewById(R.id.tvTenGioHang);
            viewHolder.tvgiaGioHang = convertView.findViewById(R.id.tvGiaGioHang);
            viewHolder.imgGioHang = convertView.findViewById(R.id.imgGioHang);
            viewHolder.btnMinus = convertView.findViewById(R.id.btnMinus);
            viewHolder.btnValues = convertView.findViewById(R.id.btnValues);
            viewHolder.btnPlus = convertView.findViewById(R.id.btnPlus);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.tvTenGioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvgiaGioHang.setText(decimalFormat.format(gioHang.getGiaSP()) + "D");
        Picasso.get().load(gioHang.getImgSP())
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgGioHang);
        viewHolder.btnValues.setText(gioHang.getSoLuong() + "");

        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl >= 10){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);

        }else if(sl <= 1){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);

        }else if (sl >= 1){
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);

        }
        ViewHolder finalViewHolder = viewHolder;
        finalViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoi = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) +1;
                int slHT = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHT = MainActivity.listGioHang.get(position).getGiaSP();

                MainActivity.listGioHang.get(position).setSoLuong(slMoi);
                long giaMoi = (giaHT * slMoi) / slHT;
                MainActivity.listGioHang.get(position).setGiaSP(giaMoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvgiaGioHang.setText(decimalFormat.format(giaMoi) + "D");
                DatHang.eventUltil();
                if (slMoi > 9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoi));

                }else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoi));
                }

            }
        });

        finalViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int slMoi = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) -1;
                int slHT = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHT = MainActivity.listGioHang.get(position).getGiaSP();

                MainActivity.listGioHang.get(position).setSoLuong(slMoi);
                long giaMoi = (giaHT * slMoi) / slHT;
                MainActivity.listGioHang.get(position).setGiaSP(giaMoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvgiaGioHang.setText(decimalFormat.format(giaMoi) + "D");
                DatHang.eventUltil();
                if (slMoi < 2){
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoi));

                }else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoi));
                }
            }
        });

        return convertView;
    }
}
