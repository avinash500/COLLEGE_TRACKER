package com.example.finalproject.student_ui.sforum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.AddStudent;
import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumAddQuestion extends AppCompatActivity
{
    EditText question;
    Spinner subjectspin;
    Button post;
    List<String> arr1;
    String QUESTION, name, redgno, date,subject;
    DatabaseReference myRef1,myRef2;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add_question);
        question=findViewById(R.id.editTextTextMultiLine);
        post=findViewById(R.id.post);
        subjectspin=findViewById(R.id.spinner);
        arr1 = new ArrayList<String>();
        arr1.add("Machine Learning");
        arr1.add("Operating System");
        arr1.add("Data Structure");
        arr1.add("DBMS");
        arr1.add("Embedded System");
        arr1.add("Software Engineering");
        arr1.add("Miscellaneous");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ForumAddQuestion.this, R.layout.student_spin_layout, arr1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectspin.setAdapter(dataAdapter);

        subjectspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });

        post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                QUESTION=question.getText().toString();
                if(!QUESTION.equals("")) {
                    Intent intent = getIntent();
                    redgno = intent.getStringArrayExtra("message")[0];
                    name = intent.getStringArrayExtra("message")[1];

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                    date = sdf.format(new Date());

                    final Question q  =new Question(QUESTION, name, redgno, date, subject,"-1");
                    database = FirebaseDatabase.getInstance();
                    myRef1=database.getReference("Question").child(subject);
                    myRef2=database.getReference("Student Forum").child(redgno).child(subject);

                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            myRef1.push().setValue(q);
                            if(dataSnapshot.getValue() !=null) {
                                String quesNo = "";
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    quesNo = snapshot.getKey().toString();
                                }
                                int quesnumber= Integer.parseInt(quesNo)+1;
                                q.setQNo(quesnumber+"");
                                myRef1.child(quesnumber+"").push().setValue(q);
                                Toast.makeText(ForumAddQuestion.this,"Question Added Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                q.setQNo(1+"");
                                myRef1.child("1").push().setValue(q);
                                Toast.makeText(ForumAddQuestion.this,"Question Added Successfully", Toast.LENGTH_SHORT).show();


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });


                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            myRef2.push().setValue(q);
                            if(dataSnapshot.getValue() !=null) {
                                String quesNo = "";
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    quesNo = snapshot.getKey().toString();
                                }
                                int quesnumber= Integer.parseInt(quesNo)+1;
                                q.setQNo(quesnumber+"");
                                myRef2.child(quesnumber+"").push().setValue(q);

                            }
                            else{
                                q.setQNo(1+"");
                                myRef2.child("1").push().setValue(q);

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
}