package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterLoaiSP;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.LoaiSP;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.ultil.CheckConnection;
import com.example.myapplication.ultil.server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> listLoaiSP;
    AdapterLoaiSP adapterLoaiSP;
    int id;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    ArrayList<SanPham> listSP;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> listGioHang;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFlip();
            getDataSp();
            getDataSpmoi();
            itemListView();
        }else{
            CheckConnection.showToast(getApplicationContext(),"Không thể kết nối internet");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    private void itemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idLoaiSP", listLoaiSP.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, Laptop_Activity.class);
                            intent.putExtra("idLoaiSP", listLoaiSP.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHe_Activity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, Info_Activity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                }
            }
        });

    }

    private void getDataSpmoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.linkSPMoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String tenSP = "";
                    Integer giaSP = 0;
                    String imgSp = "";
                    String moTaSP = "";
                    int idSP = 0;
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            tenSP = jsonObject.getString("tenSP");
                            giaSP = jsonObject.getInt("giaSP");
                            imgSp = jsonObject.getString("hinhAnhSP");
                            moTaSP = jsonObject.getString("moTaSP");
                            idSP = jsonObject.getInt("idSP");
                            listSP.add(new SanPham(ID, tenSP, giaSP, imgSp, moTaSP, idSP));
                            sanPhamAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void getDataSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.linkSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenLoaiSP");
                            hinhanhloaisp = jsonObject.getString("hinhAnhLoaiSP");
                            listLoaiSP.add(new LoaiSP(id,tenloaisp,hinhanhloaisp));
                            adapterLoaiSP.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void actionViewFlip() {
        ArrayList<String> listQC = new ArrayList<>();
        listQC.add("https://laptops.vn/uploads/1920_x_659_1614062618.jpg");
        listQC.add("https://kimlongcenter.com/upload/image/TOP%20laptop%20dell.png");

        for (int i = 0; i < listQC.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(listQC.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void initView() {
        toolbar = findViewById(R.id.tlbTrangChu);
        viewFlipper = findViewById(R.id.vLipper);
        recyclerView = findViewById(R.id.rvIteam);
        navigationView = findViewById(R.id.nvMhc);
        listView = findViewById(R.id.lvMhc);
        drawerLayout = findViewById(R.id.drawerLayout);

        listLoaiSP = new ArrayList<>();
        listLoaiSP.add(0, new LoaiSP(0, "Trang Chủ", "https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png"));

        adapterLoaiSP = new AdapterLoaiSP(listLoaiSP, getApplicationContext());
        listView.setAdapter(adapterLoaiSP);

        listSP = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), listSP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);

        if(listGioHang != null){

        }else {
            listGioHang = new ArrayList<>();
        }


    }
}