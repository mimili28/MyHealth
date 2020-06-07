package com.mariailieva.myhealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mariailieva.myhealth.FirebaseManager;
import com.mariailieva.myhealth.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView register;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        firebaseManager = new FirebaseManager(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void logIn(View view){
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        if(email.length() > 0 && password.length() > 0) {
            firebaseManager.signIn(emailTxt, passwordTxt, this);
        }
    }


}
