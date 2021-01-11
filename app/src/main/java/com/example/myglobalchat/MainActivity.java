package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private Button buttongiris;
    private TextInputEditText kullaniciadi, sifre;
    private TextView textkayit;
    private FirebaseUser currentuser;
    private FirebaseAuth gAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttongiris = findViewById(R.id.buttongiris);
        kullaniciadi = findViewById(R.id.kullaniciadi);
        sifre = findViewById(R.id.sifre);
        textkayit = findViewById(R.id.textkayit);
        gAuth = FirebaseAuth.getInstance();
        currentuser = gAuth.getCurrentUser();


        buttongiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisyap();


/*              FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database2.getReference("kullanici");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int c = 0, sayac = 0, dongu = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Kullanici kullanici = d.getValue(Kullanici.class);
                            int count = (int) dataSnapshot.getChildrenCount();

                            for (int i = 0; i <= count; i++) {
                                if (kullanici.getKullanici_adi().equals(kullaniciadi.getText().toString()) && kullanici.getKullanici_sifre().equals(sifre.getText().toString())) {
                                    Intent gecis = new Intent(MainActivity.this, chatsayfa.class);
                                    gecis.putExtra("userId",kullaniciadi.getText().toString());
                                    startActivity(gecis);
                                    finish();
                                    c = 1;
                                    sayac = i;
                                    dongu = count;
                                    break;
                                }
                            }


                        }
                        if (c == 0 && sayac == dongu) {
                            Toast.makeText(getApplicationContext(), "Kullanıcı Adınızı ve Şifrenizi Kontrol Ediniz", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });*/
            }
        });
        textkayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayidagecis = new Intent(MainActivity.this, kayitol.class);
                startActivity(kayidagecis);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser != null)
        {
            Intent gecis = new Intent(MainActivity.this, chatsayfa.class);
            startActivity(gecis);
            finish();

        }
    }

    private void girisyap() {

        String kullaniciadi2 = kullaniciadi.getText().toString();
        String kullanicisifre = sifre.getText().toString();
        if (TextUtils.isEmpty(kullaniciadi2))
        {
            Toast.makeText(getApplicationContext(),"Kullanıcı Adı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(kullanicisifre))
        {
            Toast.makeText(getApplicationContext(),"Şifre Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
        }
        else
            {
                gAuth.signInWithEmailAndPassword(kullaniciadi2,kullanicisifre)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Intent gecis = new Intent(MainActivity.this, chatsayfa.class);
                                    gecis.putExtra("userId",kullaniciadi.getText().toString());
                                    startActivity(gecis);
                                    finish();
                                }
                                else
                                    {
                                        String error = task.getException().toString();
                                        Toast.makeText(getApplicationContext(),"Hata! " + error, Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });
            }
    }




}