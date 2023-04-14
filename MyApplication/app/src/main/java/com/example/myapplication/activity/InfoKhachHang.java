package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.ultil.CheckConnection;
import com.example.myapplication.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoKhachHang extends AppCompatActivity {

    EditText edtTenKH, edtEmail, edtSDT;
    Button btnXacNhan, btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_khach_hang);

        initView();
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            eventButton();
        }else {
            CheckConnection.showToast(getApplicationContext(), "ban hay kiem tra lai ket noi");
        }

    }

    private void eventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenKH.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server.donHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String maDonHang) {
                            if(Integer.parseInt(maDonHang) > 0){
                                RequestQueue quest = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, server.chiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            CheckConnection.showToast(getApplicationContext(), "du lieu gio hang bi loi");

                                        }else {

                                            MainActivity.listGioHang.clear();
                                            CheckConnection.showToast(getApplicationContext(), "Ban da them san pham vao gio hang");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToast(getApplicationContext(), "Moi ban tiep tuc mua hang");
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
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0; i<MainActivity.listGioHang.size(); i++){
                                            JSONObject jsonObject = new JSONObject();

                                            try {
                                                jsonObject.put("madonhang", maDonHang);
                                                jsonObject.put("masanpham", MainActivity.listGioHang.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.listGioHang.get(i).getTenSP());
                                                jsonObject.put("gisanpham", MainActivity.listGioHang.get(i).getGiaSP());
                                                jsonObject.put("soluongsanpham", MainActivity.listGioHang.get(i).getSoLuong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());

                                        return hashMap;
                                    }
                                };
                                quest.add(request);
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
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenKH", ten);
                            hashMap.put("SDT", sdt);
                            hashMap.put("email", email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.showToast(getApplicationContext(), "Ban hay kiem tra lai thong tin");
                }

            }
        });

    }

    private void initView() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtSDT = findViewById(R.id.edtSDT);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTroVe = findViewById(R.id.btnTroVe);

    }
}