package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapter.DienThoaiAdapter;
import com.example.myapplication.adapter.LaptopAdapter;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.ultil.CheckConnection;
import com.example.myapplication.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Laptop_Activity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView lvLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<SanPham> listLaptop;
    int idLaptop = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        initView();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
        getIDSP();
        actionTB();
        getData(page);
        loadData();

        }else{
            CheckConnection.showToast(getApplicationContext(), "Ban hay kiem tra lai ket noi");
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

    private void initView() {

        toolbarLaptop = findViewById(R.id.tbLaptop);
        lvLaptop = findViewById(R.id.lvLaptop);
        listLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), listLaptop);
        lvLaptop.setAdapter(laptopAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHand = new mHandler();
    }
    private void getIDSP() {
        idLaptop = getIntent().getIntExtra("idLoaiSP", -1);

    }
    private void actionTB() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = server.linkDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenLaptop = "";
                int giaLaptop = 0;
                String imgLaptop = "";
                String moTaLaptop = "";
                int idSPLaptop = 0;
                if(response != null && response.length() != 2){
                    lvLaptop.removeFooterView(footerView);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLaptop = jsonObject.getString("tenSP");
                            giaLaptop = jsonObject.getInt("giaSP");
                            imgLaptop = jsonObject.getString("hinhAnhSP");
                            moTaLaptop = jsonObject.getString("moTaSP");
                            idSPLaptop = jsonObject.getInt("idSP");
                            listLaptop.add(new SanPham(id, tenLaptop, giaLaptop, imgLaptop, moTaLaptop, idSPLaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitData = true;
                    lvLaptop.removeFooterView(footerView);
                    CheckConnection.showToast(getApplicationContext(), "da het du lieu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idSP", String.valueOf(idLaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);


    }
    private void loadData() {
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), detailSP.class);
                intent.putExtra("infoSP", listLaptop.get(position));
                startActivity(intent);
            }
        });

        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    threadData threaddata = new threadData();
                    threaddata.start();

                }

            }
        });
    }
    public class threadData extends Thread{
        @Override
        public void run() {
            mHand.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHand.obtainMessage(1);
            mHand.sendMessage(message);

            super.run();
        }
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvLaptop.addFooterView(footerView);
                    break;
                case 1:
                    page++;
                    getData(page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
}