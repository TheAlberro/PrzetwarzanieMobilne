package com.example.aplikacja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendsAdapter extends ArrayAdapter<friendsModel> {

    public static String ShareId;
    public FriendsAdapter(Friends context, List<friendsModel> object){
        super((Context) context,0, object);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.activity_friendslistview,parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.friend);

        friendsModel mission = getItem(position);

        titleTextView.setText(mission.getEmail());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
               // Toast.makeText(getContext(), "Item clicked is : " + mission.getRequestorId(), Toast.LENGTH_SHORT).show();
                ShareId= mission.getRequestorId();
                Intent intent = new Intent(getContext(),friendCallendar.class);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
