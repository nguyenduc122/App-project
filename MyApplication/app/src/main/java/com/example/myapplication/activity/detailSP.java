package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class detailSP extends AppCompatActivity {

    Toolbar tbDetail;
    ImageView imgDetail;
    TextView tvTen, tvGia, tvMoTa;
    Spinner spDetail;
    Button btnDetail;

    int id= 0;
    String tenDetail = "";
    int giaDetail = 0;
    String anhDetail = "";
    String moTaDetail = "";
    int idSP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sp);

        initView();
        actionTool();
        getInformation();
        eventSpinner();
        eventButton();
    }



    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(), DatHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventButton() {
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size() > 0){
                    int sl = Integer.parseInt(spDetail.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i=0; i<MainActivity.listGioHang.size(); i++){
                        if(MainActivity.listGioHang.get(i).getIdSP() == id){
                            MainActivity.listGioHang.get(i).setSoLuong(MainActivity.listGioHang.get(i).getSoLuong() + sl);
                            if (MainActivity.listGioHang.get(i).getSoLuong() >= 10){
                                MainActivity.listGioHang.get(i).setSoLuong(10);
                            }
                            MainActivity.listGioHang.get(i).setGiaSP(giaDetail * MainActivity.listGioHang.get(i).getSoLuong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soLuong = Integer.parseInt(spDetail.getSelectedItem().toString());
                        long giaMoi = soLuong * giaDetail;
                        MainActivity.listGioHang.add(new GioHang(id, tenDetail, giaMoi, anhDetail, soLuong));
                    }

                }else {
                    int soLuong = Integer.parseInt(spDetail.getSelectedItem().toString());
                    long giaMoi = soLuong * giaDetail;
                    MainActivity.listGioHang.add(new GioHang(id, tenDetail, giaMoi, anhDetail, soLuong));

                }
                Intent intent = new Intent(getApplicationContext(), DatHang.class);
                startActivity(intent);
            }
        });
    }

    private void eventSpinner() {

        Integer[] soLuong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, soLuong);
        spDetail.setAdapter(arrayAdapter);

    }

    private void getInformation() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("infoSP");
        id = sanPham.getID();
        tenDetail = sanPham.getTenSP();
        giaDetail = sanPham.getGiaSP();
        anhDetail = sanPham.getImgSP();
        moTaDetail = sanPham.getMoTaSP();
        idSP = sanPham.getIdSP();
        tvTen.setText(tenDetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText("Giá: " + decimalFormat.format(giaDetail) + "Đ");
        tvMoTa.setText(moTaDetail);
        Picasso.get().load(anhDetail)
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error)
                .into(imgDetail);

    }

    private void actionTool() {

        setActionBar(tbDetail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tbDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {

        tbDetail = findViewById(R.id.tbDetailSP);
        imgDetail = findViewById(R.id.imgDetailSP);
        tvTen = findViewById(R.id.tvTenDetail);
        tvGia = findViewById(R.id.tvGiaDetail);
        tvMoTa = findViewById(R.id.tvMotaDetailSP);
        spDetail = findViewById(R.id.spDetail);
        btnDetail = findViewById(R.id.btnDetail);

    }
}