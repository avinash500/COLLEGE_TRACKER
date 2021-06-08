package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.finalproject.GetterSetter.Teacher;
import com.example.finalproject.admin_ui.Notice;
import com.example.finalproject.teacher_ui.attendance.AssignTeachGetInput;
import com.example.finalproject.teacher_ui.attendance.AttendanceInformation;
import com.example.finalproject.teacher_ui.attendance.AttendanceRecordAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AdminSelectUserType extends AppCompatActivity
{
    RecyclerView recyclerView;
    CheckBox box;
    Button send;
    DatabaseReference myRef;
    FirebaseDatabase database;

    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> l1 = new ArrayList<String>();
    ArrayList<String> l2 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_user_type);

        recyclerView=findViewById(R.id.recyclerView6);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        send=findViewById(R.id.sendbutton);
        box=findViewById(R.id.checkBox);

        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String usertype = intent.getStringArrayExtra("Notice")[2];

        if(usertype.equals("Student")) {
            myRef = database.getReference("Student");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        list.add(snapshot.getKey());
                    }
                    recyclerView.setAdapter(new ViewStudentAdapter(list));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(usertype.equals("Teacher"))
        {
            myRef = database.getReference("Teacher");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        Teacher teacher=snapshot.getValue(Teacher.class);
                        list.add(teacher.getTeacherId());
                    }
                    recyclerView.setAdapter(new ViewStudentAdapter(list));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {

            myRef = database.getReference("Student");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    l1.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        l1.add(snapshot.getKey());
                    }
                    l1.addAll(l2);
                    recyclerView.setAdapter(new ViewStudentAdapter(l1));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            myRef = database.getReference("Teacher");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    l2.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        Teacher teacher=snapshot.getValue(Teacher.class);
                        l2.add(teacher.getTeacherId());
                    }
                    l2.addAll(l1);
                    recyclerView.setAdapter(new ViewStudentAdapter(l2));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences getShared = getSharedPreferences("stud",MODE_PRIVATE);
                Set set = getShared.getStringSet("sregdno", new HashSet<String>());
                Intent intent = getIntent();
                String[] m = intent.getStringArrayExtra("Notice");
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                for(Object id: set)
                {
                    String reg = (String)id;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                    String date = sdf.format(new Date());

                    DatabaseReference myref =firebaseDatabase.getReference("Notice").child(reg);
                    Notice notice = new Notice(m[0], m[1],date+"");
                    myref.push().setValue(notice);
                }
                finish();
            }
        });
    }
}