package com.example.tommy.demoapp10;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SessionCreateActivity extends AppCompatActivity {
    private String SESSION_NAME;
    private String USER_NAME = "Test_Seller";
    private String USER_EMAIL;
    private boolean creating = false;

    private EditText sessionNameEdit;
    private ProgressBar spinner;
    private Button startButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SessionManagementService sessionManagementService;
    private boolean sessionServiceBound = false;
    private ServiceConnection myConnection;

    public ServiceConnection getMyConnection() {
        return myConnection;
    }

    public SessionCreateActivity() {
    }
    private ServiceConnection sessionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d("ICP_SCA", "SessionService Connected");
            SessionManagementService.SessionManagementBinder binder = (SessionManagementService.SessionManagementBinder) service;
            sessionManagementService = binder.getService();
            sessionServiceBound = true;

            mAdapter = new PendingSessionViewAdapter(sessionManagementService.getPendingSessions(), SessionCreateActivity.this);
            mRecyclerView.setAdapter(mAdapter);

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (creating) return;
                    spinner.setVisibility(View.VISIBLE);
                    if (sessionNameEdit.getText().toString().equals("")) {
                        Toast.makeText(SessionCreateActivity.this, "세션 이름을 입력해주세요", Toast.LENGTH_SHORT);
                        return;
                    }
                    creating = true;

                    SESSION_NAME = sessionNameEdit.getText().toString();

                    sessionManagementService.createSession(SESSION_NAME);
                }
            });

            sessionManagementService.setSessionListener(new SessionManagementService.SessionListener() {

                @Override
                public void onSessionCreated(Session createdSession) {
                    Intent intent = new Intent(SessionCreateActivity.this, SessionSetupActivity.class);
                    intent.putExtra("session", createdSession);
                    intent.putExtra("chat_name", SESSION_NAME);
                    intent.putExtra("user_name", USER_NAME);
                    intent.putExtra("user_email", USER_EMAIL);
                    spinner.setVisibility(View.GONE);
                    startActivity(intent);
                }
                @Override
                public void onBindFinished() {
                }
                @Override
                public void onSessionConnected() {
                }
                @Override
                public void onBroadcastAccepted() {
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(SessionCreateActivity.this, SessionManagementService.class);
        bindService(intent, sessionServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("ICP_SCA", "Trying to bind Service");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sessionServiceBound) {
            unbindService(sessionServiceConnection);
            sessionServiceBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_create);
        spinner = findViewById(R.id.create_session_progressBar);
        // Reference widget ID
        sessionNameEdit = findViewById(R.id.create_session_name);
        mRecyclerView = findViewById(R.id.create_session_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        startButton = findViewById(R.id.create_session_btn);
        spinner.setVisibility(View.GONE);

        Intent intent = new Intent();
        USER_EMAIL = intent.getStringExtra("user_email");

    }
}