package com.example.aplikacja;

import static android.content.ContentValues.TAG;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Friends extends AppCompatActivity {
   private FirebaseFirestore cloudstorage;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_friends);
        getFriendsFromDB();
    }

    public void searchForUser(View view) {
        EditText email = (EditText)findViewById(R.id.editTextTextPersonName2);
        String e_mai = email.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();

        cloudstorage = FirebaseFirestore.getInstance();
        cloudstorage.collection("users").whereEqualTo("email", e_mai)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DANE Z BAZY: "+ document.getId() + " => " + document.getData());
                                cloudstorage = FirebaseFirestore.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
                                createFriendRequest(user.getUid(),document.getId(),e_mai);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void createFriendRequest(String requestorId, String Id, String email)
    {

        cloudstorage.collection("users").document(requestorId).collection("Friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getId().equals(Id))
                                {
                                    System.out.println("Jest");
                                    Log.d(TAG, document.getId() + " => " );

                                }
                                else
                                {
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("requestorId", requestorId);
                                        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                        CollectionReference usersRef = rootRef.collection("users").document(Id).collection("FriendRequests");
                                        usersRef.document(requestorId).set(data);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void showFriendRequest(View view) {
        startActivity(new Intent(getApplicationContext(), FriendRequest.class));
    }

    private void getFriendsFromDB()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        cloudstorage = FirebaseFirestore.getInstance();

        cloudstorage.collection("users").document(user.getUid()).collection("Friends")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<friendsModel> mMissionsList = new ArrayList<>();
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : task.getResult()) {
                        friendsModel miss = document.toObject(friendsModel.class);
                        mMissionsList.add(miss);
                    }

                    ListView mMissionsListView = (ListView) findViewById(R.id.friendslistview);
                    FriendsAdapter mMissionAdapter = new FriendsAdapter(Friends.this, mMissionsList);
                    mMissionsListView.setAdapter(mMissionAdapter);

                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
    }

}
