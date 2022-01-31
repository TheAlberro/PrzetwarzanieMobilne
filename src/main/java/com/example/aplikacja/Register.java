package com.example.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "DocSnippets";
    private FirebaseFirestore db;
    FirebaseFirestore cloudstorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_register);
    }
    public void registerUser(View v) {
        mAuth = FirebaseAuth.getInstance();
        EditText email = (EditText)findViewById(R.id.EmailRegister);
        EditText passw = (EditText)findViewById(R.id.PasswordRegister);
        String e_mai = email.getText().toString().trim();
        String P_assw = passw.getText().toString().trim();

        Map<String, Object> usersMap = new HashMap();

        mAuth.createUserWithEmailAndPassword(e_mai, P_assw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            usersMap.put("email", e_mai);
                            cloudstorage = FirebaseFirestore.getInstance();
                            saveUserToFirestore(usersMap, user.getUid());

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    public void returnToLoginPage(View v)
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    private void updateUI(FirebaseUser user) {
    }
    private void saveUserToFirestore(Map map, String Id)
    {
        cloudstorage.collection("users").document(Id)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
