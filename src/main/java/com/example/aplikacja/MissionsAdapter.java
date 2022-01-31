package com.example.aplikacja;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MissionsAdapter extends ArrayAdapter<Missions> {
    private FirebaseFirestore cloudstorage;
    private FirebaseAuth mAuth;
    private MissionsAdapter adapter;
    List<Missions> data = null;

    public MissionsAdapter(OnCompleteListener<QuerySnapshot> context, List<Missions> object){
        super((Context) context,0, object);
        data = object;
        this.adapter = this; //This is an important line, you need this line to keep track the adapter variable
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.activity_listview,parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.mission_title);
        Button accept = (Button ) convertView.findViewById(R.id.acceptRequest);
        Button decline = (Button ) convertView.findViewById(R.id.declineRequest);
        Missions mission = getItem(position);

        titleTextView.setText(mission.getRequestorId());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        cloudstorage = FirebaseFirestore.getInstance();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + mission.getRequestorId(), Toast.LENGTH_SHORT).show();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Accepted : " + mission.getRequestorId(), Toast.LENGTH_SHORT).show();

                Map<String, Object> friend = new HashMap<>();
                friend.put("requestorId", mission.getRequestorId());
                friend.put("email", mission.getEmail());

                Map<String, Object> secondFriend = new HashMap<>();
                assert user != null;
                secondFriend.put("requestorId", user.getUid());
                secondFriend.put("email", user.getEmail());

                data.remove(position);
                adapter.notifyDataSetChanged();

                cloudstorage.collection("users").document(mission.getRequestorId()).collection("Friends").document(user.getUid())
                        .set(secondFriend)

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

                cloudstorage.collection("users").document(user.getUid()).collection("Friends").document(mission.getRequestorId())
                        .set(friend)
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
                deleteFriendRequest(cloudstorage,mission.getRequestorId(), user.getUid());

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Declined : " + mission.getRequestorId(), Toast.LENGTH_SHORT).show();
                deleteFriendRequest(cloudstorage,mission.getRequestorId(), user.getUid());
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }
    private void deleteFriendRequest(FirebaseFirestore db, String Id1, String Id2 )
    {
         db.collection("users").document(Id2).collection("FriendRequests").document(Id1)
        .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        DocumentReference docRef = db.collection("users").document(Id1).collection("FriendRequests").document(Id2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

}