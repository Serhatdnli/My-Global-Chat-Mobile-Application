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
        String gelenveri = getIntent().getStringExtra("userId");



        bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentgosterici, new layoutmesaj()).commit();


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
                if (item.getItemId() == R.id.nav_message) {

                    tempFragment = new layoutmesaj();
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
                if (item.getItemId() == R.id.nav_ozelmessage) {

                    tempFragment = new layoutozelmesaj();
                    Bundle kuladidata = new Bundle();//create bundle instance

                    kuladidata.putString("userId", gelenveri);

                    tempFragment.setArguments(kuladidata);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentgosterici, tempFragment).commit();
                }

                return true;
            }
        });



    }
}