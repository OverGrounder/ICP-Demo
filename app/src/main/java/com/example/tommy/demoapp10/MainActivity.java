package com.example.tommy.demoapp10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    public SessionManagementService sessionManagementService;
    private boolean sessionServiceBound = false;

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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SessionManagementService.class);
        bindService(intent, sessionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sessionServiceBound) {
            unbindService(sessionServiceConnection);
            sessionServiceBound = false;
        }
    }

    private ServiceConnection sessionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SessionManagementService.SessionManagementBinder binder = (SessionManagementService.SessionManagementBinder) service;
            sessionManagementService = binder.getService();
            sessionServiceBound = true;

            sessionManagementService.setSessionListener(new SessionManagementService.SessionListener() {

                @Override
                public void onSessionCreated() {

                }

                @Override
                public void onSessionConnected() {

                }

                @Override
                public void onStreamAccepted() {

                }

                @Override
                public void onStreamDenied() {

                }

                @Override
                public void onSessionDisconnected() {

                }

                @Override
                public void onSessionDestroyed() {

                }

                @Override
                public void onSessionCreateFailed() {

                }
            });

        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            sessionServiceBound = false;
        }
    };


}
