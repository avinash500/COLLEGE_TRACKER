package com.example.finalproject.student_ui.sforum;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.R;
import com.example.finalproject.student_ui.sattendance.GraphData;
import com.example.finalproject.teacher_ui.attendance.AttendanceInformation;
import com.example.finalproject.teacher_ui.attendance.AttendanceRecordAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {
    private ArrayList<Question> list;
    String message[];

    public ForumAdapter() {
    }

    public ForumAdapter(ArrayList<Question> list,String message[]) {
        this.list = list;
        this.message=message;
//        System.out.println("========================="+list+"============================================================");
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout_view_question, parent, false);
//        System.out.println("===============================================================");
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ForumViewHolder holder, final int position) {
        String n, d, q;
        n = list.get(position).getName();
        d = list.get(position).getDate();
        q = list.get(position).getQUESTION();
//        System.out.println("========================="+list.get(position)+"===============================");
        holder.name.setText(n);
        holder.date.setText(d);
        holder.question.setText(q);


        holder.addcmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(v.getContext(), ForumAddComment.class);
//                ArrayList<Question> al = new ArrayList<Question>();
//                al.add(list.get(position));
//                intent.putExtra("data", al);


                intent.putExtra("bin",list.get(position));
                intent.putExtra("message", message);
                v.getContext().startActivity(intent);
            }
        });
        holder.showCmt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForumShowComment.class);
                intent.putExtra("bin",list.get(position));
                v.getContext().startActivity(intent);
//
//                ArrayList<Question> al = new ArrayList<Question>();
//                al.add(list.get(position));
//                intent.putExtra("message", al);
//                v.getContext().startActivity(intent);
            }
        });

        holder.upvote.setProgress(0.3f);
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated=false;

            @Override
            public void onClick(View v)
            {


                if (!isAnimated){
                    holder.upvote.setSpeed(3f);
                    isAnimated=true;
                    holder.upvote.playAnimation();

                } else {
                    holder.upvote.setSpeed(-1F);
                    isAnimated=false;
                    holder.upvote.setProgress(0.3f);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {

        LottieAnimationView upvote;

        Button showCmt, addcmt;
        TextView name, date, question;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            upvote=itemView.findViewById(R.id.upvote);
            showCmt = itemView.findViewById(R.id.showcomment);
            addcmt = itemView.findViewById(R.id.addcomment);
            name = itemView.findViewById(R.id.nameforum);
            date = itemView.findViewById(R.id.dateforum);
            question = itemView.findViewById(R.id.questionforum);
        }


    }
}
