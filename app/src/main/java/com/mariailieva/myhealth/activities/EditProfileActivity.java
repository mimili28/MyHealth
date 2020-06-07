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
        String nameText;
        String genderText;
        String dateOfBirthText;
        int heightText;
        int weightText;

        super.onBackPressed();

        if (name.getText().toString().trim().length() > 0){
            nameText = name.getText().toString();
        }else{
            nameText = " ";
        }
        if (gender.getSelectedItem().toString().trim().length() > 0){
            genderText = gender.getSelectedItem().toString();
        }else{
            genderText = " ";
        }
        if (dateOfBirth.getText().toString().trim().length() > 0){
            dateOfBirthText = dateOfBirth.getText().toString();
        }else{
            dateOfBirthText = " ";
        }
        if (height.getText().toString().trim().length() > 0){
            heightText = Integer.parseInt(height.getText().toString());
        }else{
            heightText = 0;
        }
        if (weight.getText().toString().trim().length() > 0){
            weightText = Integer.parseInt(weight.getText().toString());
        }else{
            weightText = 0;
        }

        firebaseManager.editProfileData(nameText,dateOfBirthText,genderText,heightText,weightText);

    }

}
