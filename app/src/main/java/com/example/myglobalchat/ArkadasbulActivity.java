package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArkadasbulActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference kullaniciRef,kullaniciRef2;
    private String simdikikullaniciid;
    private FirebaseAuth gAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadasbul);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kullaniciRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        gAuth = FirebaseAuth.getInstance();
        simdikikullaniciid=gAuth.getUid();
        kullaniciRef2 = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(simdikikullaniciid);

        kullaniciRef2.child("kontrol").push().setValue("");



    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<kullanicilar> options = new FirebaseRecyclerOptions.Builder<kullanicilar>()
                .setQuery(kullaniciRef, kullanicilar.class)
                .build();

        FirebaseRecyclerAdapter<kullanicilar,ArkadasBulGoster > adapter
                = new FirebaseRecyclerAdapter<kullanicilar, ArkadasBulGoster>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ArkadasBulGoster holder, int position, @NonNull kullanicilar model) {
                holder.kullaniciprofiladi.setText(model.getName());
                holder.kullanicidurumyazisi.setText(model.getDurum());
                Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.kullanicilarprofilresim);



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gecis = new Intent(getApplicationContext(),OzelMesaj.class);
                        gecis.putExtra("kime",model.getName());
                        startActivity(gecis);


                    }
                });

            }

            @NonNull
            @Override
            public ArkadasBulGoster onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kullanici_goster_layout,parent,false);
                ArkadasBulGoster goster = new ArkadasBulGoster(view);
                return  goster;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ArkadasBulGoster extends RecyclerView.ViewHolder
    {

        TextView kullaniciprofiladi,kullanicidurumyazisi;
        CircleImageView kullanicilarprofilresim;


        public ArkadasBulGoster(@NonNull View itemView) {
            super(itemView);

            kullaniciprofiladi = itemView.findViewById(R.id.kullaniciprofiladi);
            kullanicidurumyazisi = itemView.findViewById(R.id.kullanicidurumyazisi);
            kullanicilarprofilresim = itemView.findViewById(R.id.kullanicilarprofilresim);
        }
    }
}