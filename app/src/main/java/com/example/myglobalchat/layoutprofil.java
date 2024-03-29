package com.example.myglobalchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class layoutprofil extends Fragment {



    private FirebaseAuth gAuth;
    private FirebaseUser currentuser;
    private DatabaseReference RootRef;
    private Toolbar tolbarprofil;
    private ImageButton kisiekle, grupkur;
    private ListView profilliste;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> gruplist = new ArrayList<>();
    private DatabaseReference grupreferans, privatechatRef, privatechatalici,kullaniciRef;
    private String currentkullanicid,currentkullaniciadi;
    private Integer count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profil, container, false);


        gAuth = FirebaseAuth.getInstance();
        currentuser = gAuth.getCurrentUser();
        tolbarprofil = view.findViewById(R.id.tolbarprofil);
        kisiekle = view.findViewById(R.id.kisiekle);
        currentkullanicid = gAuth.getCurrentUser().getUid();
        kullaniciRef=FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(currentkullanicid);
        grupkur = view.findViewById(R.id.grupkur);
        RootRef = FirebaseDatabase.getInstance().getReference();
        //currentkullaniciadi = RootRef.child("kullanicilar").child(currentkullanicid).child("name").toString();
        grupreferans = FirebaseDatabase.getInstance().getReference().child("gruplar");
        privatechatRef = FirebaseDatabase.getInstance().getReference().child("privatechat");

        profilliste = view.findViewById(R.id.profilliste);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, gruplist);
        profilliste.setAdapter(arrayAdapter);
        getirgostergrupr();
        kulanicibilgisial();


        profilliste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentgrupname = parent.getItemAtPosition(position).toString();
                Intent grupchat = new Intent(getContext(), grupchat.class);
                grupchat.putExtra("grupismi", currentgrupname);
                startActivity(grupchat);
            }
        });

        grupkur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grupolusturmenu();
            }
        });




        kisiekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis = new Intent(getContext(), ArkadasbulActivity.class);

                startActivity(gecis);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (currentuser == null)
        {
            Intent gecis = new Intent(getContext(),MainActivity.class);
            startActivity(gecis);

        }

    }

    private void kulanicibilgisial() {
        kullaniciRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    currentkullaniciadi = dataSnapshot.child("name").getValue().toString();

                    privatechatalici =FirebaseDatabase.getInstance().getReference().child("privatechat").child(currentkullaniciadi);
                    privatechatalici.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                //getirgosterprivatechat(dataSnapshot);
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                //getirgosterprivatechat(dataSnapshot);
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


    private void getirgostergrupr() {

        grupreferans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                gruplist.clear();
                gruplist.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


/*    private void getirgosterprivatechat(DataSnapshot dataSnapshot) {

        privatechatRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                gruplist.clear();
                gruplist.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/


    private void grupolusturmenu() {
        AlertDialog.Builder kurucu = new AlertDialog.Builder(getContext(), R.style.AlertDialog);
        kurucu.setTitle("Grup İsmini Girin : ");
        final EditText grupismi = new EditText(getContext());
        grupismi.setHint("Grup İsmi");
        kurucu.setView(grupismi);
        kurucu.setPositiveButton("Oluştur", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String grupadi = grupismi.getText().toString();
                if (TextUtils.isEmpty(grupadi)) {
                    Toast.makeText(getContext(), "Lütfen Grup İsmi Giriniz", Toast.LENGTH_SHORT).show();
                } else {
                    grupolustur(grupadi);


                }
            }
        });
        kurucu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        kurucu.show();
    }

    private void grupolustur(String grupadi) {
        RootRef.child("gruplar").child(grupadi+"(Grup)").setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Grup Başarı İle Oluşturuldu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

