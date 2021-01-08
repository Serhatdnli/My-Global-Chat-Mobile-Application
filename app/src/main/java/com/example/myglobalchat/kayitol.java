package com.example.myglobalchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class kayitol extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);

        Button buttonKayit = findViewById(R.id.buttonKayit);
        TextInputEditText kayitusername = findViewById(R.id.kayitusername);
        TextInputEditText kayitpassword = findViewById(R.id.kayitpassword);

        buttonKayit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("kullanici");
                DatabaseReference myRef3 = database.getReference("kullanici");


                myRef.addValueEventListener(new ValueEventListener() {
                    int e = 0, sayac = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Kullanici k1 = new Kullanici();


                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            Kullanici kullanici = d.getValue(Kullanici.class);
                            if (kullanici.getKullanici_adi().equals(kayitusername.getText().toString()) && e != 2) {
                                e = 1;
                                sayac++;
                                if (sayac == 1) {
                                    Toast.makeText(getApplicationContext(), "Kullanıcı Adı Kullanılıyor.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }

                        if (e == 0 && sayac == 0) { //kullanıcı adı daha önce kullanılmadıysa ve yukarıdaki ife hiç girmediyse buna girer.
                            k1.setKullanici_adi(kayitusername.getText().toString());
                            k1.setKullanici_sifre(kayitpassword.getText().toString());
                            myRef3.push().setValue(k1);
                            Toast.makeText(getApplicationContext(), "Hesap Başarıyla Oluşturuldu.", Toast.LENGTH_SHORT).show();
                            Intent gecis2 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(gecis2);
                            finish();
                            for (DataSnapshot c : dataSnapshot.getChildren()) {
                                String key = c.getKey();
                                k1.setId(key);
                                Map<String, Object> kullanicimap = new HashMap<>();
                                kullanicimap.put("kullanici_id", key);
                                myRef.child(key).updateChildren(kullanicimap);

                            }

                            e = 2;

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
