package com.example.finalproject.teacher_ui.attendance;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class   AttendanceRecordAdapter extends RecyclerView.Adapter<AttendanceRecordAdapter.AttendanceRecordViewHolder> {
    private ArrayList<AttendanceInformation> list;
    public AttendanceRecordAdapter(ArrayList<AttendanceInformation> list){
        this.list=list;
    }
    @NonNull
    @Override
    public AttendanceRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_layout_view_attendance,parent,false);
        return new AttendanceRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceRecordViewHolder holder, int position) {
        final String sname=list.get(position).getName();
        final String a=list.get(position).getAttendance();
        Log.e("log",""+sname);
        Log.e("log",""+a);
        holder.t1.setText(sname);
        holder.t2.setText(a);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AttendanceRecordViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2;

        public AttendanceRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.stud_name123);
            t2=itemView.findViewById(R.id.attend);

        }
    }
}