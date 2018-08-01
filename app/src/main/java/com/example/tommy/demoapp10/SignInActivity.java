package com.example.tommy.demoapp10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] SELLER_DUMMY_CREDENTIALS = new String[]{
            "tommy9773@naver.com:2016gkrqjs",
            "gkadnflh@gmail.com:qlalfqjsgh1"
    };

    private static final String[] CUSTOMER_DUMMY_CREDENTIALS = new String[]{
            "tommy9773@korea.ac.kr:2016gkrqjs",
            "gkadnflh@naver.com:qlalfqjsgh1"
    };

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Set up the Sign in form
        mEmailView = (EditText)findViewById(R.id.sign_in_email);
        mPasswordView = (EditText)findViewById(R.id.sign_in_password);

        Button mEmailSignInButton = (Button)findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });
    }

    private void attemptSignIn() {

        // User info
        String user_email = mEmailView.getText().toString();
        String user_password = mPasswordView.getText().toString();
        boolean isSeller = false; // default

        boolean cancel = false;
        boolean allowSignIn = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(user_password) && !isPasswordValid(user_password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user_email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(user_email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Check one is Seller
            for (String credential : SELLER_DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(user_email)) {
                    // Account exists, return true if the password matches.
                    if (pieces[1].equals(user_password)) {
                        // Seller
                        isSeller = true;
                        allowSignIn = true;
                    }
                }
            }

            // Check one is Customer
            for (String credential : CUSTOMER_DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if(pieces[0].equals(user_email)){
                    allowSignIn = pieces[1].equals(user_password);
                }
            }

            if (allowSignIn){
                // Sign in is success
                Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                mainIntent.putExtra("email", user_email);
                mainIntent.putExtra("password", user_password);
                mainIntent.putExtra("isSeller", isSeller);
                startActivity(mainIntent);
                Toast.makeText(SignInActivity.this, "Your Sign in Successfully",Toast.LENGTH_SHORT).show();

            }else{
                // Sign in is fail
                Toast.makeText(SignInActivity.this, "Your Email or Password is wrong!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
