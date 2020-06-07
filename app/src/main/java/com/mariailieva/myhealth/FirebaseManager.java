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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FirebaseManager {

    FirebaseAuth auth;
    private LoginActivity loginActivity;
    private SignUpActivity signUpActivity;
    private FirebaseFirestore firebaseFirestore;
    private String userID;

//    public FirebaseManager(LoginActivity activity){
//        auth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        loginActivity = activity;
//
//
//    }

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
//                            FirebaseDatabase.getInstance().getReference("users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                        else{
                            System.out.println("LOGIN failed" + task.getException());
                            Toast.makeText(activity, "Login failed:" +
                                    ( (FirebaseAuthException) task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(activity, "Wrong email or password!", Toast.LENGTH_LONG).show();
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
                            //Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signOut(){

        ;

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

    public void editHealthData(String date, int levelBPSystolic,int levelBPDiastolic, int levelBS){
        userID = auth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("date", date);
        user.put("BP systolic", levelBPSystolic);
        user.put("BP diastolic", levelBPDiastolic);
        user.put("BS level", levelBS);
        documentReference.update(user);
    }

    public void setHealthData(final EditText date,final EditText bpSystolic, final EditText bpDiastolic,final EditText bs){
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
                    if (documentSnapshot.contains("BS level")) {
                        bs.setText(documentSnapshot.get("BS level").toString());
                    }
                }
            }
        });
    }
    public void setProfileData(final TextView name, final TextView email, final TextView dateOfBirth, final TextView gender, final TextView weight, final TextView height){
            userID = auth.getCurrentUser().getUid();
            DocumentReference docRef = firebaseFirestore.collection("users").document(userID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                    @Nullable FirebaseFirestoreException e) {

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
            });
    }
}
