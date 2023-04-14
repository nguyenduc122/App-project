package com.example.myapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.GioHangAdapter;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.ultil.CheckConnection;

import java.text.DecimalFormat;

public class DatHang extends AppCompatActivity {

    ListView lvGioHang;
    TextView tvThongbao;
    static TextView tvTongTien;
    Button btnThanhToan, btnTieptuc;
    Toolbar tbGiohang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);

        initView();
        actionTool();
        checkData();
        eventUltil();
        itemListView();
        eventButton();

    }

    private void eventButton() {
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.listGioHang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), InfoKhachHang.class);
                    startActivity(intent);

                }else {
                    CheckConnection.showToast(getApplicationContext(), "Gio hang cua ban trong");
                }
            }
        }));

    }

    private void itemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DatHang.this);
                builder.setTitle("Xac nhan xoa");
                builder.setMessage("ban co chac muon xoa");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.listGioHang.size() <= 0){
                            tvThongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.listGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            eventUltil();
                            if (MainActivity.listGioHang.size() <= 0){
                                tvThongbao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                eventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        eventUltil();
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    public static void eventUltil() {
        long tongTien = 0;
        for (int i=0; i<MainActivity.listGioHang.size(); i++){
            tongTien += MainActivity.listGioHang.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien) + "D");

    }

    private void checkData() {
        if (MainActivity.listGioHang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);

        }else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);

        }

    }

    private void actionTool() {
        setActionBar(tbGiohang);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tbGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void initView() {
        lvGioHang = findViewById(R.id.lvGioHang);
        tvThongbao = findViewById(R.id.tvThongBao);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTieptuc = findViewById(R.id.btnTiepTuc);
        tbGiohang = findViewById(R.id.tbGioHang);

        gioHangAdapter = new GioHangAdapter(DatHang.this, MainActivity.listGioHang);
        lvGioHang.setAdapter(gioHangAdapter);

    }
}