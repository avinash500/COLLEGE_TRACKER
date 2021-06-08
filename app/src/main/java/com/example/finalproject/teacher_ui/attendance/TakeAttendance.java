package com.example.finalproject.teacher_ui.attendance;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.finalproject.R;
import com.example.finalproject.TeacherMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

public class TakeAttendance extends AppCompatActivity {

    List<String> arr,students;
    ArrayList<AssignTeachGetInput> arr2;
    String teacher_id,batch,section,branch,semester,subject;
    DatabaseReference myRef1;
    FirebaseDatabase database;
    Button back;
    int count =0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        final Spinner spinner = findViewById(R.id.spin);
        back=findViewById(R.id.back);




        arr = new ArrayList<String>();

        arr2= new ArrayList<AssignTeachGetInput>();
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String Tid = intent.getStringExtra("id");
        final DatabaseReference myRef=database.getReference("TeacherSubject").child(Tid);
        batch="blah" ;section="blah";branch="";semester="";subject="";

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

//                Toast.makeText(TakeAttendance.this, "Attendance taken sucessfully", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(TakeAttendance.this, TeacherMainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        final  RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        students=new ArrayList<String>();
        arr.add("Choose your Subject");
        arr2.add(null);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        AssignTeachGetInput b = snapshot.getValue(AssignTeachGetInput.class);
                        arr.add(b.getSubject()+"|"+b.getSemester()+b.getBranch()+b.getSection()+"("+b.getBatch()+")");
                        arr2.add(b);
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(TakeAttendance.this, R.layout.student_spin_layout,arr);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                final String currentDate = sdf.format(new Date());
                if(position!=0)
                {
                    students.clear();
                    final AssignTeachGetInput obj= arr2.get(position);
                    branch=obj.getBranch();
                    batch=obj.getBatch();
                    section=obj.getSection();
                    semester =obj.getSemester();
                    subject=obj.getSubject();
                    myRef1=database.getReference("Sections").child(batch).child(section);
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() !=null) {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    String s = snapshot.getValue(String.class);
                                    students.add(s);
                                }
                            }
                            final DatabaseReference myRef3=database.getReference("Attendance Record").child(batch).child(branch).child(semester).child(subject);

                            myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    count =0;
                                    if(dataSnapshot.getValue() !=null) {
                                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                            String s = snapshot.getKey().toString();
                                            Log.e("DateCurrent",currentDate);
                                            Log.e("DateFetched",s);
                                            if(currentDate.equals(s)){
                                                count =1;
                                            }
                                        }
                                    }
                                    if(count ==0){
                                        recyclerView.setAdapter(new AttendanceAdapter((ArrayList<String>) students,obj));
                                    }
                                    else{
                                        Toast.makeText(TakeAttendance.this, "Attendance Already Taken", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
