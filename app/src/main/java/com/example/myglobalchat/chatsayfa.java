package com.example.myglobalchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatsayfa extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView2;
    private Fragment tempFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsayfa);



        bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentgosterici, new layoutmesaj()).commit();


        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_profil) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, new layoutprofil()).commit();
                }
                if (item.getItemId() == R.id.nav_message) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, new layoutmesaj()).commit();
                }
                if (item.getItemId() == R.id.nav_ayarlar) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, new layoutayarlar()).commit();
                }
                if (item.getItemId() == R.id.nav_ozelmessage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, new layoutozelmesaj()).commit();
                }

                return true;
            }
        });

        String gelenveri = getIntent().getStringExtra("kullaniciadigonder");

    }
}