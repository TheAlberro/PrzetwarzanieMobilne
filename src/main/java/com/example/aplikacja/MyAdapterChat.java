package com.example.aplikacja;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapterChat extends RecyclerView.Adapter<MyAdapterChat.MyViewHolder> { // linia 75 zmieniam kolor
    private FirebaseAuth mAuth;
    private Chat activity;
    private List<ModelChat> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapterChat(Chat activity , List<ModelChat> mList){
        this.activity = activity;
        this.mList = mList;
    }




    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_show , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // Tu zmieniam kolor
        mAuth = FirebaseAuth.getInstance();
        String userId=mAuth.getCurrentUser().getUid();
        ModelChat item = mList.get(position);
        String h=mList.get(position).getHour();
        String m=mList.get(position).getMinute();
      //  holder.title.setText(h + "-" + m);
        holder.title.setText(mList.get(position).getMessage());
        holder.itemView.setPadding(20, 10, 20, 30);
        holder.itemView.getLayoutParams().width = 840;
     //  holder.desc.setText(mList.get(position).getSenderId());
        holder.hour.setText(mList.get(position).getDatetime());
        holder.itemView.setBackgroundColor(Color.parseColor("#FF3488"));
       // holder.desc.setText(mList.get(position).getTitle());
      //  if(item.getStatus().equals("to be done later"))holder.itemView.setBackgroundColor(Color.parseColor("#ef476f")); //czerwony

        holder.itemView.setPadding(20, 10, 20, 30);
        holder.itemView.getLayoutParams().width = 840;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(840, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        holder.itemView.measure(widthMeasureSpec, heightMeasureSpec);
        holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();

        if(item.getSenderId().equals(userId))holder.itemView.setBackgroundColor(Color.parseColor("#06d6a0")); //zielony
        if(item.getSenderId().equals(FriendsAdapter.ShareId))holder.itemView.setBackgroundColor(Color.parseColor("#118ab2")); //niebieski


        //holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
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
