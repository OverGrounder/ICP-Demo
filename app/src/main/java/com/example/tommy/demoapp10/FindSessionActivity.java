package com.example.tommy.demoapp10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class FindSessionActivity extends AppCompatActivity {
    private ListView sessionListView;
    private EditText userNameEditText;
    private String user_email;
    private Session mSession;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_session);

        sessionListView = (ListView) findViewById(R.id.session_list);
        userNameEditText = (EditText) findViewById(R.id.customer_name);

        Intent intent = getIntent();
        user_email = intent.getStringExtra("user_email");

        sessionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String user_name;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View vClicked, int index, long id) {
                // Get Session's key
                String sessionName = adapterView.getAdapter().getItem(index).toString();

                // get Session's value
                databaseReference.child("session").child(sessionName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        dataSnapshot=child.next();
                        mSession = dataSnapshot.getValue(Session.class);

                        if (userNameEditText.getText().toString().equals("")) {
                            // User Name을 입력하지 않은 경우
                            user_name = "user" + String.valueOf((int) (Math.random() * 100));
                        } else {
                            user_name = userNameEditText.getText().toString();
                        }

                        Intent cisIntent = new Intent(FindSessionActivity.this, CustomerInSessionActivity.class);
                        cisIntent.putExtra("port_num", mSession.getPORT_NUM());
                        cisIntent.putExtra("host_address", mSession.getHOST_ADDRESS());
                        cisIntent.putExtra("stream_name", mSession.getSTREAM_NAME());
                        cisIntent.putExtra("application_name", mSession.getAPPLICATION_NAME());
                        cisIntent.putExtra("user_name", user_name);
                        cisIntent.putExtra("chat_name", mSession.getCHAT_NAME());
                        cisIntent.putExtra("user_email", user_email);

                        // CustomerInSessionActivity 시작
                        startActivity(cisIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        showSessionList();
    }

    private void showSessionList(){
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        sessionListView.setAdapter(adapter);

        databaseReference.child("session").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String session_name = dataSnapshot.getKey();
                adapter.add(session_name);
                Log.e("LOG:","dataSnapShot.getKey() : " + session_name);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
