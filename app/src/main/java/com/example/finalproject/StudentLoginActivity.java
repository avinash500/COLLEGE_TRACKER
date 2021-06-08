package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Student;
import com.example.finalproject.GetterSetter.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentLoginActivity extends AppCompatActivity
{
    Button login;

    EditText StudentId, StudentPassword;
    String StudId, StudPassword;
    FirebaseDatabase database;
    DatabaseReference myRef1;
    ProgressBar progressBar;
    ArrayList<Teacher> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        login = findViewById(R.id.SlogInBtn);
        StudentPassword = findViewById(R.id.studentPassword);
        StudentId = findViewById(R.id.studentId);
        progressBar=findViewById(R.id.pBar);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                myRef1 = database.getReference("Student");
                StudId = StudentId.getText().toString();
                StudPassword = StudentPassword.getText().toString();

                if(StudId.isEmpty() || StudPassword.isEmpty())
                {
                    Toast.makeText(StudentLoginActivity.this, "Fill out all the fields ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    readUser();
                }
            }


        });
    }
    public void readUser()
    {
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int c = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (StudId.equals(dataSnapshot1.getKey())) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            Student s = dataSnapshot2.getValue(Student.class);
                            if (StudPassword.equals(s.getPassword())) {
                                c = 1;

                                Intent intent = new Intent(StudentLoginActivity.this, StudentMainActivity.class);
                                String x[] = {s.getRedg_No(), s.getName(), s.getMentorId()};
                                intent.putExtra("message", x);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
                if(c == 0)
                    Toast.makeText(StudentLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

