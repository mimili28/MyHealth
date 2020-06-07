package com.mariailieva.myhealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mariailieva.myhealth.FirebaseManager;
import com.mariailieva.myhealth.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText dateOfBirth;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email= findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        name = findViewById(R.id.fullNameText);
        dateOfBirth = findViewById(R.id.dateOfBirth);

        firebaseManager = new FirebaseManager(this);
    }

    public void signUp(View view){
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        String nameTxt = name.getText().toString();
        String dateOfBirthText = dateOfBirth.getText().toString();
        if(email.length() > 0 && password.length() > 0) {
            firebaseManager.signUp(emailTxt, passwordTxt, nameTxt, dateOfBirthText, this);

        }
    }
}
