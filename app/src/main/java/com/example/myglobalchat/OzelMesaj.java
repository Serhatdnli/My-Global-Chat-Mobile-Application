package com.example.myglobalchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class OzelMesaj extends AppCompatActivity {
    private TextView chatisimi,chattextgoster2;
    private ScrollView scrollView2;
    private EditText chatmesajgiris;
    private ImageButton mesajgondertusu2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozel_mesaj);
        chatisimi  = findViewById(R.id.chatisimi);
        chattextgoster2  = findViewById(R.id.chattextgoster2);
        scrollView2 = findViewById(R.id.scrollView2);
        chatmesajgiris = findViewById(R.id.chatmesajgiris);
        mesajgondertusu2  = findViewById(R.id.mesajgondertusu2);
    }
}