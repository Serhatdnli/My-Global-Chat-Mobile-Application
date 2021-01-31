package com.example.myglobalchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class kayitol extends AppCompatActivity {

    private FirebaseAuth Auth;
    private TextInputEditText kayitusername, kayitpassword;
    private Button buttonKayit;
    private TextView zatenhesabimvar;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);

        kayitusername = findViewById(R.id.kayitusername);
        kayitpassword = findViewById(R.id.kayitpassword);
        buttonKayit = findViewById(R.id.buttonKayit);
        zatenhesabimvar = findViewById(R.id.zatenhesabimvar);
        Auth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();


        buttonKayit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hesapolustur();

               /* FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                            myRef.push().setValue(k1);
                            Toast.makeText(getApplicationContext(), "Hesap Başarıyla Oluşturuldu.", Toast.LENGTH_SHORT).show();
                            Intent gecis2 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(gecis2);
                            finish();


                            e = 2;

                        }
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            String key = d.getKey();
                            k1.setId(key);
                            Map<String, Object> kullanicimap = new HashMap<>();
                            kullanicimap.put("kullanici_id", key);
                            myRef3.child(key).updateChildren(kullanicimap);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            }
        });

        zatenhesabimvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geri = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(geri);
                finish();
            }
        });


    }


    private void hesapolustur() {

        String kullaniciadi = kayitusername.getText().toString();
        String kullanicisifre = kayitpassword.getText().toString();
        if (TextUtils.isEmpty(kullaniciadi)) {
            Toast.makeText(getApplicationContext(), "Kullanıcı Adı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(kullanicisifre)) {
            Toast.makeText(getApplicationContext(), "Şifre Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
        } else {
            Auth.createUserWithEmailAndPassword(kullaniciadi, kullanicisifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String kullaniciID = Auth.getCurrentUser().getUid();
                                RootRef.child("kullanicilar").child(kullaniciID).setValue("");
                                Toast.makeText(getApplicationContext(), "Hesap Başarıyla Oluşturuldu", Toast.LENGTH_SHORT).show();
                                Intent gecis2 = new Intent(getApplicationContext(), chatsayfa.class);
                                startActivity(gecis2);
                                finish();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(getApplicationContext(), "Hata! " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }


}
