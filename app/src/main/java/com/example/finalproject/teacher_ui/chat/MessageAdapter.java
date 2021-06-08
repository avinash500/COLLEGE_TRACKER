package com.example.finalproject.teacher_ui.chat;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.finalproject.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    ArrayList<MyMessage> data ;
    String cu, id;
    public MessageAdapter(ArrayList<MyMessage> data, String cu, String id)
    {
        this.data = data;
        this.cu = cu;
        this.id = id;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.chatitem_left, parent, false);
            return new MessageViewHolder(view);
        }
        else
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.chatitem_right, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MyMessage y = data.get(position);
        holder.name.setText(y.getName());
        holder.message.setText(y.getMessage());
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView message;
        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.lname);
            message = itemView.findViewById(R.id.lmessage);
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        if(data.get(position).getId().equals(id))
        {
            return 1;
        }
        else
//       Log.e("Satya",position+""+data.get(position).getId());
            return 0;
    }
}
