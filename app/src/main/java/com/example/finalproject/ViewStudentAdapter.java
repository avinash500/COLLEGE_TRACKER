package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.GetterSetter.Student;
import com.example.finalproject.teacher_ui.attendance.AttendanceRecordAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentAdapter.ViewStudentHolder> {
    ArrayList<String> list;
    Set<String> al = new HashSet<String>();
    public ViewStudentAdapter()
    {}

    public ViewStudentAdapter(ArrayList<String> li)
    {
        this.list = li;
    }

    @NonNull
    @Override
    public ViewStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_layout,parent,false);
        return new ViewStudentAdapter.ViewStudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewStudentHolder holder, final int position) {
        final String regno = list.get(position);
        holder.textView.setText(regno);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked())
                {
                    al.add(list.get(position));
                    SharedPreferences shrd = v.getContext().getSharedPreferences("stud", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.clear();
                    editor.putStringSet("sregdno",al);
                    editor.apply();
                }
                else
                {
                    al.remove(list.get(position));
                    SharedPreferences shrd = v.getContext().getSharedPreferences("stud", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.clear();
                    editor.putStringSet("sregdno",al);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewStudentHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        CheckBox checkBox;
        public ViewStudentHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stud_name);
            checkBox = itemView.findViewById(R.id.attendance);
        }
    }
}
