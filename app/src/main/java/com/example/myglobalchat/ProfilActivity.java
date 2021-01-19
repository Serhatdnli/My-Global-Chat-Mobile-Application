package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {
    private String ziyaretedilenkullanici,simdikidurum,simdikikullaniciID;
    private CircleImageView ziyaretedilenkullaniciprofil;
    private TextView ziyaretedilenkullaniciprofiladi,ziyaretedilenkullaniciprofildurum;
    private Button mesajyollamaistegibutonu,mesajreddetbutonu;
    private DatabaseReference kullaniciRef;
    private FirebaseAuth gAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ziyaretedilenkullanici = getIntent().getStringExtra("ziyaretedilenkullanici");
        ziyaretedilenkullaniciprofil = findViewById(R.id.ziyaretedilenkullaniciprofil);
        ziyaretedilenkullaniciprofildurum = findViewById(R.id.ziyaretedilenkullaniciprofildurum);
        ziyaretedilenkullaniciprofiladi = findViewById(R.id.ziyaretedilenkullaniciprofiladi);
        mesajyollamaistegibutonu = findViewById(R.id.mesajyollamaistegibutonu);
        mesajreddetbutonu = findViewById(R.id.mesajreddetbutonu);
        kullaniciRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        simdikidurum="new";
        gAuth = FirebaseAuth.getInstance();
        simdikikullaniciID = gAuth.getUid();

        kullanicibilgisigetir();


    }

    private void kullanicibilgisigetir() {

        kullaniciRef.child(ziyaretedilenkullanici).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && (snapshot.hasChild("image")))
                {
                    String image = snapshot.child("image").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String durum = snapshot.child("durum").getValue().toString();
                    Picasso.get().load(image).placeholder(R.drawable.profile_image).into(ziyaretedilenkullaniciprofil);
                    ziyaretedilenkullaniciprofiladi.setText(name);
                    ziyaretedilenkullaniciprofildurum.setText(durum);

                    mesajistegiyonet();

                }
                else
                {
                    String name = snapshot.child("name").getValue().toString();
                    String durum = snapshot.child("durum").getValue().toString();
                    ziyaretedilenkullaniciprofiladi.setText(name);
                    ziyaretedilenkullaniciprofildurum.setText(durum);
                    mesajistegiyonet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void mesajistegiyonet() {
        if (!simdikikullaniciID.equals(ziyaretedilenkullanici))
        {

        }
        else
        {
            
        }


    }
}

