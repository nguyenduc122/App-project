package com.example.myapplication.model;

public class LoaiSP {
    public int Id;
    public String TenSP;
    public String ImgSP;

    public LoaiSP(int id, String tenSP, String imgSP) {
        Id = id;
        TenSP = tenSP;
        ImgSP = imgSP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getImgSP() {
        return ImgSP;
    }

    public void setImgSP(String imgSP) {
        ImgSP = imgSP;
    }
}
