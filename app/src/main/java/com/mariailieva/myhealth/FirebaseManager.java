package com.mariailieva.myhealth;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.mariailieva.myhealth.activities.LoginActivity;
import com.mariailieva.myhealth.activities.MainActivity;
import com.mariailieva.myhealth.activities.SignUpActivity;
import com.mariailieva.myhealth.fragments.ProfileFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FirebaseManager {

    FirebaseAuth auth;
    private SignUpActivity signUpActivity;
    private FirebaseFirestore firebaseFirestore;
    private String userID;


    public FirebaseManager(SignUpActivity activity) {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        signUpActivity = activity;
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);

    }
    public FirebaseManager(){
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    private void setupAuthStateListener(){
        auth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    System.out.println("Signed out");

                }
                else{
                    System.out.println("Signed in");
                }
            }
        });
    }

    public void signIn(String email, String password, final LoginActivity activity){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            System.out.println("LOGIN successful");
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                        else{
                            System.out.println("LOGIN failed" + task.getException());
                            Toast.makeText(activity, "Login failed:" +
                                    ( (FirebaseAuthException) task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signUp(final String email, String password, final String name, final String dateOfBirth, final SignUpActivity activity){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("email", email);
                            user.put("dateOfBirth", dateOfBirth);
                            documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        System.out.println("SIGNUP successful");
                                        Intent intent = new Intent(activity, MainActivity.class);
                                        activity.startActivity(intent);
                                    }else{
                                        Toast.makeText(activity, "Sign up failed:" +
                                                task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else{
                            System.out.println("SIGNUP failed");
                            Toast.makeText(activity, "Sign up failed:" +
                                     task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signOut(){
        auth.signOut();
    }

    public void editProfileData(String name, String dateOfBirth, String gender, int height, int weight){
        userID = auth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("name", name);
        user.put("dateOfBirth", dateOfBirth);
        user.put("gender", gender);
        user.put("height", height);
        user.put("weight", weight);
        documentReference.update(user);
    }

    public void editHealthData(String date, int levelBPSystolic,int levelBPDiastolic){
        userID = auth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("date", date);
        user.put("BP systolic", levelBPSystolic);
        user.put("BP diastolic", levelBPDiastolic);
        documentReference.update(user);
    }

    public void setHealthData(final EditText date,final EditText bpSystolic, final EditText bpDiastolic){
        userID = auth.getCurrentUser().getUid();
        DocumentReference docRef = firebaseFirestore.collection("users").document(userID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if(auth.getCurrentUser() != null) {
                    if (documentSnapshot.contains("date")) {
                        date.setText(documentSnapshot.getString("date"));
                    }
                    if (documentSnapshot.contains("BP systolic")) {
                        bpSystolic.setText(documentSnapshot.get("BP systolic").toString());
                    }
                    if (documentSnapshot.contains("BP diastolic")) {
                        bpDiastolic.setText(documentSnapshot.get("BP diastolic").toString());
                    }
                }
            }
        });
    }
    public void setProfileData(final TextView name, final TextView email, final TextView dateOfBirth, final TextView gender, final TextView weight, final TextView height){
            userID = auth.getCurrentUser().getUid();
            DocumentReference docRef = firebaseFirestore.collection("users").document(userID);
            docRef.addSnapshotListener( new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot != null && documentSnapshot.exists() && auth.getCurrentUser() != null) {
                        if (documentSnapshot.contains("name")) {
                            name.setText(documentSnapshot.getString("name"));
                        }
                        if (documentSnapshot.contains("email")) {
                            email.setText(documentSnapshot.getString("email"));
                        }
                        if (documentSnapshot.contains("dateOfBirth")) {
                            dateOfBirth.setText(documentSnapshot.getString("dateOfBirth"));
                        }
                        if (documentSnapshot.contains("gender")) {
                            gender.setText(documentSnapshot.getString("gender"));
                        }
                        if (documentSnapshot.contains("weight")) {
                            weight.setText(documentSnapshot.get("weight").toString());
                        }
                        if (documentSnapshot.contains("height")) {
                            height.setText(documentSnapshot.get("height").toString());
                        }
                    }
                }
            });
    }


}
