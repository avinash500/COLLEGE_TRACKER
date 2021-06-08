package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    ArrayList<Event> data;
    public EventAdapter(){}
    public EventAdapter(ArrayList<Event> data)
    {
        this.data = data;
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.eventsupport, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder holder, int position) {
        holder.clubName.setText(data.get(position).getClubName());
//        holder.imageView.
        Picasso.get().load(data.get(position).getImgView()).into(holder.imageView);
        holder.comment.setText(data.get(position).getComment());

        holder.heart.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated=false;

            @Override
            public void onClick(View v)
            {


                if (!isAnimated){
                    holder.heart.setSpeed(3f);
                    isAnimated=true;
                    holder.heart.playAnimation();

                } else {
                    holder.heart.setSpeed(-1F);
                    isAnimated=false;
                    holder.heart.setProgress(0.0f);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder
    {
        TextView clubName;
        ImageView imageView;
        TextView comment;
        LottieAnimationView heart;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.clubname);
            imageView = itemView.findViewById(R.id.society_name);
            comment = itemView.findViewById(R.id.comment);
            heart=itemView.findViewById(R.id.heart);


        }
    }
}
