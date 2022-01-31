package com.example.aplikacja;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
// My adapter linia 65 zmieniasz layout itema
public class CreateEvent extends AppCompatActivity { // bledy zegara sa w lini 210
    private FirebaseAuth mAuth;   // zeby zdobyc ID
    private EditText mTitle , mDesc;
    private Button mSaveBtn, mShowBtn;
    private FirebaseFirestore db;
    private String uTitle, uDesc , uId,uDate, uStatus, uIsPublic, uHour, uMinute;

    private RadioGroup status;
    private RadioGroup isPublic;
    String value;
RadioButton s1,s2,s3,p1,p2;
    Button timeButton;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeButton = findViewById(R.id.timeButton);
        /*
        timeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v)
            {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        CreateEvent.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                            {
                                t1Hour =hourOfDay;
                                t1Minute=minute;
                                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",Integer.parseInt(hour), Integer.parseInt(minute))); //bug

                                //  timeButton.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
            }
        });

         */
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("Date");
          /*  String minute, hour;
            minute=extras.getString("minute");
            hour=extras.getString("hour");

            if(minute!=null || hour!=null)
            {
              //  timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",Integer.parseInt(hour), Integer.parseInt(minute))); //bug
                Toast.makeText(CreateEvent.this,
                        "co sie porobilo", Toast.LENGTH_SHORT).show();

            }
            getIntent().removeExtra("minute");
            getIntent().removeExtra("hour");
            */
            getIntent().removeExtra("Date");

        }
        if(extras!=null) { //template
            //getIntent().c
            // i also use the below statement
            // bundle.remove("KEY");
        }
        setContentView(R.layout.activity_main);

        mTitle=findViewById(R.id.add_new_title);
        mDesc=findViewById(R.id.add_new_desc);

        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.add_new);

        mTitle=findViewById(R.id.add_new_title);
        mDesc=findViewById(R.id.add_new_desc);


        status=findViewById(R.id.radioGroup);
        isPublic=findViewById(R.id.radioGroup2);
        s1=findViewById(R.id.radioLater);
        s2=findViewById(R.id.radioProgress);
        s3=findViewById(R.id.radioDone);
        p1=findViewById(R.id.radioPrivate);
        p2=findViewById(R.id.radioPublic);
        mSaveBtn=findViewById(R.id.add_new_save);
        mShowBtn=findViewById(R.id.add_new_show);



        db= FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        //   Bundle bundle = getIntent().
        if (bundle != null){   //tu wklejamy z bazy podczas update
            timeButton = findViewById(R.id.timeButton);
            mSaveBtn.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            uDesc = bundle.getString("uDesc");
            uDate = bundle.getString("uDate");
            uStatus=bundle.getString("uStatus");
            uIsPublic=bundle.getString("uIsPublic");
            uHour=bundle.getString("uHour");
            uMinute=bundle.getString("uMinute");
            mTitle.setText(uTitle);
            mDesc.setText(uDesc);
            int temp1=15, temp2=15;
           // temp1=Integer.parseInt(uHour);
            //temp2=Integer.parseInt(uMinute);
            //temp1=Integer.valueOf(uHour);
            //temp2=Integer.valueOf(uMinute);
            Toast.makeText(CreateEvent.this,"public " + temp1 + temp2, Toast.LENGTH_SHORT).show();
            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",Integer.parseInt(uHour), Integer.parseInt(uMinute))); //bug
            /*
if(temp1>0 && temp1<24)
{
    //    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",Integer.valueOf(uHour), Integer.valueOf(uMinute))); //bug
    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",12, 12)); //bug
}
else {
    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", 15, 15)); //bug
}


             */

            int selectedId = status.getCheckedRadioButtonId();
            int selectedId2 = isPublic.getCheckedRadioButtonId();
        //    int selectedId3 = s1.getCheckedRadioButtonId();
          //  int selectedId4 = isPublic.getCheckedRadioButtonId();
            //int selectedId5 = isPublic.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            RadioButton radioButton2 = (RadioButton) findViewById(selectedId2);
            status.clearCheck();
            isPublic.clearCheck();
            status.setSelected(true);
           if(uStatus.equals("to be done later")) s1.setChecked(true);
            if(uStatus.equals("in progress")) s2.setChecked(true);
            if(uStatus.equals("already done")) s3.setChecked(true);

            if(uIsPublic.equals("Private"))
            {

             //   Toast.makeText(CreateEvent.this,"priv " + uStatus, Toast.LENGTH_SHORT).show();
                isPublic.setSelected(true);
                p1.setChecked(true);

            }
            if(uIsPublic.equals("Public"))
            {
             //   Toast.makeText(CreateEvent.this,"public " + uStatus, Toast.LENGTH_SHORT).show();
                isPublic.setSelected(true);
                p2.setChecked(true);
            }

        }else{
            mSaveBtn.setText("Add New");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateEvent.this , ShowActivity.class));
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String desc = mDesc.getText().toString();

                Bundle bundle1 = getIntent().getExtras();

                if (bundle1 !=null){
                    Toast.makeText(CreateEvent.this,
                            "jestem w edycji", Toast.LENGTH_LONG).show();
                    String id = uId;
                    value=uDate;
                    int selectedId = status.getCheckedRadioButtonId();
                    int selectedId2 = isPublic.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    RadioButton radioButton2 = (RadioButton) findViewById(selectedId2);
                    //  Toast.makeText(MyAndroidAppActivity.this,
                    //        radioButton.getText(), Toast.LENGTH_SHORT).show();

                    //int stat = status.getCheckedRadioButtonId();

                    // toString().trim();
                    //    String stat= radioButton.getText().toString().trim();
                    //  String pub = isPublic.toString().trim();
/*
                    //radio1.setChecked(true);
                    Toast.makeText(CreateEvent.this,
                            radioButton.getText(), Toast.LENGTH_LONG).show();
                    Toast.makeText(CreateEvent.this,
                            radioButton2.getText(), Toast.LENGTH_LONG).show();

 */                     mAuth = FirebaseAuth.getInstance();
                        String userId=mAuth.getCurrentUser().getUid();
                    updateToFireStore(userId, id , title, desc, value, radioButton.getText().toString(), radioButton2.getText().toString(),Integer.toString(hour),Integer.toString(minute));


                    }
                else {
                    int temp1 = 1, temp2=1;
                    String id = UUID.randomUUID().toString();
                    // get selected radio button from radioGroup
                    int selectedId = status.getCheckedRadioButtonId();
                    int selectedId2 = isPublic.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    RadioButton radioButton2 = (RadioButton) findViewById(selectedId2);
                    //  Toast.makeText(MyAndroidAppActivity.this,
                    //        radioButton.getText(), Toast.LENGTH_SHORT).show();

                    //int stat = status.getCheckedRadioButtonId();

                    // toString().trim();
                    //    String stat= radioButton.getText().toString().trim();
                    //  String pub = isPublic.toString().trim();
                    Toast.makeText(CreateEvent.this,
                            radioButton.getText(), Toast.LENGTH_LONG).show();
                    Toast.makeText(CreateEvent.this,
                            radioButton2.getText(), Toast.LENGTH_LONG).show();
                    mAuth = FirebaseAuth.getInstance();
                    String userId=mAuth.getCurrentUser().getUid();
                    saveToFireStore(userId, id , title , desc, value,radioButton.getText().toString(), radioButton2.getText().toString(),Integer.toString(hour),Integer.toString(minute));
                }

            }
        });


    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                timeButton = findViewById(R.id.timeButton);
               // Toast.makeText(CreateEvent.this, "czas sobie chcialem ustawic", Toast.LENGTH_SHORT).show();
                hour = selectedHour;
                minute = selectedMinute;
                /*
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                Bundle bundle = new Bundle();
                bundle.putString("hour", Integer.toString(hour));
                bundle.putString("minute", Integer.toString(minute));
                intent.putExtras(bundle);
                startActivity(intent);
                //  startActivity(new Intent(getApplicationContext(), CreateEvent.class));
*/
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute)); //bug


            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }



    private void updateToFireStore(String userId ,String id , String title , String desc, String date, String status, String isPublic, String hour, String minute){

        db.collection("Documents").document(id).update("userId",userId,"title" , title , "desc" , desc,
                "date",date,"status",status,"isPublic", isPublic )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateEvent.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateEvent.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToFireStore(String userId, String id , String title , String desc, String date, String status, String isPublic, String hour, String minute){

        if (!title.isEmpty() && !desc.isEmpty() && !hour.equals(0) && !minute.equals(0)){
            HashMap<String , Object> map = new HashMap<>();
            map.put("userId" , userId);
            map.put("id" , id);
            map.put("title" , title);
            map.put("desc" , desc);
            map.put("date" , date);
            map.put("status" , status);
            map.put("isPublic" , isPublic);
            map.put("hour" , hour);
            map.put("minute" , minute);



            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateEvent.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateEvent.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}

