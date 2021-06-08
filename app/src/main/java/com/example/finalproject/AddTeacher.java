package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.finalproject.GetterSetter.Branch;
import com.example.finalproject.GetterSetter.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTeacher extends AppCompatActivity implements OnItemSelectedListener
{


    List<String> arr;
    EditText tId,tName,tEmail,tpasswWord;
    Button addTeacher;
    String teacherId,teacherName,teacherEmail,teachPassword,teacherBranch;
    Teacher tr = new Teacher();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        tId=findViewById(R.id.tid);
        tName=findViewById(R.id.tname);
        tEmail=findViewById(R.id.temail);
        tpasswWord=findViewById(R.id.tpass);
        addTeacher=findViewById(R.id.addTeacher);

        FirebaseDatabase Teach_database = FirebaseDatabase.getInstance();
        final DatabaseReference myTRef=Teach_database.getReference("Teacher");




        final Spinner spinner =findViewById(R.id.Teacherspin);
        spinner.setOnItemSelectedListener(this);
        arr = new ArrayList<String>();
        FirebaseDatabase Branch_database = FirebaseDatabase.getInstance();
        final DatabaseReference myBRef=Branch_database.getReference("Branch");

        myBRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Branch b = snapshot.getValue(Branch.class);
                        arr.add(b.getBranch_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTeacher.this,R.layout.teacher_spin_layout,arr);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        addTeacher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                teacherId = tId.getText().toString();
                teacherName = tName.getText().toString();
                teacherEmail = tEmail.getText().toString();
                teachPassword = tpasswWord.getText().toString();
                if (teacherId.equals("") || teacherName.equals("") || teacherEmail.equals("") || teachPassword.equals("")) {
                    Toast.makeText(AddTeacher.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    tr.setTeachPassword(teachPassword);
                    tr.setTeacherEmail(teacherEmail);
                    tr.setTeacherId(teacherId);
                    tr.setTeacherName(teacherName);
                    tr.setTeacherBranch(teacherBranch);
                    // myTRef.child(teacherId).push().setValue(tr); todo change the node
                    myTRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int c = 0;
                            if (dataSnapshot.getChildren() != null) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (teacherId.equalsIgnoreCase(snapshot.getValue(Teacher.class).getTeacherId())) {
                                        c = 1;
                                        Toast.makeText(AddTeacher.this, "Student already exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (c == 0) {
                                    myTRef.push().setValue(tr);
                                    Toast.makeText(AddTeacher.this, "Teacher Added Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddTeacher.this, AdminMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AddTeacher.this, "Teacher already exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        teacherBranch = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

}