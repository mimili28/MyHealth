package com.mariailieva.myhealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.mariailieva.myhealth.FirebaseManager;
import com.mariailieva.myhealth.R;

import java.util.Arrays;

public class EditProfileActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText dateOfBirth;
    Spinner gender;
    EditText height;
    EditText weight;
    FirebaseManager firebaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.nameText);
        email = findViewById(R.id.emailText);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        gender = findViewById(R.id.genderText);
        height = findViewById(R.id.heightText);
        weight = findViewById(R.id.weightText);

        firebaseManager = new FirebaseManager();

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            String nameText =(String) bundle.get("name");
            String dateOfBirthText =(String) bundle.get("dateOfBirth");
            String genderText = (String) bundle.get("gender");
            String heightNum = (String) bundle.get("height");
            String weightNum = (String) bundle.get("weight");
            name.setText(nameText);
            dateOfBirth.setText(dateOfBirthText);

            String[] genders = getResources().getStringArray(R.array.gender_array);
            gender.setSelection(Arrays.asList(genders).indexOf(genderText));

            height.setText(heightNum);
            weight.setText(weightNum);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String nameText = name.getText().toString();
        String genderText = gender.getSelectedItem().toString();
        String dateOfBirthText = dateOfBirth.getText().toString();
        int heightText = Integer.parseInt(height.getText().toString());
        int weightText = Integer.parseInt(weight.getText().toString());

        firebaseManager.editProfileData(nameText,dateOfBirthText,genderText,heightText,weightText);

    }

}
