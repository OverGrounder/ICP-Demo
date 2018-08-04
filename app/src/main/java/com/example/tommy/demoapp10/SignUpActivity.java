package com.example.tommy.demoapp10;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends Activity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();

    private EditText emailEdit, passwordEdit, nameEdit, nicknameEdit, addressEdit;
    private CheckBox isSellerCheckBox;
    private Button registerButton, cancelButton;
    private FirebaseAuth mAuth;
    private String email, password, name, nickname, address, uid;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        nameEdit = findViewById(R.id.nameEdit);
        nicknameEdit = findViewById(R.id.nicknameEdit);
        addressEdit = findViewById(R.id.addressEdit);
        isSellerCheckBox = findViewById(R.id.sellerCheckBox);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();
                name = nameEdit.getText().toString();
                nickname = nicknameEdit.getText().toString();
                address = addressEdit.getText().toString();

                // TODO: Verify inputs (Email, password, etc.)

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("ICP-SignUp", "createUserWithEmail:Success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    uid = user.getUid();
                                    Toast.makeText(SignUpActivity.this, "회원가입 완료", Toast.LENGTH_LONG).show();
                                    updateUserInfo();
                                    finish();
                                } else {
                                    Log.w("ICP-SignUp", "createUserWithEmail:Failed");
                                    Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void updateUserInfo() {
        userRef = databaseReference.child("users").child(uid);
        userRef.setValue(new User(name, nickname, address, isSellerCheckBox.isChecked()));
    }

}
