package com.example.aplikacja;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FriendRequest extends AppCompatActivity implements OnCompleteListener<QuerySnapshot> {

    private FirebaseFirestore cloudstorage;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_friend_requests);
        getFriendRequestsFromDB();
    }


    private void getFriendRequestsFromDB()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        cloudstorage = FirebaseFirestore.getInstance();

        cloudstorage.collection("users").document(user.getUid()).collection("FriendRequests")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Missions> mMissionsList = new ArrayList<>();
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Missions miss = document.toObject(Missions.class);
                        mMissionsList.add(miss);
                    }

                    ListView mMissionsListView = (ListView) findViewById(R.id.listview);
                    MissionsAdapter mMissionAdapter = new MissionsAdapter(FriendRequest.this, mMissionsList);
                    mMissionsListView.setAdapter(mMissionAdapter);

                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
