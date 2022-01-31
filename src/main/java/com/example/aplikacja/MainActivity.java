package com.example.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_main);

    }
    public void Save(View v) {
        mAuth = FirebaseAuth.getInstance();
        EditText email = (EditText)findViewById(R.id.EmailLogin);
        EditText passw = (EditText)findViewById(R.id.PasswordLogin);
        String e_mai = email.getText().toString().trim();
        String P_assw = passw.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(e_mai, P_assw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                           // startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    public void createAcc(View v) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
    private void updateUI(FirebaseUser user) {
    }

}