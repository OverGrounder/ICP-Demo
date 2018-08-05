package com.example.tommy.demoapp10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// TODO: Add promoting product selection
// TODO: Connect with SessionManagementService for Broadcast Control

public class SessionSetupActivity extends AppCompatActivity {
    private Button bcStart_Btn;
    private Session session;
    private SessionManagementService sessionManagementService;
    View.OnClickListener bcStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sessionManagementService.requestBroadcast();
        }
    };
    private boolean sessionServiceBound = false;
    private ServiceConnection myConnection;
    private ServiceConnection sessionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SessionManagementService.SessionManagementBinder binder = (SessionManagementService.SessionManagementBinder) service;
            sessionManagementService = binder.getService();
            sessionServiceBound = true;
            bcStart_Btn.setOnClickListener(bcStartListener);

            sessionManagementService.setSessionListener(new SessionManagementService.SessionListener() {
                @Override
                public void onSessionCreated(Session createdSession) {
                }

                @Override
                public void onSessionConnected() {
                }

                @Override
                public void onBroadcastAccepted() {
                    Intent broadcastIntent = new Intent(SessionSetupActivity.this, BroadcastActivity.class);
                    broadcastIntent.putExtra("session", session);
                    startActivity(broadcastIntent);
                }

                @Override
                public void onBroadcastDenied() {

                }

                @Override
                public void onSessionDisconnected() {

                }

                @Override
                public void onSessionDestroyed() {

                }

                @Override
                public void onSessionCreateFailed(int result_code, Session session) {

                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            sessionServiceBound = false;
        }
    };

    public ServiceConnection getMyConnection() {
        return myConnection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_setup);

        Intent intent = getIntent();
        session = intent.getParcelableExtra("session");

        bcStart_Btn = findViewById(R.id.sSetup_bcStart_Btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(SessionSetupActivity.this, SessionManagementService.class);
        bindService(intent, sessionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sessionServiceBound) {
            sessionManagementService.finishBroadcast();
            unbindService(sessionServiceConnection);
            sessionServiceBound = false;
        }
    }
}