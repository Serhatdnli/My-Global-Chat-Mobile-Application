package com.example.myglobalchat;

import androidx.annotation.Nullable;

public class kullanicilar {

    public String name, durum, image, uid;

    public kullanicilar() {

    }

    public kullanicilar(String name, String durum, @Nullable String image, String uid) {
        this.name = name;
        this.durum = durum;
        this.image = image;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
