package com.example.myapplication.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int ID;
    public String tenSP;
    public Integer giaSP;
    public String imgSP;
    public String moTaSP;
    public int idSP;

    public SanPham(int ID, String tenSP, Integer giaSP, String imgSP, String moTaSP, int idSP) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.imgSP = imgSP;
        this.moTaSP = moTaSP;
        this.idSP = idSP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Integer getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(Integer giaSP) {
        this.giaSP = giaSP;
    }

    public String getImgSP() {
        return imgSP;
    }

    public void setImgSP(String imgSP) {
        this.imgSP = imgSP;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }
}
