package com.example.tommy.demoapp10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomerMainFragment extends Fragment {
    private String userEmail;
    private String userPassword;

    // UI referencers
    private Button playButton;
    private Button shoppingBasketBtn;
    private Button infoButton;
    private Button homePageBtn;

    public CustomerMainFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_customer_main, container, false);

        // UI reference
        playButton = view.findViewById(R.id.cutomer_main_play_btn);
        shoppingBasketBtn = view.findViewById(R.id.customer_main_sb);
        infoButton = view.findViewById(R.id.customer_main_info_btn);
        homePageBtn = view.findViewById(R.id.customer_main_hp_btn);

        // Get argument
        if (getArguments()!=null) {
            userEmail = getArguments().getString("user_email");
            userPassword = getArguments().getString("user_password");
        }

        // OnClickListener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start FindSessionActivity
                Intent fsIntent = new Intent(getActivity(), FindSessionActivity.class);
                fsIntent.putExtra("user_email", userEmail);
                startActivity(fsIntent);
            }
        });

        return view;
    }
}
