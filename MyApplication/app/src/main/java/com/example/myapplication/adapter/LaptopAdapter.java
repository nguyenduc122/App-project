package com.example.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> listLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> listDienThoai) {
        this.context = context;
        this.listLaptop = listDienThoai;
    }


    @Override
    public int getCount() {
        return listLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return listLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView tvTenLaptop, tvGiaLaoptop, tvMoTaLaptop;
        ImageView imgLaptop;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHol = null;
        if(convertView == null){
            vHol = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_laptop,null);
            vHol.tvTenLaptop = convertView.findViewById(R.id.tvTenLaptop);
            vHol.tvGiaLaoptop = convertView.findViewById(R.id.tvGiaLaptop);
            vHol.tvMoTaLaptop = convertView.findViewById(R.id.tvMoTaLaptop);
            vHol.imgLaptop = convertView.findViewById(R.id.imgLaptop);
            convertView.setTag(vHol);

        }else {
            vHol = (LaptopAdapter.ViewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        vHol.tvTenLaptop.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,####,###");
        vHol.tvGiaLaoptop.setText("Giá" + decimalFormat.format(sanPham.getGiaSP()) +"Đ");
        vHol.tvMoTaLaptop.setMaxLines(2);
        vHol.tvMoTaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        vHol.tvMoTaLaptop.setText(sanPham.getMoTaSP());
        Picasso.get().load(sanPham.getImgSP())
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error)
                .into(vHol.imgLaptop);

        return convertView;
    }
}
