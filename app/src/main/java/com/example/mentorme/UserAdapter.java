package com.example.mentorme;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    MainActivity mainActivity;
    ArrayList<Users> usersArrayList;
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity = mainActivity;
        this.usersArrayList = usersArrayList;
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Users user = usersArrayList.get(position);
        holder.contactTV.setText(user.getName());
        holder.descTV.setText(user.getExpertise());

        holder.itemView.setOnClickListener(view ->{
            Intent in = new Intent(mainActivity, Chat.class);
            in.putExtra("userID",user.getUserID());
            in.putExtra("userName",user.getName());
            mainActivity.startActivity(in);
        });
    }
    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView contactTV, descTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactTV = itemView.findViewById(R.id.contactTV);
            descTV = itemView.findViewById(R.id.descTV);
        }
    }
}
