package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OzelMesaj extends AppCompatActivity {
    private TextView chatisimi,chattextgoster2;
    private ScrollView scrollView2;
    private EditText chatmesajgiris;
    private ImageButton mesajgondertusu2;
    private String currentchatismi, currentkullaniciadi, currentkullanicid, simdikitarih, simdikisaat;
    private DatabaseReference kullaniciRef, gidecekkullaniciismiRef, chatmesajkeyRef;
    private FirebaseAuth gAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozel_mesaj);
        chatisimi  = findViewById(R.id.chatisimi);
        gAuth = FirebaseAuth.getInstance();
        chattextgoster2  = findViewById(R.id.chattextgoster2);
        scrollView2 = findViewById(R.id.scrollView2);
        currentkullanicid = gAuth.getCurrentUser().getUid();
        currentchatismi = getIntent().getStringExtra("kime");
        chatmesajgiris = findViewById(R.id.chatmesajgiris);
        mesajgondertusu2  = findViewById(R.id.mesajgondertusu2);
        kullaniciRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        gidecekkullaniciismiRef = FirebaseDatabase.getInstance().getReference().child("privatechat");

        kulanicibilgisial();



        mesajgondertusu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesajlarıveritabaninakaydet();
                chatmesajgiris.setText("");
                scrollView2.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });



    }


        private void mesajlarıveritabaninakaydet() {

        String mesaj = chatmesajgiris.getText().toString();
        String mesajkey = gidecekkullaniciismiRef.push().getKey();

        if (TextUtils.isEmpty(mesaj)) {
            Toast.makeText(getApplicationContext(),"Lütfen Bir Mesaj Giriniz",Toast.LENGTH_SHORT).show();

        } else {
            Calendar tarihcek = Calendar.getInstance();
            SimpleDateFormat simdikizamanformat = new SimpleDateFormat("MMM dd, yyyy");
            simdikitarih = simdikizamanformat.format(tarihcek.getTime());


            Calendar saatcek = Calendar.getInstance();
            SimpleDateFormat simdikisaatformat = new SimpleDateFormat("hh:mm a");
            simdikisaat = simdikisaatformat.format(saatcek.getTime());

            HashMap<String, Object> grupmesajkey = new HashMap<>();
            gidecekkullaniciismiRef.updateChildren(grupmesajkey);
            chatmesajkeyRef = gidecekkullaniciismiRef.child(mesajkey);
            HashMap<String, Object> mesajbilgimap = new HashMap<>();
            mesajbilgimap.put("gönderici", currentkullaniciadi);
            mesajbilgimap.put("message", mesaj);
            mesajbilgimap.put("alici", currentchatismi);
            mesajbilgimap.put("date", simdikitarih);
            mesajbilgimap.put("time", simdikisaat);
            chatmesajkeyRef.updateChildren(mesajbilgimap);


        }


    }


    private void kulanicibilgisial() {
        kullaniciRef.child(currentkullanicid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentkullaniciadi = dataSnapshot.child("name").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}