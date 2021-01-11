package com.example.myglobalchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class layoutprofil extends Fragment {

    private TextView profilkullaniciadi;
    private Button exitbutton;
    private FirebaseAuth gAuth;
    private FirebaseUser currentuser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profil,container,false);

        exitbutton = view.findViewById(R.id.exitbutton);
        gAuth = FirebaseAuth.getInstance();
        currentuser = gAuth.getCurrentUser();

        profilkullaniciadi = view.findViewById(R.id.profilkulaniciadi);

        if (getArguments()!= null) {
            String kalahari3 = this.getArguments().getString("userId");
            profilkullaniciadi.setText(kalahari3);
        }

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gAuth.signOut();
                currentuser = null;
                Intent gecis = new Intent(getContext(), MainActivity.class);
                startActivity(gecis);
                getActivity().finish();

            }
        });

        return view;
    }
}

