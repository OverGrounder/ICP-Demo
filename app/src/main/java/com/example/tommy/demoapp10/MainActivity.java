package com.example.tommy.demoapp10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        // Set User info
        String userEmail = intent.getStringExtra("user_email");
        String userPassword = intent.getStringExtra("user_password");
        boolean isSeller = intent.getBooleanExtra("isSeller", false);

        if (isSeller) Log.d("ICP_Main", "is Seller!");
        else Log.d("ICP_Main", "is not Seller!");

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }

}
