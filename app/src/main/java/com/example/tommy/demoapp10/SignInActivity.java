package com.example.tommy.demoapp10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private String user_email;
    private String user_password;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference(), userRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Set up the Sign in form
        mEmailView = findViewById(R.id.sign_in_email);
        mPasswordView = findViewById(R.id.sign_in_password);

        mAuth.signOut();

        Button mEmailSignUpButton = findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        if (currentUser == null) {
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptSignIn();
                }
            });
        }
        else {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            userRef = databaseReference.child("users").child(firebaseUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);

                    // User info
                    user_email = mEmailView.getText().toString();
                    user_password = mPasswordView.getText().toString();

                    Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                    Boolean isSeller = user.getISSELLER();

                    mainIntent.putExtra("email", user_email);
                    mainIntent.putExtra("password", user_password);
                    mainIntent.putExtra("isSeller", isSeller);

                    if (isSeller) Log.d("ICP_SignIn", "is Seller!");
                    else Log.d("ICP_SignIn", "is not Seller!");

                    startActivity(mainIntent);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // TODO: 토스트나 뭐 아무거로 알려주기
                    finish();
                }
            });
        }
    }

    private void attemptSignIn() {
        // User info
        user_email = mEmailView.getText().toString();
        user_password = mPasswordView.getText().toString();

        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            userRef = databaseReference.child("users").child(firebaseUser.getUid());
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    user = dataSnapshot.getValue(User.class);

                                    Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                                    Boolean isSeller = user.getISSELLER();

                                    mainIntent.putExtra("email", user_email);
                                    mainIntent.putExtra("password", user_password);
                                    mainIntent.putExtra("isSeller", isSeller);

                                    if (isSeller) Log.d("ICP_SignIn", "is Seller!");
                                    else Log.d("ICP_SignIn", "is not Seller!");

                                    startActivity(mainIntent);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // TODO: 토스트나 뭐 아무거로 알려주기
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
