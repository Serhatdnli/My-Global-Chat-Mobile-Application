package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
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

public class grupchat extends AppCompatActivity {

    private ScrollView scrollView;
    private TextView grupchattextgoster, grupisimi;
    private EditText grupmesajgiris;
    private ImageButton mesajgondertusu;
    private String currentgrupismi, currentkullaniciadi, currentkullanicid, simdikitarih, simdikisaat;
    private FirebaseAuth gAuth;
    private DatabaseReference kullaniciRef, grupismiRef, grupmesajkeyRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupchat);
        grupchattextgoster = findViewById(R.id.grupchattextgoster);
        grupmesajgiris = findViewById(R.id.grupmesajgiris);
        mesajgondertusu = findViewById(R.id.mesajgondertusu);
        scrollView = findViewById(R.id.scrollView);
        currentgrupismi = getIntent().getStringExtra("grupismi");
        grupisimi = findViewById(R.id.grupisimi);
        gAuth = FirebaseAuth.getInstance();
        currentkullanicid = gAuth.getCurrentUser().getUid();
        grupisimi.setText(currentgrupismi);
        kullaniciRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        grupismiRef = FirebaseDatabase.getInstance().getReference().child("gruplar").child(currentgrupismi);

        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        kulanicibilgisial();





        grupmesajgiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);



            }
        });
        mesajgondertusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesajlarıveritabaninakaydet();
                grupmesajgiris.setText("");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        grupismiRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
                    mesajgoster(dataSnapshot);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
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



    private void mesajgoster(DataSnapshot dataSnapshot) {
        Iterator iterator2 = dataSnapshot.getChildren().iterator();
        while (iterator2.hasNext())
        {
            String mesajdate = ((DataSnapshot)iterator2.next()).getValue().toString();
            String mesajmessage = ((DataSnapshot)iterator2.next()).getValue().toString();
            String mesajname = ((DataSnapshot)iterator2.next()).getValue().toString();
            String mesajtime= ((DataSnapshot)iterator2.next()).getValue().toString();
            grupchattextgoster.append(mesajname + " :\n" + mesajmessage +"\n" + mesajtime + "     " + mesajdate + "\n\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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


    private void mesajlarıveritabaninakaydet() {

        String mesaj = grupmesajgiris.getText().toString();
        String mesajkey = grupismiRef.push().getKey();

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
            grupismiRef.updateChildren(grupmesajkey);
            grupmesajkeyRef = grupismiRef.child(mesajkey);
            HashMap<String, Object> mesajbilgimap = new HashMap<>();
            mesajbilgimap.put("name", currentkullaniciadi);
            mesajbilgimap.put("message", mesaj);
            mesajbilgimap.put("date", simdikitarih);
            mesajbilgimap.put("time", simdikisaat);
            grupmesajkeyRef.updateChildren(mesajbilgimap);


        }


    }

}