package com.example.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowActivityFriend extends AppCompatActivity {

    // odczyt z bazy danych
    private FirebaseAuth mAuth;   // zeby zdobyc ID
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapterFriend adapter;
    private List<Model> list;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friend);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
        }
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_show_friend);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapterFriend(this , list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperFriend(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        showData();

        Button button1 = (Button) findViewById(R.id.Button_send); //FIND THE BUTTON
        String finalValue = value;
       /* button1.setOnClickListener(new View.OnClickListener() { //SET ON CLICK LISTENER
                @Override
                public void onClick(View v) {
                    //YOUR CUSTOM LOGIC ON BUTTON CLICK
                 //   startActivity(new Intent(getApplicationContext(), CreateEvent.class));
                    Intent intent = new Intent(getApplicationContext(), CreateEvent.class);

                    intent.putExtra("Date", finalValue);
                    startActivity(intent);
                    //intent.putExtra("Date",selectedGridDate);
                }
        }); */
        
    }


    public void showData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
           // Toast.makeText(ShowActivity.this, "valeue " + value, Toast.LENGTH_SHORT).show();
        }

        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    int czy_puste=0;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            Model wez_date = new Model(snapshot.getString("id") ,snapshot.getString("date"));

                            if(value.equals(wez_date.date)) {
                               // Toast.makeText(ShowActivity.this,"date " + wez_date.date, Toast.LENGTH_SHORT).show();
                                    czy_puste++;

                                Model model1 = new Model(snapshot.getString("userId") , snapshot.getString("id") , snapshot.getString("title") , snapshot.getString("desc"),
                                        snapshot.getString("date"),snapshot.getString("isPublic"),snapshot.getString("status"), snapshot.getString("hour"),snapshot.getString("minute") );



                                mAuth = FirebaseAuth.getInstance();
                                String userId=mAuth.getCurrentUser().getUid();
                                if(model1.getUserId().equals(FriendsAdapter.ShareId))list.add(model1);


                            }
                        }
                        adapter.notifyDataSetChanged();
                        if(czy_puste==0)   Toast.makeText(ShowActivityFriend.this,"No events added this day", Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowActivityFriend.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void showDataFriend(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
            // Toast.makeText(ShowActivity.this, "valeue " + value, Toast.LENGTH_SHORT).show();
        }

        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    int czy_puste=0;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            Model wez_date = new Model(snapshot.getString("id") ,snapshot.getString("date"));

                            if(value.equals(wez_date.date)) {
                                // Toast.makeText(ShowActivity.this,"date " + wez_date.date, Toast.LENGTH_SHORT).show();
                                czy_puste++;

                                Model model1 = new Model(snapshot.getString("userId") , snapshot.getString("id") , snapshot.getString("title") , snapshot.getString("desc"),
                                        snapshot.getString("date"),snapshot.getString("isPublic"),snapshot.getString("status"), snapshot.getString("hour"),snapshot.getString("minute") );



                                mAuth = FirebaseAuth.getInstance();
                                String userId=mAuth.getCurrentUser().getUid();
                                if(FriendsAdapter.ShareId.equals(userId))list.add(model1);


                            }
                        }
                        adapter.notifyDataSetChanged();
                        if(czy_puste==0)   Toast.makeText(ShowActivityFriend.this,"No events added this day", Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowActivityFriend.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }
    public void InsertEvent(View view) {
        startActivity(new Intent(getApplicationContext(), CreateEvent.class));
    }
}












