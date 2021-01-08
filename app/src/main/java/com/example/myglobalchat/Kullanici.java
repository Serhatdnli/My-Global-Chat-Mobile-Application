package com.example.myglobalchat;

public class Kullanici {
    private String id;
    private String kullanici_adi;
    private String kullanici_sifre;

    public Kullanici(String id, String kullanici_adi, String kullanici_sifre) {
        this.id = id;
        this.kullanici_adi = kullanici_adi;
        this.kullanici_sifre = kullanici_sifre;
    }

    public Kullanici() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getKullanici_sifre() {
        return kullanici_sifre;
    }

    public void setKullanici_sifre(String kullanici_sifre) {
        this.kullanici_sifre = kullanici_sifre;
    }
}
