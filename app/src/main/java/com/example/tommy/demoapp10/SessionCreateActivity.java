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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionCreateActivity extends AppCompatActivity {
    private String SESSION_NAME;
    private String USER_NAME = "Test_Seller";
    private String USER_EMAIL;
    private boolean creating = false;

    private EditText sessionNameEdit;
    private ProgressBar spinner;
    private Button startButton;

    private SessionManagementService sessionManagementService;
    private boolean sessionServiceBound = false;
    private ServiceConnection myConnection;

    public ServiceConnection getMyConnection() {
        return myConnection;
    }

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();

    public SessionCreateActivity() {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_create);
        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        Intent intent = new Intent();
        USER_EMAIL = intent.getStringExtra("user_email");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SessionManagementService.class);
        bindService(intent, sessionServiceConnection, Context.BIND_AUTO_CREATE);

        // Reference widget ID
        sessionNameEdit = (EditText)findViewById(R.id.session_name);

        startButton = (Button)findViewById(R.id.start);

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

                ChatFragment cf = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("chat_name", SESSION_NAME);
                bundle.putString("user_name", USER_NAME);
                bundle.putString("user_email", USER_EMAIL);
                cf.setArguments(bundle);

                // DB session 목록에 session 추가

                /*
                // 기입이 완료된 후, Start 버튼을 누르면 SellerSessionActivity 시작
                Intent sisIntent = new Intent(SessionCreateActivity.this, SellerInSessionActivity.class);
                sisIntent.putExtra("port_num", PORT_NUM);
                sisIntent.putExtra("stream_name", STREAM_NAME);
                sisIntent.putExtra("host_address", HOST_ADDRESS);
                sisIntent.putExtra("application_name", APPLICATION_NAME);
                startActivity(sisIntent);
                */
            }
        });
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
                public void onSessionCreated(Session createdSession) {
                    Intent intent = new Intent(SessionCreateActivity.this, SessionSetupActivity.class);
                    intent.putExtra("session", createdSession);
                    spinner.setVisibility(View.GONE);
                    startActivity(intent);
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
}
