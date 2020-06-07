package com.mariailieva.myhealth.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.mariailieva.myhealth.FirebaseManager;
import com.mariailieva.myhealth.R;

public class MyHealthFragment extends Fragment {

    private EditText dateText;
    private EditText bpSystolicNum;
    private EditText bpDiastolicNum;
    private EditText levelBSNum;
    private Button updateBtn;
    private TextView bpStatus;
    private TextView bsStatus;
    private TextView bmiStatus;
    private FirebaseManager firebaseManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_health_fragment, container, false);
        firebaseManager = new FirebaseManager();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        dateText = view.findViewById(R.id.dateText);
        bpSystolicNum = view.findViewById(R.id.BPSystolic);
        bpDiastolicNum = view.findViewById(R.id.BPDiastolic);
        levelBSNum = view.findViewById(R.id.BSLevelText);
        bpStatus = view.findViewById(R.id.bpLevelInfo);
        bsStatus = view.findViewById(R.id.bsLevelInfo);
        bmiStatus = view.findViewById(R.id.bmiLevelInfo);

        updateBtn = view.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                setStatus();
            }
        });

        setStatus();

        firebaseManager.setHealthData(dateText, bpSystolicNum, bpDiastolicNum, levelBSNum);
        return view;
    }


    public void updateData(){
        String date = dateText.getText().toString();
        int bpSystolic = Integer.parseInt(bpSystolicNum.getText().toString());
        int bpDiastolic = Integer.parseInt(bpDiastolicNum.getText().toString());
        int bs = Integer.parseInt(levelBSNum.getText().toString());
        firebaseManager.editHealthData(date, bpSystolic,bpDiastolic, bs);
    }

    public String calculateBMI(int height, int weight){
        double bmi = ((weight*1.0)/(height*height))*10000;
        if(bmi<18.5){
            bmiStatus.setTextColor(Color.parseColor("#1AC1DD"));
            return "underweight";
        }
        else if(bmi>18.5 && bmi<24.9){
            bmiStatus.setTextColor(Color.parseColor("#03C04A"));
            return "normal";
        }
        else if(bmi>25 && bmi<29.9){
            bmiStatus.setTextColor(Color.parseColor("#FE6E00"));
            return "overweight";
        }
        else {
            bmiStatus.setTextColor(Color.parseColor("#FF0000"));
            return "obese";
        }
    }

    public String calculateBP(int bpSystolic, int bpDiastolic){
        if(bpSystolic<90 && bpDiastolic<60){
            bpStatus.setTextColor(Color.parseColor("#1AC1DD"));
            return "Low";
        }
        else if((bpSystolic>=90 && bpSystolic<120) && bpDiastolic<=80){
            bpStatus.setTextColor(Color.parseColor("#03C04A"));
            return "Normal";
        }
        else if((bpSystolic>=120 && bpSystolic<129) && bpDiastolic<=80){
            bpStatus.setTextColor(Color.parseColor("#FFF200"));
            return "elevated";
        }
        else if((bpSystolic>=130 && bpSystolic<139) || (bpDiastolic>80 && bpDiastolic<89)){
            bpStatus.setTextColor(Color.parseColor("#FE6E00"));
            return "High, stage 1";
        }
        else {
            bpStatus.setTextColor(Color.parseColor("#FF0000"));
            return "High, stage 2";
        }

    }

    public void setStatus() {
        DocumentReference docRef = firebaseFirestore.collection("users").document(userId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {

                bmiStatus.setText(calculateBMI(documentSnapshot.getLong("height").intValue(),documentSnapshot.getLong("weight").intValue()));
                if(bpDiastolicNum.getText().toString().trim().length() > 0 && bpSystolicNum.getText().toString().trim().length()>0) {
                    bpStatus.setText(calculateBP(documentSnapshot.getLong("BP systolic").intValue(), documentSnapshot.getLong("BP diastolic").intValue()));
                }
            }

        });
    }


}
