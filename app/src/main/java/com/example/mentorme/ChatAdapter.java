package com.example.mentorme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int item_RECEIVE = 0;
    public static final int item_SEND = 1;
    private Context context;
    private ArrayList<MsgModel> chatAdpAL;
    FirebaseUser fuser;

    public ChatAdapter(Context context, ArrayList<MsgModel> chatAdpAL) {
        this.context = context;
        this.chatAdpAL = chatAdpAL;
    }
    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatAdpAL.get(position).getSenderUid().equals(fuser.getUid())) return item_SEND;
        else return item_RECEIVE;
    }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == item_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
            MsgModel msg = chatAdpAL.get(position);
            holder.messageTV.setText(msg.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatAdpAL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView messageTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.messageTV);
        }
    }

}
