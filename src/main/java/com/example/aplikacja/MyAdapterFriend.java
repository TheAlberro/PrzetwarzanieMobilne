package com.example.aplikacja;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapterFriend extends RecyclerView.Adapter<MyAdapterFriend.MyViewHolder> { // linia 75 zmieniam kolor
    private ShowActivityFriend activityF;

    private List<Model> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MyAdapterFriend(ShowActivityFriend activity , List<Model> mList){
        this.activityF = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        Model item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uUserId" , item.getUserId());
        bundle.putString("uId" , item.getId());
        bundle.putString("uTitle" , item.getTitle());
        bundle.putString("uDesc" , item.getDesc());
        bundle.putString("uIsPublic" , item.getIsPublic());
        bundle.putString("uStatus" , item.getStatus());
        bundle.putString("uDate" , item.getDate());
        bundle.putString("uHour" , item.getHour());
        bundle.putString("uMinute" , item.getMinute());
        Intent intent = new Intent(activityF , CreateEvent.class);
        intent.putExtras(bundle);
        activityF.startActivity(intent);
    }
/*
    public void deleteData(int position){
        Model item = mList.get(position);
        db.collection("Documents").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activityF, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activityF, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activityF.showData();
    }
    private void notifyRemovedFriend(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activityF.showDataFriend();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activityF).inflate(R.layout.item_show , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // Tu zmieniam kolor
        Model item = mList.get(position);
        String h=mList.get(position).getHour();
        String m=mList.get(position).getMinute();
        //  holder.title.setText(h + "-" + m);
        holder.title.setText(mList.get(position).getTitle());
        holder.desc.setText(mList.get(position).getDesc());
        int q=Integer.parseInt(mList.get(position).getHour());
        String hourText = "";
        if(q>9) hourText = mList.get(position).getHour() + ":";
        else hourText = "0"+mList.get(position).getHour() + ":";
        q=Integer.parseInt(mList.get(position).getMinute());
        if(q>9) hourText += mList.get(position).getMinute();
        else hourText += "0"+mList.get(position).getMinute();

        holder.hour.setText(hourText);
        holder.itemView.setBackgroundColor(Color.parseColor("#FF3488"));
        holder.itemView.setPadding(20, 10, 20, 30);
        holder.itemView.getLayoutParams().width = 840;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(840, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        holder.itemView.measure(widthMeasureSpec, heightMeasureSpec);
        holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();
        // holder.desc.setText(mList.get(position).getTitle());
        if(item.getStatus().equals("to be done later"))holder.itemView.setBackgroundColor(Color.parseColor("#ef476f")); //czerwony
        if(item.getStatus().equals("in progress"))holder.itemView.setBackgroundColor(Color.parseColor("#118ab2")); //niebieski
        if(item.getStatus().equals("already done"))holder.itemView.setBackgroundColor(Color.parseColor("#06d6a0")); //zielony


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title , desc, hour;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            desc = itemView.findViewById(R.id.desc_text);
            hour = itemView.findViewById(R.id.hour_text);
        }
    }
}
