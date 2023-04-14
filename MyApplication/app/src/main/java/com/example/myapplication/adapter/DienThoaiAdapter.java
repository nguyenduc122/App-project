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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> listDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> listDienThoai) {
        this.context = context;
        this.listDienThoai = listDienThoai;
    }


    @Override
    public int getCount() {
        return listDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return listDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class viewHolder{
        TextView tvTenDT, tvGiaDT, tvMoTaDT;
        ImageView imgDienThoai;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder vHolder = null;
        if(convertView == null){
            vHolder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dienthoai,null);
            vHolder.tvTenDT = convertView.findViewById(R.id.tvTenDT);
            vHolder.tvGiaDT = convertView.findViewById(R.id.tvGiaDT);
            vHolder.tvMoTaDT = convertView.findViewById(R.id.tvMoTaDT);
            vHolder.imgDienThoai = convertView.findViewById(R.id.imgDienThoai);
            convertView.setTag(vHolder);

        }else {
            vHolder = (viewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        vHolder.tvTenDT.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,####,###");
        vHolder.tvGiaDT.setText("Giá" + decimalFormat.format(sanPham.getGiaSP()) +"Đ");
        vHolder.tvMoTaDT.setMaxLines(2);
        vHolder.tvMoTaDT.setEllipsize(TextUtils.TruncateAt.END);
        vHolder.tvMoTaDT.setText(sanPham.getMoTaSP());
        Picasso.get().load(sanPham.getImgSP())
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error)
                .into(vHolder.imgDienThoai);

        return convertView;
    }
}
