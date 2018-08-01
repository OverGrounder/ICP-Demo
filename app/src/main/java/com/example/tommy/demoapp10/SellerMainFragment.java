package com.example.tommy.demoapp10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SellerMainFragment extends Fragment {

    private String userEmail;
    private String userPassword;

    // UI referencers
    private Button playButton;
    private Button broadcastBtn;
    private Button infoButton;
    private Button homePageBtn;

    public SellerMainFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_seller_main, container, false);

        // UI reference
        playButton = view.findViewById(R.id.seller_main_play_btn);
        broadcastBtn = view.findViewById(R.id.seller_main_broadcast_btn);
        infoButton = view.findViewById(R.id.seller_main_info_btn);
        homePageBtn = view.findViewById(R.id.seller_main_hp_btn);

        // Get argument
        if (getArguments()!=null) {
            userEmail = getArguments().getString("user_email");
            userPassword = getArguments().getString("user_password");
        }

        // OnClickListener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        broadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SessionSetupActivity
                Intent ssIntent = new Intent(getActivity(), SessionSetupActivity.class);
                ssIntent.putExtra("user_email", userEmail);
                startActivity(ssIntent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

}
