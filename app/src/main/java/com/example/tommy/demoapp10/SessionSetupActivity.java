package com.example.tommy.demoapp10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionSetupActivity extends AppCompatActivity {
    private int PORT_NUM;
    private String CHAT_NAME;
    private String USER_NAME;
    private String USER_EMAIL;
    private String STREAM_NAME;
    private String HOST_ADDRESS;
    private String APPLICATION_NAME;

    private EditText portNumEdit;
    private EditText chatNameEdit;
    private EditText userNameEdit;
    private EditText streamNameEdit;
    private EditText hostAddressEdit;
    private EditText applicationNameEdit;
    private Button startButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_setup);

        Intent intent = new Intent();
        USER_EMAIL = intent.getStringExtra("user_email");

        // Reference widget ID
        portNumEdit = (EditText)findViewById(R.id.port_num);
        chatNameEdit = (EditText)findViewById(R.id.chat_name);
        userNameEdit = (EditText)findViewById(R.id.seller_name);
        streamNameEdit = (EditText)findViewById(R.id.stream_name);
        hostAddressEdit = (EditText)findViewById(R.id.host_address);
        applicationNameEdit = (EditText)findViewById(R.id.application_name);
        startButton = (Button)findViewById(R.id.start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (portNumEdit.getText().toString().equals("")
                        || chatNameEdit.getText().toString().equals("")
                        || userNameEdit.getText().toString().equals("")
                        || streamNameEdit.getText().toString().equals("")
                        || hostAddressEdit.getText().toString().equals("")
                        || applicationNameEdit.getText().toString().equals("")) {

                    Toast.makeText(SessionSetupActivity.this, "기입이 완료되지 않았습니다.", Toast.LENGTH_SHORT);
                    return;
                }

                PORT_NUM = Integer.parseInt(portNumEdit.getText().toString());
                CHAT_NAME = chatNameEdit.getText().toString();
                USER_NAME = userNameEdit.getText().toString();
                STREAM_NAME = streamNameEdit.getText().toString();
                HOST_ADDRESS = hostAddressEdit.getText().toString();
                APPLICATION_NAME = applicationNameEdit.getText().toString();

                ChatFragment cf = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("chat_name", CHAT_NAME);
                bundle.putString("user_name", USER_NAME);
                bundle.putString("user_email", USER_EMAIL);
                cf.setArguments(bundle);

                // DB session 목록에 session 추가
                Session session = new Session(PORT_NUM, CHAT_NAME, STREAM_NAME, HOST_ADDRESS, APPLICATION_NAME);
                databaseReference.child("session").child(session.getSTREAM_NAME()).push().setValue(session);

                /*
                // 기입이 완료된 후, Start 버튼을 누르면 SellerSessionActivity 시작
                Intent sisIntent = new Intent(SessionSetupActivity.this, SellerInSessionActivity.class);
                sisIntent.putExtra("port_num", PORT_NUM);
                sisIntent.putExtra("stream_name", STREAM_NAME);
                sisIntent.putExtra("host_address", HOST_ADDRESS);
                sisIntent.putExtra("application_name", APPLICATION_NAME);
                startActivity(sisIntent);
                */
            }
        });
    }
}
