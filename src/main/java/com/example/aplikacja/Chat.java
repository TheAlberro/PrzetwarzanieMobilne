package com.example.aplikacja;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Chat extends AppCompatActivity {

    // odczyt z bazy danych
    private FirebaseAuth mAuth;   // zeby zdobyc ID
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapterChat adapter;
    private List<ModelChat> list;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
        }
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapterChat(this , list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperChat(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        showData();

        Button button1 = (Button) findViewById(R.id.Button_send); //FIND THE BUTTON
        String finalValue = value;
        button1.setOnClickListener(new View.OnClickListener() { //SET ON CLICK LISTENER
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) { ///////////////////////////////////////////////////tutaj wyslanie wiadomosci sie odbywa
                //YOUR CUSTOM LOGIC ON BUTTON CLICK
                //   startActivity(new Intent(getApplicationContext(), CreateEvent.class));
              /*  Intent intent = new Intent(getApplicationContext(), CreateEvent.class);

                intent.putExtra("Date", finalValue);
                startActivity(intent); */
                TextView message1 = (TextView) findViewById(R.id.text_message);
                //intent.putExtra("Date",selectedGridDate);
                String id = UUID.randomUUID().toString();
                String receiverId=FriendsAdapter.ShareId;
                mAuth = FirebaseAuth.getInstance();
                String userId=mAuth.getCurrentUser().getUid();
                String senderId=userId;
              //  String text = textView.getText().toString();
                String message=message1.getText().toString();
                String datetime = LocalDateTime.now().toString();
                saveToFireStore(id,datetime,message,receiverId,senderId);
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
            }
        });

    }


    public void showData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
            // Toast.makeText(ShowActivity.this, "valeue " + value, Toast.LENGTH_SHORT).show();
        }

        db.collection("Chats").orderBy("datetime", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    int czy_puste=0;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            ModelChat wez_chat = new ModelChat(snapshot.getString("id") ,snapshot.getString("datetime"),snapshot.getString("message"),snapshot.getString("senderId"),snapshot.getString("receiverId"));


                              //   Toast.makeText(ShowActivity.this,"date " + wez_date.date, Toast.LENGTH_SHORT).show();
                                czy_puste++;

                             //   Model model1 = new Model(snapshot.getString("userId") , snapshot.getString("id") , snapshot.getString("title") , snapshot.getString("desc"),
                                 //       snapshot.getString("date"),snapshot.getString("isPublic"),snapshot.getString("status"), snapshot.getString("hour"),snapshot.getString("minute") );

                            ModelChat model1 = new ModelChat(snapshot.getString("id") , snapshot.getString("datetime"), snapshot.getString("message") ,
                                    snapshot.getString("receiverId"), snapshot.getString("senderId"));

                          //  Toast.makeText(Chat.this,"message " + model1.getMessage(), Toast.LENGTH_SHORT).show();
                                mAuth = FirebaseAuth.getInstance();
                                String userId=mAuth.getCurrentUser().getUid();
                                if((model1.senderId.equals(userId)   && model1.receiverId.equals(FriendsAdapter.ShareId)) || (model1.receiverId.equals(userId)   && model1.senderId.equals(FriendsAdapter.ShareId) ))
                                {
                                    list.add(model1);
                                }
                          //  Collections.sort(list, Collections.reverseOrder());

                               // if(model1.g.equals(userId))list.add(model1);



                        }
                        adapter.notifyDataSetChanged();
                        if(czy_puste==0)   Toast.makeText(Chat.this,"Chat pusty", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chat.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }
    private void saveToFireStore( String id,  String datetime, String message, String receiverId, String senderId) {

        if (!message.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("receiverId" , receiverId);
            map.put("id" , id);
            map.put("message" , message);
            map.put("senderId" , senderId);
            map.put("datetime" , datetime);




            db.collection("Chats").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Chat.this, "message send !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Chat.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }


}












