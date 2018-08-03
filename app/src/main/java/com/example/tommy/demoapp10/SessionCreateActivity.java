package com.example.tommy.demoapp10;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionCreateActivity extends AppCompatActivity {
    private String SESSION_NAME;
    private String USER_NAME = "Test_Seller";
    private String USER_EMAIL;

    private EditText SessionNameEdit;
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

        Intent intent = new Intent();
        USER_EMAIL = intent.getStringExtra("user_email");

        // Reference widget ID
        SessionNameEdit = (EditText)findViewById(R.id.session_name);

        startButton = (Button)findViewById(R.id.start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionNameEdit.getText().toString().equals("")) {
                    Toast.makeText(SessionCreateActivity.this, "기입이 완료되지 않았습니다.", Toast.LENGTH_SHORT);
                    return;
                }

                SESSION_NAME = SessionNameEdit.getText().toString();

                sessionManagementService.createSession(SESSION_NAME);

                /* TODO: Wait Until onSessionCreated() or onSessionCreateFailed() ? */

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
