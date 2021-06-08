package com.example.finalproject.teacher_ui.attendance;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private ArrayList<String> dataStudents;
    AssignTeachGetInput a;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int flag=0,c=0;
    int count=0;

    public AttendanceAdapter(ArrayList<String> dataStudents, AssignTeachGetInput a){
        this.dataStudents=dataStudents;
        this.a=a;

    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_layout,parent,false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceViewHolder holder, int position) {

        final String sname=dataStudents.get(position);
        holder.t.setText(sname);
        final String batch,branch,semester,subject;
        batch=a.getBatch();
        branch=a.getBranch();
        semester=a.getSemester();
        subject=a.getSubject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        final String currentDate = sdf.format(new Date());
        final DatabaseReference myRef=database.getReference("Attendance Record").child(batch).child(branch).child(semester).child(subject).child(currentDate).child(sname);
        final DatabaseReference myRef2=database.getReference("Attendance Record").child(batch).child(branch).child(semester).child(subject);

        final DatabaseReference myRef1=database.getReference("Student Attendance Record").child(batch).child(branch).child(semester).child(sname).child(subject);

            myRef.push().setValue("Absent");
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long p;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().toString().equalsIgnoreCase("Total Classes")) {
                            for (DataSnapshot s : snapshot.getChildren()) {
                                p = s.getValue(Long.class);
                                p = p + 1;
                                myRef1.child("Total Classes").removeValue();
                                myRef1.child("Total Classes").push().setValue(p);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


        holder.r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.r.isChecked()) {
                    myRef.removeValue();
                    myRef.push().setValue("Present");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long p;
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.getKey().toString().equalsIgnoreCase("Total Present")){
                                    for(DataSnapshot s: snapshot.getChildren()){
                                        p= s.getValue(Long.class);
                                        p=p+1;
                                        myRef1.child("Total Present").removeValue();
                                        myRef1.child("Total Present").push().setValue(p);
                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    c=1;
                    flag =1;
                }
                else{
                    myRef.removeValue();
                    myRef.push().setValue("Absent");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long p;
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.getKey().toString().equalsIgnoreCase("Total Present")){
                                    for(DataSnapshot s: snapshot.getChildren()){
                                        p= s.getValue(Long.class);
                                        p=p-1;
                                        myRef1.child("Total Present").removeValue();
                                        myRef1.child("Total Present").push().setValue(p);
                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    c=0;
                    flag=0;
                }
                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            if(c ==1){
                                myRef1.child("Total Present").setValue(1);
                            }
                            else{
                                myRef1.child("Total Absent").setValue(1);
                            }

                        }
                      /*  else{
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                              for(DataSnapshot snapshot1:snapshot.getChildren()){
                                  if( snapshot.getKey().toString().equalsIgnoreCase("Total Present") && c ==1){
                                      long p = snapshot1.getValue(Long.class);
                                      p=p+1;
                                      myRef1.child("Total Present").setValue(p);
                                      break;
                                  }
                                  if(snapshot.getKey().toString().equalsIgnoreCase("Total Absent") && c ==0){
                                      long a=snapshot1.getValue(Long.class);
                                      a=a+1;
                                      myRef1.child("Total Absent").setValue(a);
                                      break;
                                  }

                              }
                            }
                        }*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataStudents.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder
    {
        TextView t;
        CheckBox r;
        public AttendanceViewHolder(@NonNull View itemView)
        {
            super(itemView);
            t=itemView.findViewById(R.id.stud_name);
            r=itemView.findViewById(R.id.attendance);
        }


    }

}
