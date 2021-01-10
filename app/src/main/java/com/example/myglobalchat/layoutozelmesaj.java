package com.example.myglobalchat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class layoutozelmesaj extends Fragment {
    private ImageButton geritusu;
    private ImageView profilresim, gonderbtn;
    private TextView kullaniciadiozelmesaj;
    private EditText mesajgirdialani;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ozelmesaj, container, false);

        geritusu =view.findViewById(R.id.geritusu);
        profilresim =view.findViewById(R.id.profilresim);
        gonderbtn = view.findViewById(R.id.gonderbtn);
        mesajgirdialani = view.findViewById(R.id.mesajgirialani);
        kullaniciadiozelmesaj = view.findViewById(R.id.kullaniciadiozelmesaj);




        if (getArguments()!= null) {
            String kalahari3 = this.getArguments().getString("userId");

        }


        return view;


    }


}
