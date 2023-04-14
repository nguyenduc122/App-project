package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLoaiSP extends BaseAdapter {
    ArrayList<LoaiSP> arrayListLaoiSp;
    Context context;

    public AdapterLoaiSP(ArrayList<LoaiSP> arrayListLaoiSp, Context context) {
        this.arrayListLaoiSp = arrayListLaoiSp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLaoiSp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLaoiSp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHoder{
        TextView tvTenLoaiSP;
        ImageView imgLoaiSP;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = null;
        if (convertView == null){
            viewHoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_sp,null);
            viewHoder.tvTenLoaiSP = convertView.findViewById(R.id.tvLoaiSP);
            viewHoder.imgLoaiSP = convertView.findViewById(R.id.imgLoaiSP);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        viewHoder.tvTenLoaiSP.setText(loaiSP.getTenSP());
        Picasso.get().load(loaiSP.getImgSP())
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error)
                .into(viewHoder.imgLoaiSP);
        return convertView;
    }
}
