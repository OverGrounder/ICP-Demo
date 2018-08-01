package com.example.tommy.demoapp10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        // Set User info
        String userEmail = intent.getStringExtra("user_email");
        String userPassword = intent.getStringExtra("user_password");
        boolean isSeller = intent.getBooleanExtra("isSeller", false);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("user_email", userEmail);
        bundle.putString("user_password", userPassword);

        if (isSeller) {
            // User is Seller
            SellerMainFragment smf = new SellerMainFragment();
            smf.setArguments(bundle);
            ft.replace(R.id.container_main, smf);
            ft.commit();
        }
        else {
            // User is Customer
            CustomerMainFragment cmf = new CustomerMainFragment();
            cmf.setArguments(bundle);
            ft.replace(R.id.container_main, cmf);
            ft.commit();
        }

    }
}
