package com.mariailieva.myhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.mariailieva.myhealth.FirebaseManager;
import com.mariailieva.myhealth.R;
import com.mariailieva.myhealth.activities.EditProfileActivity;
import com.mariailieva.myhealth.activities.LoginActivity;
import com.mariailieva.myhealth.activities.MainActivity;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView email;
    private TextView dateOfBirth;
    private TextView gender;
    private TextView height;
    private TextView weight;

    private Button logOutBtn;
    private Button editBtn;


    FirebaseManager firebaseManager;



    public ProfileFragment() {}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        name = v.findViewById(R.id.nameText);
        email = v.findViewById(R.id.emailText);
        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        gender = v.findViewById(R.id.genderText);
        weight = v.findViewById(R.id.weightText);
        height = v.findViewById(R.id.heightText);

        logOutBtn = v.findViewById(R.id.logOutBtn);
        editBtn = v.findViewById(R.id.editProfile);

        firebaseManager = new FirebaseManager();



        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("dateOfBirth",dateOfBirth.getText().toString());
                if(gender.getText().toString().trim().length()>0){
                    intent.putExtra("gender",gender.getText().toString());
                }
                if(height.getText().toString().trim().length()>0){
                    intent.putExtra("height",height.getText().toString());
                }
                if(weight.getText().toString().trim().length()>0){
                    intent.putExtra("weight", weight.getText().toString());
                }
                v.getContext().startActivity(intent);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                firebaseManager.signOut();

            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        firebaseManager.setProfileData(name, email, dateOfBirth, gender, weight, height);
    }
}
