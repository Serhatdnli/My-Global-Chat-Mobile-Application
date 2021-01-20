package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class OzelMesaj extends AppCompatActivity {
    private TextView chatisimi, chattextgoster2;
    private ScrollView scrollView2;
    private EditText chatmesajgiris;
    private ImageButton mesajgondertusu2;
    private String currentchatismi, currentkullaniciadi, currentkullanicid, simdikitarih, simdikisaat;
    private DatabaseReference kullaniciRef, gidecekkullaniciismiRef, chatmesajkeyRef,chatmesajkeyRef2,chatmesajkeyRef3;
    private FirebaseAuth gAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozel_mesaj);
        chatisimi = findViewById(R.id.chatisimi);
        gAuth = FirebaseAuth.getInstance();
        chattextgoster2 = findViewById(R.id.chattextgoster2);
        scrollView2 = findViewById(R.id.scrollView2);
        currentkullanicid = gAuth.getCurrentUser().getUid();
        currentchatismi = getIntent().getStringExtra("kime");
        chatisimi.setText(currentchatismi);
        chatmesajgiris = findViewById(R.id.chatmesajgiris);
        mesajgondertusu2 = findViewById(R.id.mesajgondertusu2);
        kullaniciRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(currentkullanicid);
        chatmesajkeyRef = FirebaseDatabase.getInstance().getReference().child("privatechat").child(currentchatismi);
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


    private void kulanicibilgisial() {
        kullaniciRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentkullaniciadi = dataSnapshot.child("name").getValue().toString();
                    Toast.makeText(getApplicationContext(),currentkullaniciadi,Toast.LENGTH_SHORT).show();
                    chatmesajkeyRef3 =FirebaseDatabase.getInstance().getReference().child("privatechat").child(currentkullaniciadi);
                    chatmesajkeyRef3.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                mesajgoster(dataSnapshot);
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                mesajgoster(dataSnapshot);
                            }

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();






    }



    private void mesajlarıveritabaninakaydet() {

        String mesaj = chatmesajgiris.getText().toString();
        String mesajkey = chatmesajkeyRef.push().getKey();

        if (TextUtils.isEmpty(mesaj)) {
            Toast.makeText(getApplicationContext(), "Lütfen Bir Mesaj Giriniz", Toast.LENGTH_SHORT).show();

        } else {
            Calendar tarihcek = Calendar.getInstance();
            SimpleDateFormat simdikizamanformat = new SimpleDateFormat("MMM dd, yyyy");
            simdikitarih = simdikizamanformat.format(tarihcek.getTime());


            Calendar saatcek = Calendar.getInstance();
            SimpleDateFormat simdikisaatformat = new SimpleDateFormat("hh:mm a");
            simdikisaat = simdikisaatformat.format(saatcek.getTime());

            HashMap<String, Object> chatmesajkey = new HashMap<>();
            chatmesajkeyRef.updateChildren(chatmesajkey);
            gidecekkullaniciismiRef = chatmesajkeyRef.child(mesajkey);
            HashMap<String, Object> mesajbilgimap = new HashMap<>();
            mesajbilgimap.put("alici", currentchatismi);
            mesajbilgimap.put("date", simdikitarih);
            mesajbilgimap.put("gonderici", currentkullaniciadi);
            mesajbilgimap.put("message", mesaj);
            mesajbilgimap.put("time", simdikisaat);
            gidecekkullaniciismiRef.updateChildren(mesajbilgimap);

            chatmesajkeyRef2= FirebaseDatabase.getInstance().getReference().child("privatechat").child(currentkullaniciadi);
            chatmesajkeyRef2.updateChildren(chatmesajkey);
            gidecekkullaniciismiRef = chatmesajkeyRef2.child(mesajkey);
            HashMap<String, Object> mesajbilgimap2 = new HashMap<>();
            mesajbilgimap2.put("alici", currentchatismi);
            mesajbilgimap2.put("date", simdikitarih);
            mesajbilgimap2.put("gonderici", currentkullaniciadi);
            mesajbilgimap2.put("message", mesaj);
            mesajbilgimap2.put("time", simdikisaat);
            gidecekkullaniciismiRef.updateChildren(mesajbilgimap2);




        }


    }





    private void mesajgoster(DataSnapshot dataSnapshot) {
        Iterator iterator2 = dataSnapshot.getChildren().iterator();
        while (iterator2.hasNext()) {
            String mesajalici = ((DataSnapshot) iterator2.next()).getValue().toString();
            String mesajdate = ((DataSnapshot) iterator2.next()).getValue().toString();
            String mesajgonderici = ((DataSnapshot) iterator2.next()).getValue().toString();
            String mesajmesaj = ((DataSnapshot) iterator2.next()).getValue().toString();
            String mesajtime2 = ((DataSnapshot) iterator2.next()).getValue().toString();

           if (mesajalici.equals(currentkullaniciadi) && mesajgonderici.equals(currentchatismi)) {

                chattextgoster2.append(""+mesajgonderici + " :\n" + mesajmesaj + "\n" + mesajtime2 + "     " + mesajdate + "\n\n");
                scrollView2.fullScroll(ScrollView.FOCUS_DOWN);

            }if (mesajalici.equals(currentchatismi) && mesajgonderici.equals(currentkullaniciadi))
            {
                chattextgoster2.append(""+mesajgonderici + " :\n" + mesajmesaj + "\n" + mesajtime2 + "     " + mesajdate + "\n\n");
                scrollView2.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }


    }
}