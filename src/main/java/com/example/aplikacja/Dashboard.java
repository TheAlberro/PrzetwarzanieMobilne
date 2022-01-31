package com.example.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this, CreateEvent.class);
//      //  startActivity(new Intent(getApplicationContext(), CreateEvent.class));
//       // startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
//      //  setContentView(R.layout.add_new);
//       // startActivity(new Intent(getApplicationContext(), CreateEvent.class));
//
//     //   EditText editText = (EditText) findViewById(R.id.edit_message);
//      //  String message = editText.getText().toString();
//      //  intent.putExtra(EXTRA_MESSAGE, message);
//       // startActivity(intent);
//      // startActivity();
//       // setContentView(R.layout.add_item);
//      //  startActivity(new Intent(getApplicationContext(), CalendarAdapter.class));
//      //  One way to use the calendar widget is putting it in the xml file is shown in main.xml
////        setContentView(R.layout.main);
//
//        /*
//         Other way is to add is using the java code as follows.
//		*/
//      //  MonthView mv = new MonthView(this);
//       // setContentView(mv);
////        Calendar cal = Calendar.getInstance();
////        cal.set(2012, Calendar.DECEMBER,12);
////        mv.GoToDate(cal.getTime());
//
//   }
//   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.calendar);
        Intent intent = new Intent(this, CalendarView.class);
        startActivity(intent);
//        Intent intent = new Intent(this, CreateEvent.class);
//        startActivity(intent);

        //setContentView(R.layout.simple_calendar);
    }

    public void goToFriends(View view) {
        startActivity(new Intent(getApplicationContext(), Friends.class));
    }
//        setContentView(R.layout.simple_calendar);
//
//        SimpleCalendar simpleCalendar = findViewById(R.id.square_day);
//        Calendar calendar = Calendar.getInstance();
//        // Get current month
//        int month = calendar.get(Calendar.MONTH);
//
//        //Get current Year
//        int year = calendar.get(Calendar.YEAR);
//
//        simpleCalendar.setUserCurrentMonthYear(month,year);
//        simpleCalendar.setCallBack(new SimpleCalendar.DayClickListener() {
//            @Override
//            public void onDayClick(View view) {
//                // Create on Click event here.
//            }
//        });

}