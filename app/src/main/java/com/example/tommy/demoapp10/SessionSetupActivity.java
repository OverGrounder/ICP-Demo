package com.example.tommy.demoapp10;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// TODO: Add promoting product selection
// TODO: Connect with SessionManagementService for Broadcast Control

public class SessionSetupActivity extends AppCompatActivity {
    Button bcStart_Btn;
    Session session;
    View.OnClickListener bcStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent broadcastIntent = new Intent(SessionSetupActivity.this, BroadcastActivity.class);
            broadcastIntent.putExtra("session", session);
            startActivity(broadcastIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_setup);

        Intent intent = getIntent();
        session = intent.getParcelableExtra("session");

        bcStart_Btn = findViewById(R.id.sSetup_bcStart_Btn);

        bcStart_Btn.setOnClickListener(bcStartListener);
    }
}