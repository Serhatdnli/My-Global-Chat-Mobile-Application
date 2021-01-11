package com.example.myglobalchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class layoutayarlar extends Fragment {
    private Button ayarlari_guncelle;
    private EditText nickname,kullanicidurum;
    private CircleImageView profile_image;
    private String currentuserID;
    private FirebaseAuth gAuth;
    private DatabaseReference RootRef;
    private TextView exittext;
    private FirebaseUser currentuser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_ayarlar, container, false);
        ayarlari_guncelle = view.findViewById(R.id.ayarlari_guncelle);
        nickname = view.findViewById(R.id.nickname);
        kullanicidurum = view.findViewById(R.id.kullanicidurum);
        profile_image = view.findViewById(R.id.profile_image);
        gAuth = FirebaseAuth.getInstance();
        currentuserID = gAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        exittext = view.findViewById(R.id.exittext);
        currentuser = gAuth.getCurrentUser();


        ayarlari_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ayarguncelleme();
            }
        });
        exittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gAuth.signOut();
                currentuser = null;
                Intent gecis = new Intent(getContext(), MainActivity.class);
                startActivity(gecis);
                getActivity().finish();

            }
        });
         kullanicibilgisigetir();
        nickname.setVisibility(View.VISIBLE);






        return view;
    }




    private void ayarguncelleme() {
        String nicknameayarla = nickname.getText().toString();
        String durumayarla = kullanicidurum.getText().toString();
        if (TextUtils.isEmpty(nicknameayarla))
        {
            Toast.makeText(getContext(),"Kullanıcı Adı Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(durumayarla))
        {
            Toast.makeText(getContext(),"Durum Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
        }
        else
            {
                HashMap <String,String> profilmap = new HashMap<>();
                profilmap.put("uid", currentuserID);
                profilmap.put("name", nicknameayarla);
                profilmap.put("durum", durumayarla);
                RootRef.child("kullanicilar").child(currentuserID).setValue(profilmap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getContext(),"Ayarlar Başarıyla Güncellendi",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    {
                                        nickname.setVisibility(View.VISIBLE);
                                        String error = task.getException().toString();
                                        Toast.makeText(getContext(),"Hata " + error, Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });

            }



    }

    private void kullanicibilgisigetir() {
        RootRef.child("kullanicilar").child(currentuserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("resim")))
                        {
                            String nicknamegetir = dataSnapshot.child("name").getValue().toString();
                            String durumgetir = dataSnapshot.child("durum").getValue().toString();
                            String resimgetir = dataSnapshot.child("resim").getValue().toString();
                            nickname.setText(nicknamegetir);
                            kullanicidurum.setText(durumgetir);
                        }
                        else if((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {

                            String nicknamegetir = dataSnapshot.child("name").getValue().toString();
                            String durumgetir = dataSnapshot.child("durum").getValue().toString();
                            nickname.setText(nicknamegetir);
                            kullanicidurum.setText(durumgetir);
                        }
                        else
                            {
                                Toast.makeText(getContext(),"Lütfen Profil Bilgilerini Ayarla & Güncelle",Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
