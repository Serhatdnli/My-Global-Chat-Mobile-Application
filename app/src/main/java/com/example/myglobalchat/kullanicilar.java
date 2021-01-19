package com.example.myglobalchat;

public class kullanicilar {

    public String name,durum,image;
    public kullanicilar()
    {

    }

    public kullanicilar(String name, String durum, String image) {
        this.name = name;
        this.durum = durum;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
