package com.example.myglobalchat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class layoutprofil extends Fragment {

    private TextView profilkullaniciadi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profil,container,false);

        profilkullaniciadi = view.findViewById(R.id.profilkulaniciadi);

        if (getArguments()!= null) {
            String kalahari3 = this.getArguments().getString("userId");
            profilkullaniciadi.setText(kalahari3);
        }

        return view;
    }
}

