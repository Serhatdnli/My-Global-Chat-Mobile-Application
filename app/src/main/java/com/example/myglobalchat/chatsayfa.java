package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chatsayfa extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView2;
    private Fragment tempFragment;
    private FirebaseUser currentuser;
    private FirebaseAuth gAuth;
    private DatabaseReference RoofRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsayfa);
        String gelenveri = getIntent().getStringExtra("userId");
        gAuth = FirebaseAuth.getInstance();
        currentuser = gAuth.getCurrentUser();
        RoofRef = FirebaseDatabase.getInstance().getReference();



        bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentgosterici, new layoutprofil()).commit();


        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_profil) {

                    tempFragment = new layoutprofil();
                    Bundle kuladidata = new Bundle();//create bundle instance
                    kuladidata.putString("userId", gelenveri);

                    tempFragment.setArguments(kuladidata);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, tempFragment).commit();
                }

                if (item.getItemId() == R.id.nav_ayarlar) {

                    tempFragment = new layoutayarlar();
                    Bundle kuladidata = new Bundle();//create bundle instance
                    kuladidata.putString("userId", gelenveri);
                    tempFragment.setArguments(kuladidata);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, tempFragment).commit();
                }


                return true;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser == null)
        {
            Intent gecis = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(gecis);
            finish();
        }
        else
            {
                kullanicidogrula();
            }
    }



    private void kullanicidogrula() {

        String mevcutkullaniciID = gAuth.getCurrentUser().getUid();
        RoofRef.child("kullanicilar").child(mevcutkullaniciID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists())
                {

                }
                else
                {
                    tempFragment = new layoutayarlar();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, tempFragment).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}